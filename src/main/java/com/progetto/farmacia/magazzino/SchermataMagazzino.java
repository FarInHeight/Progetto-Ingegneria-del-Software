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
 * Classe che gestiste la schermata contenente tutti i farmaci nel magazzino della {@code farmacia}
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
     * Costruttore per la schermata magazzino
     */
    public SchermataMagazzino(){super();}

    /**
     * Costruttore per la schermata magazzino
     * @param farmaci farmaci presenti nel magazzino
     * @param visualizzaMagazzinoControl control tramite il quale è possibile tornare alla schermata precedente
     */
    public SchermataMagazzino(ArrayList<EntryMagazzinoFarmacia> farmaci, VisualizzaMagazzinoControl visualizzaMagazzinoControl) {
        super();
        setFarmaci(farmaci);
        setControl(visualizzaMagazzinoControl);
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
        SchermataMagazzino.farmaci = farmaci;
    }

    /**
     * Getter per la control della schermata
     * @return control della schermata
     */
    public static VisualizzaMagazzinoControl getControl() {
        return control;
    }

    /**
     * Setter per la control della schermata
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

    }

    /**
     * Metodo che inizializza la schermata inserendo i dati relativi ai farmaci nel magazzino
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
     * Metodo che aggiorna la schermata a seguito della rimozione di alcuni farmaci
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
     * Metodo che crea la control relativa alla rimozione di un farmaco
     * @param event evento associato alla pressione del {@code button} rimuovi farmaco
     * @param farmaco farmaco da rimuovere
     */
    public void rimuoviFarmaco(ActionEvent event,EntryMagazzinoFarmacia farmaco) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        RimuoviFarmaciControl rimuoviFarmaciControl = new RimuoviFarmaciControl(farmaco,stage,this);
        rimuoviFarmaciControl.clickSuRimuoviFarmaco();
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
