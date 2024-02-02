package services;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Conexion {
    // Crea un pool de hilos con un número fijo de hilos.
    ExecutorService executorService = Executors.newFixedThreadPool(10);

    public void conectar() throws IOException {
        int port = 6789;
        ServerSocket serverSocket = new ServerSocket(port);

        // Para cada conexión de cliente, envía un nuevo ClientHandler al pool de hilos.
        while (true) {
            try {
                Socket connectionSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(connectionSocket);
                executorService.execute(clientHandler);
                ClientHandler.addClientHandler(clientHandler); // Agrega el ClientHandler al mapa
            } catch (IOException e) {
                System.out.println("Error al conectar al cliente");
            }
        }
    }
}