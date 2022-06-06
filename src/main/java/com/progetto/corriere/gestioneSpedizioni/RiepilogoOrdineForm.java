package com.progetto.corriere.gestioneSpedizioni;

import com.progetto.corriere.SchermataPrincipaleCorriere;
import com.progetto.entity.LottoOrdinato;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Classe modella la boundary {@code RiepilogoOrdineForm}
 */
public class RiepilogoOrdineForm extends Application implements Initializable {

    @FXML
    private TextField nominativoFarmacista;
    @FXML
    private Text campoIDOrdine;
    @FXML
    private TextArea riepilogoOrdineTextArea;
    @FXML
    private Text infoFarmacia;

    private static ConfermaRicezioneSpedizioneControl control;
    private static EntryListaSpedizioni spedizione;

    /**
     * Costruisce un oggetto {@code RiepilogoOrdineForm}
     */
    public RiepilogoOrdineForm(){
        super();
    }

    /**
     * Costruisce un oggetto {@code RiepilogoOrdineForm} dati in input la sepdizione e la control che gestisce la conferma della ricezione delle spdizioni
     * @param spedizione spedizione di cui mostrare il riepilogo
     * @param control control {@code ConfermaRicezioneSpedizioneControl} che permette di tornare a visualizzare la {@code ListaSpedizioni}
     */
    public RiepilogoOrdineForm(EntryListaSpedizioni spedizione, ConfermaRicezioneSpedizioneControl control) {
        setSpedizione(spedizione);
        setControl(control);
    }

    /**
     * Ritorna la control che gestisce la conferma della ricezione delle spedizioni
     * @return control {@code ConfermaRicezioneSpedizioneControl} che gestisce la conferma della ricezione delle spedizioni
     */
    public ConfermaRicezioneSpedizioneControl getControl() {
        return control;
    }

    /**
     * Permette di settare la control che gestice la conferma della ricezione delle spedizioni
     * @param control control {@code ConfermaRicezioneSpedizioneControl} che gestisce la conferma della ricezione delle spedizioni
     */
    public void setControl(ConfermaRicezioneSpedizioneControl control) {
        if (control == null) {
            throw new NullPointerException("control = null");
        }
        RiepilogoOrdineForm.control = control;
    }

    /**
     * Ritorna la spedizione di cui mostrare il riepilogo
     * @return oggetto di tipo {@code EntryListaSpedizioni} relativo alla spedizione di cui mostrare il riepilogo
     */
    public EntryListaSpedizioni getSpedizione() {
        return spedizione;
    }

    /**
     * Permette di settare la spdizione di cui mostrare il riepilogo
     * @param spedizione oggetto di tipo {@code EntryListaSpedizioni } relativo alla spedizione di cui mostrare il riepilogo
     */
    public void setSpedizione(EntryListaSpedizioni spedizione) {
        if (spedizione == null) {
            throw new NullPointerException("spedizione = null");
        }
        RiepilogoOrdineForm.spedizione = spedizione;
    }

    /**
     * Permette di mostrare a schermo il riepilogo di una spedizione
     * @param stage stage della schermata
     * @throws IOException lanciata se il caricamento del file fxml non è andato a buon fine
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("riepilogoOrdine.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        double stageWidth = 600;
        double stageHeight = 400;

        Stage subStage = new Stage();
        //centra la schermata
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        subStage.setX((screenBounds.getWidth() - stageWidth) / 2);
        subStage.setY((screenBounds.getHeight() - stageHeight) / 2);

        //mostra la schermata di login
        subStage.setTitle("Riepilogo Ordine");
        subStage.setScene(scene);
        subStage.setMinWidth(stageWidth);
        subStage.setMinHeight(stageHeight);
        subStage.show();
        subStage.setOnCloseRequest(event -> { Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Per uscire dal programma effettua il logout.");
            alert.setTitle("Chiusura Programma");
            alert.setHeaderText("AVVISO");
            event.consume();
            alert.showAndWait();
        });
    }

    /**
     * Metodo che inizializza il riepigolo ordine inserendo i dati relativi alla spedizione
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Cambio l'ID dell'Ordine nella schermata
        this.campoIDOrdine.setText(SchermataPrincipaleCorriere.getCorriere().getNominativo());

        //Inserisco l'Ordine nella TextArea
        ArrayList<LottoOrdinato> lottiContenuti = spedizione.getOrdine().getLottiContenuti();
        for (LottoOrdinato lotto : lottiContenuti) {
            riepilogoOrdineTextArea.setText(riepilogoOrdineTextArea.getText() + lotto.getNomeFarmaco() + ": " + lotto.getQuantitaOrdine() + "\n");
        }

        //Inserisco le credenziali della Farmacia nel Text
        infoFarmacia.setText(spedizione.getOrdine().getNomeFarmacia() + ", " + spedizione.getOrdine().getIndirizzoConsegna());
    }

    /**
     * Metodo che permette di confermare la consegna di una spedizione
     * @param event evento associato alla pressione del tasto conferma consegna
     */
    @FXML
    public void confermaRicezione(ActionEvent event){
        String nominativo = nominativoFarmacista.getText();
        if (!(nominativo == null || nominativo.equals(""))) {
            getControl().clickSuConfermaRicezione(nominativo);
            indietro(event);
        }
    }

    /**
     * Permette di tornare indietro e visualizzare la {@code ListaSpedizioni}
     * @param event evento associato alla pressione del tasto consegna
     */
    @FXML
    private void indietro(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        getControl().clickSuIndietro(stage);
    }
}
