package net.media.training.chatApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class ChatClientInput implements Runnable {
	PrintWriter out;

	public ChatClientInput(PrintWriter out) {
		this.out = out;

	}

//	@Override
	public void run() {
		String fromUser;
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(
				System.in));

		try {
			while (true) {
				fromUser = stdIn.readLine();
			    if (fromUser != null) {
	                out.println(fromUser);
			    }
				

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
