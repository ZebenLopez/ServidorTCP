package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import view.ControladorView;
import view.RegistroView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * The type Hello controller.
 * <p>
 *     Clase HelloController que se encarga de la lógica de la pantalla de inicio de sesión
 *
 * @author Zebenzuí López Conde
 * @version 1.0  2ºA DAM
 */
public class HelloController {

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
     * Login.
     * <p>
     *     Método que se llama cuando se hace clic en el botón de inicio de sesión
     *     <p>
     *         Verifica si el usuario y la contraseña son correctos y muestra un mensaje de éxito o error
     *         <p>
     *             Si el usuario y la contraseña son correctos, abre la vista del controlador<br>
     *             Si el usuario o la contraseña son incorrectos, muestra un mensaje de error<br>
     *             Si los campos de texto están vacíos, muestra un mensaje de error<br>
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void login(ActionEvent actionEvent) throws IOException {
        // Verifica si los campos de texto están vacíos
        if (usuario.getText().equals("") || password.getText().equals("")) {
            // Muestra un mensaje de error si los campos de texto están vacíos
            System.out.println("Rellene todos los campos");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Rellene todos los campos");
            alert.setContentText("Por favor, inténtelo de nuevo");
            alert.showAndWait();
        } else if (verificarUsuario(usuario.getText(), password.getText())) {
            // Si el usuario y la contraseña son correctos, muestra un mensaje de éxito y abre la vista del controlador
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login");
            alert.setHeaderText(null);
            alert.setContentText("Usuario logueado con éxito");
            alert.showAndWait();
            ((Button) actionEvent.getSource()).getScene().getWindow().hide();
            ControladorView.show();
        } else {
            // Si el usuario o la contraseña son incorrectos, muestra un mensaje de error
            System.out.println("Usuario o contraseña incorrectos");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Usuario o contraseña incorrectos");
            alert.setContentText("Por favor, inténtelo de nuevo");
            alert.showAndWait();
        }
    }

    /**
     * Registro.
     * <p>
     *     Método que se llama cuando se hace clic en el botón de registro
     *     <p>
     *         Cierra la ventana actual y abre la vista de registro
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void registro(ActionEvent actionEvent) throws IOException {
        // Cierra la ventana actual y abre la vista de registro
        ((Button) actionEvent.getSource()).getScene().getWindow().hide();
        RegistroView.show();
    }

    /**
     * Verificar usuario boolean.
     * <p>
     *     Método para verificar si el usuario y la contraseña son correctos
     *     <p>
     *         Lee el archivo de usuarios y verifica si el usuario y la contraseña son correctos
     *
     * @param usuario  the usuario
     * @param password the password
     * @return the boolean
     */
    public boolean verificarUsuario(String usuario, String password) {
        // Lee el archivo de usuarios y verifica si el usuario y la contraseña son correctos
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

    /**
     * Olvido contraseña.
     * <p>
     *     Método que se llama cuando se hace clic en el enlace de olvido de contraseña
     *
     * @param mouseEvent the mouse event
     */
    public void olvidoContrasegna(MouseEvent mouseEvent) {
        // Muestra un mensaje con la contraseña de administrador
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Olvidó su contraseña");
        alert.setHeaderText("Esta vez te ayudo por ser el/la profesor/a.\nPero no te acostumbres.\nTambién te puedes registrar.");
        alert.setContentText("Usuario: admin\nContraseña: admin");
        alert.showAndWait();
    }

    public void abrirAyuda(KeyEvent keyEvent) {
        // Abre el archivo de ayuda
        services.Ayuda.abrirHelp();
    }
}