package com.progetto.farmacia.ordini;

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
 * Classe che modella l'elenco dei farmaci ordinabili dalla farmacia
 */
public class ElencoFarmaci extends Application implements Initializable {

    @FXML
    private Text usernameLabel;

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
     * Istanzia un oggetto di tipo {@code ElencoFarmaci}
     */
    public ElencoFarmaci(){
        super();
    }

    /**
     * Istanzia un oggetto di tipo {@code ElencoFarmaci} dati in input la control che gestisce la creazione degli ordini,
     * la farmacia e l'elenco dei farmaci ordinabili dalla farmacia
     * @param control control che gestisce la creazione di un ordine
     * @param farmacia farmacia che intende effettuare l'ordine
     * @param farmaci elenco dei farmaci ordinabili dalla farmacia
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

    /**
     * Permette di settare la control che gestisce la creazione di un ordine
     * @param control control che gestisce la creazione di un ordine
     */
    public void setControl(CreaOrdineControl control) {
        if(control == null){
            throw new NullPointerException("control = null");
        }
        ElencoFarmaci.control = control;
    }

    /**
     * Permette di mostrare a schermo l'elenco dei farmaci
     * @param stage riferimento alla finestra corrente
     * @throws IOException se il caricamento del file {@code fxml} della schermata non è andato a buon fine
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

    /*
     * Permette di tornare indietro e visualizzare il {@code FormOrdine}
     */
    @FXML
    private void conferma(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();  // prendo lo stage corrente
        ElencoFarmaci.control.clickSuConferma(stage);
    }

    /**
     * Permette di inizializzare la schermata contenente l'elenco dei farmaci
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
     * Permette di aggiungere un farmaco all'elenco dei farmaci
     * @param entry entry relativo al farmaco da aggiungere all'elenco dei farmaci
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
     * Permette di aggiungere un farmaco all'elenco dei farmaci
     * @param entry entry relativo al farmaco da rimuovere all'elenco dei farmaci
     * */
    public void rimuoviFarmaco(EntryFormOrdine entry) {
        if(entry == null) {
            throw new NullPointerException("Entry in rimuovi farmaco di ElencoFarmaci = null");
        }
        ElencoFarmaci.farmaci.remove(entry);
        ElencoFarmaci.ref.getItems().remove(entry);
    }
}