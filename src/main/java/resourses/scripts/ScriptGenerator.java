package resourses.scripts;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ScriptGenerator {
    public static void main(String[] args) {
        File file = new File("./src/script3.txt");

        String str1 = "add\n";
        String str3 =
                "6\n" +
                "7\n" +
                "23\n" +
                "20\n" +
                "DISTANCE_EDUCATION\n" +
                "FIRST\n" +
                "Саша\n" +
                "2003\n" +
                "JANUARY\n" +
                "21\n" +
                "11\n" +
                "11\n" +
                "11\n" +
                "182\n" +
                "123456\n" +
                "ORANGE\n";
        try(FileWriter fileWriter = new FileWriter(file)){
            for (int i = 0; i < 500; i++) {
                String str2 = "P31"+i+"\n";
                fileWriter.write(str1+str2+str3);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*
    import сollectionData.MyCollection;
import utils.Command;
import utils.Loader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Iterator;
import java.util.Set;

public class SERVER {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //final String FILE_PATH = "src/MyData.tld";
        final String FILE_PATH=System.getenv().get("LAB5");
        MyCollection myCollection = null;
        try {
            myCollection = Loader.load(FILE_PATH);
        } catch(NullPointerException e){
            System.out.println("Environment variable hasn't been initialized");
            System.exit(0);
        }
        if (myCollection ==null) System.exit(0);
        Charset charset = Charset.forName("UTF-8");
        CharsetEncoder encoder = charset.newEncoder();

        ByteBuffer buffer = ByteBuffer.allocate(99278);
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
                    }catch (IOException e){
                        key.cancel();
                        client.close();
                        continue;
                    }
                    buffer.flip();
                    Command request= (Command) new ObjectInputStream(new ByteArrayInputStream(buffer.array())).readObject();
                    buffer.clear();
                    if("Exit".equals(request.getDescription())){
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
    }
}
     */

}
