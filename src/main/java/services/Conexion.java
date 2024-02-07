package services;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The type Conexion.
 *
 * @author Zebenzuí López Conde
 * @version 1.0  2ºA DAM
 */
// Clase Conexion que se encarga de establecer la conexión con los clientes
public class Conexion {
    /**
     * Conectar.
     *
     * @throws IOException the io exception
     */
// Método para conectar con los clientes
    public void conectar() throws IOException {
        // Puerto en el que se escuchan las conexiones
        int port = 6789;
        // Crea un nuevo ServerSocket que escucha en el puerto especificado
        ServerSocket serverSocket = new ServerSocket(port);

        // Bucle infinito para aceptar conexiones de clientes
        while (true) {
            try {
                // Acepta una conexión de un cliente
                Socket connectionSocket = serverSocket.accept();
                // Crea un nuevo ClientHandler para manejar la conexión del cliente
                ClientHandler clientHandler = new ClientHandler(connectionSocket);
                // Inicia un nuevo hilo para el ClientHandler
                new Thread(clientHandler).start();
                // Imprime un mensaje indicando que un cliente se ha conectado
                System.out.println("Cliente conectado desde ip: " + connectionSocket.getInetAddress()) ;
            } catch (IOException e) {
                // Imprime un mensaje de error si no se puede conectar al cliente
                System.out.println("Error al conectar al cliente");
            }
        }
    }
}