package org.example.services;


import org.example.dao.DataBaseManager;
import org.example.models.Order;
import org.example.models.OrderItem;

import java.util.List;
public class OrderService {
    private final DataBaseManager dataBaseManager;

    public OrderService(DataBaseManager dataBaseManager) {
        this.dataBaseManager = dataBaseManager;
    }

    public int addOrder(String deliveryAddress, String customerName, List<OrderItem> orderItemList)
    {
        Order order = new Order(deliveryAddress, customerName, orderItemList);
        return dataBaseManager.addOrder(order);
    }

    public String checkOrderStatus(int orderId)
    {
        return dataBaseManager.checkOrderDeliveryStatus(orderId);
    }


}
