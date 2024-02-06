package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import view.ControladorView;
import view.RegistroView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Zebenzuí López Conde
 * @version 1.0
 * 2ºA DAM
 */
public class HelloController {


    public TextField usuario;
    public TextField password;

    public void login(ActionEvent actionEvent) throws IOException {
        if (usuario.getText().equals("") || password.getText().equals("")) {
            System.out.println("Rellene todos los campos");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Rellene todos los campos");
            alert.setContentText("Por favor, inténtelo de nuevo");
            alert.showAndWait();
        } else if (verificarUsuario(usuario.getText(), password.getText())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login");
            alert.setHeaderText(null);
            alert.setContentText("Usuario logueado con éxito");
            alert.showAndWait();
            ((Button) actionEvent.getSource()).getScene().getWindow().hide();
            ControladorView.show();
        } else {
            System.out.println("Usuario o contraseña incorrectos");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Usuario o contraseña incorrectos");
            alert.setContentText("Por favor, inténtelo de nuevo");
            alert.showAndWait();
        }

    }

    public void registro(ActionEvent actionEvent) throws IOException {
        ((Button) actionEvent.getSource()).getScene().getWindow().hide();
        RegistroView.show();
    }


    public boolean verificarUsuario(String usuario, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("usuarios.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(usuario) && parts[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void olvidoContraseña(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Olvidó su contraseña");
        alert.setHeaderText("Esta vez te ayudo por ser el/la profesor/a.\nPero no te acostumbres.\nTambién te puedes registrar.");
        alert.setContentText("Usuario: admin\nContraseña: admin");
        alert.showAndWait();
    }
}