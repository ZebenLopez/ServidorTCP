package services;

import controller.ControladorController;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Conexion {
    public void conectar() throws IOException {
        int port = 6789;
        ServerSocket serverSocket = new ServerSocket(port);

        // Para cada conexión de cliente, crea y ejecuta un nuevo hilo.
        while (true) {
            try {
                Socket connectionSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(connectionSocket);
                new Thread(clientHandler).start();
                System.out.println("Cliente conectado desde ip: " + connectionSocket.getInetAddress()) ;
            } catch (IOException e) {
                System.out.println("Error al conectar al cliente");
            }
        }
    }
}