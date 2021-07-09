package net.media.training.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class EchoClient {
	private static final String targetMachine = "172.16.164.112";

	public static void main(String[] args) throws IOException {
		Socket echoSocket = null;
		PrintWriter out = null;
		BufferedReader in = null;

		try {
			echoSocket = new Socket(InetAddress.getByName(targetMachine), 12345, InetAddress.getLocalHost(), 15003);
//			echoSocket = new Socket(InetAddress.getLocalHost(), 14444, InetAddress.getLocalHost(), 15003);
//			Socket s1 = new Socket(InetAddress.getByName(targetMachine), 4444, InetAddress.getByName(targetMachine), 14050);
			out = new PrintWriter(echoSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
			System.out.println("Fine here");
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host: " + targetMachine);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for " + "the connection to: " + targetMachine);
			System.exit(1);
		}
		System.out.println("local" + echoSocket.getLocalPort());
		System.out.println("port" + echoSocket.getPort());
		System.out.println(echoSocket.getRemoteSocketAddress().toString());

		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		String userInput;

		while ((userInput = stdIn.readLine()) != null && !userInput.isEmpty()) {
			out.println(userInput);
			System.out.println("echo: " + in.readLine());
		}
		out.println("HeyyyyHeyyyyHeyyyyHeyyyyHeyyyy");
		out.close();
		in.close();
		stdIn.close();
	}

}
