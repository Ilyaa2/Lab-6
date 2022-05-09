package exampleOfServer;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;

public class clie {
    private static int PORT = 8888;
    private static SocketAddress addr;
    private static SocketChannel channel;
    private static ByteBuffer byteBuffer;

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        connect();
        while (true) {
            Human human = new Human("client", 26);
            exchange(human);
            Human response = getResponse();
            System.out.println(response.toString());
        }
    }

    public static void connect() throws UnknownHostException, IOException {
        addr = new InetSocketAddress(InetAddress.getLocalHost(), PORT);
        channel = SocketChannel.open();
        channel.connect(addr);
        if (channel.isConnected()) System.out.println("Connection established");
    }

    //IOException для каждого по разному
    public static void exchange(Human human) throws IOException {
        byteBuffer = ByteBuffer.allocate(3000000);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(byteArrayOutputStream);
        oos.writeObject(human);
        oos.flush();
        byte[] bytes = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.flush();
        byteBuffer.put(bytes);
        byteBuffer.flip();
        channel.write(byteBuffer);
    }

    public static Human getResponse() throws IOException, ClassNotFoundException {
        byteBuffer = ByteBuffer.allocate(3000000);
        InputStream inputStream = Channels.newInputStream(channel);
        Human human = (Human) new ObjectInputStream(inputStream).readObject();
        return human;
    }
}

