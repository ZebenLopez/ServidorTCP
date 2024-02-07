package services;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Ayuda {
    public static void abrirHelp(){
        Desktop desktop = Desktop.getDesktop();
        try {
            File archivo = new File("src/main/resources/help/Ayuda.chm");
            desktop.open(archivo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
