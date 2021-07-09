package net.media.training.nio;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;


public class SelectSockets {

    private static final int PORT_NUMBER = 12345;
    private final ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

    public static void main(String[] args) throws Exception {

        new SelectSockets().go();
    }

    private static void registerChannel(Selector selector, SelectableChannel channel, int ops) throws Exception {

        if (channel == null) {
            return;
        }
        channel.configureBlocking(false);
        channel.register(selector, ops);
    }

    public void go() throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();
        Selector selector = Selector.open();

        serverSocket.bind(new InetSocketAddress(PORT_NUMBER));
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {

            System.out.println("Waiting...");
            int n = selector.select();

            if (n == 0) {
                System.out.println("Countinue...");
                continue;
            }

            Iterator iterator = selector.selectedKeys().iterator();

            while (iterator.hasNext()) {
                SelectionKey key = (SelectionKey) iterator.next();

                if (key.isAcceptable()) {
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    server.configureBlocking(false);
                    SocketChannel channel = server.accept();
                    System.out.println("Accpeting conneciton form client " + channel.socket().getRemoteSocketAddress());

                    registerChannel(selector, channel, SelectionKey.OP_READ);
                    sayHello(channel);
                }

                if (key.isReadable()) {
                    System.out.println("Readable...");
                    readDataFromSocket(key);
                }

                iterator.remove();
            }
        }
    }

    private void readDataFromSocket(SelectionKey key) throws Exception {

        SocketChannel socketChannel = (SocketChannel) key.channel();
        int count;
        buffer.clear();

        while ((count = socketChannel.read(buffer)) > 0) {
            buffer.flip();

            while (buffer.hasRemaining()) {
                socketChannel.write(buffer);
            }

            buffer.clear();
        }

        if (count < 0) {
            socketChannel.close();
        }

    }

    private void sayHello(SocketChannel socketChannel) throws Exception {

        buffer.clear();
        buffer.put("Hi there!".getBytes());
        buffer.flip();
        socketChannel.write(buffer);
    }
}
