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

public class RegistrazioneCompletata extends Application {

    private static RegistrazioneFarmaciRicevutiControl control;

    public RegistrazioneCompletata() {
    }

    public RegistrazioneCompletata(RegistrazioneFarmaciRicevutiControl control) {
        setControl(control);
    }

    public void setControl(RegistrazioneFarmaciRicevutiControl control) {
        if (control == null){
            throw new NullPointerException("control = null");
        }
        this.control = control;
    }

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
    }

    @FXML
    private void chiudi(ActionEvent event) {
        Stage stage = ((Stage) (((Button) event.getSource()).getScene().getWindow()));  // chiudo l'avviso
        RegistrazioneCompletata.control.clickSuChiudi(stage);
    }
}
