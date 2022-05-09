import incapsulatedCommands.ExecuteScript;
import utils.Switch;
import exceptions.*;
import utils.CollectionManager;
import utils.Command;
import utils.InteractiveCollector;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CLIENT {
    private static int PORT = 8888;
    private static SocketAddress addr;
    private static SocketChannel channel;
    private static ByteBuffer byteBuffer;

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Welcome to my console application");
        System.out.println("Enter any command to begin work with console. To see the list of available commands type \"help\"\n");
        Command command = null;
        //if (!(file.exists() && file.canWrite() && file.canRead() && file.isFile())) throw new IOException();
        ArrayList<Command> commands = new ArrayList<>();
        Switch sw = new Switch(new InteractiveCollector(), new CollectionManager(), commands);
        Scanner scanner = new Scanner(System.in);
        try {
            connect();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("Trying to reconnect");
            Thread.sleep(15000);
            try {
                connect();
            } catch (IOException ee) {
                System.out.println("Server is not available, try another time");
                System.exit(0);
            }
        }
        while (true) {
            try {
                String s = scanner.nextLine();
                command = sw.interpret(s);
                if (command instanceof ExecuteScript) {
                    System.out.println(command.execute(null));
                    for (Command com : commands) {
                        sendToServer(com);
                        String str = getResponse();
                        System.out.println(str);
                        if ("Sorry, there's a lot of elements here, can't add more".equals(str)) break;
                        if ("Exit".equals(com.getDescription())) System.exit(0);
                    }
                } else {
                    sendToServer(command);
                    String response = getResponse();
                    System.out.println(response);
                    if (command.getDescription().equals("Exit")) System.exit(0);
                    //oos.writeObject(command);
                }
            } catch (FileNotFoundException e) {
                System.out.println("Can not find the file");
                break;
            } catch (IOException e) {
                System.out.println(e.getMessage());
                System.out.println("Trying to reconnect");
                Thread.sleep(15000);
                try {
                    connect();
                } catch (Exception ee) {
                    System.out.println("Server is not available, try another time");
                    System.exit(0);
                }
            } catch (NumberFormatException | InputMismatchException e) {
                System.out.println("Parameter is incorrect");
            } catch (IncorrectValueException e) {
                System.out.println(e.getMessage());
            } catch (NullArgumentException e) {
                System.out.println("Argument cannot be null");
            } catch (UnderZeroValueException e) {
                System.out.println("Parameter cannot be under zero");
            } catch (IllegalArgumentException e) {
                System.out.println("Parameter must match with allowed values in enum");
            } catch (DateTimeException e) {
                System.out.println("Something happened wrong, you probably entered incorrect parameter in date or time");
            } catch (MaxValueException e) {
                System.out.println("Too large number");
            } catch (UnknownCommandException e) {
                System.out.println("Unknown command");
            } catch (NoSuchElementException e) {
                System.out.println("end of file reached");
                break;
            } catch (Exception e) {
                System.out.println("Please try again");
            }
        }
    }

    public static void connect() throws UnknownHostException, IOException {
        addr = new InetSocketAddress(InetAddress.getLocalHost(), PORT);
        channel = SocketChannel.open();
        channel.connect(addr);
        if (channel.isConnected()) System.out.println("Connection established");
    }

    //IOException для каждого по разному
    public static void sendToServer(Command command) throws IOException {
        byteBuffer = ByteBuffer.allocate(3000000);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(byteArrayOutputStream);
        oos.writeObject(command);
        oos.flush();
        byte[] bytes = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.flush();
        byteBuffer.put(bytes);
        byteBuffer.flip();
        channel.write(byteBuffer);
    }

    public static String getResponse() throws IOException {
        byteBuffer = ByteBuffer.allocate(3000000);
        InputStream inputStream = Channels.newInputStream(channel);
        inputStream.read(byteBuffer.array());
        return new String(byteBuffer.array()).trim();
    }

}
