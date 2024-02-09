package services;

import controller.ControladorController;
import javafx.application.Platform;
import javafx.scene.control.Alert;

/**
 * The type Monitoreo alertas.
 * <p>
 * Clase MonitoreoAlertas que se encarga de monitorear y enviar alertas basadas en el uso de CPU, memoria y disco
 *
 * @author Zebenzuí López Conde
 * @version 1.0  2ºA DAM
 */
public class MonitoreoAlertas {
    /**
     * The Controlador controller.
     */
// Controlador de la interfaz de usuario
    ControladorController controladorController;

    /**
     * Instantiates a new Monitoreo alertas.
     * <p>
     * Constructor de la clase MonitoreoAlertas
     *
     * @param controladorController the controlador controller
     */
    public MonitoreoAlertas(ControladorController controladorController) {
        this.controladorController = controladorController;
    }

    /**
     * Monitoreo cpu.
     * <p>
     * Método para monitorear el uso de CPU y enviar una alerta si supera un cierto umbral
     * <p>
     * Si el uso de CPU supera el valor del slider, se imprime un mensaje en la consola, se crea una alerta, se obtiene el manejador del cliente por su identificador y se envía la alerta al cliente si el manejador del cliente no es nulo
     * Si no se pudo enviar la alerta al cliente, se imprime un mensaje en la consola
     * Se muestra una alerta en la interfaz de usuario
     * El hilo duerme durante 5 segundos
     *
     * @param cpu           the cpu
     * @param identificador the identificador
     * @throws InterruptedException the interrupted exception
     */
    public void monitoreoCPU(double cpu, String identificador) throws InterruptedException {
        new Thread(() -> {
            if (cpu >= controladorController.getCpuSliderValue()) {
                // Imprime un mensaje en la consola si el uso de CPU supera el valor del slider
                System.out.println("Alerta: El uso de CPU supera el " + controladorController.getCpuSliderValue() + "% con un valor de " + cpu + "%");
                // Crea una alerta si el uso de CPU supera el valor del slider
                String alerta = "El uso de CPU supera el " + controladorController.getCpuSliderValue() + "% con un valor de " + cpu + "%";
                // Obtiene el manejador del cliente por su identificador
                ClientHandler clientHandler = ClientHandler.getClientHandlerByIdentifier(identificador);
                if (clientHandler != null) {
                    // Envia la alerta al cliente si el manejador del cliente no es nulo
                    clientHandler.enviarAlerta(alerta);
                    // Imprime un mensaje en la consola indicando que la alerta fue enviada al cliente
                    System.out.println("Alerta enviada al cliente: " + identificador);
                } else {
                    // Imprime un mensaje en la consola si no se pudo enviar la alerta al cliente
                    System.out.println("No se pudo enviar la alerta al cliente: " + identificador);
                }
                // Muestra una alerta en la interfaz de usuario
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Alerta de uso de CPU");
                    alert.setHeaderText(null);
                    alert.setContentText("El uso de CPU es de " + cpu + "%, se enviará una alerta al cliente " + identificador + ".");
                    alert.showAndWait();
                });
            }
        }).start();
        // El hilo duerme durante 5 segundos
        Thread.sleep(5000);
    }

    /**
     * Monitoreo memoria.
     * <p>
     *     Método para monitorear el uso de memoria y enviar una alerta si supera un cierto umbral
     *     <p>
     *         Si el uso de memoria supera el valor del slider, se imprime un mensaje en la consola, se crea una alerta, se obtiene el manejador del cliente por su identificador y se envía la alert
     *         a al cliente si el manejador del cliente no es nulo
     *         Si no se pudo enviar la alerta al cliente, se imprime un mensaje en la consola
     *         Se muestra una alerta en la interfaz de usuario
     *         El hilo duerme durante 10 segundos
     *
     * @param memoria       the memoria
     * @param identificador the identificador
     * @throws InterruptedException the interrupted exception
     */
    public void monitoreoMemoria(double memoria, String identificador) throws InterruptedException {
        if (memoria >= controladorController.getMemoriaSliderValue()) {
            // Imprime un mensaje en la consola si el uso de memoria supera el valor del slider
            System.out.println("Alerta: El uso de memoria supera el " + controladorController.getMemoriaSliderValue() + "% con un valor de " + memoria + "%");
            // Crea una alerta si el uso de memoria supera el valor del slider
            String alerta = "El uso de memoria supera el " + controladorController.getMemoriaSliderValue() + "% con un valor de " + memoria + "%";
            // Obtiene el manejador del cliente por su identificador
            ClientHandler clientHandler = ClientHandler.getClientHandlerByIdentifier(identificador);
            if (clientHandler != null) {
                // Envia la alerta al cliente si el manejador del cliente no es nulo
                clientHandler.enviarAlerta(alerta);
                // Imprime un mensaje en la consola indicando que la alerta fue enviada al cliente
                System.out.println("Alerta enviada al cliente: " + identificador);
            } else {
                // Imprime un mensaje en la consola si no se pudo enviar la alerta al cliente
                System.out.println("No se pudo enviar la alerta al cliente: " + identificador);
            }
            // Muestra una alerta en la interfaz de usuario
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Alerta de uso de Memoria");
                alert.setHeaderText(null);
                alert.setContentText("El uso de memoria es de " + memoria + "%, se enviará una alerta al cliente " + identificador + ".");
                alert.showAndWait();
            });
            // El hilo duerme durante 10 segundos
            Thread.sleep(10000);
        }
    }

    /**
     * Monitoreo disco.
     * <p>
     *     Método para monitorear el uso de disco y enviar una alerta si supera un cierto umbral
     *     <p>
     *         Si el uso de disco supera el valor del slider, se imprime un mensaje en la consola, se crea una alerta, se obtiene el manejador del cliente por su identificador y se envía la alerta al cliente si el manejador del cliente no es nulo
     *         Si no se pudo enviar la alerta al cliente, se imprime un mensaje en la consola
     *         Se muestra una alerta en la interfaz de usuario
     *         El hilo duerme durante 10 segundos
     *
     * @param disco         the disco
     * @param identificador the identificador
     * @throws InterruptedException the interrupted exception
     */
    public void monitoreoDisco(double disco, String identificador) throws InterruptedException {
        if (disco >= controladorController.getDiscoSliderValue()) {
            // Imprime un mensaje en la consola si el uso de disco supera el valor del slider
            System.out.println("Alerta: El uso de disco supera el " + controladorController.getDiscoSliderValue() + "%");
            // Crea una alerta si el uso de disco supera el valor del slider
            String alerta = "El uso de disco supera el " + controladorController.getDiscoSliderValue() + "% con un valor de " + disco + "%";
            // Obtiene el manejador del cliente por su identificador
            ClientHandler clientHandler = ClientHandler.getClientHandlerByIdentifier(identificador);
            if (clientHandler != null) {
                // Envia la alerta al cliente si el manejador del cliente no es nulo
                clientHandler.enviarAlerta(alerta);
                // Imprime un mensaje en la consola indicando que la alerta fue enviada al cliente
                System.out.println("Alerta enviada al cliente: " + identificador);
            } else {
                // Imprime un mensaje en la consola si no se pudo enviar la alerta al cliente
                System.out.println("No se pudo enviar la alerta al cliente: " + identificador);
            }
            // Muestra una alerta en la interfaz de usuario
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Alerta de uso de Disco");
                alert.setHeaderText(null);
                alert.setContentText("El uso de disco es de " + disco + "%, se enviará una alerta al cliente " + identificador + ".");
                alert.showAndWait();
            });
            // El hilo duerme durante 10 segundos
            Thread.sleep(10000);
        }
    }
}