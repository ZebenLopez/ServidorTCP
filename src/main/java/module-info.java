module org.example.servidortcpfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires jasperreports;
    requires opencsv;
    requires java.sql;
    requires java.desktop;


    exports controller;
    opens controller to javafx.fxml;
    exports view;
    opens view to javafx.fxml;
}