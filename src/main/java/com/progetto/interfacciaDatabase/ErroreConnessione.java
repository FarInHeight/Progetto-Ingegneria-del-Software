package com.progetto.interfacciaDatabase;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;

/** rappresenta la classa dell'errore connessione
 *
 */
public class ErroreConnessione extends Application {
    /**
     * permette di creare un oggetto {@code ErroreConnessione}
     */
    public ErroreConnessione(){
        super();
    }
    @FXML
    private void chiudiErrore(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    /**
     * permette di mostrare la schermata di errore
     * @param stage {@code stage} della schermata
     * @throws IOException se non è possibile caricare il file fxml della schermata di errore
     */
    @Override
    public void start(Stage stage) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Connessione interrotta. Riprova più tardi");
        alert.setTitle("Connessione Interrotta");
        alert.setHeaderText("ERRORE");
        alert.showAndWait();

    }
}