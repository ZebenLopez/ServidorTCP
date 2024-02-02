package controller;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import services.ClientHandler;

import java.net.URL;
import java.util.ResourceBundle;

public class ControladorController {
    @FXML
    public Label cpuLabel;
    public Label sistema;
    public Label memoriaLabel;
    public Label espacioDisco;
    public Label libreDisco;
    public Label porcentajeDisco;
    public ListView<String> lista;

    // Constructor sin argumentos
    public ControladorController() {
    }

    @FXML
    public void initialize() {
        if (ClientHandler.getClientIdentifiers() != null) {
            sistema.setText(ClientHandler.getSistema());
        }
        new Thread(() -> {
            while (true){
                try {
                    Thread.sleep(2000);
                    System.out.println(ClientHandler.getCpu());
                    Platform.runLater(() -> {
                        cpuLabel.setText(String.valueOf(ClientHandler.getCpu()) + "%");
                        memoriaLabel.setText(String.valueOf(ClientHandler.getMemoria()) + "%");
                        sistema.setText(ClientHandler.getSistema());
                        espacioDisco.setText(String.valueOf(ClientHandler.getEspacioDisco()) + " GB");
                        libreDisco.setText(String.valueOf(ClientHandler.getEspacioLibre()) + " GB");
                        porcentajeDisco.setText(String.valueOf(ClientHandler.getPorcentajeOcupado()) + "%");
                    });
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

}