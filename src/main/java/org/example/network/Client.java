package org.example.network;
import java.io.*;
public class Client {
    public static void main(String[] args) {
        try (Creator creator = new Creator("127.0.0.1", 8000))
        {


            System.out.println("Client connected");
            String request = "I'd like to order";
            System.out.println(request);
            creator.writeLine(request);
            String response = creator.readLine();
            System.out.println(response);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
