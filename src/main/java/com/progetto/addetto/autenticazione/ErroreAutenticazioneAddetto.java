package com.progetto.addetto.autenticazione;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ErroreAutenticazioneAddetto extends Application implements Initializable {

    private int tipo;

    @FXML
    private Label outputLabel;

    /**
     * costruire un istanza di {@code ErroreAutenticazione}
     */
    public ErroreAutenticazioneAddetto(){
        super();
    }

    /**
     * costruire un istanza di {@code ErroreAutenticazione} dato in input il tipo di errore
     * @param tipo di errore (0 per username errato, 1 per password errata)
     */
    public ErroreAutenticazioneAddetto(int tipo){
        super();
        this.tipo = tipo;
    }
    @FXML
    private void chiudiErrore(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    /**
     * permette di mostrare la schermata di errore
     * @param stage {@code stage} della schermata di login
     * @throws IOException se non è possibile caricare il file fxml della schermata di errore
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("erroreAutenticazione.fxml"));

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(this.tipo == 0){
            outputLabel.setText("L'ID inserito è errato.");
        }
        else if(this.tipo == 1){
            outputLabel.setText("La password inserita è errata.");
        }
    }
}