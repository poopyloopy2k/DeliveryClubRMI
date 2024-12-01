package org.example.network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Logger;

import org.example.dao.DataBaseManager;
import org.example.models.Menu;
import org.example.network.rmi.DeliveryService;
import org.example.network.rmi.DeliveryServiceImplementation;
import org.example.services.OrderService;

public class Server {
    private static final Logger logger = Logger.getLogger(Server.class.getName());
    public static void main(String[] args) {
        try
        {
             OrderService orderService = new OrderService(new DataBaseManager());
             DeliveryServiceImplementation deliveryServiceImplementation = new DeliveryServiceImplementation(orderService);
             Registry registry = LocateRegistry.createRegistry(1099);
             registry.rebind("OrderService", deliveryServiceImplementation);
             logger.info("Server started");
             while (true) {
                Thread.sleep(1000);
            }
        }
        catch (RemoteException e)
        {
            logger.severe("RMI exception: " + e.getMessage());
        }
        catch(InterruptedException e)
        {
            logger.severe("Server" + e.getMessage());
        }
        catch (Exception e){
           logger.severe("unexpected exception" + e.getMessage());
        }


    }


}
