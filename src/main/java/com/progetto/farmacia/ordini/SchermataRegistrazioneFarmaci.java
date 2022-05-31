package com.progetto.farmacia.ordini;

import com.progetto.entity.EntryListaSegnalazioni;
import com.progetto.entity.EntryMagazzinoFarmacia;
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

public class SchermataRegistrazioneFarmaci extends Application implements Initializable {

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
     * Costruttore per la schermata magazzino
     */
    public SchermataRegistrazioneFarmaci(){super();}

    /**
     * Costruttore per la schermata magazzino
     * @param farmaci farmaci presenti nel magazzino
     * @param registrazioneFarmaciRicevutiControl control tramite il quale è possibile tornare alla schermata precedente
     */
    public SchermataRegistrazioneFarmaci(ArrayList<EntryMagazzinoFarmacia> farmaci, RegistrazioneFarmaciRicevutiControl registrazioneFarmaciRicevutiControl, int idOrdine) {
        super();
        setFarmaci(farmaci);
        setControl(registrazioneFarmaciRicevutiControl);
        setIdOrdine(idOrdine);
    }

    public static int getIdOrdine() {
        return idOrdine;
    }

    public static void setIdOrdine(int idOrdine) {
        if (idOrdine < 0) {
            throw new IllegalArgumentException("idOrdine < 0");
        }
        SchermataRegistrazioneFarmaci.idOrdine = idOrdine;
    }

    /**
     * Getter per la lista dei farmaci nel magazzino
     * @return farmaci nel magazzino
     */
    public static ArrayList<EntryMagazzinoFarmacia> getFarmaci() {
        return farmaci;
    }

    /**
     * Setter per la lista dei farmaci nel magazzino
     * @param farmaci farmaci nel magazzino
     */
    public static void setFarmaci(ArrayList<EntryMagazzinoFarmacia> farmaci) {
        if (farmaci == null) {
            throw new NullPointerException("farmaci = null");
        }
        SchermataRegistrazioneFarmaci.farmaci = farmaci;
    }

    /**
     * Getter per la control della schermata
     * @return control della schermata
     */
    public static RegistrazioneFarmaciRicevutiControl getControl() {
        return control;
    }

    /**
     * Setter per la control della schermata
     * @param control control della schermata
     */
    public static void setControl(RegistrazioneFarmaciRicevutiControl control) {
        if (control == null) {
            throw new NullPointerException("control = null");
        }
        SchermataRegistrazioneFarmaci.control = control;
    }

    /**
     * Permette di mostrare la schermata con la lista dei farmaci nel magazzino
     * @param stage stage della schermata
     * @throws IOException lanciata se il caricamento del file fxml non è andato a buon fine
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
     * Metodo che inizializza la schermata inserendo i dati relativi ai farmaci da caricare
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

        if (SchermataRegistrazioneFarmaci.getFarmaci()!=null) {
            for(EntryMagazzinoFarmacia farmaco : SchermataRegistrazioneFarmaci.getFarmaci()) {
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
