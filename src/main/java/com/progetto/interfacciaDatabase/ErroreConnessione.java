package com.progetto.interfacciaDatabase;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.IOException;

/** Classe modella la boundary {@code ErroreConnessioneBoundary}
 *
 */
public class ErroreConnessione extends Application {
    /**
     * Istanzia un oggetto di tipo {@code ErroreConnessione}
     */
    public ErroreConnessione(){
        super();
    }

    /**
     * Permette di mostrare la schermata di errore
     * @param stage oggett di tipo {@code stage} della schermata
     */
    @Override
    public void start(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Connessione interrotta. Riprova più tardi");
        alert.setTitle("Connessione Interrotta");
        alert.setHeaderText("ERRORE");
        alert.showAndWait();
    }
}