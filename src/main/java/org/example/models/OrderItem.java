package org.example.models;

import lombok.Data;
    import lombok.NoArgsConstructor;

    @Data
    @NoArgsConstructor
    public class OrderItem {
        private int orderId;
        private int itemId;
        private String dishName;
        private int quantity;

        public OrderItem(String dishName, int quantity) {
            this.dishName = dishName;
            this.quantity = quantity;
        }

    }
