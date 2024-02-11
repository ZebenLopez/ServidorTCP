package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import models.User;
import services.Ayuda;
import view.HelloApplication;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Registro controller.
 * Clase RegistroController que se encarga de la lógica de la pantalla de registro
 *
 * @author Zebenzuí López Conde
 * @version 1.0
 * 2ºA DAM
 */

public class RegistroController {
    /**
     * The Usuario.
     */
// Campos de texto para el usuario y la contraseña
    public TextField usuario;
    /**
     * The Password.
     */
    public TextField password;
    /**
     * The Repetirpassword.
     */
    public TextField repetirpassword;

    // Lista de usuarios registrados
    private static List<User> registeredUsers = new ArrayList<>();

    /**
     * Registro.
     * <p>
     *     Método que se llama cuando se hace clic en el botón de registro
     *     <p>
     *         Verifica si los campos de texto están vacíos<br>
     *         Muestra un mensaje de error si los campos de texto están vacíos<br>
     *         Muestra un mensaje de error si las contraseñas no coinciden<br>
     *         Verifica si el nombre de usuario ya existe en el fichero<br>
     *         Muestra un mensaje de error si el nombre de usuario ya existe<br>
     *         Si todo está correcto, registra al usuario y muestra un mensaje de éxito<br>
     *         Cierra la ventana actual y abre la vista de inicio de sesión<br>
     *         Guarda los usuarios registrados en un fichero<br>
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
// Método que se llama cuando se hace clic en el botón de registro
    public void registro(ActionEvent actionEvent) throws IOException {
        // Verifica si los campos de texto están vacíos
        if (usuario.getText().equals("") || password.getText().equals("") || repetirpassword.getText().equals("")) {
            // Muestra un mensaje de error si los campos de texto están vacíos
            System.out.println("Rellene todos los campos");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Rellene todos los campos");
            alert.setContentText("Por favor, inténtelo de nuevo");
            alert.showAndWait();
        } else if (!password.getText().equals(repetirpassword.getText())) {
            // Muestra un mensaje de error si las contraseñas no coinciden
            System.out.println("Las contraseñas no coinciden");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Las contraseñas no coinciden");
            alert.setContentText("Por favor, inténtelo de nuevo");
            alert.showAndWait();
        } else if (usuarioExisteEnFichero(usuario.getText())) {
            // Muestra un mensaje de error si el nombre de usuario ya existe
            System.out.println("El nombre de usuario ya existe");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("El nombre de usuario ya existe");
            alert.setContentText("Por favor, elija un nombre de usuario diferente");
            alert.showAndWait();
        } else {
            // Si todo está correcto, registra al usuario y muestra un mensaje de éxito
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

    /**
     * Atras.
     * <p>
     *     Método que se llama cuando se hace clic en el botón de atrás
     *     <p>
     *         Cierra la ventana actual y abre la vista de inicio de sesión
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
// Método que se llama cuando se hace clic en el botón de atrás
    public void atras(ActionEvent actionEvent) throws IOException {
        // Cierra la ventana actual y abre la vista de inicio de sesión
        ((Button) actionEvent.getSource()).getScene().getWindow().hide();
        HelloApplication.showLogin();
    }

    /**
     * Guardar usuarios en fichero.
     * <p>
     *     Método para guardar los usuarios registrados en un fichero
     *
     * @param user the user
     */
// Método para guardar los usuarios registrados en un fichero
    public void guardarUsuariosEnFichero(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("usuarios.txt", true))) {
            writer.write(user.getUsername() + "," + user.getPassword());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Usuario existe en fichero boolean.
     * <p>
     *     Método para verificar si el nombre de usuario ya existe en el fichero
     *
     * @param usuario the usuario
     * @return the boolean
     */
// Método para verificar si el nombre de usuario ya existe en el fichero
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

    public void abrirAyuda(KeyEvent keyEvent) {
        if (keyEvent.getCode().toString().equals("F1")){
            Ayuda.abrirHelp();
        }
    }
}