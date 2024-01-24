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
            while (true) {
                Socket connectionSocket = serverSocket.accept();
                new ClientHandler(connectionSocket).start();
            }
        }
    }
}
