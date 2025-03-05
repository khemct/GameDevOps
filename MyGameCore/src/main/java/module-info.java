module com.example.mygamecore {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.mygamecore to javafx.fxml;
    exports com.example.mygamecore;
}