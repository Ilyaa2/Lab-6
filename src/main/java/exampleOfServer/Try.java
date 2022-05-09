package exampleOfServer;

import java.io.IOException;
import java.util.Scanner;

public class Try {
    //DataInputStream in = new DataInputStream(System.in);
    Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        //DataInputStream in = new DataInputStream(System.in);
        //Scanner sc = new Scanner(System.in);
        //sc.remove();

            int inChar;
            String s = "";
            try {
                inChar = System.in.read();
                System.out.println(",kjr");
                while (System.in.available() > 0) {
                    s += (char) inChar;
                    inChar = System.in.read();
                    System.out.println(s);
                }
            } catch (IOException e) {
                System.out.println("Ошибка");
            }

    }
}


