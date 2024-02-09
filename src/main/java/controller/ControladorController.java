package controller;

import eu.hansolo.medusa.Gauge;
import javafx.application.Platform;
import javafx.collections.SetChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import net.sf.jasperreports.engine.JRException;
import services.*;
import view.ControladorView;
import view.HelloApplication;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
/**
 * The type Controlador controller.
 *
 * @author Zebenzuí López Conde
 * @version 1.0
 * 2ºA DAM
 */

/**
 * Clase ControladorController que se encarga de controlar la interfaz de usuario y la lógica de la aplicación
 */
public class ControladorController {
    // Agrega estos campos para mantener una referencia a las instancias
    private ControladorView controladorView;
    private Conexion conexion;
    public Gauge graficoCPU;
    public Gauge graficoRAM;
    public Label temperaturaLabel;
    public Gauge graficoDisco;
    public Gauge graficoTemperatura;
    /**
     * The Monitoreo alertas.
     * MonitoreoAlertas monitoreoAlertas;
     */
    MonitoreoAlertas monitoreoAlertas;


    // Variable para controlar si el hilo debe seguir ejecutándose o no
    private volatile boolean running = true;
    /**
     * The Cpu slider.
     */
// Sliders y labels para la interfaz de usuario
    public Slider cpuSlider;
    /**
     * The Memoria slider.
     */
    public Slider memoriaSlider;
    /**
     * The Disco slider.
     */
    public Slider discoSlider;
    /**
     * The Cpu label slider.
     */
    public Label cpuLabelSlider;
    /**
     * The Memoria label slider.
     */
    public Label memoriaLabelSlider;
    /**
     * The Disco label slider.
     */
    public Label discoLabelSlider;
    /**
     * The Selected client.
     */
// String para almacenar el cliente seleccionado
    public String selectedClient;

    /**
     * The Cpu label.
     */
// Etiquetas y lista en la interfaz de usuario
    @FXML
    public Label cpuLabel;
    /**
     * The Sistema.
     */
    public Label sistema;
    /**
     * The Memoria label.
     */
    public Label memoriaLabel;
    /**
     * The Espacio disco.
     */
    public Label espacioDisco;
    /**
     * The Libre disco.
     */
    public Label libreDisco;
    /**
     * The Porcentaje disco.
     */
    public Label porcentajeDisco;
    /**
     * The Lista.
     */
    public ListView<String> lista;

    // ScheduledExecutorService para programar tareas en un hilo separado
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    /**
     * Instantiates a new Controlador controller.
     */
// Constructor de la clase ControladorController
    public ControladorController() {
        this.monitoreoAlertas = new MonitoreoAlertas(this);
    }

