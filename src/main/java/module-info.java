module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires rt;
    requires jfxrt;

    opens org.example to javafx.fxml;
    exports org.example;
}