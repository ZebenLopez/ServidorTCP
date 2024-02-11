package services;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRCsvDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Reporte.
 * <p>
 * Clase Reporte que se encarga de generar reportes basados en los datos de uso de CPU, memoria y disco
 *
 * @author Zebenzuí López Conde
 * @version 1.0  2ºA DAM
 */
public class Reporte {

    // Ruta del archivo del reporte
    private static final String rutaReporte = "./Reporte.jrxml";
    // Ruta del archivo CSV que contiene los datos para el reporte
    private static String rutaCSV;
    private static String rutaPDF;
    private static String rutaJasper;
    static JasperPrint jasperPrint;
    // Mapa que almacena los JasperPrints para cada cliente
    private static Map<String, JasperPrint> jasperPrints = new HashMap<>();

    /**
     * Generar reportes.
     * <p>
     * Método para generar los reportes para un cliente específico
     * <p>
     * Si el archivo del reporte no existe, muestra una alerta en la interfaz de usuario<br>
     * Si el archivo CSV no existe, lanza una excepción<br>
     * Compila el reporte<br>
     * Crea un nuevo DataSource a partir del archivo CSV<br>
     * Crea un nuevo HashMap para los parámetros del reporte<br>
     * Llena el reporte con los datos del DataSource y los parámetros <br>
     * Almacena el JasperPrint en el Map con el idCliente como clave <br>
     * Exporta el reporte a un archivo PDF <br>
     * Si ocurre un error, imprime la traza de la excepción
     *
     * @param idCliente the id cliente
     * @throws JRException the jr exception
     */
    public static void generarReportes(String idCliente) throws JRException {
        // Establece la ruta del archivo CSV basado en el id del cliente
        rutaCSV = idCliente + ".csv";
        // Establece la ruta del archivo PDF donde se guardará el reporte
        rutaPDF = idCliente + ".pdf";
        // Establece la ruta del archivo Jasper donde se guardará el reporte
        rutaJasper = idCliente + ".jasper";
        // Crea un nuevo hilo para generar el reporte
        new Thread(() -> {
            try {
                // Verifica si el archivo del reporte existe
                File reporte = new File(rutaReporte);
                if (!reporte.exists()) {
                    // Si no existe, muestra una alerta en la interfaz de usuario
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error al generar el reporte");
                    alert.setContentText("No se encontró el archivo " + rutaReporte);
                    alert.showAndWait();
                }

                // Verifica si el archivo CSV existe
                File csv = new File(rutaCSV);
//                if (!csv.exists()) {
//                    // Si no existe, lanza una excepción
//                    throw new FileNotFoundException("No se encontró el archivo " + rutaCSV);
//                }
                JasperCompileManager.compileReportToFile(rutaReporte, rutaJasper);

                // Compila el reporte
                JasperReport jasperReport = JasperCompileManager.compileReport(rutaReporte);
                JasperCompileManager.compileReportToFile(rutaReporte, rutaJasper);
                // Crea un nuevo DataSource a partir del archivo CSV
                JRCsvDataSource dataSource = new JRCsvDataSource(csv);
                dataSource.setRecordDelimiter("\n");
                dataSource.setFieldDelimiter(',');
                dataSource.setColumnNames(new String[]{"Sistema", "Usuario", "CPU (%)", "Memoria (%)", "Espacio en disco (GB)", "Espacio libre (GB)", "Porcentaje ocupado (%)"});

                // Crea un nuevo HashMap para los parámetros del reporte
//                HashMap<String, Object> parameters = new HashMap<>();

                // Llena el reporte con los datos del DataSource y los parámetros
                jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);
//                JasperViewer.viewReport(jasperPrint, false);

                // Almacena el JasperPrint en el Map con el idCliente como clave
//                jasperPrints.put(idCliente, jasperPrint);

                // Exporta el reporte a un archivo PDF
                JasperExportManager.exportReportToPdfFile(jasperPrint, rutaPDF);

            } catch (JRException | FileNotFoundException e) {
                // Imprime la traza de la excepción si ocurre un error
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Mostrar reporte.
     * <p>
     * Método para mostrar el reporte de un cliente específico
     * <p>
     * Crea un nuevo hilo para mostrar el reporte <br>
     * Recupera el JasperPrint del Map utilizando el id como clave <br>
     * Si el JasperPrint es nulo, muestra una alerta en la interfaz de usuario <br>
     * Si el JasperPrint no es nulo, muestra el reporte
     *
     * @param id the id
     */
//    public static void mostrarReporte(String id) {
//        // Establece la ruta del archivo CSV basado en el id del cliente
//        rutaCSV = id + ".csv";
//        // Establece la ruta del archivo PDF donde se guardará el reporte
//        rutaPDF = id + ".pdf";
//        // Establece la ruta del archivo Jasper donde se guardará el reporte
//        rutaJasper = id + ".jasper";
//        // Crea un nuevo hilo para generar el reporte
//        new Thread(() -> {
//            try {
//                // Verifica si el archivo del reporte existe
//                File reporte = new File(rutaReporte);
//                if (!reporte.exists()) {
//                    // Si no existe, muestra una alerta en la interfaz de usuario
//                    Alert alert = new Alert(Alert.AlertType.ERROR);
//                    alert.setTitle("Error");
//                    alert.setHeaderText("Error al generar el reporte");
//                    alert.setContentText("No se encontró el archivo " + rutaReporte);
//                    alert.showAndWait();
//                }
//
//                // Verifica si el archivo CSV existe
//                File csv = new File(rutaCSV);
////                if (!csv.exists()) {
////                    // Si no existe, lanza una excepción
////                    throw new FileNotFoundException("No se encontró el archivo " + rutaCSV);
////                }
//                JasperCompileManager.compileReportToFile(rutaReporte, rutaJasper);
//
//                // Compila el reporte
//                JasperReport jasperReport = JasperCompileManager.compileReport(rutaReporte);
//                JasperCompileManager.compileReportToFile(rutaReporte, rutaJasper);
//                // Crea un nuevo DataSource a partir del archivo CSV
//                JRCsvDataSource dataSource = new JRCsvDataSource(csv);
//                dataSource.setRecordDelimiter("\n");
//                dataSource.setFieldDelimiter(',');
//                dataSource.setColumnNames(new String[]{"Sistema", "Usuario", "CPU (%)", "Memoria (%)", "Espacio en disco (GB)", "Espacio libre (GB)", "Porcentaje ocupado (%)"});
//
//                // Crea un nuevo HashMap para los parámetros del reporte
////                HashMap<String, Object> parameters = new HashMap<>();
//
//                // Llena el reporte con los datos del DataSource y los parámetros
//                jasperPrint = JasperFillManager.fillReport(rutaJasper, null, dataSource);
//                JasperViewer.viewReport(jasperPrint, false);
//
//                // Almacena el JasperPrint en el Map con el idCliente como clave
////                jasperPrints.put(idCliente, jasperPrint);
//
//                // Exporta el reporte a un archivo PDF
//                JasperExportManager.exportReportToPdfFile(jasperPrint, rutaPDF);
//
//            } catch (JRException | FileNotFoundException e) {
//                // Imprime la traza de la excepción si ocurre un error
//                e.printStackTrace();
//            }
//        }).start();
//    }
   public static void mostrarReporte(String id) {
    // Crea un nuevo hilo para mostrar el reporte
    new Thread(() -> {
        try {
            // Establece la ruta del archivo Jasper basado en el id del cliente
            String rutaJasper = "./" + id + ".jasper";
            // Carga el archivo Jasper
            JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(rutaJasper);

            File csv = new File(rutaCSV);
            JRCsvDataSource dataSource = new JRCsvDataSource(csv);
                dataSource.setRecordDelimiter("\n");
                dataSource.setFieldDelimiter(',');
                dataSource.setColumnNames(new String[]{"Sistema", "Usuario", "CPU (%)", "Memoria (%)", "Espacio en disco (GB)", "Espacio libre (GB)", "Porcentaje ocupado (%)"});
            // Llena el reporte con los datos del DataSource y los parámetros
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);
            if (jasperPrint == null) {
                // Si el JasperPrint es nulo, muestra una alerta en la interfaz de usuario
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("No se ha generado un reporte para el cliente seleccionado.\nPor favor, genere un reporte primero.");
                    alert.showAndWait();
                });
            } else {
                // Si el JasperPrint no es nulo, muestra el reporte
                JasperViewer viewer = new JasperViewer(jasperPrint, false);
                viewer.setVisible(true);
            }
        } catch (JRException e) {
            // Imprime la traza de la excepción si ocurre un error
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }).start();
}

    /**
     * Mostrar pdf.
     * <p>
     * Método para mostrar el PDF de un cliente específico
     * <p>
     * Establece la ruta del archivo PDF basado en el id del cliente <br>
     * Crea un nuevo hilo para mostrar el PDF <br>
     * Verifica si el archivo PDF existe <br>
     * Si el archivo PDF existe, lo abre <br>
     * Si ocurre un error al abrir el archivo, lanza una excepción <br>
     * Si el archivo PDF no existe, muestra una alerta en la interfaz de usuario <br>
     *
     * @param id the id
     */
    public static void mostrarPDF(String id) {
        // Establece la ruta del archivo PDF basado en el id del cliente
        String rutaPDF = "./" + id + ".pdf";
        // Crea un nuevo hilo para mostrar el PDF
        new Thread(() -> {
            // Verifica si el archivo PDF existe
            File file = new File(rutaPDF);
            if (file.exists()) {
                // Si el archivo PDF existe, lo abre
                Desktop desktop = Desktop.getDesktop();
                try {
                    desktop.open(file);
                } catch (IOException e) {
                    // Si ocurre un error al abrir el archivo, lanza una excepción
                    throw new RuntimeException(e);
                }
            } else {
                // Si el archivo PDF no existe, muestra una alerta en la interfaz de usuario
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("No se ha generado un PDF para el cliente seleccionado.\nPor favor, genere un reporte primero.");
                    alert.showAndWait();
                });
            }
        }).start();
    }
}