    /**
     * The Update thread.
     * Hilo que se ejecuta indefinidamente y actualiza los datos del cliente seleccionado en la interfaz de usuario cada segundo
     */
// Hilo que se ejecuta indefinidamente y actualiza los datos del cliente seleccionado en la interfaz de usuario cada segundo
    Thread updateThread = new Thread(() -> {
        while (running) {
            try {
                Thread.sleep(1000);
                Platform.runLater(() -> {
                    String selectedClient = lista.getSelectionModel().getSelectedItem();
                    if (selectedClient != null) {
                        try {
                            actualizarDatosCliente(selectedClient);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    });

    /**
     * Initialize.
     * Método que se llama al inicializar la interfaz de usuario
     * <p>
     * Añade un SetChangeListener a clientIdentifiers para actualizar la lista cuando se conecta o desconecta un cliente
     * Configura los sliders
     * Inicia el hilo updateThread
     */
    @FXML
    public void initialize() {
        // Añade un SetChangeListener a clientIdentifiers para actualizar la lista cuando se conecta o desconecta un cliente
        ClientHandler.getClientIdentifiers().addListener((SetChangeListener<String>) change -> {
            if (change.wasAdded()) {
                Platform.runLater(() -> {
                    lista.getItems().add(change.getElementAdded());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Cliente conectado");
                    alert.setHeaderText(null);
                    alert.setContentText("Cliente conectado: " + change.getElementAdded());
                    alert.showAndWait();
                });
            } else if (change.wasRemoved()) {
                Platform.runLater(() -> {
                    lista.getItems().remove(change.getElementRemoved());
                    lista.getSelectionModel().clearSelection();
                    borrarDatosCliente();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Cliente desconectado");
                    alert.setHeaderText(null);
                    alert.setContentText("Cliente desconectado: " + change.getElementRemoved());
                    alert.showAndWait();
                });
            }
        });
        configureSlider(cpuSlider, cpuLabelSlider);
        configureSlider(memoriaSlider, memoriaLabelSlider);
        configureSlider(discoSlider, discoLabelSlider);
        updateThread.start();

        controladorView = new ControladorView();
        conexion = new Conexion();
    }

    /**
     * Actualizar datos cliente.
     * <p>
     * Método que se llama cuando se selecciona un nuevo cliente en la lista o cuando se necesita actualizar los datos del cliente seleccionado
     * <p>
     *      Busca el cliente con el identificador dado y actualiza las etiquetas en la interfaz de usuario con los datos del cliente
     *
     * @param identificadorCliente the identificador cliente
     * @throws InterruptedException the interrupted exception
     */
// Método que se llama cuando se selecciona un nuevo cliente en la lista o cuando se necesita actualizar los datos del cliente seleccionado
    public void actualizarDatosCliente(String identificadorCliente) throws InterruptedException {
        // Busca el cliente con el identificador dado y actualiza las etiquetas en la interfaz de usuario con los datos del cliente
        ClientHandler clientHandler = ClientHandler.getClientHandlerByIdentifier(identificadorCliente);
        if (clientHandler != null) {
            cpuLabel.setText((clientHandler.getCpu()) + "%");
            memoriaLabel.setText((clientHandler.getMemoria()) + "%");
            sistema.setText(clientHandler.getSistema());
            espacioDisco.setText((clientHandler.getEspacioDisco()) + " GB");
            libreDisco.setText((clientHandler.getEspacioLibre()) + " GB");
            graficoCPU.setValue(clientHandler.getCpu());
            graficoRAM.setValue(clientHandler.getMemoria());
            graficoDisco.setValue(clientHandler.getPorcentajeOcupado());
            graficoTemperatura.setValue(clientHandler.getMemoria() - 12);
            double temperatura = clientHandler.getMemoria() - 12.00;
            DecimalFormat df = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.US));
            String temperaturaFormateada = df.format(temperatura);
            temperaturaLabel.setText(temperaturaFormateada + "°C");
            enviarAlertas(identificadorCliente);
        }
    }

    /**
     * Enviar alertas.
     * <p>
     * Método para enviar alertas basadas en los datos del cliente
     *
     * @param identificadorCliente the identificador cliente
     */
// Método para enviar alertas basadas en los datos del cliente
    public void enviarAlertas(String identificadorCliente) {
        scheduler.scheduleAtFixedRate(() -> {
            if (identificadorCliente != null) {
                ClientHandler clientHandler = ClientHandler.getClientHandlerByIdentifier(identificadorCliente);
                if (clientHandler != null) {
                    try {
                        monitoreoAlertas.monitoreoCPU(clientHandler.getCpu(), identificadorCliente);
                        monitoreoAlertas.monitoreoMemoria(clientHandler.getMemoria(), identificadorCliente);
                        monitoreoAlertas.monitoreoDisco(clientHandler.getPorcentajeOcupado(), identificadorCliente);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 0, 5, TimeUnit.SECONDS);
    }

    /**
     * Borrar datos cliente.
     * <p>
     * Método para borrar los datos del cliente de la interfaz de usuario
     */
// Método para borrar los datos del cliente de la interfaz de usuario
    public void borrarDatosCliente() {
        cpuLabel.setText("");
        memoriaLabel.setText("");
        sistema.setText("");
        espacioDisco.setText("");
        libreDisco.setText("");
        graficoCPU.setValue(0);
        graficoRAM.setValue(0);
        graficoDisco.setValue(0);
        graficoTemperatura.setValue(0);
        temperaturaLabel.setText("");
    }

    /**
     * Reporte.
     * <p>
     * Método para generar un reporte del cliente seleccionado
     * <p>
     * Crea un nuevo hilo que ejecuta el método generarReporte() del cliente seleccionado
     * Si no se ha seleccionado un cliente, muestra un mensaje de error
     * Si se ha seleccionado un cliente, muestra un mensaje de éxito
     * Si ocurre un error al generar el reporte, muestra un mensaje de error
     * Si ocurre un error al generar el reporte, muestra un mensaje de error
     *
     * @param actionEvent the action event
     * @throws JRException the jr exception
     */
// Método para generar un reporte del cliente seleccionado
    public void reporte(ActionEvent actionEvent) throws JRException {
        // Crea un nuevo hilo que ejecuta el método generarReporte() del cliente seleccionado
        new Thread(() -> {
            String selectedClient = lista.getSelectionModel().getSelectedItem();
            if (selectedClient != null) {
                ClientHandler clientHandler = ClientHandler.getClientHandlerByIdentifier(selectedClient);
                if (clientHandler != null) {
                    try {
                        Reporte.generarReportes(selectedClient);
                        Platform.runLater(() -> {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Reporte generado");
                            alert.setHeaderText(null);
                            alert.setContentText("Reporte generado para el cliente: " + selectedClient);
                            alert.showAndWait();
                        });
                    } catch (JRException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error al generar el reporte");
                    alert.setContentText("No se ha seleccionado un cliente");
                    alert.showAndWait();
                });
                return;
            }
        }).start();

    }

    /**
     * Cpu slider.
     * <p>
     * Métodos para manejar los eventos de los sliders
     *
     * @param mouseEvent the mouse event
     */
// Métodos para manejar los eventos de los sliders
    public void cpuSlider(MouseEvent mouseEvent) {
        cpuSlider.setValue(cpuSlider.getValue());
    }

    /**
     * Memoria slider.
     * <p>
     * Método para manejar los eventos de los sliders
     *
     * @param mouseEvent the mouse event
     */
    public void memoriaSlider(MouseEvent mouseEvent) {
        memoriaSlider.setValue(memoriaSlider.getValue());
    }

    /**
     * Disco slider.
     * <p>
     * Método para manejar los eventos de los sliders
     *
     * @param mouseEvent the mouse event
     */
    public void discoSlider(MouseEvent mouseEvent) {
        discoSlider.setValue(discoSlider.getValue());
    }

    // Método para configurar los sliders

    /**
     * Configure slider.
     * <p>
     * Método para configurar los sliders
     *
     * @param slider the slider
     * @param label  the label
     */
    private void configureSlider(Slider slider, Label label) {
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            label.setText(String.format("%.2f", newValue) + "%");
        });
    }

    /**
     * Gets cpu slider value.
     * <p>
     * Métodos para obtener los valores de los sliders
     *
     * @return the cpu slider value
     */
// Métodos para obtener los valores de los sliders
    public double getCpuSliderValue() {
        return cpuSlider.getValue();
    }

    /**
     * Gets memoria slider value.
     * <p>
     * Método para obtener los valores de los sliders
     *
     * @return the memoria slider value
     */
    public double getMemoriaSliderValue() {
        return memoriaSlider.getValue();
    }

    /**
     * Gets disco slider value.
     * <p>
     * Método para obtener los valores de los sliders
     *
     * @return the disco slider value
     */
    public double getDiscoSliderValue() {
        return discoSlider.getValue();
    }

    /**
     * Mostrar reporte.
     * <p>
     * Métodos para mostrar el reporte y el PDF del cliente seleccionado
     * <p>
     * Crea un nuevo hilo que ejecuta el método mostrarReporte() del cliente seleccionado
     * Si no se ha seleccionado un cliente, muestra un mensaje de error
     * Si se ha seleccionado un cliente, muestra un mensaje de éxito
     * Si ocurre un error al mostrar el reporte, muestra un mensaje de error
     *
     * @param actionEvent the action event
     */
// Métodos para mostrar el reporte y el PDF del cliente seleccionado
    public void mostrarReporte(ActionEvent actionEvent) {
        new Thread(() -> {
            String selectedClient = lista.getSelectionModel().getSelectedItem();
            if (selectedClient != null) {
                ClientHandler clientHandler = ClientHandler.getClientHandlerByIdentifier(selectedClient);
                if (clientHandler != null) {
                    Reporte.mostrarReporte(selectedClient);
                }
            } else {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error al mostrar el reporte");
                    alert.setContentText("No se ha seleccionado un cliente");
                    alert.showAndWait();
                });
            }
        }).start();
    }

    /**
     * Mostrar pdf.
     * <p>
     * Método para mostrar el PDF del cliente seleccionado
     * <p>
     * Crea un nuevo hilo que ejecuta el método mostrarPDF() del cliente seleccionado
     * Si no se ha seleccionado un cliente, muestra un mensaje de error
     * Si se ha seleccionado un cliente, muestra un mensaje de éxito
     * Si ocurre un error al mostrar el PDF, muestra un mensaje de error
     *
     * @param actionEvent the action event
     */
    public void mostrarPDF(ActionEvent actionEvent) {
        new Thread(() -> {
            String selectedClient = lista.getSelectionModel().getSelectedItem();
            if (selectedClient != null) {
                ClientHandler clientHandler = ClientHandler.getClientHandlerByIdentifier(selectedClient);
                if (clientHandler != null) {
                    Reporte.mostrarPDF(selectedClient);
                }
            } else {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error al mostrar el PDF");
                    alert.setContentText("No se ha seleccionado un cliente");
                    alert.showAndWait();
                });
            }
        }).start();
    }

    /**
     * Ayuda.
     * <p>
     * Método para abrir la ayuda
     *
     * @param actionEvent the action event
     */

    public void ayuda(ActionEvent actionEvent) {
        Ayuda.abrirHelp();
    }


    /**
     * Cerrar sesion.
     * <p>
     * Método para cerrar la sesión
     * <p>
     * Obtiene el ClientHandler correspondiente
     * Cierra la conexión
     * Detén el hilo de actualización
     * Cierra la ventana actual y abre la vista de inicio de sesión
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
//    public void cerrarSesion(ActionEvent actionEvent) throws IOException {
//        // Obtén el ClientHandler correspondiente
//        ClientHandler clientHandler = ClientHandler.getClientHandlerByIdentifier(selectedClient);
//        if (clientHandler != null) {
//            // Cierra la conexión
//            clientHandler.cerrarConexion();
//        }
//
//        // Detén el hilo de actualización
//        running = false;
//
//        // Cierra la ventana actual y abre la vista de inicio de sesión
//        ((Button) actionEvent.getSource()).getScene().getWindow().hide();
//        HelloApplication.showLogin();
//    }
    public void cerrarSesion(ActionEvent actionEvent) throws IOException {
        // Iterate over all ClientHandlers and close the connection
        for (ClientHandler clientHandler : ClientHandler.getClientHandlers().values()) {
            clientHandler.cerrarConexion();
        }

        // Detén la conexión con el servidor
        controladorView.stop();
        conexion.stop();

        // Detén el hilo de actualización
        running = false;
        // Cierra la ventana actual y abre la vista de inicio de sesión
        ((Button) actionEvent.getSource()).getScene().getWindow().hide();
        HelloApplication.showLogin();
    }
}