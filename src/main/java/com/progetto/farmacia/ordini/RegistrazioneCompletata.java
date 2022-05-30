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

    private RegistrazioneFarmaciRicevutiControl control;

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

    @FXML
    private void chiudi(ActionEvent event) {
        Stage stage = ((Stage) (((Button) event.getSource()).getScene().getWindow()));  // chiudo l'avviso
        control.clickSuIndietro(stage);
    }
}
