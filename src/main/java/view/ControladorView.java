package view;

import controller.ControladorController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import services.Conexion;

import java.io.IOException;

/**
 * The type Controlador view.
 *
 * @author Zebenzuí López Conde
 * @version 1.0  2ºA DAM
 */
// Clase ControladorView que se encarga de mostrar la vista del controlador
public class ControladorView {
    // Stage actual
    private static Stage stage = new Stage();

    /**
     * Show.
     *
     * @throws IOException the io exception
     */
// Método para mostrar la vista del controlador
    public static void show() throws IOException {
        // Oculta el stage actual
        stage.hide();
        // Crea un nuevo Stage
        Stage newStage = new Stage();

        // Cargador FXML para cargar la vista principal
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("controlador-view.fxml"));
        // Crea una nueva escena con la vista principal
        Scene scene = new Scene(fxmlLoader.load());
        // Establece el título del nuevo escenario
        newStage.setTitle("Contorlador");
        // Establece la escena del nuevo escenario
        newStage.setScene(scene);
        // Hace que el nuevo escenario no sea redimensionable
        newStage.setResizable(false);
        // Cierra la aplicación cuando se cierra el nuevo escenario
        newStage.setOnCloseRequest(event -> {
            System.exit(0);
        });
        // Muestra el nuevo escenario
        newStage.show();

        // Obtiene el controlador de la vista principal
        ControladorController controladorController = fxmlLoader.getController();
        // Crea un nuevo hilo para conectar con el servidor
        new Thread(() -> {
            Conexion conexion = new Conexion();
            try {
                // Intenta conectar con el servidor
                conexion.conectar();
            } catch (IOException e) {
                // Lanza una excepción si ocurre un error al conectar con el servidor
                throw new RuntimeException(e);
            }
        }).start();
    }
}