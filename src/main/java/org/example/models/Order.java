package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Order implements Serializable {
    private String deliveryAddress;
    private LocalDateTime deliveryTime;
    private boolean delivered;
    private int orderId;
    private String customerName;
    private List<OrderItem> orderItemList;

    public Order(String deliveryAddress, String customerName, List<OrderItem> orderItemList) {
        this.deliveryAddress = deliveryAddress;
        this.customerName = customerName;
        this.deliveryTime = LocalDateTime.now();
        this.delivered = false;
        this.orderItemList = orderItemList;
    }
    //for database
    public Order(int orderId, boolean delivered, LocalDateTime deliveryTime) {
        this.orderId = orderId;
        this.delivered = delivered;
        this.deliveryTime = deliveryTime;
    }


    public void markDelivered() {
        this.delivered = true;
    }

    public boolean isDelivered() {
        if (this.delivered) {
            return true;
        }
        else {
            return LocalDateTime.now().isAfter(this.deliveryTime.plusMinutes(30));
        }
    }
}
