module com.progetto {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires java.sql;

    exports com.progetto.farmacia.autenticazione;
    exports com.progetto.addetto.autenticazione;
    opens com.progetto.farmacia.autenticazione to javafx.fxml;

    opens com.progetto.addetto.autenticazione to javafx.fxml;
}