package view;

import controller.RegistroController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The type Registro view.
 * <p>
 *     Clase RegistroView que se encarga de mostrar la vista de registro
 *
 * @author Zebenzuí López Conde
 * @version 1.0  2ºA DAM
 */
public class RegistroView {
    // Stage actual
    private static Stage stage = new Stage();

    /**
     * Show.
     * <p>
     *     Método para mostrar la vista de registro
     *     <p>
     *         Oculta el stage actual
     *         Crea un nuevo Stage
     *         Cargador FXML para cargar la vista de registro
     *         Crea una nueva escena con la vista de registro
     *         Establece el título del nuevo escenario
     *         Establece la escena del nuevo escenario
     *         Hace que el nuevo escenario no sea redimensionable
     *         Cierra la aplicación cuando se cierra el nuevo escenario
     *         Muestra el nuevo escenario
     *
     * @throws IOException the io exception
     */
    public static void show() throws IOException {
        // Oculta el stage actual
        stage.hide();
        // Crea un nuevo Stage
        Stage newStage = new Stage();

        // Cargador FXML para cargar la vista de registro
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("registro-view.fxml"));
        // Crea una nueva escena con la vista de registro
        Scene scene = new Scene(fxmlLoader.load());

        // Establece el título del nuevo escenario
        newStage.setTitle("Registro");
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

        // Obtiene el controlador de la vista de registro
        RegistroController registroController = fxmlLoader.getController();
    }
}