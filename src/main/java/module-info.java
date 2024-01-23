module org.example.servidortcpfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.servidortcpfx to javafx.fxml;
    exports org.example.servidortcpfx;
}