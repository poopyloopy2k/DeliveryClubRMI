
package org.example.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Client {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 8000;

    public static void main(String[] args) {
        try (Creator creator = new Creator(SERVER_IP, SERVER_PORT)) {
            System.out.println("Connected to the server.");

            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("\nAvailable commands: ");
                System.out.println("1. menu, 2. order, 3. status, 4. exit");
                System.out.print("Enter your command: ");

                String command = scanner.nextLine();
                creator.writeLine(command);

                if (command.equalsIgnoreCase("menu")) {
                    String response;
                    while (!(response = creator.readLine()).equalsIgnoreCase("Please type 'order' to make the order")) {
                        System.out.println(response);
                    }
                    System.out.println(response);
                } else if (command.equalsIgnoreCase("order")) {
                    System.out.print("Enter your name: ");
                    String name = scanner.nextLine();
                    creator.writeLine(name);

                    System.out.print("Enter your delivery address: ");
                    String address = scanner.nextLine();
                    creator.writeLine(address);

                    while (true) {
                        System.out.print("Enter dish name (or 'DONE' to finish): ");
                        String dishName = scanner.nextLine();
                        creator.writeLine(dishName);

                        if (dishName.equalsIgnoreCase("DONE")) {
                            break;
                        }

                        System.out.print("Enter quantity for " + dishName + ": ");
                        String quantity = scanner.nextLine();
                        creator.writeLine(quantity);
                    }


                    System.out.println("Server: " + creator.readLine());
                } else if (command.equalsIgnoreCase("status")) {

                    System.out.print("Enter your order ID: ");
                    String orderId = scanner.nextLine();
                    creator.writeLine(orderId);
                    String response = creator.readLine();
                    creator.writeLine("Order status: " + response);


                    System.out.println("Order status: " + creator.readLine());
                } else if (command.equalsIgnoreCase("exit")) {
                    System.out.println("Exiting...");
                    break;
                } else {
                    System.out.println("Unknown command. Please try again.");
                }
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
