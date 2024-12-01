package org.example.models;

import lombok.Data;
    import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.io.Serializable;

@Data
    @NoArgsConstructor

    public class OrderItem implements Serializable {
        private int orderId;
        private int itemId;
        private String dishName;
        private int quantity;

        public OrderItem(String dishName, int quantity) {
            this.dishName = dishName;
            this.quantity = quantity;
        }

    }
