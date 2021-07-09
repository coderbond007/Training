package net.media.training.httpproxy.service;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class HttpProxyServer
{
    static Logger log = Logger.getLogger( HttpProxyServer.class );
    private static final int HTTP_PORT = 8080;
    Vector<String> addressVect = null;

    public HttpProxyServer() throws IOException
    {
    }

    public void runServer() throws Exception
    {
        ServerSocket server;
        server = new ServerSocket( HTTP_PORT );
        log.info( "Http server listening on :: " + HTTP_PORT );

        while ( true )
        {
            try
            {
                Socket socket = server.accept();
                log.info( "Accepted connection from: " + socket.getRemoteSocketAddress() + ", at: " + socket.getLocalSocketAddress() );
                ThreadedServer threadedServer = new ThreadedServer( socket );
                log.info("Starting execution in separate thread...");
                threadedServer.start();
            }
            catch(Exception exception)
            {
                log.error( exception.getMessage(), exception );
            }
        }
    }

    public static void main(String [] args) throws Exception
    {
        log.info("Running proxy server...");
        new HttpProxyServer().runServer();
    }
}

