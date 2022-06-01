package com.progetto.farmacia.ordini;

import com.progetto.entity.EntryListaOrdini;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Classe che moodella l'avviso che viene mostrato nel momento in cui il farmacista carica solo una parte dei farmaci
 * contenuta in un ordine
 */
public class AvvisoCaricamentoParziale extends Application implements Initializable {

    @FXML
    private TextArea farmaciMancantiText;

    private static String farmaciMancanti;
    private static EntryListaOrdini ordine;
    private static RegistrazioneFarmaciRicevutiControl control;

    /**
     * Istanzia un oggetto di tipo {@code AvvisoCaricamentoParziale}
     */
    public AvvisoCaricamentoParziale(){
        super();
    }

    /**
     * Istanzia un oggetto di tipo {@code AvvisoCaricamentoParziale} dati in input i farmaci non caricati, l'ordine caricato e
     * la control che gestisce la registrazione dei farmaci ricevuti
     * @param farmaciMancanti farmaci non caricati
     * @param ordine ordine appena caricato
     * @param control control che gestisce la registrazione dei farmaci ricevuti
     */
    public AvvisoCaricamentoParziale(String farmaciMancanti, EntryListaOrdini ordine, RegistrazioneFarmaciRicevutiControl control) {
        setFarmaciMancanti(farmaciMancanti);
        setOrdine(ordine);
        this.setControl(control);
    }

    private void setControl(RegistrazioneFarmaciRicevutiControl control) {
        if(control == null) {
            throw new NullPointerException("Riepilogo = null");
        }
        AvvisoCaricamentoParziale.control = control;
    }

    /**
     * Permette di settare i farmaci non caricati
     * @param farmaciMancanti farmaci non caricati
     */
    public void setFarmaciMancanti(String farmaciMancanti) {
        if (farmaciMancanti == null) {
            throw new NullPointerException("farmaci mancanti = null");
        }
        AvvisoCaricamentoParziale.farmaciMancanti = farmaciMancanti;
    }

    /**
     * Permette di settare l'ordine caricato
     * @param ordine ordine caricato
     */
    public void setOrdine(EntryListaOrdini ordine) {
        if (ordine==null) {
            throw new NullPointerException("ordine=null");
        }
        this.ordine=ordine;
    }

    /**
     * Permette di visualizzare l'{@code AvvisoCaricamentoParziale} a schermo
     * @param stage stage della schermata di errore
     * @throws IOException se il caricamento del file {@code fxml} non è andato a buon fine
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("avvisoCaricamentoParziale.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 350);

        double stageWidth = 410;
        double stageHeight = 360;

        Stage subStage = new Stage();
        //centra la schermata
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        subStage.setX((screenBounds.getWidth() - stageWidth) / 2);
        subStage.setY((screenBounds.getHeight() - stageHeight) / 2);

        subStage.setTitle("Caricamento Parziale");
        subStage.setScene(scene);
        subStage.setWidth(stageWidth);
        subStage.setHeight(stageHeight);
        subStage.setMinWidth(stageWidth);
        subStage.setMinHeight(stageHeight);
        subStage.initOwner(stage);
        subStage.initModality(Modality.APPLICATION_MODAL);
        subStage.show();
        subStage.setOnCloseRequest(event -> { Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Per uscire dal programma effettua il logout.");
            alert.setTitle("Chiusura Programma");
            alert.setHeaderText("AVVISO");
            event.consume();
            alert.showAndWait();
        });
    }

    /**
     * Inizializza la schermata coi farmaci non caricati
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.farmaciMancantiText.setText(AvvisoCaricamentoParziale.farmaciMancanti);
    }

    //Invoca crea segnalazione
    @FXML
    private void creaSegnalazione(ActionEvent event) {
        //PlaceHolder, non so esattamente che dovremmo passare
        CreaSegnalazioneControl creaSegnalazioneControl = new CreaSegnalazioneControl(AvvisoCaricamentoParziale.ordine, AvvisoCaricamentoParziale.control);
        creaSegnalazioneControl.start(event);
    }


}
