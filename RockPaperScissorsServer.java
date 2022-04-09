import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class RockPaperScissorsServer {

    private static final int port = 20;
    private static final Socket socket = null;
    public static LinkedList<clientThread> clientList = new LinkedList<>();
    private static ServerSocket server = null;

    public static void main(String[] args) {

        //Create IO Objects
        BufferedReader in;
        PrintWriter out;
        Scanner consoleInput = new Scanner(System.in);

        //for random number
        Random rand = new Random();


        try {
            System.out.println("Server is starting ...");
            server = new ServerSocket(port);
            System.out.println("Server has started");
            System.out.println("===========================================");

            new Thread() {
                @Override
                public void run() {
                    super.run();
                    Scanner consoleInput = new Scanner(System.in);
                    while (true) {
                        //System.out.println("Server: ");
                        String serverInput = consoleInput.nextLine();


                        for (int i = 0; i < clientList.size(); i++) {
                            //System.out.println(serverInput);
                            clientList.get(i).out.println(serverInput);
                        }
                    }
                }
            }.start();
            while (true) {
                clientList.add(new clientThread(server.accept()));
                clientList.peekLast().start();
                System.out.print("Client has been connected\n");
                System.out.println("===========================================");
            }
        } catch (IOException e) {
            System.out.println("Can not listen to port: " + port);
        }
    }
}

class clientThread extends Thread {
    Socket clientSocket;
    BufferedReader in;
    PrintWriter out;
    //Scanner consoleInput = new Scanner(System.in);

    //for random number
    Random rand = new Random();

    public clientThread(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.in = in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.out = new PrintWriter(clientSocket.getOutputStream(), true);


    }

    @Override
    public void run() {
        try {

            out.println("Rock-Paper-Scissors Server");
            String name = in.readLine();
            //Server sending the game rules to Player
            out.println("Hi " + name + ", Enter 1 for Rock, 2 for Paper and 3 for Scissors");
            System.out.println("Player Name: " + name);
            System.out.println("===========================================");

            String result;

            while (clientSocket.isConnected()) {
                int userInput = parseInt(in.readLine());
                //Shows player name on Console dialogue
                System.out.println(name + ": " + userInput);

                if (userInput == 1 || userInput == 2 || userInput == 3) {

                    //Generate random digits between 1-3
                    // nextInt as provided by Random is exclusive of the top value, so you need to add 1
                    int randomNum = rand.nextInt((3 - 1) + 1) + 1;
                    System.out.print("Server: ");
                    System.out.println(randomNum);

                    //Sending to player
                    out.println(randomNum);

                    //Game Logic
                    if (randomNum == userInput) {
                        result = name + ", It's a draw!";
                    } else if (randomNum == 1 && userInput == 2) {
                        result = name + " won! Paper beats Rock!";
                    } else if (randomNum == 1 && userInput == 3) {
                        result = name + " lose! Rock beats Scissors!";
                    } else if (randomNum == 2 && userInput == 1) {
                        result = name + " lose! Paper beats Rock!";
                    } else if (randomNum == 2 && userInput == 3) {
                        result = name + " won! Scissors beat Paper!";
                    } else if (randomNum == 3 && userInput == 1) {
                        result = name + " won! Rock beats Scissors!";
                    } else {
                        result = name + " lose! Scissors beat Paper!";
                    }

                } else {
                    String randomNum = "Received invalid input!";
                    System.out.print("Server: ");
                    System.out.println(randomNum);

                    //Sending to player
                    out.println(randomNum);

                    result = name + ", please enter a digit between 1-3!";
                }


                System.out.print("Result: ");
                System.out.println(result);
                System.out.println("=================================================");
                //Sending to player
                out.println(result);

            }
        } catch (Exception e) {
            System.out.println("Client Left");
            System.out.println("===========================================");
        }
        RockPaperScissorsServer.clientList.remove(this);
    }
}