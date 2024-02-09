package services;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The type Conexion.
 * <p>
 *     Clase que se encarga de establecer la conexión con los clientes
 *
 * @author Zebenzuí López Conde
 * @version 1.0  2ºA DAM
 */
public class Conexion {
    private volatile boolean running = true;
    private ServerSocket serverSocket;
    private Thread conexionThread;


    /**
     * Conectar.
     * <p>
     *     Método que se encarga de establecer la conexión con los clientes
     *     <p>
     *         Se crea un nuevo ServerSocket que escucha en el puerto 6789
     *         Bucle infinito para aceptar conexiones de clientes
     *         <p>
     *             Acepta una conexión de un cliente
     *             Crea un nuevo ClientHandler para manejar la conexión del cliente
     *             Inicia un nuevo hilo para el ClientHandler
     *             Imprime un mensaje indicando que un cliente se ha conectado
     *             Imprime un mensaje de error si no se puede conectar al cliente
     *
     * @throws IOException the io exception
     */
    public void conectar() throws IOException {
        // Puerto en el que se escuchan las conexiones
        int port = 6789;
        // Crea un nuevo ServerSocket que escucha en el puerto especificado
        serverSocket = new ServerSocket(port);
        conexionThread = new Thread(() -> {
            while (running) {
                try {
                    Socket connectionSocket = serverSocket.accept();
                    ClientHandler clientHandler = new ClientHandler(connectionSocket);
                    new Thread(clientHandler).start();
                    System.out.println("Cliente conectado desde ip: " + connectionSocket.getInetAddress()) ;
                } catch (IOException e) {
                    if (running) {
                        e.printStackTrace();
                        System.out.println("Error al conectar al cliente");
                    }
                }
            }
        });
        conexionThread.start();
    }

    /**
     * Stop.
     * <p>
     *     Método para detener la conexión con los clientes
     *     <p>
     *         Establece el valor de running a false
     *         Cierra el ServerSocket
     *         Interrumpe el hilo de conexión
     */
    public void stop() {
        running = false;
        if (serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (conexionThread != null) {
            conexionThread.interrupt();
        }
    }
}