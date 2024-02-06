package services;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRCsvDataSource;
import net.sf.jasperreports.view.JasperViewer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class Reporte {

    protected static String rutaReporte = "./reporte.jrxml";
    protected static String rutaCSV = "./Zeben.csv";

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

    public static void generarReporte() throws JRException {
        new Thread(() -> {
            try {
                File reporte = new File(rutaReporte);
                if (!reporte.exists()) {
                    throw new FileNotFoundException("No se encontró el archivo " + rutaReporte);
                }

                File csv = new File(rutaCSV);
                if (!csv.exists()) {
                    throw new FileNotFoundException("No se encontró el archivo " + rutaCSV);
                }

                JasperReport jasperReport = JasperCompileManager.compileReport(rutaReporte);
                JRCsvDataSource dataSource = new JRCsvDataSource(csv);
                dataSource.setRecordDelimiter("\n");
                dataSource.setFieldDelimiter(';');
                dataSource.setColumnNames(new String[]{"Sistema", "usuario;", "cpu", "memoria", "espacioDisco", "espacioLibre", "porcentajeOcupado"});

                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);
                JasperViewer.viewReport(jasperPrint, false);
            } catch (JRException | FileNotFoundException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
