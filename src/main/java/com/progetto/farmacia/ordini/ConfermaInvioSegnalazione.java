package com.progetto.farmacia.ordini;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Classe che modella la boundary {@code ConfermaInvioSegnalazione}
 */
public class ConfermaInvioSegnalazione extends Application {
    private static CreaSegnalazioneControl control;

    /**
     * Costruttore di un oggetto di classe {@code ConfermaInvioSegnalazione}
     */
    public ConfermaInvioSegnalazione(){super();}

    /**
     * Costruttore di un oggetto di classe {@code ConfermaInvioSegnalazione} che prende in input la control che ha creato
     * il messaggio di conferma
     * @param control control che ha creato il messaggio di conferma
     */
    public ConfermaInvioSegnalazione(CreaSegnalazioneControl control){this.setControl(control);}

    private void setControl(CreaSegnalazioneControl control) {
        if(control == null) {
            throw new NullPointerException("Riepilogo = null");
        }
        ConfermaInvioSegnalazione.control = control;
    }
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("confermaInvioSegnalazione.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 350, 220);

        double stageWidth = 350;
        double stageHeight = 220;

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - stageWidth) / 2);
        stage.setY((screenBounds.getHeight() - stageHeight) / 2);

        stage.setTitle("Avvenuta Registrazione Segnalazione");
        stage.setScene(scene);
        stage.setMinWidth(stageWidth);
        stage.setMinHeight(stageHeight);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    private void chiudi(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //ottiene stage corrente
        ConfermaInvioSegnalazione.control.clickSuChiudi(stage);
    }
}
