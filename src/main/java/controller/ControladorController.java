package controller;


import javafx.application.Platform;
import javafx.collections.SetChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import services.ClientHandler;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

// Clase ControladorController
public class ControladorController {
    String selectedClient;

    // Etiquetas y lista en la interfaz de usuario
    @FXML
    public Label cpuLabel;
    public Label sistema;
    public Label memoriaLabel;
    public Label espacioDisco;
    public Label libreDisco;
    public Label porcentajeDisco;
    public ListView<String> lista;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);


    // Hilo que se ejecuta indefinidamente y actualiza los datos del cliente seleccionado en la interfaz de usuario cada segundo
    Thread updateThread = new Thread(() -> {
        while (true) {
            try {
                Thread.sleep(1000);
                Platform.runLater(() -> {
                    String selectedClient = lista.getSelectionModel().getSelectedItem();
                    if (selectedClient != null) {
                        actualizarDatosCliente(selectedClient);
                    }
                });
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    });

    // Constructor sin argumentos
    public ControladorController() {
    }

    @FXML
    public void initialize() {
        // Añade un SetChangeListener a clientIdentifiers para actualizar la lista cuando se conecta o desconecta un cliente
        ClientHandler.getClientIdentifiers().addListener((SetChangeListener<String>) change -> {
            if (change.wasAdded()) {
                Platform.runLater(() -> {
                    lista.getItems().add(change.getElementAdded());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Cliente conectado");
                    alert.setHeaderText(null);
                    alert.setContentText("Cliente conectado: " + change.getElementAdded());
                    alert.showAndWait();
                });
            } else if (change.wasRemoved()) {
                Platform.runLater(() -> {
                    lista.getItems().remove(change.getElementRemoved());
                    lista.getSelectionModel().clearSelection();
                    borrarDatosCliente();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Cliente desconectado");
                    alert.setHeaderText(null);
                    alert.setContentText("Cliente desconectado: " + change.getElementRemoved());
                    alert.showAndWait();
                });
            }
        });
        updateThread.start();
    }

    // Método que se llama cuando se selecciona un nuevo cliente en la lista o cuando se necesita actualizar los datos del cliente seleccionado
    public void actualizarDatosCliente(String identificadorCliente) {
        // Busca el cliente con el identificador dado y actualiza las etiquetas en la interfaz de usuario con los datos del cliente
        ClientHandler clientHandler = ClientHandler.getClientHandlerByIdentifier(identificadorCliente);
        if (clientHandler != null){
            cpuLabel.setText((clientHandler.getCpu()) + "%");
            memoriaLabel.setText((clientHandler.getMemoria()) + "%");
            sistema.setText(clientHandler.getSistema());
            espacioDisco.setText((clientHandler.getEspacioDisco()) + " GB");
            libreDisco.setText((clientHandler.getEspacioLibre()) + " GB");
            porcentajeDisco.setText((clientHandler.getPorcentajeOcupado()) + "%");
        }
    }
    public void borrarDatosCliente() {
        cpuLabel.setText("");
        memoriaLabel.setText("");
        sistema.setText("");
        espacioDisco.setText("");
        libreDisco.setText("");
        porcentajeDisco.setText("");
    }
}