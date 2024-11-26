package org.example;
import org.example.dao.DataBaseManager;
import org.example.models.*;

import java.util.List;

public class Test {
    public static void main(String[] args) {
        DataBaseManager db = new DataBaseManager();
        db.createTables();
        List<OrderItem> items = List.of(
                new OrderItem("Pizza", 2),
                new OrderItem("Sushi", 1)
        );
        Order order = new Order("Независимости 11", "Alice", items);
        Order order1 = new Order("Прямая 11", "Федя", items);
        String s = db.checkOrderDeliveryStatus(5);
    }
}
