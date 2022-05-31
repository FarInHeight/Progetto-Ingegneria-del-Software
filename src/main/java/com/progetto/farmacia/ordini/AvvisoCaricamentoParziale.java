package com.progetto.farmacia.ordini;

import com.progetto.entity.EntryFormOrdine;
import com.progetto.entity.EntryListaOrdini;
import com.progetto.entity.Ordine;
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

public class AvvisoCaricamentoParziale extends Application implements Initializable {

    @FXML
    private TextArea farmaciMancantiText;

    private static String farmaciMancanti;
    private static EntryListaOrdini ordine;
    private static RegistrazioneFarmaciRicevutiControl control;

    public AvvisoCaricamentoParziale(){
        super();
    }

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

    public void setFarmaciMancanti(String farmaciMancanti) {
        if (farmaciMancanti == null) {
            throw new NullPointerException("farmaci mancanti = null");
        }
        AvvisoCaricamentoParziale.farmaciMancanti = farmaciMancanti;
    }

    public void setOrdine(EntryListaOrdini ordine) {
        if (ordine==null) {
            throw new NullPointerException("ordine=null");
        }
        this.ordine=ordine;
    }

    /**
     * Metodo utilizzato per visualizzare la {@code SchermataErroreQuantita} a schermo
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

        subStage.setTitle("Problema Quantità");
        subStage.setScene(scene);
        subStage.setWidth(stageWidth);
        subStage.setHeight(stageHeight);
        subStage.setMinWidth(stageWidth);
        subStage.setMinHeight(stageHeight);
        subStage.initOwner(stage); //imposto come proprietario del Riepilogo la Lista Spedizioni
        subStage.initModality(Modality.APPLICATION_MODAL);  //blocco il focus sulla schermata di Riepilogo
        subStage.show();
        subStage.setOnCloseRequest(event -> { Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Per uscire dal programma effettua il logout.");
            alert.showAndWait();
            alert.setTitle("Chiusura Programma");
            alert.setHeaderText("AVVISO");
            event.consume();
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.farmaciMancantiText.setText(AvvisoCaricamentoParziale.farmaciMancanti);
    }

    @FXML
    private void creaSegnalazione(ActionEvent event) {
        //PlaceHolder, non so esattamente che dovremmo passare
        CreaSegnalazioneControl creaSegnalazioneControl = new CreaSegnalazioneControl(AvvisoCaricamentoParziale.ordine, AvvisoCaricamentoParziale.control);
        creaSegnalazioneControl.start(event);
    }


}
