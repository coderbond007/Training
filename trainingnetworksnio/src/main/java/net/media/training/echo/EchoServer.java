package net.media.training.echo;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
	private static final int BUFFER_SIZE = 256;
	private static final int listenPort = 14444;

	public static void main(String[] args) throws Exception {
		ServerSocket serverSock = new ServerSocket(listenPort, 50 ,InetAddress.getByName("172.16.164.112"));
//		ServerSocket serverSock = new ServerSocket(listenPort, 50 ,InetAddress.getLocalHost());
		System.out.println("listening on port " + listenPort);
		Socket sock = serverSock.accept();
		System.out.println("new connection " + sock);
		System.out.println("local port" + sock.getLocalPort());
		System.out.println("port" + sock.getPort());
		System.out.println("local address: "+ sock.getLocalAddress());
		System.out.println("remote address: "+ sock.getRemoteSocketAddress());

		InputStream sockIn = sock.getInputStream();
		OutputStream sockOut = sock.getOutputStream();
		byte[] buffer = new byte[BUFFER_SIZE];
		while (true) {
			Thread.sleep(50);
			int count = 0;
			if ((count = sockIn.available()) > 0) {
				if (count >= buffer.length) {
					count = buffer.length;
				}
				count = sockIn.read(buffer, 0, count);
				System.out.println("echoing " + count + " bytes");
				sockOut.write(buffer, 0, count);
				sockOut.flush();
			}
		}
	}

}
