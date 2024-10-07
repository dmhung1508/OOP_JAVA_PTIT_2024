module com.cinema {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.cinema to javafx.fxml;
    exports com.cinema;
}
