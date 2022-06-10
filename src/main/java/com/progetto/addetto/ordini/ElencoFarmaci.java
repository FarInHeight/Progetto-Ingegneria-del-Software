package com.progetto.addetto.ordini;

import com.progetto.entity.EntryFormOrdine;
import com.progetto.entity.Farmacia;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Classe che mostra l'elenco dei farmaci ordinabili dall'addetto
 */
public class ElencoFarmaci extends Application implements Initializable {

    @FXML
    private Text usernameLabel;
    @FXML
    private TextField campoRicerca;
    private static Farmacia farmacia;

    private static CreaOrdineControl control;

    private static ArrayList<EntryFormOrdine> farmaci;

    private static TableView<EntryFormOrdine> ref;  // riferimento per poter apportare modifiche dall'esterno
    @FXML
    private TableView<EntryFormOrdine> lista;

    @FXML
    private TableColumn<EntryFormOrdine, String> nomeFarmaco;

    @FXML
    private TableColumn<EntryFormOrdine, String> principioAttivo;

    @FXML
    private TableColumn<EntryFormOrdine, FlowPane> strumenti;

    /**
     * Costruttore utilizzato per istanziare un {@code ElencoFarmaci}
     */
    public ElencoFarmaci(){
        super();
    }

    /**
     * Costruttore utilizzato per istanziare un {@code ElencoFarmaci} che prende in input la control che ha creato l'oggetto,
     * la farmacia a cui fa riferimento l'elenco e la lista di farmaci da mostrare a schermo.
     * @param control control che ha creato l'oggetto
     * @param farmacia farmacia che vuole ordinare farmaci
     * @param farmaci farmaci da mostrare a video
     */
    public ElencoFarmaci(CreaOrdineControl control, Farmacia farmacia, ArrayList<EntryFormOrdine> farmaci) {
        super();
        this.setFarmacia(farmacia);
        this.setEntryElencoFarmaci(farmaci);
        this.setControl(control);
    }

    private void setEntryElencoFarmaci(ArrayList<EntryFormOrdine> entryElencoFarmaci) {
        if(entryElencoFarmaci == null){
            throw new NullPointerException("entryElencoFarmaci = null");
        }
        ElencoFarmaci.farmaci = entryElencoFarmaci;
    }

    private void setFarmacia(Farmacia farmacia){
        if(farmacia == null){
            throw new NullPointerException("farmacia = null");
        }
        ElencoFarmaci.farmacia = farmacia;
    }

    private void setControl(CreaOrdineControl control) {
        if(control == null){
            throw new NullPointerException("control = null");
        }
        ElencoFarmaci.control = control;
    }

    /**
     * Metodo utilizzato per visualizzare un {@code ElencoFarmaci} a schermo
     * @param stage stage dell'elenco
     * @throws IOException se il caricamento del file {@code fxml} non è andato a buon fine
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("elencoFarmaci.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 400);

        double stageWidth = 800;
        double stageHeight = 400;

        Stage newStage = new Stage();

        //centra la schermata
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        newStage.setX((screenBounds.getWidth() - stageWidth) / 2);
        newStage.setY((screenBounds.getHeight() - stageHeight) / 2);

        newStage.setTitle("Elenco farmaci");
        newStage.setScene(scene);
        newStage.setHeight(stageHeight);
        newStage.setWidth(stageWidth);
        newStage.setMinWidth(stageWidth);
        newStage.setMinHeight(stageHeight);
        newStage.initOwner(stage); //imposto come proprietario dello stage dell'errore lo stage della schermata di login passato in input
        newStage.initModality(Modality.WINDOW_MODAL);  //blocco il focus sulla schermata delle'errore
        newStage.show();
        newStage.setOnCloseRequest(event -> { Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Per uscire dal programma effettua il logout.");
            alert.setTitle("Chiusura Programma");
            alert.setHeaderText("AVVISO");
            event.consume();
            alert.showAndWait();
        });
    }

    @FXML
    private void conferma(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();  // prendo lo stage corrente
        ElencoFarmaci.control.clickSuConferma(stage);
    }

    /**
     * Metodo utilizzato per personalizzare un {@code ElencoFarmaci}
     * @param url
     * @param resourceBundle
     */
    @SuppressWarnings("JavadocDeclaration")
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.usernameLabel.setText(ElencoFarmaci.farmacia.getNome());
        this.nomeFarmaco.setCellValueFactory(new PropertyValueFactory<>("nomeFarmaco"));
        this.principioAttivo.setCellValueFactory(new PropertyValueFactory<>("principioAttivo"));
        this.strumenti.setCellValueFactory(new PropertyValueFactory<>("strumenti"));
        for(EntryFormOrdine entry : ElencoFarmaci.farmaci) {
            this.lista.getItems().add(entry);
        }
        ElencoFarmaci.ref = this.lista;
    }

    /**
     * Metodo utilizzato per aggiungere un farmaco all'elenco
     * @param entry entry del form ordine
     */
    public void aggiungiFarmaco(EntryFormOrdine entry) {
        if(entry == null) {
            throw new NullPointerException("Entry in aggiungi farmaco = null");
        }
        if(!ElencoFarmaci.farmaci.contains(entry)) {
            ElencoFarmaci.farmaci.add(entry);
            ElencoFarmaci.ref.getItems().add(entry);
        }
    }

    /**
     * Metodo utilizzato per rimuovere un farmaco dall'elenco
     * @param entry entry del form ordine
     */
    public void rimuoviFarmaco(EntryFormOrdine entry) {
        if(entry == null) {
            throw new NullPointerException("Entry in rimuovi farmaco di ElencoFarmaci = null");
        }
        ElencoFarmaci.farmaci.remove(entry);
        ElencoFarmaci.ref.getItems().remove(entry);
    }

    @FXML
    private void cambiaContenuto() {
        this.lista.getItems().clear();
        String campo = this.campoRicerca.getText();
        for(EntryFormOrdine entry : ElencoFarmaci.farmaci) {
            if(entry.getNomeFarmaco().strip().toLowerCase().contains(campo.toLowerCase().strip()) ||
                    entry.getPrincipioAttivo().strip().toLowerCase().contains(campo.toLowerCase().strip()))
                this.lista.getItems().add(entry);
        }
    }
}
