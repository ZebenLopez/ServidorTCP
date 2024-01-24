module org.example.servidortcpfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    exports controller;
    opens controller to javafx.fxml;
    exports view;
    opens view to javafx.fxml;
}