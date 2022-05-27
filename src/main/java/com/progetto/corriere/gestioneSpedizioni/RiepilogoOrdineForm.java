package com.progetto.corriere.gestioneSpedizioni;

import com.progetto.corriere.SchermataPrincipaleCorriere;
import com.progetto.entity.EntryListaSpedizioni;
import com.progetto.entity.LottoOrdinato;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
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
 * Classe che mostra a schermo il riepilogo di una spedizione
 */
public class RiepilogoOrdineForm extends Application implements Initializable {

    @FXML
    TextField nominativoFarmacista;
    @FXML
    Text campoIDOrdine;
    @FXML
    TextArea riepilogoOrdineTextArea;
    @FXML
    Text infoFarmacia;

    private static ConfermaRicezioneSpedizioneControl control;
    private static EntryListaSpedizioni spedizione;

    /**
     * Costruttore per il RiepilogoOrdineForm
     */
    public RiepilogoOrdineForm(){
        super();
    }

    /**
     * Costruttore per il RiepilogoOrdineForm
     * @param spedizione spedizione di cui mostrare il riepilogo
     * @param control control che permette di tornare a visualizzare la {@code ListaSpedizioni}
     */
    public RiepilogoOrdineForm(EntryListaSpedizioni spedizione, ConfermaRicezioneSpedizioneControl control) {
        setSpedizione(spedizione);
        setControl(control);
    }

    /**
     * Getter per la control
     * @return control
     */
    public ConfermaRicezioneSpedizioneControl getControl() {
        return control;
    }

    /**
     * Setter per la control
     * @param control control
     */
    public void setControl(ConfermaRicezioneSpedizioneControl control) {
        if (control == null) {
            throw new NullPointerException("control = null");
        }
        RiepilogoOrdineForm.control = control;
    }

    /**
     * Getter per la spedizione di cui mostrare il riepilogo
     * @return spedizione di cui mostrare il riepilogo
     */
    public EntryListaSpedizioni getSpedizione() {
        return spedizione;
    }

    /**
     * Setter per la spedizione di cui mostrare il riepilogo
     * @param spedizione spedizione di cui mostrare il riepilogo
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
        Scene scene = new Scene(fxmlLoader.load(), 800, 400);

        double stageWidth = 800;
        double stageHeight = 400;

        Stage subStage = new Stage();
        //centra la schermata
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        subStage.setX((screenBounds.getWidth() - stageWidth) / 2);
        subStage.setY((screenBounds.getHeight() - stageHeight) / 2);

        //mostra la schermata di login
        subStage.setTitle("Riepilogo Ordine");
        subStage.setScene(scene);
        subStage.setMinWidth(stageWidth + 50);
        subStage.setMinHeight(stageHeight);
        subStage.show();
    }

    /**
     * Metodo che inizializza la schermata inserendo i dati relativi alla spedizione
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
     * @param event evento associato alla pressione del {@code button} conferma consegna
     */
    @FXML
    public void confermaConsegna(ActionEvent event){
        String nominativo = nominativoFarmacista.getText();
        if (!(nominativo == null || nominativo.equals(""))) {
            getControl().confermaRicezione(nominativo);
            indietro(event);
        }
    }

    /**
     * Permette di tornare indietro e visualizzare la {@code ListaSpedizioni}
     * @param event evento associato alla pressione del {@code button} consegna
     */
    @FXML
    private void indietro(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        getControl().clickSuIndietro(stage);
    }
}
