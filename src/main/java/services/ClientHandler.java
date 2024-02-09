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
 * The type Client handler.
 * <p>
 *     Clase que se encarga de manejar las conexiones de los clientes
 *
 * @author Zebenzuí López Conde
 * @version 1.0 2ºA DAM
 */
public class ClientHandler extends Thread {
    private PrintWriter csvWriter;

    // Controlador de la interfaz de usuario
    private final ControladorController controladorController;

    // Conjunto de identificadores de clientes
    private static ObservableSet<String> clientIdentifiers = FXCollections.observableSet(new HashSet<>());

    // Datos del cliente
    private String sistema;
    private String usuario;
    private double cpu;
    private double memoria;
    private double espacioDisco;
    private double espacioLibre;
    private double porcentajeOcupado;
    private String usb;
    private String identificador;

    // Socket del cliente
    private final Socket clientSocket;

    /**
     * Instantiates a new Client handler.
     * <p>
     *     Constructor de la clase ClientHandler
     *
     * @param connectionSocket the connection socket
     */
// Constructor de la clase ClientHandler
    public ClientHandler(Socket connectionSocket) {
        this.clientSocket = connectionSocket;
        this.controladorController = new ControladorController();
    }

    /**
     * Gets client identifiers.
     * <p>
     *     Método para obtener los identificadores de los clientes
     *     <p>
     *         Método estático para obtener los identificadores de los clientes
     *
     * @return the client identifiers
     */
// Método estático para obtener los identificadores de los clientes
    public static ObservableSet<String> getClientIdentifiers() {
        // Devuelve el conjunto de identificadores de clientes
        return clientIdentifiers;
    }

    /**
     * Run.
     * <p>
     *     Método que se ejecuta cuando se inicia el hilo
     *     <p>
     *         Crear un BufferedReader para leer la entrada del cliente
     *         Leer la entrada completa del cliente
     *         Deserializar la entrada en una lista de objetos
     *         Extraer los datos y almacenarlos en variables
     *         Crear un identificador único para cada clientSocket
     *         Llama al método para crear el archivo .csv
     *         Imprimir los datos del cliente
     */
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
//                System.out.println("Datos recibidos del cliente "+ identificador + ": " + datosCliente);
                if (sistema == null || usuario == null) {
                    System.out.println("No se puede obtener el sistema o el usuario");
                } else {
                    identificador = usuario + (clientSocket.getInetAddress() + "").replace("/", "-");
                }
                // Crear un identificador único para cada clientSocket
                clientIdentifiers.add(identificador);
                clientHandlers.put(identificador, this);

            }
        } catch (IOException e) {
            System.out.println("Cliente desconectado!");
            onClientDisconnected();
        }
    }

    /**
     * Crear archivo csv.
     * <p>
     *     Método para crear un archivo CSV con los datos del cliente
     *     <p>
     *         Extraer los datos y almacenarlos en variables
     *         Crear un archivo .csv con el nombre del usuario
     *         Escribe los encabezados en el archivo .csv solo si el archivo estaba vacío
     *         Escribe los datos del cliente en el archivo .csv
     *         Limpia el PrintWriter para asegurarte de que los datos se escriban en el archivo
     *
     * @param datosCliente the datos cliente
     */
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

    /**
     * On client disconnected.
     * <p>
     *     Método que se llama cuando un cliente se desconecta
     *     <p>
     *         Elimina el identificador del cliente del conjunto de identificadores
     *         Cierra el PrintWriter cuando el cliente se desconecta
     */
    private void onClientDisconnected() {
        System.out.println("Cliente desconectado: " + identificador);
        clientIdentifiers.remove(identificador);

        // Cierra el PrintWriter cuando el cliente se desconecta
        if (csvWriter != null) {
            csvWriter.close();
        }
    }

    private static Map<String, ClientHandler> clientHandlers = new HashMap<>();

    /**
     * Gets client handler by identifier.
     * <p>
     *     Método para obtener un ClientHandler por su identificador
     *
     * @param identifier the identifier
     * @return the client handler by identifier
     */
    public static ClientHandler getClientHandlerByIdentifier(String identifier) {
        return clientHandlers.get(identifier);
    }


    /**
     * Gets sistema.
     * <p>
     *     Método para obtener el sistema
     *
     * @return the sistema
     */
    public String getSistema() {
        return sistema;
    }

    /**
     * Gets usuario.
     *
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Gets cpu.
     *
     * @return the cpu
     */
    public double getCpu() {
        return cpu;
    }

    /**
     * Gets memoria.
     *
     * @return the memoria
     */
    public double getMemoria() {
        return memoria;
    }

    /**
     * Gets espacio disco.
     *
     * @return the espacio disco
     */
    public double getEspacioDisco() {
        return espacioDisco;
    }

    /**
     * Gets espacio libre.
     *
     * @return the espacio libre
     */
    public double getEspacioLibre() {
        return espacioLibre;
    }

    /**
     * Gets porcentaje ocupado.
     *
     * @return the porcentaje ocupado
     */
    public double getPorcentajeOcupado() {
        return porcentajeOcupado;
    }

    /**
     * Enviar alerta.
     * <p>
     *     Método para enviar una alerta al cliente
     *
     * @param alerta the alerta
     */
    public void enviarAlerta(String alerta) {
        try {
            PrintWriter outToClient = new PrintWriter(clientSocket.getOutputStream(), true);
            outToClient.println(alerta);
            outToClient.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Cerrar conexion.
     * <p>
     *     Método para cerrar la conexión con el cliente
     */
    public void cerrarConexion() {
    if (clientSocket != null && !clientSocket.isClosed()) {
        try {
            clientSocket.close();
            System.out.println("Socket del cliente cerrado");
        } catch (IOException e) {
            System.out.println("Error al cerrar el socket del cliente");
            e.printStackTrace();
        }
    }
}

/**
* Crea un mapeo de identificadores de clientes a ClientHandlers
* */
    public static Map<String, ClientHandler> getClientHandlers() {
        return clientHandlers;
    }
}