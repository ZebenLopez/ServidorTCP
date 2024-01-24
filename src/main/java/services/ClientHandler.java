package services;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ClientHandler extends Thread {
    private final Socket clientSocket;

    // Constructor que acepta un socket como parámetro
    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        BufferedReader inFromClient = null;
        try {
            // Crear un BufferedReader para leer la entrada del cliente
            inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            // Leer la entrada completa del cliente
            String line;

            while ((line = inFromClient.readLine()) != null) {
                StringBuilder sb = new StringBuilder();
                sb.append(line);
                String clientInput = sb.toString();

                // Deserializar la entrada en una lista de objetos
                Gson gson = new Gson();
                Type listType = new TypeToken<List<Object>>() {
                }.getType();
                List<Object> datosCliente = gson.fromJson(clientInput, listType);

                // Imprimir los datos del cliente
                System.out.println("Datos recibidos del cliente: " + datosCliente);

                // Guardar datosCliente en un archivo CSV
                try (FileWriter writer = new FileWriter("datosCliente.csv", true)) {
                    for (Object dato : datosCliente) {
                        // Reemplazar las comas con punto y coma
//                        String processedDato = dato.toString().replace(",", ".");
//                        writer.append(processedDato).append(",");
                        writer.append(dato.toString()).append(",");
                    }
                    writer.append("\n");
                } catch (IOException e) {
                    System.out.println("Error al escribir en el archivo CSV");
                }
            }

        } catch (IOException e) {
            System.out.println("Error al leer la entrada del cliente");
        } finally {
            try {
                // Cerrar el socket del cliente
                clientSocket.close();
            } catch (IOException e) {
                System.out.println("Cliente desconectado");
            }
        }
    }
}