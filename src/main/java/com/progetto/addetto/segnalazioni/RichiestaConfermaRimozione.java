package com.progetto.addetto.segnalazioni;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Classe che implementa la boundary {@code RichiestaConfermaRimozione}
 */
public class RichiestaConfermaRimozione extends Application implements Initializable {
    @FXML
    private Text messaggio;
    private static GestioneSegnalazioniControl control;
    private static EntryListaSegnalazioni entry;

    /**
     * Costruisce una {@code RichiestaConfermaRimozione}
     */
    public RichiestaConfermaRimozione(){
        super();
    }

    /**
     * Costruttore della classe {@code RichiestaConfermaRimozione}
     * @param entry segnalazione che caratterizza la schermata di rimozione
     * @param control control che ha dato origine alla richiesta di rimozione
     */
    public RichiestaConfermaRimozione(EntryListaSegnalazioni entry, GestioneSegnalazioniControl control) {
        this.setSegnalazione(entry);
        this.setControl(control);
    }

    private void setSegnalazione(EntryListaSegnalazioni entry) {
        if(entry == null) {
            throw new NullPointerException("Entry Lista Segnalazione in Riepilogo = null");
        }
        RichiestaConfermaRimozione.entry = entry;
    }

    private void setControl(GestioneSegnalazioniControl control) {
        if(control == null){
            throw new NullPointerException("Visualizza Segnalazioni Control = null");
        }
        RichiestaConfermaRimozione.control = control;
    }

    /**
     * Metodo utilizzato per visualizzare la {@code RichiestaConfermaRimozione} a schermo
     * @param stage stage della richiesta di conferma della rimozione
     * @throws IOException se il caricamento del file {@code fxml} non è andato a buon fine
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("richiestaConfermaRimozione.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 250);

        double stageWidth = 400;
        double stageHeight = 250;

        Stage subStage = new Stage();
        //centra la schermata
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        subStage.setX((screenBounds.getWidth() - stageWidth) / 2);
        subStage.setY((screenBounds.getHeight() - stageHeight) / 2);

        subStage.setTitle("Richiesta Rimozione Segnalazione");
        subStage.setScene(scene);
        subStage.setMinWidth(stageWidth);
        subStage.setMinHeight(stageHeight);
        subStage.initOwner(stage); //imposto come proprietario del Riepilogo la Lista Spedizioni
        subStage.initModality(Modality.WINDOW_MODAL);  //blocco il focus sulla schermata di Riepilogo
        subStage.show();
        subStage.setOnCloseRequest(event -> { Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Per uscire dal programma effettua il logout.");
            alert.setTitle("Chiusura Programma");
            alert.setHeaderText("AVVISO");
            event.consume();
            alert.showAndWait();
        });    }

    @FXML
    private void annulla(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();  // chiudo l'avviso
    }

    @FXML
    private void elimina(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();  // prendo lo stage corrente
        RichiestaConfermaRimozione.control.clickSuElimina(stage, RichiestaConfermaRimozione.entry);
    }


    /**
     * Metodo per personalizzare il messaggio di {@code RichiestaConfermaRimozione} in base alla segnalazione a cui si riferisce
     * @param url
     * @param resourceBundle
     */
    @SuppressWarnings("JavadocDeclaration")
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.messaggio.setText("Sei sicuro di volere eliminare la segnalazione (ID: " + RichiestaConfermaRimozione.entry.getIdSegnalazione() + ")?");
    }
}
