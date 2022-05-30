package com.progetto.farmacia.ordini;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Classe che modella la boundary {@code CreazioneSegnalazioneForm}
 */
public class CreazioneSegnalazioneForm extends Application implements Initializable {
    @FXML
    private TextArea riepilogoOrdine;
    @FXML
    private TextArea commento;
    private static String riepilogo;
    private static CreaSegnalazioneControl control;
    private static String segnalazione;

    /**
     * Costruttore di un oggetto di tipo {@code CreazioneSegnalazioneForm}
     */
    public CreazioneSegnalazioneForm() {super();}

    /**
     * Costruttore di un oggetto di tipo {@code CreazioneSegnalazioneForm} che prende in input il riepilogo dell'ordine
     * da mostrare e la control che ha creato l'oggetto
     * @param riepilogo riepilogo dell'ordine
     * @param control control che ha creato l'oggetto
     */
    public CreazioneSegnalazioneForm(String riepilogo, CreaSegnalazioneControl control) {
        this.setRiepilogo(riepilogo);
        this.setControl(control);
    }
    private void setRiepilogo(String riepilogo) {
        if(riepilogo == null) {
            throw new NullPointerException("Riepilogo = null");
        }
        CreazioneSegnalazioneForm.riepilogo = riepilogo;
    }
    private void setControl(CreaSegnalazioneControl control) {
        if(control == null) {
            throw new NullPointerException("Riepilogo = null");
        }
        CreazioneSegnalazioneForm.control = control;
    }
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("creazioneSegnalazioneForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 500);

        double stageWidth = 600;
        double stageHeight = 500;

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - stageWidth) / 2);
        stage.setY((screenBounds.getHeight() - stageHeight) / 2);

        stage.setTitle("Creazione Segnalazione Form");
        stage.setScene(scene);
        stage.setMinWidth(stageWidth);
        stage.setMinHeight(stageHeight);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.riepilogoOrdine.setText(CreazioneSegnalazioneForm.riepilogo);
    }

    @FXML
    private void inviaSegnalazione(ActionEvent event) {
        CreazioneSegnalazioneForm.segnalazione = this.commento.getText();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //ottiene stage corrente
        CreazioneSegnalazioneForm.control.clickSuInvia(stage);
    }

    public String getSegnalazione() {
        return CreazioneSegnalazioneForm.segnalazione;
    }
}
