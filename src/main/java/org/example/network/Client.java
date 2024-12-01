
package org.example.network;

import org.example.models.Menu;
import org.example.models.OrderItem;
import org.example.network.rmi.DeliveryService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class Client {
    final private static Logger logger = Logger.getLogger(Client.class.getName());
    final static int PORT = 1099;
    final static String HOST = "localhost";
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry(HOST, PORT);
            DeliveryService deliveryService = (DeliveryService) registry.lookup("OrderService");
            Scanner scanner = new Scanner(System.in);
            System.out.println("Connect to the server");
            System.out.println("Available commands: 1. menu, 2. order, 3. status, 4. exit");
            System.out.print("Enter your command: ");
            while (true) {
                String command = scanner.nextLine().trim();
                if (command.equalsIgnoreCase("menu")){
                    System.out.println("That's a menu");
                    System.out.println(Menu.printMenuToClient());
                    System.out.println("type 'order' to take the order");
                } else if (command.equalsIgnoreCase("order")) {
                    System.out.print("Please provide your name: ");
                    String customerName = scanner.nextLine().trim();
                    System.out.print("Please provide your delivery address: ");
                    String address = scanner.nextLine().trim();

                    List<OrderItem> orderItems = new ArrayList<>();
                    while (true) {
                        System.out.print("Please enter the dish name (or 'DONE' to finish): ");
                        String dishName = scanner.nextLine().trim();
                        if (dishName.equalsIgnoreCase("DONE")) {
                            break;
                        }
                        System.out.print("Please enter the quantity for " + dishName + ": ");
                        try {
                            int quantity = Integer.parseInt(scanner.nextLine().trim());
                            orderItems.add(new OrderItem(dishName, quantity));
                        } catch (NumberFormatException e) {
                            System.out.println("Please enter an integer number");
                        }
                    }
                    int orderId = deliveryService.makeTheOrder(customerName, address, orderItems);
                    System.out.println("Your order has been placed successfully! Your Order number is: " + orderId);
                } else if (command.equalsIgnoreCase("status")) {
                    System.out.print("Please enter your order ID: ");
                    int orderId = Integer.parseInt(scanner.nextLine().trim());
                    String status = deliveryService.getStatus(orderId);
                    System.out.println("Order status: " + status);
                } else if (command.equalsIgnoreCase("exit")) {
                    deliveryService.exit();
                    break;
                } else {
                    System.out.println("Unknown command.");
                }
            }
        }
        catch (NotBoundException e)
        {
            logger.severe("Not Bound Exception" + e.getMessage());
            e.printStackTrace();
        }
        catch (IOException e)
        {
            logger.severe("IO Exception" + e.getMessage());
            e.printStackTrace();
        }
        catch (Exception e) {
            logger.severe("RMI exception" + e.getMessage());
            e.printStackTrace();
        }


    }
}
