package net.media.training.chatApp;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class ChatMultiServer {
	private static final List<ChatMultiServerThread> threads = new ArrayList<ChatMultiServerThread>();

	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = null;
		boolean listening = true;

		try {
			serverSocket = new ServerSocket(4444);
		} catch (IOException e) {
			System.err.println("Could not listen on port: 4444.");
			System.exit(-1);
		}

		while (listening) {
			new ChatMultiServerThread(serverSocket.accept()).start();
		}

		serverSocket.close();
	}

	public static List<ChatMultiServerThread> getAllThreads() {
		return threads;
	}

	public static void addUser(ChatMultiServerThread user) {
		threads.add(user);
	}

	public static void sendToAll(String message) {
		for (ChatMultiServerThread userThread : threads) {
			userThread.sendMessage(message);
		}
	}

	public static void removeUser(ChatMultiServerThread user) {
		Iterator<ChatMultiServerThread> userIterator = threads.iterator();
		while (userIterator.hasNext()) {
			ChatMultiServerThread userThread = userIterator.next();
			if (userThread.equals(user)) {
				userIterator.remove();
			}
		}
	}

}
