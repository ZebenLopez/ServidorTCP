package services;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRCsvDataSource;
import net.sf.jasperreports.view.JasperViewer;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Reporte {

    private static final String rutaReporte = "./Reporte.jrxml";
    private static String rutaCSV;
    private static Map<String, JasperPrint> jasperPrints = new HashMap<>();

//    public static void generarReporte() throws JRException {
//        new Thread(() -> {
//            try {
//                File reporte = new File(rutaReporte);
//                if (!reporte.exists()) {
//                    throw new FileNotFoundException("No se encontró el archivo " + rutaReporte);
//                }
//
//                File csv = new File(rutaCSV);
//                if (!csv.exists()) {
//                    throw new FileNotFoundException("No se encontró el archivo " + rutaCSV);
//                }
//
//                JasperReport jasperReport = JasperCompileManager.compileReport(rutaReporte);
//                JRCsvDataSource dataSource = new JRCsvDataSource(csv);
//                dataSource.setRecordDelimiter("\n");
//                dataSource.setFieldDelimiter(';');
//                dataSource.setColumnNames(new String[]{"sistema", "usuario", "cpu", "memoria", "espacioDisco", "espacioLibre", "porcentajeOcupado", "usb"});
//
//                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);
//                JasperViewer.viewReport(jasperPrint, false);
//            } catch (JRException | FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        }).start();
//    }
//

    public static void generarReportes(String idCliente) throws JRException {
        rutaCSV = "./" + idCliente + ".csv";
        String rutaPDF = "./" + idCliente + ".pdf";
        new Thread(() -> {
            try {
                File reporte = new File(rutaReporte);
                if (!reporte.exists()) {
//                    throw new FileNotFoundException("No se encontró el archivo " + rutaReporte);
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error al generar el reporte");
                    alert.setContentText("No se encontró el archivo " + rutaReporte);
                    alert.showAndWait();
                }

                File csv = new File(rutaCSV);
                if (!csv.exists()) {
                    throw new FileNotFoundException("No se encontró el archivo " + rutaCSV);
                }

                JasperReport jasperReport = JasperCompileManager.compileReport(rutaReporte);
                JRCsvDataSource dataSource = new JRCsvDataSource(csv);
                dataSource.setRecordDelimiter("\n");
                dataSource.setFieldDelimiter(',');
                dataSource.setColumnNames(new String[]{"Sistema", "Usuario", "CPU (%)", "Memoria (%)", "Espacio en disco (GB)", "Espacio libre (GB)", "Porcentaje ocupado (%)"});

                HashMap<String, Object> parameters = new HashMap<>();


                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);

                // Almacena el JasperPrint en el Map con el idCliente como clave
                jasperPrints.put(idCliente, jasperPrint);

                JasperExportManager.exportReportToPdfFile(jasperPrint, rutaPDF);

            } catch (JRException | FileNotFoundException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void mostrarReporte(String id) {
        new Thread(() -> {
            // Recupera el JasperPrint del Map utilizando el id como clave
            JasperPrint jasperPrint = jasperPrints.get(id);
            if (jasperPrint == null) {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("No se ha generado un Reporte para el cliente seleccionado.");
                    alert.showAndWait();
                });
            } else {
                JasperViewer.viewReport(jasperPrint, false);
            }
        }).start();
    }

    public static void mostrarPDF(String id) {
        String rutaPDF = "./" + id + ".pdf";
        new Thread(() -> {
            File file = new File(rutaPDF);
            if (file.exists()) {
                Desktop desktop = Desktop.getDesktop();
                try {
                    desktop.open(file);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("No se ha generado un PDF para el cliente seleccionado.");
                    alert.showAndWait();
                });
            }
        }).start();
    }
}
