package Sieciowe;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Services {

    private static DataOutputStream dataOutputStream = null;
    private static DataInputStream dataInputStream = null;

    public static Response userRegister() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Register your account");
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        // save user to DB

        return new Response(username, true, "200 OK", "User registered and logged in");
    }

    public static Response userLogIn() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();


        // if user in db

        return new Response(username, true, "200 OK", "User registered and logged in");
    }

    public static void enterChat(String username) throws IOException {
        Socket socket = new Socket("localhost", 1234);
        Client client = new Client(socket, username);

        client.listenForMessage();
        client.sendMessage();
    }

    public static Response userLogout() {
        return new Response("", false, "200 OK", "Succesfully logged out");
    }

    public static void sendToServer(String path) throws IOException {
        try (Socket socket = new Socket("localhost", 5000)) {
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            sendFile(path);
            dataInputStream.close();
            dataInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void getFromServer(String filename, String fullPath) {

    }

    public static void sendFile(String path) throws Exception {
        int bytes = 0;
        File file = new File(path);
        FileInputStream fileInputStream = new FileInputStream(file);

        dataOutputStream.writeLong(file.length());
        byte[] buffer = new byte[4 * 1024];
        while ((bytes = fileInputStream.read(buffer)) != -1) {
            dataOutputStream.write(buffer, 0, bytes);
            dataOutputStream.flush();
        }
        fileInputStream.close();
    }

}
