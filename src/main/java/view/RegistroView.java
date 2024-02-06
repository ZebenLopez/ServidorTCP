package view;

import controller.RegistroController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 *
 * @author Zebenzuí López Conde
 * @version 1.0
 * 2ºA DAM
 */

public class RegistroView {
    private static Stage stage = new Stage();
    public static void show() throws IOException {
        stage.hide();
        // Crea un nuevo Stage con el nuevo estilo.
        Stage newStage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("registro-view.fxml")); // Cargador FXML para cargar la vista principal
        Scene scene = new Scene(fxmlLoader.load()); // Crea una nueva escena con la vista principal
        newStage.setTitle("Panel de registro"); // Establece el título del nuevo escenario
        newStage.setScene(scene); // Establece la escena del nuevo escenario
        newStage.setResizable(false); // Hace que el nuevo escenario no sea redimensionable
        newStage.setOnCloseRequest(event -> {
            System.exit(0); // Cierra la aplicación cuando se cierra el nuevo escenario
        });
        newStage.show(); // Muestra el nuevo escenario

        RegistroController registroController = fxmlLoader.getController(); // Obtiene el controlador de la vista principal
    }
}
