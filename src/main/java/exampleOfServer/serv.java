package exampleOfServer;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Iterator;
import java.util.Set;

public class serv {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Charset charset = Charset.forName("UTF-8");
        CharsetEncoder encoder = charset.newEncoder();

        ByteBuffer buffer = ByteBuffer.allocate(3000000);
        Selector selector = Selector.open();
        ServerSocketChannel server = ServerSocketChannel.open();
        server.socket().bind(new java.net.InetSocketAddress(8888));
        server.configureBlocking(false);
        SelectionKey serverkey = server.register(selector, SelectionKey.OP_ACCEPT);
        for (; ; ) {
            selector.select();
            Set keys = selector.selectedKeys();
            for (Iterator i = keys.iterator(); i.hasNext(); ) {
                SelectionKey key = (SelectionKey) i.next();
                i.remove();
                if (key == serverkey) {
                    if (key.isAcceptable()) {
                        SocketChannel client = server.accept();
                        client.configureBlocking(false);
                        SelectionKey clientkey = client.register(selector, SelectionKey.OP_READ);
                    }
                } else {
                    SocketChannel client = (SocketChannel) key.channel();
                    if (!key.isReadable()) continue;
                    try {
                        int bytesread = client.read(buffer);
                        if (bytesread == -1) throw new IOException();
                    } catch (IOException e) {
                        key.cancel();
                        client.close();
                        continue;
                    }
                    buffer.flip();
                    Human human = (Human) new ObjectInputStream(new ByteArrayInputStream(buffer.array())).readObject();
                    buffer.clear();
                    System.out.println(human.toString());
                    human.name = "сервер";
                    buffer = ByteBuffer.allocate(3000000);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(byteArrayOutputStream);
                    oos.writeObject(human);
                    oos.flush();
                    byte[] bytes = byteArrayOutputStream.toByteArray();
                    byteArrayOutputStream.flush();
                    buffer.put(bytes);
                    buffer.flip();
                    client.write(buffer);
                }
            }
        }
    }
}

