package org.example.network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import org.example.dao.DataBaseManager;
import org.example.models.Menu;
import org.example.services.OrderService;

public class Server {
    public static void main(String[] args) {

        try(ServerSocket serverSocket = new ServerSocket(8000))
        {
            System.out.println("Welcome to Delivery");
            ServerManager serverManager = new ServerManager(new OrderService(new DataBaseManager()));
            while(true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected" + clientSocket.getRemoteSocketAddress());

                new Thread(()-> {
                    try(Creator creator = new Creator(clientSocket))
                    {
                        serverManager.handleClient(creator);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }).start();

            }
        } catch (IOException e )
        {
            throw new RuntimeException(e);
        }

    }


}
