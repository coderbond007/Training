package net.media.training.chatApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class ChatMultiServerThread extends Thread {
	private Socket socket;

	public ChatMultiServerThread(Socket socket) {
		super("KKMultiServerThread");
		this.socket = socket;
	}

	@Override
	public void run() {

		try {
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket
					.getInputStream()));

			String inputLine, outputLine;
			ChatProtocol kkp = new ChatProtocol();
			outputLine = kkp.processInput(null, this);
			out.println(outputLine);

			while ((inputLine = in.readLine()) != null) {
				outputLine = kkp.processInput(inputLine, this);
				out.println(outputLine);
				if ("Bye".equals(outputLine)) {
					break;
				}
			}
			out.close();
			in.close();
			socket.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendMessage(String msg) {
		try {
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			out.println(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
