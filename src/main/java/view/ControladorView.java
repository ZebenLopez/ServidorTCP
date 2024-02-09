package view;

import controller.ControladorController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import services.Conexion;

import java.io.IOException;

/**
 * The type Controlador view.
 * <p>
 *     Clase ControladorView que se encarga de mostrar la vista del controlador
 *
 * @author Zebenzuí López Conde
 * @version 1.0  2ºA DAM
 */
public class ControladorView {
    static Conexion conexion;
    private static Thread conexionThread;

    // Stage actual
    private static Stage stage = new Stage();

    /**
     * Show.
     * <p>
     *     Método para mostrar la vista del controlador
     *     <p>
     *         Oculta el stage actual
     *         Crea un nuevo Stage
     *         Cargador FXML para cargar la vista principal
     *         Crea una nueva escena con la vista principal
     *         Establece el título del nuevo escenario
     *         Establece la escena del nuevo escenario
     *         Hace que el nuevo escenario no sea redimensionable
     *         Cierra la aplicación cuando se cierra el nuevo escenario
     *         Muestra el nuevo escenario
     *         Obtiene el controlador de la vista principal
     *         Crea un nuevo hilo para conectar con el servidor
     *         Intenta conectar con el servidor
     *         Lanza una excepción si ocurre un error al conectar con el servidor
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
        conexionThread =  new Thread(() -> {
            conexion = new Conexion();
            try {
                // Intenta conectar con el servidor
                conexion.conectar();
            } catch (IOException e) {
                // Lanza una excepción si ocurre un error al conectar con el servidor
                System.out.println("Error al conectar con el servidor");
            }
        });
        conexionThread.start();
    }

    public void stop() {
        if (conexionThread != null) {
            conexionThread.interrupt();
            conexionThread = null;
            stage.close();
        }
        conexion.stop();
    }
}