package net.media.training.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;


public class ChannelAccept {

    private static final String GREETING = "Hello world!!!";

    public static void main(String[] args) throws Exception {
        int port = 12345;

        ByteBuffer buffer = ByteBuffer.wrap(GREETING.getBytes());
        //Its open but not connected
        //Attempting an I/O operation on an unconnected SocketChannel will throw a NotYetConnectedException
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false);

        while (true) {

            System.out.println("Waiting for client...");
            SocketChannel socketChannel = serverSocketChannel.accept();

            if (socketChannel == null) {
                //No connections, snooze for a while
                Thread.sleep(10000);
            } else {
                System.out.println("Incoming connection from: " + socketChannel.socket().getRemoteSocketAddress());

                buffer.rewind();
                socketChannel.write(buffer);
                socketChannel.close();
            }
        }
    }
}
