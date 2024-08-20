module org.example.listacompras {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.listacompras to javafx.fxml;
    exports org.example.listacompras;
}