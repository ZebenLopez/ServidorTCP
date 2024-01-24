package services;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Conexion {
    public void conectar() throws IOException {
        String respuesta = "Conectado al server";
        int port = 6789;
        ServerSocket serverSocket = new ServerSocket(port);

        while (true) {
            try {
                Socket connectionSocket = serverSocket.accept();
                new ClientHandler(connectionSocket).start();
                System.out.println("Conectado al cliente");
            } catch (IOException e) {
                System.out.println("Error al conectar al cliente");
            }
        }

    }
}
