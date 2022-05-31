package com.progetto.addetto.autenticazione;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Classe che implementa la boundary {@code ErroreAutenticazione}
 */
public class ErroreAutenticazione extends Application {

    private int tipo;

    /**
     * Costruisce un'istanza di {@code ErroreAutenticazione}
     */
    public ErroreAutenticazione(){
        super();
    }

    /**
     * Costruisce un'istanza di {@code ErroreAutenticazione} dato in input il tipo di errore ([0] per username errato, [1] per password errata)
     * @param tipo tipo di errore
     */
    public ErroreAutenticazione(int tipo){
        super();
        this.tipo = tipo;
    }
    @FXML
    private void chiudiErrore(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    /**
     * permette di mostrare la schermata di errore
     * @param stage oggetto di classe {@code Stage} della schermata di login
     * @throws IOException se non è possibile caricare il file {@code fxml} della schermata di errore
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        if(this.tipo == 0){
            fxmlLoader = new FXMLLoader(getClass().getResource("erroreAutenticazioneFormatoId.fxml"));
        }
        else if(this.tipo == 1){
            fxmlLoader = new FXMLLoader(getClass().getResource("erroreAutenticazionePassword.fxml"));
        }
        else if(this.tipo == 2){
            fxmlLoader = new FXMLLoader(getClass().getResource("erroreAutenticazioneId.fxml"));
        }
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

        subStage.setOnCloseRequest(event -> { Alert alert = new Alert(Alert.AlertType.INFORMATION); alert.setContentText("Per uscire dal programma effettua il logout."); alert.showAndWait(); event.consume(); });
    }
}