package net.media.training.nio;

import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;


public class ConnectAsync {

    private static void doSomethingUseful() {
        System.out.println("Doing something useful...");
    }

    public static void main(String[] args) throws Exception {
        String host = "www.google.com";
        int port = 80;

        System.out.println("start connect");
        InetSocketAddress address = new InetSocketAddress(host, port);
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(address);

        while (!socketChannel.finishConnect()) {
            doSomethingUseful();
        }

        System.out.println("Connection established!!!");
        socketChannel.close();
    }
}
