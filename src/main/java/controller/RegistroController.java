package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import models.User;
import view.HelloApplication;
import view.RegistroView;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RegistroController {
    public TextField usuario;
    public TextField password;
    public TextField repetirpassword;

    private static List<User> registeredUsers = new ArrayList<>();


 public void registro(ActionEvent actionEvent) throws IOException {
    if (usuario.getText().equals("") || password.getText().equals("") || repetirpassword.getText().equals("")) {
        System.out.println("Rellene todos los campos");
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Rellene todos los campos");
        alert.setContentText("Por favor, inténtelo de nuevo");
        alert.showAndWait();
    } else if (!password.getText().equals(repetirpassword.getText())) {
        System.out.println("Las contraseñas no coinciden");
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Las contraseñas no coinciden");
        alert.setContentText("Por favor, inténtelo de nuevo");
        alert.showAndWait();
    } else if (usuarioExisteEnFichero(usuario.getText())) {
        System.out.println("El nombre de usuario ya existe");
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("El nombre de usuario ya existe");
        alert.setContentText("Por favor, elija un nombre de usuario diferente");
        alert.showAndWait();
    } else {
        User newUser = new User(usuario.getText(), password.getText());
        registeredUsers.add(newUser);
        guardarUsuariosEnFichero(newUser);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Registro");
        alert.setHeaderText(null);
        alert.setContentText("Usuario registrado con éxito");
        alert.showAndWait();
        ((Button) actionEvent.getSource()).getScene().getWindow().hide();
        HelloApplication.showLogin();
    }
}

    public void atras(ActionEvent actionEvent) throws IOException {
        ((Button) actionEvent.getSource()).getScene().getWindow().hide();
        HelloApplication.showLogin();
    }
public void guardarUsuariosEnFichero(User user) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("usuarios.txt", true))) {
        writer.write(user.getUsername() + "," + user.getPassword());
        writer.newLine();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    public boolean usuarioExisteEnFichero(String usuario) {
        try (BufferedReader reader = new BufferedReader(new FileReader("usuarios.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(usuario)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
