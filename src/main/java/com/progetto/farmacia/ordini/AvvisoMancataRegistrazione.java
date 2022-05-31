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

import java.io.IOException;

/**
 * Classe che modella la boundary {@code AvvisoMancataRegistrazione}
 */
public class AvvisoMancataRegistrazione extends Application {

    private static VerificaRegistrazioniFarmaciControl control;

    /**
     * Costruttore di un oggeto di classe {@code AvvisoMancataRegistrazione}
     */
    public AvvisoMancataRegistrazione(){super();}

    /**
     * Costruttore di un oggetto di classe {@code AvvisoMancataRegistrazione} che prende in input la control che ha creato
     * l'avviso
     * @param control control che ha creato l'avviso
     */
    public AvvisoMancataRegistrazione(VerificaRegistrazioniFarmaciControl control) {
        this.setControl(control);
    }

    private void setControl(VerificaRegistrazioniFarmaciControl control) {
        if(control == null) {
            throw new NullPointerException("Control in VerificaRegistrazioniFarmaciControl = null");
        }
        AvvisoMancataRegistrazione.control = control;
    }

    /**
     * Metodo utilizzato per visualizzare un avviso {@code AvvisoMancataRegistrazione} a schermo
     * @param stage stage dell'avviso
     * @throws IOException se il caricamento del file {@code fxml} non è andato a buon fine
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("avvisoMancataRegistrazione.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 350, 250);

        double stageWidth = 350;
        double stageHeight = 250;

        stage = new Stage();

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - stageWidth) / 2);
        stage.setY((screenBounds.getHeight() - stageHeight) / 2);

        stage.setTitle("Lista Ordini");
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
    private void continua(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //ottiene stage corrente
        AvvisoMancataRegistrazione.control.clickSuContinua(stage);
    }
}
