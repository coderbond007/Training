package net.media.training.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EchoServer {
    private final InetAddress addr;
    private final int port;
    private final Map<SocketChannel, List<byte[]>> dataMap;
    private final ByteBuffer buffer = ByteBuffer.allocate(8192);
    private final ByteBuffer newBuffer = ByteBuffer.allocate(8192);
    private Selector selector;

    public EchoServer(InetAddress addr, int port) throws IOException {
        this.addr = addr;
        this.port = port;
        dataMap = new HashMap<SocketChannel, List<byte[]>>();
        startServer();
    }

    private static void log(String s) {
        System.out.println(s);
    }

    public static void main(String[] args) throws Exception {
        new EchoServer(null, 12345);
    }

    private void startServer() throws IOException {
        // create selector and channel
        selector = Selector.open();
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);

        // bind to port
        InetSocketAddress listenAddr = new InetSocketAddress(addr, port);
        serverChannel.socket().bind(listenAddr);
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        log("Echo server ready. Ctrl-C to stop.");

        // processing
        while (true) {
            // wait for events
            selector.select();

            // wakeup to work on selected keys
            Iterator keys = selector.selectedKeys().iterator();
            while (keys.hasNext()) {
                SelectionKey key = (SelectionKey) keys.next();

                // this is necessary to prevent the same key from coming up
                // again the next time around.
                keys.remove();

                if (!key.isValid()) {
                    continue;
                }

                if (key.isAcceptable()) {
                    accept(key);
                } else if (key.isReadable()) {
                    read(key);
                } else if (key.isWritable()) {
                    write(key);
                }
            }
        }
    }

    private void accept(SelectionKey key) throws IOException {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel channel = serverChannel.accept();
        channel.configureBlocking(false);

        // write welcome message
        channel.write(ByteBuffer.wrap("Welcome, this is the echo server\r\n".getBytes("US-ASCII")));

        Socket socket = channel.socket();
        SocketAddress remoteAddr = socket.getRemoteSocketAddress();
        log("Connected to: " + remoteAddr);

        // register channel with selector for further IO
        dataMap.put(channel, new ArrayList<byte[]>());
        channel.register(selector, SelectionKey.OP_READ);
    }

    private void read(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();


        int numRead = -1;
        try {
            numRead = channel.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (numRead == -1) {
            dataMap.remove(channel);
            Socket socket = channel.socket();
            SocketAddress remoteAddr = socket.getRemoteSocketAddress();
            log("Connection closed by client: " + remoteAddr);
            channel.close();
            key.cancel();
            return;

        }

        CharBuffer charBuffer = buffer.asCharBuffer();
        buffer.flip();
        while (buffer.hasRemaining()) {
            char c = (char) buffer.get();
            newBuffer.putChar(c);
            if (c == '\n') {
                byte[] data = new byte[newBuffer.capacity() - newBuffer.remaining()];
                System.arraycopy(newBuffer.array(), 0, data, 0, newBuffer.capacity() - newBuffer.remaining());
                doEcho(key, data);
                newBuffer.clear();
            }
        }
        byte[] data = new byte[numRead];
        System.arraycopy(buffer.array(), 0, data, 0, numRead);

        // write back to client
        //doEcho(key, data);
        buffer.clear();
    }

    private void write(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        List<byte[]> pendingData = dataMap.get(channel);
        Iterator<byte[]> items = pendingData.iterator();
        while (items.hasNext()) {
            byte[] item = items.next();
            items.remove();
            channel.write(ByteBuffer.wrap(item));
        }
        channel.register(selector, SelectionKey.OP_READ);
        //key.interestOps(SelectionKey.OP_READ);
    }

    private void doEcho(SelectionKey key, byte[] data) throws ClosedChannelException {
        SocketChannel channel = (SocketChannel) key.channel();
        List<byte[]> pendingData = dataMap.get(channel);
        pendingData.add(data);
        channel.register(selector, SelectionKey.OP_WRITE);
        //key.interestOps(SelectionKey.OP_WRITE);
    }
}
