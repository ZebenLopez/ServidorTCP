package view;

import controller.ControladorController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import services.Conexion;

import java.io.IOException;

public class HelloApplication extends Application {

    private static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        Stage newStage = new Stage();

        HelloApplication.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> {
            System.exit(0); // Cierra la aplicación cuando se cierra el escenario
        });
        stage.show();
    }

    public static void showLogin() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Login!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException {
        launch();
    }
}