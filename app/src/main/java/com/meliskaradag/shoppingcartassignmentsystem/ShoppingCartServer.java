package com.meliskaradag.shoppingcartassignmentsystem;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;

public class ShoppingCartServer {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the port number to listen: ");
        int port = scanner.nextInt();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            while (true) {
                new ClientHandler(serverSocket.accept()).start();
            }
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
