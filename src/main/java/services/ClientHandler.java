package services;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.ControladorController;

public class ClientHandler extends Thread {
    private final ControladorController controladorController;
    private static Set<String> clientIdentifiers = new HashSet<>(); // Cambiado a Set para evitar duplicados
    private static String sistema;
    private static String usuario;
    private static double cpu;
    private static double memoria;
    private static double espacioDisco;
    private static double espacioLibre;
    private static double porcentajeOcupado;
    private String usb;
    private String identificador;

    private final Socket clientSocket;

    // Constructor
    public ClientHandler(Socket connectionSocket) {
        this.clientSocket = connectionSocket;
        this.controladorController = new ControladorController();
        // El identificador se crea aquí para cada clientSocket
        this.identificador = sistema + "_" + usuario;
        onClientConnected();
    }

@Override
public void run() {
    try {
        // Crear un BufferedReader para leer la entrada del cliente
        BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        // Leer la entrada completa del cliente
        String line;
        while (true) { // Bucle infinito
            if ((line = inFromClient.readLine()) != null) {
                StringBuilder sb = new StringBuilder();
                sb.append(line);
                String clientInput = sb.toString();

                // Deserializar la entrada en una lista de objetos
                Gson gson = new Gson();
                Type listType = new TypeToken<List<Object>>() {}.getType();
                List<Object> datosCliente = gson.fromJson(clientInput, listType);

                // Extraer los datos y almacenarlos en variables
                sistema = datosCliente.get(0).toString();
                usuario = datosCliente.get(1).toString();
                cpu = Double.parseDouble(datosCliente.get(2).toString());
                memoria = Double.parseDouble(datosCliente.get(3).toString());
                espacioDisco = Double.parseDouble(datosCliente.get(4).toString());
                espacioLibre = Double.parseDouble(datosCliente.get(5).toString());
                porcentajeOcupado = Double.parseDouble(datosCliente.get(6).toString());
                usb = datosCliente.get(7).toString();

                // Imprimir los datos del cliente
                System.out.println("Datos recibidos del cliente: " + datosCliente);

                // Crear un identificador único para cada clientSocket
                identificador = sistema + "_" + usuario;
                clientIdentifiers.add(identificador);
                System.out.println("Identificador: " + identificador);
            } else {
                // Si no hay más datos para leer, rompe el bucle
                break;
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

    private void onClientConnected() {
        System.out.println("Cliente conectado: " + identificador);
        clientIdentifiers.add(identificador);
    }


    public static Set<String> getClientIdentifiers() {
        return clientIdentifiers;
    }
    private static Map<String, ClientHandler> clientHandlers = new HashMap<>();

    public static void addClientHandler(ClientHandler clientHandler) {
        clientHandlers.put(clientHandler.getIdentificador(), clientHandler);
    }

    public static ClientHandler getClientHandlerByIdentifier(String identifier) {
        return clientHandlers.get(identifier);
    }

    public static String getSistema() {
        return sistema;
    }
    public String getUsuario() {
        return usuario;
    }
    public static double getCpu() {
        return cpu;
    }
    public static double getMemoria() {
        return memoria;
    }
    public static double getEspacioDisco() {
        return espacioDisco;
    }
    public static double getEspacioLibre() {
        return espacioLibre;
    }
    public static double getPorcentajeOcupado() {
        return porcentajeOcupado;
    }
    public String getUsb() {
        return usb;
    }
    public String getIdentificador() {
        return identificador;
    }
    public Socket getClientSocket() {
        return clientSocket;
    }
}