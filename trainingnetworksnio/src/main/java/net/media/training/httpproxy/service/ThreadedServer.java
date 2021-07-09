package net.media.training.httpproxy.service;

import org.apache.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ThreadedServer extends Thread
{
    static Logger log = Logger.getLogger( HttpProxyServer.class );
    static boolean isRunning = false;
    String webServerIp = null;
    int port = 0;
    Socket socket = null;

    public ThreadedServer( Socket socket )
    {
        this.socket = socket;
    }

    public void run()
    {
        try {
            executeRequest();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void executeRequest()
    {
        BufferedReader bufferedReader = null;
        BufferedOutputStream bufferedOutputStream = null;

        try
        {
            validateRequest( socket );

            bufferedReader = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );
            bufferedOutputStream = new BufferedOutputStream( socket.getOutputStream() );

            String currentLine;
            int line = 0;
            String urlOfHttpConnection = null;
            String action = null;
            int contentLength = 0;
            StringBuffer data = new StringBuffer();

            while ((currentLine = bufferedReader.readLine()) != null && !currentLine.isEmpty())
            {
                System.out.println(currentLine);
                line++;

                if ( line == 1 )
                {
                    if ( currentLine.indexOf( "GET" ) == 0 )
                    {
                        urlOfHttpConnection = getHttpUrl( currentLine );
                        action = "GET";
                    }
                    else if ( currentLine.indexOf( "POST" ) == 0 )
                    {
                        urlOfHttpConnection = getHttpUrl( currentLine );
                        action = "POST";
                    }
                }
                else
                {
                    data.append( currentLine ).append( "\r\n" );
                }

                if ( currentLine.startsWith("Content-Length: ") )
                {
                    contentLength = Integer.parseInt( currentLine.substring( "Content-Length: ".length() ) );
                    break;
                }
            }

            if (action == null || "null".equalsIgnoreCase(action)) {
                return;
            }

            if ("POST".equalsIgnoreCase(action) && contentLength != 0)
            {
                data.append( getPostData( bufferedReader , contentLength ) );
            }

            fetchSiteContent( urlOfHttpConnection, action, bufferedOutputStream, data );
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if ( bufferedReader != null )
            {
                try
                {
                    bufferedReader.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }

            if ( bufferedOutputStream != null )
            {
                try
                {
                    bufferedOutputStream.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public void fetchSiteContent( String url, String action, BufferedOutputStream bufferedOutputStream, StringBuffer data ) throws IOException
    {
        String host = getUrlHost( url );
        String queryString = getQueryString( url );
        Socket _socket = createSocket( host );

        String header = getHeaders( queryString, action,  data.toString() );

        log.info( "<==HEADER START==>\n" + header + "\n<===HEADER END===>");

        OutputStream outStream = new BufferedOutputStream( _socket.getOutputStream(  ) , 1 );
        outStream.write( header.getBytes("UTF-8") );

        byte[] inputData = getDataFromInputStream( _socket.getInputStream() );

        if ( inputData != null )
        {
            bufferedOutputStream.write(inputData);
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
        }
    }

    private String getHttpUrl( String header )
    {
        if (header == null || header.isEmpty())
        {
            return null;
        }

        Pattern pattern = Pattern.compile("[^\\s]*\\s+([^\\s]*)\\s+HTTP/1.[0|1]");

        Matcher matcher = pattern.matcher( header );

        String httpUrl = null;

        if ( matcher.find() )
        {
            httpUrl = matcher.toMatchResult().group( 1 );
        }
        System.out.println("=> " + httpUrl);
        return httpUrl;
    }

    private static void validateRequest(Socket socket)
    {
            InetSocketAddress socketAddress = (InetSocketAddress)socket.getRemoteSocketAddress();

            log.info("Request coming from :: " + socketAddress.getAddress().getHostAddress());

            /*
            if(!addressVect.contains(socketAddress.getAddress().getHostAddress()))
            {
                log.info("Request from invalid server :: " + socketAddress.getAddress().getHostAddress());
                socket.shutdownInput();
            }
            */
    }

    private static String getPostData(Reader reader, int contentLength) throws IOException
    {
        StringBuilder requestContent = new StringBuilder();

        for ( int i = 0 ; i <= contentLength + 1 ; i++ )
        {
           requestContent.append( ( char ) reader.read() );
        }

        return requestContent.toString();
    }

    private static String getUrlHost(String url) throws MalformedURLException
    {
        if( url == null )
        {
            return null;
        }

        return new URL( url ).getHost();
    }

    private static String getQueryString(String _url) throws MalformedURLException
    {
        String queryString;

        URL url = new URL( _url );

        if ( url.getQuery() != null )
        {
            queryString = url.getPath() + '?' + url.getQuery();
        }
        else if( url.getPath() != null )
        {
            queryString = url.getPath();
        }
        else
        {
            queryString = "/";
        }

        return queryString;
    }

    private Socket createSocket( String host ) throws IOException
    {
        Socket socket;

        if ( parseData( webServerIp ) != null && port != 0 )
        {
            log.info( "Address == " + webServerIp + " : " + port );
            socket = new Socket( webServerIp , port );
        }
        else
        {
            InetAddress address = InetAddress.getByName( host );
            log.info( "Address == " + address + " : " + "80" );

            socket = new Socket( address , 80 );
        }

        return socket;
    }

    private static String getHeaders(String queryString, String action, String header)
    {
        header += "\r\n";

        header = action + ' ' + queryString + " HTTP/1.1\r\n" + header;

        return header;
    }

    private static byte[] getDataFromInputStream(InputStream inputStream)
            throws IOException
    {
        // Byte Array to store the contents of the file
        byte[] putputBuffer = null;

        BufferedInputStream bufferedInputStream = null;

        try
        {
            bufferedInputStream = new BufferedInputStream( inputStream );

            int available = bufferedInputStream.available();
            if (available == 0)
            {
                available = 1024;
            }

            // temp byte array, used if the data is broken up into multiple small parts
            byte[] tempByteArray = new byte[available];

            // ArrayList to store all the temp byte arrays
            ArrayList data = new ArrayList();
            int length;

            while ((length = bufferedInputStream.read(tempByteArray, 0, available)) > -1)
            {
                if (length != tempByteArray.length)
                {
                    byte[] trunc = new byte[length];
                    System.arraycopy(tempByteArray, 0, trunc, 0, length);
                    data.add(trunc);
                }
                else
                {
                    data.add(tempByteArray);
                }

                available = bufferedInputStream.available();

                if (available == 0)
                {
                    available = 1024;
                }

                tempByteArray = new byte[available];
            }

            length = 0;

            if (data.size() == 1)
            {
                return (byte[]) data.get(0);
            }

            // getting the actual length of the file downloaded
            for (Object aData : data)
            {
                length += ((byte[]) aData).length;
            }

            putputBuffer = new byte[length]; // final output buffer
            length = 0;

            // copy the contents of all the temp arrays to the final buffer
            for (Object aData1 : data)
            {
                tempByteArray = (byte[]) aData1;
                System.arraycopy(tempByteArray, 0, putputBuffer, length, tempByteArray.length);
                length += tempByteArray.length;
            }
        }
        finally
        {
            if (bufferedInputStream != null)
            {
                try
                {
                    bufferedInputStream.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }

        bufferedInputStream.close();
        return putputBuffer;
    }

    public String parseData(String str)
    {
        // cat.info("In parseData");
        if (str == null || str.trim().isEmpty() || "null".equalsIgnoreCase(str.trim()))
        {
            // cat.info("Leaving parseData with null");
            return null;
        }
        else
        {
            // cat.info("Leaving parseData with string");
            return str.trim();
        }
    }
}

