package com.progetto.addetto.ordini;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Classe che modella la boundary {@code MessaggioConfermaOrdine}
 */
public class MessaggioConfermaOrdine extends Application {
    private static VerificaCorrettezzaOrdineControl control;

    /**
     * Costruttore utilizzato per istanziare un {@code MessaggioConfermaOrdine}
     */
    public MessaggioConfermaOrdine() {super();}

    /**
     * Costruttore utilizzato per istanziare un {@code MessaggioConfermaOrdine} data in input la control che l'ha creato.
     * @param control control che ha creato il messaggio
     */
    public MessaggioConfermaOrdine(VerificaCorrettezzaOrdineControl control) {
        this.setControl(control);
    }

    private void setControl(VerificaCorrettezzaOrdineControl control) {
        if(control == null) {
            throw new NullPointerException("Control = null");
        }
        MessaggioConfermaOrdine.control = control;
    }
    /**
     * Metodo utilizzato per visualizzare il {@code MessaggioConfermaOrdine} a schermo
     * @param stage stage della schermata di errore
     * @throws IOException se il caricamento del file {@code fxml} non è andato a buon fine
     */
    @Override
    public void start(Stage stage) throws IOException {
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

        subStage.setOnCloseRequest(event -> { Alert alert = new Alert(Alert.AlertType.INFORMATION); alert.setContentText("Per uscire dal programma effettua il logout."); alert.showAndWait(); event.consume(); });
    }

    @FXML
    private void chiudi(ActionEvent event) {
        MessaggioConfermaOrdine.control.clickSuChiudi(event);
    }
}
