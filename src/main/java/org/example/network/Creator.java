package org.example.network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Creator implements Closeable {

    private final Socket socket;
    private final BufferedReader reader;
    private final BufferedWriter writer;

    public Creator(String ip, int port) {
        try {
            this.socket = new Socket(ip, port);
            this.reader = createReader();
            this.writer = createWriter();
        } catch (IOException e) {
            throw new RuntimeException();
        }

    }

    public Creator(ServerSocket serverSocket) {
        try {
            this.socket = serverSocket.accept();
            this.reader = createReader();
            this.writer = createWriter();
        } catch (IOException e) {
            throw new RuntimeException();
        }

    }

    public void writeLine(String message) {
        try {
            writer.write(message);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException();
        }

    }

    public String readLine() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
    public String readAll() {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            return stringBuilder.toString();
        }
        catch (IOException e )
        {
            throw new RuntimeException("Failed read data from stream", e);
        }

    }
    private BufferedReader createReader() throws IOException {
        return new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    private BufferedWriter createWriter() throws IOException {
        return new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    @Override
    public void close() throws IOException {
        writer.close();
        reader.close();
        socket.close();
    }
}