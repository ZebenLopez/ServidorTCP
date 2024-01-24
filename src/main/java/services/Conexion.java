package services;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Conexion {
    public void conectar() throws IOException {
        int port = 6789;
        ServerSocket serverSocket = new ServerSocket(port);

        while (true) {
            try {
                Socket connectionSocket = serverSocket.accept();
//                System.out.println("Conectado al cliente");
                new ClientHandler(connectionSocket).start();
            } catch (IOException e) {
                System.out.println("Error al conectar al cliente");
            }
        }
    }
}