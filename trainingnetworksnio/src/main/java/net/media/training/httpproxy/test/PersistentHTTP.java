package net.media.training.httpproxy.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;


public class PersistentHTTP
{
    public PersistentHTTP() throws IOException
    {
        Socket connection = null;
        connection = new Socket("askforsolution.com", 80);
        OutputStream con_out = connection.getOutputStream();
        InputStream con_in  = connection.getInputStream();
        PrintWriter out_writer = new PrintWriter(con_out, false);
        out_writer.write("GET / HTTP/1.1\r\n");
        out_writer.write("Host: askforsolution.com\r\n");
        out_writer.write("Connection: keep-alive\r\n");
        out_writer.write("Keep-Alive: timeout=300, max=100\r\n");
        out_writer.write("\r\n");
        out_writer.write( 0 );
        out_writer.flush();

        // If we were not interpreting this data as a character stream, we might need to adjust byte ordering here.
        InputStreamReader isr_reader = new InputStreamReader(con_in);
        char[] streamBuf = new char[8192];
        int amountRead;
        StringBuilder receivedData = new StringBuilder();
        while((amountRead = isr_reader.read(streamBuf)) > 0)
        {
            receivedData.append(streamBuf, 0, amountRead);
        }

        System.out.println("Received Data :: " + receivedData);
        // Response is processed here.

        if(!connection.isClosed())
        {
            System.out.println("Connection Still Open...");
            out_writer = new PrintWriter(con_out, false);
            out_writer.write("GET / HTTP/1.1\r\n");
            out_writer.write("Host: askforsolution.com\r\n");
            out_writer.write("Connection: close\r\n");
            out_writer.write("\r\n");
            out_writer.write( 0 );
            out_writer.flush();

            System.out.println("Sent!!!");

            isr_reader = new InputStreamReader(con_in);
            streamBuf = new char[8192];
            receivedData.setLength(0);

            while((amountRead = isr_reader.read(streamBuf)) > 0)
            {
                System.out.println("HII " + amountRead);
                if (amountRead > 0)
                {
                    receivedData.append(streamBuf, 0, amountRead);
                }
            }
            System.out.println("Received Data :: " + receivedData);
        }
        // Process response here
    }

    public static void main(String[] args) throws IOException
    {
        System.out.println("Connecting...");
        new PersistentHTTP();
    }

}
