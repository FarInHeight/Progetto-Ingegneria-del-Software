module com.progetto {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires java.sql;

    exports com.progetto.farmacia.autenticazione;
    exports com.progetto.addetto.autenticazione;
    exports com.progetto.addetto;
    exports com.progetto.corriere.gestioneSpedizioni;
    exports com.progetto.corriere;
    exports com.progetto.corriere.autenticazione;
    exports com.progetto.entity;
    opens com.progetto.farmacia.autenticazione to javafx.fxml;
    opens com.progetto.addetto.autenticazione to javafx.fxml;
    opens com.progetto.addetto.segnalazioni to javafx.fxml, javafx.graphics;
    opens com.progetto.addetto to javafx.fxml;
    opens com.progetto.addetto.ordini to javafx.fxml;
    opens com.progetto.entity to javafx.base;
    opens com.progetto.farmacia.ordini to javafx.fxml, javafx.graphics;
    opens com.progetto.farmacia.magazzino to javafx.fxml, javafx.graphics;
    opens com.progetto.farmacia to javafx.fxml;
    opens com.progetto.corriere.autenticazione to javafx.graphics,javafx.fxml;
    opens com.progetto.corriere to javafx.graphics,javafx.fxml;
    opens com.progetto.corriere.gestioneSpedizioni to javafx.graphics,javafx.fxml;
}