
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
            System.out.println("\nAvailable commands: ");
            System.out.println("1. menu, 2. order, 3. status, 4. exit");
            System.out.print("Enter your command: ");
            while (true) {


                String command = scanner.nextLine().trim();
                creator.writeLine(command);
                String response;
                while(!(response = creator.readLine()).equals("end"))
                {
                    System.out.println(response);
                    if(response.equals("Thank you for your time. Your session is over"))
                    {
                        return;
                    }
                }


            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
