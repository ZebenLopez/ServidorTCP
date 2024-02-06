package services;

import controller.ControladorController;
import javafx.application.Platform;
import javafx.scene.control.Alert;

public class MonitoreoAlertas {
    ControladorController controladorController;

    public MonitoreoAlertas(ControladorController controladorController) {
        this.controladorController = controladorController;
    }

    public void monitoreoCPU(double cpu, String identificador) throws InterruptedException {
        new Thread(() -> {
            if (cpu >= controladorController.getCpuSliderValue()) {
                System.out.println("Alerta: El uso de CPU supera el " + controladorController.getCpuSliderValue() + "% con un valor de " + cpu + "%");
                String alerta = "El uso de CPU supera el " + controladorController.getCpuSliderValue() + "% con un valor de " + cpu + "%";
                ClientHandler clientHandler = ClientHandler.getClientHandlerByIdentifier(identificador);
                if (clientHandler != null) {
                    clientHandler.enviarAlerta(alerta);
                    System.out.println("Alerta enviada al cliente: " + identificador);
                } else {
                    System.out.println("No se pudo enviar la alerta al cliente: " + identificador);
                }
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Alerta de uso de CPU");
                    alert.setHeaderText(null);
                    alert.setContentText("El uso de CPU es de " + cpu + "%, se enviará una alerta al cliente " + identificador + ".");
                    alert.showAndWait();
                });
            }
        }).start();
        Thread.sleep(5000);

    }

    public void monitoreoMemoria(double memoria, String identificador) throws InterruptedException {
        if (memoria >= controladorController.getMemoriaSliderValue()) {
            System.out.println("Alerta: El uso de memoria supera el " + controladorController.getMemoriaSliderValue() + "% con un valor de " + memoria + "%");
            String alerta = "El uso de memoria supera el " + controladorController.getMemoriaSliderValue() + "% con un valor de " + memoria + "%";
            ClientHandler clientHandler = ClientHandler.getClientHandlerByIdentifier(identificador);
            if (clientHandler != null) {
                clientHandler.enviarAlerta(alerta);
                System.out.println("Alerta enviada al cliente: " + identificador);
            } else {
                System.out.println("No se pudo enviar la alerta al cliente: " + identificador);
            }
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Alerta de uso de Memoria");
                alert.setHeaderText(null);
                alert.setContentText("El uso de memoria es de " + memoria + "%, se enviará una alerta al cliente " + identificador + ".");
                alert.showAndWait();
            });
            Thread.sleep(10000);
        }
    }

    public void monitoreoDisco(double disco, String identificador) throws InterruptedException {
        if (disco >= controladorController.getDiscoSliderValue()) {
            System.out.println("Alerta: El uso de disco supera el " + controladorController.getDiscoSliderValue() + "%");
            String alerta = "El uso de disco supera el " + controladorController.getDiscoSliderValue() + "% con un valor de " + disco + "%";
            ClientHandler clientHandler = ClientHandler.getClientHandlerByIdentifier(identificador);
            if (clientHandler != null) {
                clientHandler.enviarAlerta(alerta);
                System.out.println("Alerta enviada al cliente: " + identificador);
            } else {
                System.out.println("No se pudo enviar la alerta al cliente: " + identificador);
            }
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Alerta de uso de Disco");
                alert.setHeaderText(null);
                alert.setContentText("El uso de disco es de " + disco + "%, se enviará una alerta al cliente " + identificador + ".");
                alert.showAndWait();
            });
            Thread.sleep(10000);
        }
    }
}
