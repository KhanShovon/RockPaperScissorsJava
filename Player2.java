import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class Player2 {
    public static Socket socket = null;

    public static void main(String[] args) {
        try {
            socket = new Socket("localhost", 20);
            System.out.println("\n\nConnected to Server\n" + "Socket: " + socket.getInetAddress() + ":" + socket.getPort());
            System.out.println("===========================================");

        } catch (IOException e) {
            System.out.print("Connection to network can not be established");
        }
        BufferedReader in;
        PrintWriter out;
        Scanner consoleInput = new Scanner(System.in);
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("Server: " + in.readLine());
            System.out.print("Enter player name: ");
            String you = consoleInput.nextLine();
            out.println(you);

            //Player getting game rules
            String gameRules = in.readLine();
            System.out.println("Server: " + gameRules);
            System.out.println("=======================================================================");


            while (true) {
                System.out.print(you + ": ");
                String userInput = consoleInput.nextLine();
                switch (userInput) {
                    case "1" -> System.out.println(you + ": Rock\n");
                    case "2" -> System.out.println(you + ": Paper\n");
                    case "3" -> System.out.println(you + ": Scissors\n");
                    default -> System.out.println(you + ": Invalid input!\n");
                }

                out.println(userInput);

                System.out.print("Server: ");
                String serverTurn = in.readLine();
                System.out.println(serverTurn);

                switch (serverTurn) {
                    case "1" -> System.out.println("Server: Rock\n");
                    case "2" -> System.out.println("Server: Paper\n");
                    case "3" -> System.out.println("Server: Scissors\n");
                    default -> System.out.println("Server: Can't play unless your input is valid!\n");
                }

                System.out.print("Result: ");
                System.out.println(in.readLine());
                System.out.println("=================================================");


            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}