package services;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.ControladorController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
/**
 *
 * @author Zebenzuí López Conde
 * @version 1.0
 * 2ºA DAM
 */

public class ClientHandler extends Thread {
    private PrintWriter csvWriter;

    private final ControladorController controladorController;
    //    private static Set<String> clientIdentifiers = new HashSet<>(); // Cambiado a Set para evitar duplicados
    private static ObservableSet<String> clientIdentifiers = FXCollections.observableSet(new HashSet<>());

    public static ObservableSet<String> getClientIdentifiers() {
        return clientIdentifiers;
    }

    private String sistema;
    private String usuario;
    private double cpu;
    private double memoria;
    private double espacioDisco;
    private double espacioLibre;
    private double porcentajeOcupado;
    private String usb;
    private String identificador;

    private final Socket clientSocket;

    // Constructor
    public ClientHandler(Socket connectionSocket) {
        this.clientSocket = connectionSocket;
        this.controladorController = new ControladorController();
    }

    @Override
    public void run() {
        try {
            // Crear un BufferedReader para leer la entrada del cliente
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // Leer la entrada completa del cliente
            String line;
            while ((line = inFromClient.readLine()) != null) { // Bucle infinito
                StringBuilder sb = new StringBuilder();
                sb.append(line);
                String clientInput = sb.toString();

                // Deserializar la entrada en una lista de objetos
                Gson gson = new Gson();
                Type listType = new TypeToken<List<Object>>() {
                }.getType();
                List<Object> datosCliente = gson.fromJson(clientInput, listType);

                // Extraer los datos y almacenarlos en variables
                sistema = datosCliente.get(0).toString();
                usuario = datosCliente.get(1).toString();
                cpu = Double.parseDouble(datosCliente.get(2).toString());
                memoria = Double.parseDouble(datosCliente.get(3).toString());
                espacioDisco = Double.parseDouble(datosCliente.get(4).toString());
                espacioLibre = Double.parseDouble(datosCliente.get(5).toString());
                porcentajeOcupado = Double.parseDouble(datosCliente.get(6).toString());

                crearArchivoCSV(datosCliente);

                // Imprimir los datos del cliente
                System.out.println("Datos recibidos del cliente "+ identificador + ": " + datosCliente);
                if (sistema == null || usuario == null) {
                    System.out.println("No se puede obtener el sistema o el usuario");
                } else {
                    identificador = usuario + (clientSocket.getInetAddress() + "").replace("/", "-");
                }
                // Crear un identificador único para cada clientSocket
                clientIdentifiers.add(identificador);
                clientHandlers.put(identificador, this);

                // Llama al método para crear el archivo .csv

            }
        } catch (IOException e) {
            System.out.println("Cliente desconectado!");
            onClientDisconnected();
        }
    }
    public void crearArchivoCSV(List<Object> datosCliente) {
        try {
            // Extraer los datos y almacenarlos en variables
            String sistema = datosCliente.get(0).toString();
            String usuario = datosCliente.get(1).toString();
            double cpu = Double.parseDouble(datosCliente.get(2).toString());
            double memoria = Double.parseDouble(datosCliente.get(3).toString());
            double espacioDisco = Double.parseDouble(datosCliente.get(4).toString());
            double espacioLibre = Double.parseDouble(datosCliente.get(5).toString());
            double porcentajeOcupado = Double.parseDouble(datosCliente.get(6).toString());


            // Crear un archivo .csv con el nombre del usuario
            FileWriter fileWriter = new FileWriter(identificador + ".csv", true);
            PrintWriter csvWriter = new PrintWriter(fileWriter);
            // Escribe los encabezados en el archivo .csv solo si el archivo estaba vacío
            File file = new File(identificador + ".csv");
            if (file.length() == 0) {
                csvWriter.println("Sistema,Usuario,CPU (%),Memoria (%),Espacio en disco (GB),Espacio libre (GB),Porcentaje ocupado (%)");
            }

            // Escribe los datos del cliente en el archivo .csv
            String data = String.format(Locale.US, "%s,%s,%.2f,%.2f,%.2f,%.2f,%.2f",
                    sistema, usuario, cpu, memoria, espacioDisco, espacioLibre, porcentajeOcupado);
//            data = data.replace(".", ",");

            csvWriter.println(data);

            // Limpia el PrintWriter para asegurarte de que los datos se escriban en el archivo
            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void onClientDisconnected() {
        System.out.println("Cliente desconectado: " + identificador);
        clientIdentifiers.remove(identificador);

        // Cierra el PrintWriter cuando el cliente se desconecta
        if (csvWriter != null) {
            csvWriter.close();
        }
    }

    private static Map<String, ClientHandler> clientHandlers = new HashMap<>();

    public static ClientHandler getClientHandlerByIdentifier(String identifier) {
        return clientHandlers.get(identifier);
    }

    public String getSistema() {
        return sistema;
    }

    public String getUsuario() {
        return usuario;
    }

    public double getCpu() {
        return cpu;
    }

    public double getMemoria() {
        return memoria;
    }

    public double getEspacioDisco() {
        return espacioDisco;
    }

    public double getEspacioLibre() {
        return espacioLibre;
    }

    public double getPorcentajeOcupado() {
        return porcentajeOcupado;
    }

    public void enviarAlerta(String alerta) {
        try {
            PrintWriter outToClient = new PrintWriter(clientSocket.getOutputStream(), true);
            outToClient.println(alerta);
            outToClient.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}