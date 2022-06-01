package com.progetto.farmacia.magazzino;

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
import javafx.scene.control.Button;
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
 * Classe che modella la boundary {@code SchermataMagazzinoBoundary} contenente tutti i farmaci nel magazzino della farmacia
 */
public class SchermataMagazzino extends Application implements Initializable {

    @FXML
    private Text usernameLabel;
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
    private static VisualizzaMagazzinoControl control;

    /**
     * Istanzia un oggetto di tipo {@code SchermataMagazzino}
     */
    public SchermataMagazzino(){super();}

    /**
     * Istanzia un oggetto di tipo {@code SchermataMagazzino} dati in input i farmaci presenti e la control che gestisce
     * la visualizzazione della schermata del magazzino
     * @param farmaci farmaci presenti nel magazzino
     * @param visualizzaMagazzinoControl control tramite il quale è possibile tornare alla schermata precedente
     */
    public SchermataMagazzino(ArrayList<EntryMagazzinoFarmacia> farmaci, VisualizzaMagazzinoControl visualizzaMagazzinoControl) {
        super();
        setFarmaci(farmaci);
        setControl(visualizzaMagazzinoControl);
    }

    /**
     * Ritorna la lista dei farmaci contenuti in magazzino
     * @return oggetto di tipo {@code  ArrayList<EntryMagazzinoFarmacia>} contenente ifarmaci presenti in magazzino
     */
    public static ArrayList<EntryMagazzinoFarmacia> getFarmaci() {
        return farmaci;
    }

    /**
     * Permette di settare la lista dei farmaci nel magazzino
     * @param farmaci farmaci nel magazzino
     */
    public static void setFarmaci(ArrayList<EntryMagazzinoFarmacia> farmaci) {
        if (farmaci == null) {
            throw new NullPointerException("farmaci = null");
        }
        SchermataMagazzino.farmaci = farmaci;
    }

    /**
     * Ritorna la control della schermata magazzino
     * @return oggetto di tipo {@code VisualizzaMagazzinoControl} contenente la control della schermata
     */
    public static VisualizzaMagazzinoControl getControl() {
        return control;
    }

    /**
     * Permette di settare la control della schermata
     * @param control control della schermata
     */
    public static void setControl(VisualizzaMagazzinoControl control) {
        if (control == null) {
            throw new NullPointerException("control = null");
        }
        SchermataMagazzino.control = control;
    }

    /**
     * Permette di mostrare la schermata con la lista dei farmaci nel magazzino
     * @param stage stage della schermata
     * @throws IOException lanciata se il caricamento del file fxml non è andato a buon fine
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("schermataMagazzino.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 400);

        double stageWidth = 800;
        double stageHeight = 440;

        Stage subStage = new Stage();
        //centra la schermata
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        subStage.setX((screenBounds.getWidth() - stageWidth) / 2);
        subStage.setY((screenBounds.getHeight() - stageHeight) / 2);

        //mostra la schermata del magazzino
        subStage.setTitle("Magazzino");
        subStage.setScene(scene);
        subStage.setMinWidth(stageWidth + 50);
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
     * Permette di inizializzare la schermata magazzino inserendo i dati relativi ai farmaci presenti in magazzino
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.usernameLabel.setText(SchermataPrincipaleFarmacia.getFarmacia().getNome());
        this.nomeFarmaco.setCellValueFactory(new PropertyValueFactory<>("nome"));
        this.principioAttivo.setCellValueFactory(new PropertyValueFactory<>("principioAttivo"));
        this.dataScadenza.setCellValueFactory(new PropertyValueFactory<>("dataScadenza"));
        this.quantita.setCellValueFactory(new PropertyValueFactory<>("quantita"));
        this.strumenti.setCellValueFactory(new PropertyValueFactory<>("strumenti"));

        if (SchermataMagazzino.getFarmaci()!=null) {
            for(EntryMagazzinoFarmacia farmaco : SchermataMagazzino.getFarmaci()) {
                this.lista.getItems().add(farmaco);
                Button pulsanteConsegna = (Button)farmaco.getStrumenti().getChildren().get(0);
                pulsanteConsegna.setOnAction(event -> rimuoviFarmaco(event, farmaco));
            }
        }
    }

    /**
     * Permette di aggiornare la schermata a seguito della rimozione di alcuni farmaci
     */
    public void update(){
        this.lista.getItems().clear();
        if (SchermataMagazzino.getFarmaci()!=null) {
            for(EntryMagazzinoFarmacia farmaco : SchermataMagazzino.getFarmaci()) {
                if (farmaco.getQuantita() != 0) {
                    this.lista.getItems().add(farmaco);
                }
            }
        }
    }

    /**
     * Permette di creare la control relativa alla rimozione di un farmaco
     * @param event evento associato alla pressione del {@code button} rimuovi farmaco
     * @param farmaco farmaco da rimuovere
     */
    public void rimuoviFarmaco(ActionEvent event,EntryMagazzinoFarmacia farmaco) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        RimuoviFarmaciControl rimuoviFarmaciControl = new RimuoviFarmaciControl(farmaco,stage,this);
        rimuoviFarmaciControl.clickSuRimuoviFarmaco();
    }

    /*
     * Permette di tornare indietro e visualizzare la SchermataPrincipaleFarmacia
    */
    @FXML
    private void indietro(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();  // prendo lo stage corrente
        control.clickSuIndietro(stage);
    }
}
