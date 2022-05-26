package com.progetto.addetto.segnalazioni;

import com.progetto.entity.AddettoAzienda;
import com.progetto.entity.EntryListaSegnalazioni;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
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

public class ListaSegnalazioni extends Application implements Initializable {
    @FXML
    private static AddettoAzienda addetto;

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
    /**
     * Costruisce una {@code ListaSegnalazioni}
     */
    public ListaSegnalazioni(){
        super();
    }
    /**
     * Costruisce una {@code ListaSegnalazioni} secondo il contenuto nel DBMS dell'Azienda
     * @param addetto addetto che richiede la lista delle segnalazioni
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
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("listaSegnalazioni.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 400);

        double stageWidth = 800;
        double stageHeight = 400;

        Stage subStage = new Stage();
        //centra la schermata
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        subStage.setX((screenBounds.getWidth() - stageWidth) / 2);
        subStage.setY((screenBounds.getHeight() - stageHeight) / 2);

        //mostra la schermata di login
        subStage.setTitle("Lista Segnalazioni");
        subStage.setScene(scene);
        subStage.setMinWidth(stageWidth + 50);
        subStage.setMinHeight(stageHeight);
        subStage.initOwner(stage); //imposto come proprietario dello stage dell'errore lo stage della schermata di login passato in input
        subStage.show();
    }

    /**
     * Metodo utilizzato per personalizzare la {@code ListaSegnalazioni} dell'Addetto
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.usernameLabel.setText(ListaSegnalazioni.addetto.getNominativo());
        idSegnalazione.setCellValueFactory(new PropertyValueFactory<>("idSegnalazione"));
        idOrdine.setCellValueFactory(new PropertyValueFactory<>("idOrdine"));
        nomeFarmacia.setCellValueFactory(new PropertyValueFactory<>("nomeFarmacia"));
        data.setCellValueFactory(new PropertyValueFactory<>("data"));
        strumenti.setCellValueFactory(new PropertyValueFactory<>("strumenti"));

        for(EntryListaSegnalazioni entry : this.segnalazioni) {
            this.lista.getItems().add(entry);
        }
    }

    @FXML
    private void indietro(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();  // prendo lo stage corrente
        ListaSegnalazioni.control.clickSuIndietro(stage);
    }
}
