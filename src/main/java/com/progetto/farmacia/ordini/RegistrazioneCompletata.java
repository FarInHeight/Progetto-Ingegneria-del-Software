package com.progetto.farmacia.ordini;

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

/**
 * Classe che modella la boundary {@code RegistrazioneCompetataBoundary}
 */
public class RegistrazioneCompletata extends Application {

    private static RegistrazioneFarmaciRicevutiControl control;

    /**
     * Istanzia un oggetto di tipo {@code RegistrazioneCompetataBoundary}
     */
    public RegistrazioneCompletata() {
    }

    /**
     * Istanzia un oggetto di tipo {@code RegistrazioneCompetataBoundary} dato in input la control che si occupa di registrare
     * i farmaci ricevuti
     * @param control control che si occupa di registrare i farmaci ricevuti
     */
    public RegistrazioneCompletata(RegistrazioneFarmaciRicevutiControl control) {
        setControl(control);
    }

    private void setControl(RegistrazioneFarmaciRicevutiControl control) {
        if (control == null){
            throw new NullPointerException("control = null");
        }
        this.control = control;
    }

    /**
     * Permette di visualizzare il messaggio {@code RegistrazioneCompletata}
     * @param stage stage della schermata di errore
     * @throws IOException se il caricamento del file {@code fxml} non è andato a buon fine
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("registrazioneCompletata.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 350);

        double stageWidth = 410;
        double stageHeight = 360;

        //centra la schermata
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - stageWidth) / 2);
        stage.setY((screenBounds.getHeight() - stageHeight) / 2);

        stage.setTitle("Problema Quantità");
        stage.setScene(scene);
        stage.setWidth(stageWidth);
        stage.setHeight(stageHeight);
        stage.setMinWidth(stageWidth);
        stage.setMinHeight(stageHeight);
        stage.initModality(Modality.APPLICATION_MODAL);  //blocco il focus sulla schermata di Riepilogo
        stage.show();
        stage.setOnCloseRequest(event -> { Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Per uscire dal programma effettua il logout.");
            alert.setTitle("Chiusura Programma");
            alert.setHeaderText("AVVISO");
            event.consume();
            alert.showAndWait();
        });
    }

    @FXML
    private void chiudi(ActionEvent event) {
        Stage stage = ((Stage) (((Button) event.getSource()).getScene().getWindow()));  // chiudo l'avviso
        RegistrazioneCompletata.control.clickSuChiudi(stage);
    }
}
