package org.example.network.rmi;

import org.example.models.Menu;
import org.example.models.OrderItem;
import org.example.services.OrderService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class DeliveryServiceImplementation extends UnicastRemoteObject implements DeliveryService {
    private final OrderService orderService;

    public DeliveryServiceImplementation(OrderService orderService) throws RemoteException {
        super();
        this.orderService = orderService;
    }

    @Override
    public String getMenu() throws RemoteException {
       return Menu.printMenuToClient();
    }

    @Override
    public int makeTheOrder(String customerName, String address, List<OrderItem> orderItemList) throws RemoteException {
        int id = orderService.addOrder( customerName, address, orderItemList);
        System.out.println("Your order has been placed successfully! Your Order number is: " + id);
        return id;
    }


    @Override
    public String getStatus(int orderID) throws RemoteException {
        return orderService.checkOrderStatus(orderID);
    }

    @Override
    public void exit() throws RemoteException {
        System.out.println("Thank you for using our service");
    }
}
