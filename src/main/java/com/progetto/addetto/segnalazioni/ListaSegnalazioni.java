package com.progetto.addetto.segnalazioni;

import com.progetto.addetto.AddettoAzienda;
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
 * Classe che implementa la boundary {@code ListaSegnalazioni}
 */
public class ListaSegnalazioni extends Application implements Initializable {
    private static AddettoAzienda addetto;
    private static TableView<EntryListaSegnalazioni> ref;  // riferimento per poter apportare modifiche dall'esterno
    @FXML
    private Text usernameLabel;

    @FXML
    private TableView<EntryListaSegnalazioni> lista;

    @FXML
    private TableColumn<EntryListaSegnalazioni, Integer> idSegnalazione;
    @FXML
    private TableColumn<EntryListaSegnalazioni, Integer> idOrdine;
    @FXML
    private TableColumn<EntryListaSegnalazioni, String> nomeFarmacia;
    @FXML
    private TableColumn<EntryListaSegnalazioni, String> data;
    @FXML
    private TableColumn<EntryListaSegnalazioni, FlowPane> strumenti;

    private static ArrayList<EntryListaSegnalazioni> segnalazioni;

    private static GestioneSegnalazioniControl control;

    private Stage stage;
    /**
     * Costruisce una {@code ListaSegnalazioni}
     */
    public ListaSegnalazioni(){
        super();
    }
    /**
     * Costruisce una {@code ListaSegnalazioni} secondo il contenuto nel database dell'Azienda
     * @param addetto addetto che richiede la lista delle segnalazioni
     * @param segnalazioni lista delle segnalazioni
     * @param control control che ha dato origine alla lista delle segnalazioni
     */
    public ListaSegnalazioni(AddettoAzienda addetto, ArrayList<EntryListaSegnalazioni> segnalazioni, GestioneSegnalazioniControl control){
        super();
        this.setAddettoAZienda(addetto);
        this.setSegnalazioni(segnalazioni);
        this.setControl(control);
    }

    private void setControl(GestioneSegnalazioniControl control) {
        if(control == null){
            throw new NullPointerException("Visualizza Segnalazioni Control = null");
        }
        ListaSegnalazioni.control = control;
    }
    private void setAddettoAZienda(AddettoAzienda addetto) {
        if(addetto == null){
            throw new NullPointerException("Addetto dell'Azienda = null");
        }
        ListaSegnalazioni.addetto = addetto;
    }

    private void setSegnalazioni(ArrayList<EntryListaSegnalazioni> segnalazioni) {
        if(segnalazioni == null){
            throw new NullPointerException("Lista segnalazioni = null");
        }
        ListaSegnalazioni.segnalazioni = segnalazioni;
    }
    /**
     * Metodo utilizzato per visualizzare la {@code ListaSegnalazioni} a schermo
     * @param stage stage della lista
     * @throws IOException se il caricamento del file {@code fxml} non è andato a buon fine
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("listaSegnalazioni.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 400);

        double stageWidth = 800;
        double stageHeight = 400;

        this.stage = new Stage();

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        this.stage.setX((screenBounds.getWidth() - stageWidth) / 2);
        this.stage.setY((screenBounds.getHeight() - stageHeight) / 2);

        this.stage.setTitle("Lista Segnalazioni");
        this.stage.setScene(scene);
        this.stage.setMinWidth(stageWidth);
        this.stage.setMinHeight(stageHeight);
        this.stage.initOwner(stage);
        this.stage.show();
        this.stage.setOnCloseRequest(event -> { Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Per uscire dal programma effettua il logout.");
            alert.setTitle("Chiusura Programma");
            alert.setHeaderText("AVVISO");
            event.consume();
            alert.showAndWait();
        });
    }

    /**
     * Metodo utilizzato per personalizzare la {@code ListaSegnalazioni} dell'Addetto
     * @param url
     * @param resourceBundle
     */
    @SuppressWarnings("JavadocDeclaration")
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.usernameLabel.setText(ListaSegnalazioni.addetto.getNominativo());
        this.idSegnalazione.setCellValueFactory(new PropertyValueFactory<>("idSegnalazione"));
        this.idOrdine.setCellValueFactory(new PropertyValueFactory<>("idOrdine"));
        this.nomeFarmacia.setCellValueFactory(new PropertyValueFactory<>("nomeFarmacia"));
        this.data.setCellValueFactory(new PropertyValueFactory<>("data"));
        this.strumenti.setCellValueFactory(new PropertyValueFactory<>("strumenti"));

        for(EntryListaSegnalazioni entry : ListaSegnalazioni.segnalazioni) {
            this.lista.getItems().add(entry);
        }
        ListaSegnalazioni.ref = this.lista;
    }

    /**
     * Metodo per ottenere lo stage della lista delle segnalazioni e permettere ad un oggeto di classe {@code GestioneSegnalazioniControl}
     * di distruggerlo. Il metodo è stato creato senza modificatore di visibilità affinché possa essere invocato soltanto da classi
     * che si trovano nello stesso package.
     * @return stage della lista
     */
    Stage getStage() {
        return this.stage;
    }


    @FXML
    private void indietro(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();  // prendo lo stage corrente
        ListaSegnalazioni.control.clickSuIndietro(stage);
    }

    /**
     * Metodo utilizzato per rimuovere una entry dalla {@code ListaSegnalazioni}
     * @param entry segnalazione da rimuovere dalla lista
     */
    public void update(EntryListaSegnalazioni entry) {
        if(entry == null) {
            throw new NullPointerException("Entry della lista in conferma eliminazione = null");
        }
        ListaSegnalazioni.segnalazioni.remove(entry);
        ListaSegnalazioni.ref.getItems().remove(entry);
    }
}
