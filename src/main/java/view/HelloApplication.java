package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * The type Hello application.
 * <p>
 *     Clase HelloApplication que se encarga de iniciar la aplicación y mostrar la vista de inicio de sesión
 *
 * @author Zebenzuí López Conde
 * @version 1.0  2ºA DAM
 */
public class HelloApplication extends Application {

    // Stage actual
    private static Stage stage;

    /**
     * Start.
     * <p>
     *     Método para iniciar la aplicación y mostrar la vista de inicio de sesión
     *     <p>
     *         Crear un nuevo Stage
     *         Establecer el stage actual
     *         Cargador FXML para cargar la vista de inicio de sesión
     *         Crear una nueva escena con la vista de inicio de sesión
     *         Establecer el título del escenario
     *         Establecer la escena del escenario
     *         Cerrar la aplicación cuando se cierra el escenario
     *         Mostrar el escenario
     *
     * @param stage the stage
     * @throws IOException the io exception
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Crea un nuevo Stage
        Stage newStage = new Stage();

        // Establece el stage actual
        HelloApplication.stage = stage;
        // Cargador FXML para cargar la vista de inicio de sesión
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        // Crea una nueva escena con la vista de inicio de sesión
        Scene scene = new Scene(fxmlLoader.load());
        // Establece el título del escenario
        stage.setTitle("Login");
        // Establece la escena del escenario
        stage.setScene(scene);
        // Cierra la aplicación cuando se cierra el escenario
        stage.setOnCloseRequest(event -> {
            System.exit(0);
        });
        // Muestra el escenario
        stage.show();
    }

    /**
     * Show login.
     * <p>
     *     Método para mostrar la vista de inicio de sesión
     *     <p>
     *         Cargador FXML para cargar la vista de inicio de sesión
     *         Crea una nueva escena con la vista de inicio de sesión
     *         Establece el título del escenario
     *
     * @throws IOException the io exception
     */
    public static void showLogin() throws IOException {
        // Cargador FXML para cargar la vista de inicio de sesión
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        // Crea una nueva escena con la vista de inicio de sesión
        Scene scene = new Scene(fxmlLoader.load());
        // Establece el título del escenario
        stage.setTitle("Login!");
        // Establece la escena del escenario
        stage.setScene(scene);
        // Muestra el escenario
        stage.show();
    }

    /**
     * The entry point of application.
     * <p>
     *     Método principal para iniciar la aplicación
     *
     * @param args the input arguments
     * @throws IOException the io exception
     */
    public static void main(String[] args) throws IOException {
        launch();
    }
}