import сollectionData.MyCollection;
import utils.CollectionManager;
import utils.Command;
import utils.Loader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

public class SERVER {
    final static String FILE_PATH = "src/MyData.tld";
    //final String FILE_PATH=System.getenv().get("LAB5");

    static volatile MyCollection myCollection = null;

    public static void main(String[] args) {
        new Serv().start();
        new ReadConsoleAndSave().start();
    }

    static class ReadConsoleAndSave extends Thread {
        @Override
        public void run() {
            Scanner scanner = new Scanner(System.in);
            CollectionManager collectionManager = new CollectionManager();
            while (true) {
                try {
                    if ("save".equals(scanner.nextLine())){
                        collectionManager.save(myCollection);
                        System.out.println("Collection was saved successfully");
                    }
                } catch (NoSuchElementException e) {
                    System.out.println("EOF not allowed");
                    System.exit(0);
                }
            }
        }
    }

    static class Serv extends Thread {
        @Override
        public void run() {
            try {
                //final String FILE_PATH = "src/MyData.tld";
                //final String FILE_PATH=System.getenv().get("LAB5");

                //myCollection = null;
                try {
                    myCollection = Loader.load(FILE_PATH);
                } catch (NullPointerException e) {
                    System.out.println("Environment variable hasn't been initialized");
                    System.exit(0);
                }
                if (myCollection == null) System.exit(0);
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
                            Command request = (Command) new ObjectInputStream(new ByteArrayInputStream(buffer.array())).readObject();
                            buffer.clear();
                            if ("Exit".equals(request.getDescription())) {
                                String responce = request.execute(myCollection);
                                client.write(encoder.encode(CharBuffer.wrap(responce)));
                                key.cancel();
                                client.close();
                            } else {
                                String response = request.execute(myCollection);
                                client.write(encoder.encode(CharBuffer.wrap(response)));
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}