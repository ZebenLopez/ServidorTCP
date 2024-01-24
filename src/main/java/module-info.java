module org.example.servidortcpfx {
    requires javafx.controls;
    requires javafx.fxml;



    exports controller;
    opens controller to javafx.fxml;
    exports view;
    opens view to javafx.fxml;
}