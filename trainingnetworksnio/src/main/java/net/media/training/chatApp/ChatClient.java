package net.media.training.chatApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public final class ChatClient {
    private static final String hostName = "127.0.0.1";

    public static void main(String[] args) throws IOException {

        Socket kkSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            kkSocket = new Socket(hostName, 4444);
            out = new PrintWriter(kkSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + hostName);
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;
        //String fromUser;

        Thread t = new Thread(new ChatClientInput(out));
        t.start();
		while ((fromServer = in.readLine()) != null) {
            System.out.println("Server: " + fromServer);
            if ("Bye.".equals(fromServer)) {
                break;
            }

        }

        out.close();
        in.close();
        stdIn.close();
        kkSocket.close();
    }
}