package services;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * The type Ayuda.
 * <p>
 *      Clase que se encarga de abrir el archivo de ayuda.
 *
 * @author Zebenzuí López Conde
 * @version 1.0 2ºA DAM
 */

public class Ayuda {
    public static void abrirHelp() {
        Desktop desktop = Desktop.getDesktop();
        try {
            File archivo = new File("Ayuda.chm");
            desktop.open(archivo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
