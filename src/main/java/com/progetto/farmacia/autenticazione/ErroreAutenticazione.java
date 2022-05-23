package com.progetto.farmacia.autenticazione;

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

public class ErroreAutenticazione extends Application {
    
    @FXML
    private void chiudiErrore(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    //creare costruttore in base al tipo di errore

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("erroreAutenticazioneId.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        double subStageWidth = 350;
        double subStageHeight = 250;

        Stage subStage = new Stage();

        //centra la schermata
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        subStage.setX((screenBounds.getWidth() - subStageWidth) / 2);
        subStage.setY((screenBounds.getHeight() - subStageHeight) / 2);

        //mostra la schermata di errore
        subStage.setTitle("Errore");
        subStage.setScene(scene);
        subStage.setHeight(subStageHeight);
        subStage.setWidth(subStageWidth);
        subStage.setMinWidth(subStageWidth);
        subStage.setMinHeight(subStageHeight);
        subStage.initOwner(stage); //imposto come proprietario dello stage dell'errore lo stage della schermata di login passato in input
        subStage.initModality(Modality.WINDOW_MODAL);  //blocco il focus sulla schermata delle'errore
        subStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
