package com.progetto.farmacia.ordini;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Classe che modella la boundary {@code ConfermaInvioSegnalazione}
 */
public class ConfermaInvioSegnalazione extends Application {
    private static RegistrazioneFarmaciRicevutiControl controlRegistrazione;
    /**
     * Costruttore di un oggetto di classe {@code ConfermaInvioSegnalazione}
     */
    public ConfermaInvioSegnalazione(){super();}

    /**
     * Costruttore di un oggetto di classe {@code ConfermaInvioSegnalazione} che prende in input la control che ha creato
     * il messaggio di conferma
     * @param controlRegistrazione control che ha creato il messaggio di conferma
     */
    public ConfermaInvioSegnalazione(RegistrazioneFarmaciRicevutiControl controlRegistrazione){
        this.setControlRegistrazione(controlRegistrazione);
    }

    private void setControlRegistrazione(RegistrazioneFarmaciRicevutiControl controlRegistrazione) {
        if(controlRegistrazione == null) {
            throw new NullPointerException("Riepilogo = null");
        }
        ConfermaInvioSegnalazione.controlRegistrazione = controlRegistrazione;
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
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //ottiene stage corrente
        ConfermaInvioSegnalazione.controlRegistrazione.clickSuChiudi(stage);
    }
}
