package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import view.ControladorView;
import view.RegistroView;

import java.io.IOException;

public class HelloController {


    public void login(ActionEvent actionEvent) throws IOException {
        ((Button) actionEvent.getSource()).getScene().getWindow().hide();
        ControladorView.show();
    }

    public void registro(ActionEvent actionEvent) throws IOException {
        ((Button) actionEvent.getSource()).getScene().getWindow().hide();
        RegistroView.show();
    }
}