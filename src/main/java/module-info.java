module com.progetto {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires java.sql;

    exports com.progetto.farmacia.autenticazione;
    exports com.progetto.addetto.autenticazione;
    exports com.progetto.addetto;
    opens com.progetto.farmacia.autenticazione to javafx.fxml;
    opens com.progetto.addetto.autenticazione to javafx.fxml;
    opens com.progetto.addetto.segnalazioni to javafx.fxml, javafx.graphics;
    opens com.progetto.entity to javafx.base;
    opens com.progetto.farmacia.ordine to javafx.fxml;
    opens com.progetto.farmacia to javafx.fxml;
}