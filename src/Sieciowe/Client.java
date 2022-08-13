package Sieciowe;

import java.io.*;
import java.net.Socket;
import java.sql.SQLOutput;
import java.util.Objects;
import java.util.Scanner;

// A client sends messages to the server, the server spawns a thread to communicate with the client.
// Each communication with a client is added to an array list so any message sent gets sent to every other client
// by looping through it.

public class Client {

    // A client has a socket to connect to the server and a reader and writer to receive and send messages respectively.
    private static DataOutputStream dataOutputStream = null;
    private static DataInputStream dataInputStream = null;
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;
    boolean isLoggedIn;

    public Client(Socket socket, String username) {
        try {
            this.socket = socket;
            this.username = username;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void sendMessage() {
        try {
            System.out.println(username);
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            Scanner scanner = new Scanner(System.in);
            while (socket.isConnected()) {
                String messageToSend = scanner.nextLine();
                if (messageToSend.toLowerCase().equals("exit")){
                    closeEverything(socket, bufferedReader, bufferedWriter);
                    return;
                }
                bufferedWriter.write(username + ": " + messageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void listenForMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgFromGroupChat;
                // While there is still a connection with the server, continue to listen for messages on a separate thread.
                while (socket.isConnected()) {
                    try {
                        // Get the messages sent from other users and print it to the console.
                        msgFromGroupChat = bufferedReader.readLine();
                        System.out.println(msgFromGroupChat);
                    } catch (IOException e) {
                        // Close everything gracefully.
                        closeEverything(socket, bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start();
    }

    // Helper method to close everything so you don't have to repeat yourself.
    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        // Note you only need to close the outer wrapper as the underlying streams are closed when you close the wrapper.
        // Note you want to close the outermost wrapper so that everything gets flushed.
        // Note that closing a socket will also close the socket's InputStream and OutputStream.
        // Closing the input stream closes the socket. You need to use shutdownInput() on socket to just close the input stream.
        // Closing the socket will also close the socket's input stream and output stream.
        // Close the socket after closing the streams.
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Run the program.
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        User user = new User("", false);
        // Get a username for the user and a socket connection.
        while (true){
            clearScreen();
            if (user.isLoggedIn) {
                showMenuWhenLoggedIn();
            } else {
                showMenu();
            }
            String mode = scanner.nextLine();
            Request req = new Request(user.username, user.isLoggedIn, mode, "");
            Response resp = ApiGateway.checkInput(req);

            if (!Objects.equals(resp.username, "")){
                user.username = resp.username;
                user.isLoggedIn = true;
                System.out.println(user.username);
            }

        }






        // Create a socket to connect to the server.
//        Socket socket = new Socket("localhost", 1337);
//        Client client = new Client(socket, username);

        // Pass the socket and give the client a username.


        // Infinite loop to read and send messages.
//
    }

    public static void showMenu() {
        System.out.println("Options:");
        System.out.println("1. Login");
        System.out.println("2. Register ");
        System.out.println("3. Show chat ");

    }

    public static void showMenuWhenLoggedIn(){
//        System.out.println("Hello " + username);
        System.out.println("Options:");
        System.out.println("1. Enter chat");
        System.out.println("2. Send file");
        System.out.println("3. Get file");
        System.out.println("4. Logout ");
    }


    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }



}



