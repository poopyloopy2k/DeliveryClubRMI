package org.example.network;

import java.io.*;
import java.net.ServerSocket;
import org.example.models.Menu;

public class Server {
    public static void main(String[] args) {

        try(ServerSocket serverSocket = new ServerSocket(8000))
        {
            System.out.println("Welcome to Delivery");

            while(true) {

                Creator creator = new Creator(serverSocket);
                        new Thread(() -> {
                            String request = creator.readLine();
                            System.out.println(request);
                            String response = "that's a menu ";
                            System.out.println("Choose something to order");
                            Menu.printMenuToClient(creator);
                            creator.writeLine(response);
                            System.out.println(response);

                            try{creator.close(); }catch(IOException e){}

                        }).start();

            }
        } catch (IOException e )
        {
            throw new RuntimeException(e);
        }

    }


}
