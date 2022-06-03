package com.progetto.farmacia.ordini;

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
 * Classe che modella la boundary {@code MessaggioConfermaOrdine}
 */
public class MessaggioConfermaOrdine extends Application {

    private static Object control;

    private static Stage stage;

    /**
     * Istanzia un oggetto di tipo {@code MessaggioConfermaOrdine}
     */
    public MessaggioConfermaOrdine(){super();}

    public MessaggioConfermaOrdine(Object control){
        MessaggioConfermaOrdine.control = control;
    }

    /**
     * Permette di visualizzare il {@code MessaggioConfermaOrdine}
     * @param stage stage della schermata di errore
     * @throws IOException se il caricamento del file {@code fxml} non è andato a buon fine
     */
    @Override
    public void start(Stage stage) throws IOException {
        MessaggioConfermaOrdine.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("messaggioConfermaOrdine.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 250);

        double stageWidth = 400;
        double stageHeight = 250;

        Stage subStage = new Stage();
        //centra la schermata
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        subStage.setX((screenBounds.getWidth() - stageWidth) / 2);
        subStage.setY((screenBounds.getHeight() - stageHeight) / 2);

        subStage.setTitle("Conferma Ordine");
        subStage.setScene(scene);
        subStage.setMinWidth(stageWidth);
        subStage.setMinHeight(stageHeight);
        subStage.initOwner(stage); //imposto come proprietario del Riepilogo la Lista Spedizioni
        subStage.initModality(Modality.WINDOW_MODAL);  //blocco il focus sulla schermata di Riepilogo
        subStage.show();
    }

    @FXML
    private void chiudi(ActionEvent event) {
        if (control instanceof FormOrdine formOrdine) {
            formOrdine.indietro(MessaggioConfermaOrdine.stage);
        } else {
            FormModificaOrdine formModificaOrdine = (FormModificaOrdine) control;
            formModificaOrdine.indietro(MessaggioConfermaOrdine.stage);
        }
    }
}
