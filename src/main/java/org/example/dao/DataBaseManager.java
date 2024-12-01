package org.example.dao;
import java.io.Serializable;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.example.models.*;
public class DataBaseManager implements Serializable {
    private static final String URL = "jdbc:sqlite:orders.db";

    private Connection connect()
    {
        try{
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(URL);
        }
        catch (SQLException | ClassNotFoundException e)
        {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

    public void createTables()
    {
        String createOrdersTables = """
                CREATE TABLE IF NOT EXISTS orders (
                orderId INTEGER PRIMARY KEY AUTOINCREMENT,
                customerName TEXT NOT NULL,
                customerAddress TEXT NOT NULL,
                deliveryTime TEXT NOT NULL,
                delivered BOOLEAN NOT NULL
                );
                """;
        String createOrderItemsTable = """
                CREATE TABLE IF NOT EXISTS items (
                itemId INTEGER PRIMARY KEY AUTOINCREMENT,
                orderId INTEGER NOT NULL,
                dishName TEXT NOT NULL,
                quantity INTEGER NOT NULL,
                FOREIGN KEY (orderId) REFERENCES orders(orderId) ON DELETE CASCADE   
                );
                
                """;
        try(Connection conn = connect(); Statement stmt = conn.createStatement()){
            stmt.executeUpdate(createOrdersTables);
            stmt.executeUpdate(createOrderItemsTable);
            System.out.println("Orders table created");

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    public int addOrder(Order order)  {
        String insertOrderSQL = """
                INSERT INTO orders (customerName, customerAddress, deliveryTime, delivered)
                VALUES (?,?,?,?);
                """;

        String insertItemSQL = """
                INSERT INTO items (orderId, dishName, quantity)
                VALUES (?,?,?);
                """;

        try(Connection connection = connect())
        {
            PreparedStatement orderStatement = connection.prepareStatement(insertOrderSQL, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement itemStatement = connection.prepareStatement(insertItemSQL);

            orderStatement.setString(1, order.getCustomerName());
            orderStatement.setString(2, order.getDeliveryAddress());
            orderStatement.setString(3, order.getDeliveryTime().toString());
            orderStatement.setBoolean(4, order.isDelivered());
            orderStatement.executeUpdate();

            ResultSet generatedKeys = orderStatement.getGeneratedKeys();
            if(generatedKeys.next()) {
                int orderId = generatedKeys.getInt(1);

                for (OrderItem orderItem : order.getOrderItemList()) {
                    itemStatement.setInt(1, orderId);
                    itemStatement.setString(2, orderItem.getDishName());
                    itemStatement.setInt(3, orderItem.getQuantity());
                    itemStatement.executeUpdate();
                }
                return orderId;
            }
            else {
                throw new SQLException("Failed to retrieve the order ID.");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return -1;
        }

    }
    public void deleteOrder(int orderId)  {
        String deleteOrderSQL = " DELETE FROM orders WHERE orderId = ?";

        try(Connection connection = connect())
        {
            PreparedStatement orderStatement = connection.prepareStatement(deleteOrderSQL);
            orderStatement.setInt(1, orderId);
            orderStatement.executeUpdate();
            System.out.println("Order id " + orderId + " successfully deleted");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    public String checkOrderDeliveryStatus(int orderId) {
        String selectOrderSQL = "SELECT delivered, deliveryTime FROM orders WHERE orderId = ?";

        try (Connection connection = connect()) {
            connection.setAutoCommit(false);

            PreparedStatement orderStatement = connection.prepareStatement(selectOrderSQL);
            orderStatement.setInt(1, orderId);
            ResultSet rs = orderStatement.executeQuery();

            if (rs.next()) {
                boolean delivered = rs.getBoolean("delivered");
                LocalDateTime deliveryTime = LocalDateTime.parse(rs.getString("deliveryTime"));
                Order order = new Order(orderId, delivered, deliveryTime);

                if (order.isDelivered()) {
                    markAsDelivered(orderId, connection);
                    connection.commit();
                    String message = "Order ID " + orderId + " has been successfully delivered.";
                    System.out.println(message);
                    return message;
                } else {
                    String message = "Order ID " + orderId + " has not been delivered yet.";
                    System.out.println(message);
                    return message;
                }
            } else {
                String message = "Order ID " + orderId + " not found.";
                System.out.println(message);
                return message;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try (Connection connection = connect()) {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return "An error occurred while checking the order status.";
        }
    }

private void markAsDelivered(int orderId, Connection connection)  {
        String updateOrderSQL  = "UPDATE orders SET delivered = ? WHERE orderId = ?";
        try(PreparedStatement orderStatement = connection.prepareStatement(updateOrderSQL))
        {
            orderStatement.setBoolean(1, true);
            orderStatement.setInt(2, orderId);
            orderStatement.executeUpdate();
            System.out.println("Order id " + orderId + " marked as delivered");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
}


}
