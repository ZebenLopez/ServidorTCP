package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import view.HelloApplication;
import view.RegistroView;

import java.io.IOException;

public class RegistroController {
    public TextField usuario;
    public TextField password;
    public TextField repetirpassword;

    public void registro(ActionEvent actionEvent) throws IOException {
        ((Button) actionEvent.getSource()).getScene().getWindow().hide();
        HelloApplication.showLogin();
    }

    public void atras(ActionEvent actionEvent) throws IOException {
        ((Button) actionEvent.getSource()).getScene().getWindow().hide();
        HelloApplication.showLogin();
    }
}
