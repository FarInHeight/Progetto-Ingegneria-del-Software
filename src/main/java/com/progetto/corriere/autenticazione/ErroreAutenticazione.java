package com.progetto.corriere.autenticazione;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Classe che modella la boundary {@code ErroreAutenticazioneBoundary}
 */
public class ErroreAutenticazione extends Application {

    private int tipo;

    /**
     * Istanzia un oggetto di tipo {@code ErroreAutenticazione}
     */
    public ErroreAutenticazione(){
        super();
    }

    /**
     * Istanzia un oggetto di tipo {@code ErroreAutenticazione} dato in input il tipo di errore
     * @param tipo {@code int} relativo al tipo errore (0 per username errato, 1 per password errata)
     */
    public ErroreAutenticazione(int tipo){
        super();
        this.tipo = tipo;
    }
    @FXML
    private void chiudiErrore(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    /**
     * Permette di mostrare la schermata di errore
     * @param stage oggetto di tipo {@code stage} relativo alla schermata di login
     * @throws IOException se non è possibile caricare il file {@code fxml} della schermata di errore
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        if(this.tipo == 0){
            fxmlLoader = new FXMLLoader(getClass().getResource("erroreAutenticazioneFormatoId.fxml"));
        }
        else if(this.tipo == 1){
            fxmlLoader = new FXMLLoader(getClass().getResource("erroreAutenticazionePassword.fxml"));
        }
        else if(this.tipo == 2){
            fxmlLoader = new FXMLLoader(getClass().getResource("erroreAutenticazioneId.fxml"));
        }
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        double subStageWidth = 350;
        double subStageHeight = 250;

        Stage subStage = new Stage();

        //centra la schermata
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        subStage.setX((screenBounds.getWidth() - subStageWidth) / 2);
        subStage.setY((screenBounds.getHeight() - subStageHeight) / 2);

        //mostra la schermata di errore
        subStage.setTitle("Errore");
        subStage.setScene(scene);
        subStage.setHeight(subStageHeight);
        subStage.setWidth(subStageWidth);
        subStage.setMinWidth(subStageWidth);
        subStage.setMinHeight(subStageHeight);
        subStage.initOwner(stage); //imposto come proprietario dello stage dell'errore lo stage della schermata di login passato in input
        subStage.initModality(Modality.WINDOW_MODAL);  //blocco il focus sulla schermata delle'errore
        subStage.show();
    }
}