package com.progetto.farmacia.ordini;

import com.progetto.addetto.segnalazioni.EntryListaSegnalazioni;
import com.progetto.farmacia.magazzino.EntryMagazzinoFarmacia;
import com.progetto.farmacia.SchermataPrincipaleFarmacia;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Classe che modella la boundary {@code SchermataRegistrazioneFarmaciBoundary}
 */
public class SchermataRegistrazioneFarmaciForm extends Application implements Initializable {

    @FXML
    private Text idOrdineLabel;
    @FXML
    private Text farmaciaLabel;
    @FXML
    private TableView<EntryMagazzinoFarmacia> lista;
    @FXML
    private TableColumn<EntryListaSegnalazioni, String> nomeFarmaco;
    @FXML
    private TableColumn<EntryListaSegnalazioni, String> principioAttivo;
    @FXML
    private TableColumn<EntryListaSegnalazioni, String> dataScadenza;
    @FXML
    private TableColumn<EntryListaSegnalazioni, Integer> quantita;
    @FXML
    private TableColumn<EntryListaSegnalazioni, FlowPane> strumenti;
    private static ArrayList<EntryMagazzinoFarmacia> farmaci;
    private static RegistrazioneFarmaciRicevutiControl control;
    private static int idOrdine;

    /**
     * Istanzia un oggetto di tipo {@code SchermataRegistrazioneFarmaci}
     */
    public SchermataRegistrazioneFarmaciForm(){super();}

    /**
     * Istanzia un oggetto di tipo {@code SchermataRegistrazioneFarmaci}
     * @param farmaci farmaci presenti nel magazzino
     * @param registrazioneFarmaciRicevutiControl control che gestisce la registrazione dei farmaci
     */
    public SchermataRegistrazioneFarmaciForm(ArrayList<EntryMagazzinoFarmacia> farmaci, RegistrazioneFarmaciRicevutiControl registrazioneFarmaciRicevutiControl, int idOrdine) {
        super();
        setFarmaci(farmaci);
        setControl(registrazioneFarmaciRicevutiControl);
        setIdOrdine(idOrdine);
    }

    /**
     * Ritorna l'id dell'ordine
     * @return {@code int} contenente l'id dell'ordine
     */

    public static int getIdOrdine() {
        return idOrdine;
    }

    /**
     * Permette di settare l'id dell'ordine
     * @param idOrdine id dell'ordine da impostare
     */
    public static void setIdOrdine(int idOrdine) {
        if (idOrdine < 0) {
            throw new IllegalArgumentException("idOrdine < 0");
        }
        com.progetto.farmacia.ordini.SchermataRegistrazioneFarmaciForm.idOrdine = idOrdine;
    }

    /**
     * Ritorna la lista dei farmaci presenti in magazzino
     * @return oggetto di tipo {@code ArrayList<EntryMagazzinoFarmacia>} contennete i farmaci presenti in magazzino
     */
    public static ArrayList<EntryMagazzinoFarmacia> getFarmaci() {
        return farmaci;
    }

    /**
     * Permette di settare la lista dei farmaci presenti in magazzino
     * @param farmaci farmaci presenti in magazzino
     */
    public static void setFarmaci(ArrayList<EntryMagazzinoFarmacia> farmaci) {
        if (farmaci == null) {
            throw new NullPointerException("farmaci = null");
        }
        com.progetto.farmacia.ordini.SchermataRegistrazioneFarmaciForm.farmaci = farmaci;
    }

    /**
     * Ritorna per la control che gestisce la schermata di resigtrazione farmaci
     * @return oggetto di tipo {@code RegistrazioneFarmaciRicevutiControl} contenente la control che gestisce la schermata di resigtrazione farmaci
     */
    public static RegistrazioneFarmaciRicevutiControl getControl() {
        return control;
    }

    /**
     * Permette di settare la control che gestisce la schermata di resigtrazione farmaci
     * @param control control che gestisce la schermata di resigtrazione farmaci
     */
    public static void setControl(RegistrazioneFarmaciRicevutiControl control) {
        if (control == null) {
            throw new NullPointerException("control = null");
        }
        com.progetto.farmacia.ordini.SchermataRegistrazioneFarmaciForm.control = control;
    }

    /**
     * Permette di mostrare a schermo la schermata con la lista dei farmaci nel magazzino
     * @param stage stage della schermata
     * @throws IOException lanciata se il caricamento del file {@code fxml} non è andato a buon fine
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("schermataRegistrazioneFarmaci.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 400);

        double stageWidth = 800;
        double stageHeight = 440;

        //centra la schermata
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - stageWidth) / 2);
        stage.setY((screenBounds.getHeight() - stageHeight) / 2);

        //mostra la schermata di registrazione dei farmaci
        stage.setTitle("Registrazone Farmaci");
        stage.setScene(scene);
        stage.setMinWidth(stageWidth + 50);
        stage.setMinHeight(stageHeight);
        stage.show();
        stage.setOnCloseRequest(event -> { Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Per uscire dal programma effettua il logout.");
            alert.setTitle("Chiusura Programma");
            alert.setHeaderText("AVVISO");
            event.consume();
            alert.showAndWait();
        });
    }

    /**
     * Permette di inizializzare la schermata inserendo i dati relativi ai farmaci da caricare
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.idOrdineLabel.setText("ID ordine: " + idOrdine);
        this.farmaciaLabel.setText(SchermataPrincipaleFarmacia.getFarmacia().getNome());
        this.nomeFarmaco.setCellValueFactory(new PropertyValueFactory<>("nome"));
        this.principioAttivo.setCellValueFactory(new PropertyValueFactory<>("principioAttivo"));
        this.dataScadenza.setCellValueFactory(new PropertyValueFactory<>("dataScadenza"));
        this.quantita.setCellValueFactory(new PropertyValueFactory<>("quantita"));
        this.strumenti.setCellValueFactory(new PropertyValueFactory<>("strumenti"));

        if (com.progetto.farmacia.ordini.SchermataRegistrazioneFarmaciForm.getFarmaci()!=null) {
            for(EntryMagazzinoFarmacia farmaco : com.progetto.farmacia.ordini.SchermataRegistrazioneFarmaciForm.getFarmaci()) {
                this.lista.getItems().add(farmaco);
            }
        }
    }

    @FXML
    private void confermaRegistrazione(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        getControl().clickSuConfermaRegistrazione();
    }

    /**
     * Permette di tornare indietro e visualizzare la {@code SchermataPrincipaleFarmacia}
     * @param event evento associato alla pressione del {@code button} indietro
     */
    @FXML
    private void indietro(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();  // prendo lo stage corrente
        control.clickSuIndietro(stage);
    }

}
