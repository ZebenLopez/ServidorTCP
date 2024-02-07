package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * The type Hello application.
 *
 * @author Zebenzuí López Conde
 * @version 1.0  2ºA DAM
 */
// Clase HelloApplication que se encarga de iniciar la aplicación y mostrar la vista de inicio de sesión
public class HelloApplication extends Application {

    // Stage actual
    private static Stage stage;

    // Método para iniciar la aplicación
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
     *
     * @throws IOException the io exception
     */
// Método para mostrar la vista de inicio de sesión
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
     *
     * @param args the input arguments
     * @throws IOException the io exception
     */
// Método principal para iniciar la aplicación
    public static void main(String[] args) throws IOException {
        // Lanza la aplicación
        launch();
    }
}