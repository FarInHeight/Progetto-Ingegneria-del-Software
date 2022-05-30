package com.progetto.farmacia.ordini;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AvvisoCaricamentoParziale extends Application implements Initializable {

    @FXML
    private Text farmaciMancantiText;

    private String farmaciMancanti;

    public AvvisoCaricamentoParziale(){
        super();
    }

    public AvvisoCaricamentoParziale(String farmaciMancanti) {
        setFarmaciMancanti(farmaciMancanti);
    }

    public void setFarmaciMancanti(String farmaciMancanti) {
        if (farmaciMancanti == null) {
            throw new NullPointerException("farmaci mancanti = null");
        }
        this.farmaciMancanti = farmaciMancanti;
    }

    /**
     * Metodo utilizzato per visualizzare la {@code SchermataErroreQuantita} a schermo
     * @param stage stage della schermata di errore
     * @throws IOException se il caricamento del file {@code fxml} non è andato a buon fine
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("schermataErroreQuantita.fxml"));
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
        subStage.initModality(Modality.WINDOW_MODAL);  //blocco il focus sulla schermata di Riepilogo
        subStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.farmaciMancantiText.setTextContent(farmaciMancanti);
    }

    @FXML
    private void creaSegnalazione(ActionEvent event) {
        //PlaceHolder, non so esattamente che dovremmo passare
        CreaSegnalazioneControl creaSegnalazioneControl = new CreaSegnalazioneControl();
        //creaSegnalazioneControl.start(event);
    }


}
