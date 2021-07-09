package net.media.training.knock.multiThreaded.multi;

import java.io.IOException;
import java.net.ServerSocket;

public class KKMultiServer {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        boolean listening = true;

        try {
            serverSocket = new ServerSocket(4444);

        } catch (IOException e) {
            System.err.println("Could not listen on port: 4444.");
            System.exit(-1);
        }
        System.out.println("Listening on 4444");
        while (listening) {
            new KKMultiServerThread(serverSocket.accept()).start();
        }

        serverSocket.close();
	}

}
