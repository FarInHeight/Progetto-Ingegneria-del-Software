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
 * rappresenta il massaggio di conferma eliminazione di un ordine
 */
public class MessaggioConfermaEliminazioneOrdine extends Application {
    /**
     * permette di mostrare a schermo il messaggio di conferma eliminazione di un ordine
     * @param stage stage di lista ordini
     * @throws IOException se il caricamento del file fxml della schermata di errore non è adnato a buon fine
     */

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("messaggioConfermaEliminazioneOrdine.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 250);

        double stageWidth = 400;
        double stageHeight = 250;

        Stage subStage = new Stage();
        //centra la schermata
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        subStage.setX((screenBounds.getWidth() - stageWidth) / 2);
        subStage.setY((screenBounds.getHeight() - stageHeight) / 2);

        subStage.setTitle("Conferma Eliminazione Ordine");
        subStage.setScene(scene);
        subStage.setMinWidth(stageWidth);
        subStage.setMinHeight(stageHeight);
        subStage.initOwner(stage); //imposto come proprietario del Riepilogo la Lista Spedizioni
        subStage.initModality(Modality.WINDOW_MODAL);  //blocco il focus sulla schermata di Riepilogo
        subStage.show();
    }

    @FXML
    private void chiudi(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();  // chiudo l'avviso
        ListaOrdini.update();
    }
}
