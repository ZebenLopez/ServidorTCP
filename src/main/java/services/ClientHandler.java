package services;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class ClientHandler extends Thread {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(clientSocket.getOutputStream());

            // Leer la entrada completa del cliente
            String line;
            try {
                while ((line = inFromClient.readLine()) != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(line);
                    String clientInput = sb.toString();

                    // Deserializar la entrada en una lista
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<String>>(){}.getType();
                    List<String> datosCliente = gson.fromJson(clientInput, listType);

                    // Imprimir los datos del cliente
                    System.out.println("Datos recibidos del cliente: " + datosCliente);
                }
            } catch (SocketException e) {
                System.out.println("No se puede leer la entrada del cliente");
                return;
            }

            // Hacer algo con la lista...

        } catch (IOException e) {
            throw new RuntimeException("Error handling client communication", e);
        }
    }
}