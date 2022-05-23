module com.progetto.progettois {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.progetto.progettois to javafx.fxml;
    exports com.progetto.progettois;
    exports com.progetto.farmacia.autenticazione;
    opens com.progetto.farmacia.autenticazione to javafx.fxml;
}