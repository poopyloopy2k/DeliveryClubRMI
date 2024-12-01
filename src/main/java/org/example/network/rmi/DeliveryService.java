package org.example.network.rmi;

import org.example.models.OrderItem;

import java.rmi.RemoteException;
import java.rmi.Remote;
import java.util.List;

public interface DeliveryService extends Remote {

    String getMenu() throws RemoteException;
    int makeTheOrder(String customerName, String address, List<OrderItem> orderItemList) throws RemoteException;
    String getStatus(int orderID) throws RemoteException;
    void exit() throws RemoteException;

}
