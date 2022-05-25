package com.progetto.addetto.segnalazioni;

import com.progetto.entity.AddettoAzienda;
import com.progetto.entity.EntryListaSegnalazioni;
import com.progetto.entity.Segnalazione;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Locale;
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
    public ListaSegnalazioni(AddettoAzienda addetto){
        super();
        this.setAddettoAZienda(addetto);
    }

    private void setAddettoAZienda(AddettoAzienda addetto){
        if(addetto == null){
            throw new NullPointerException("Addetto dell'Azienda = null");
        }
        ListaSegnalazioni.addetto = addetto;
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


        //centra la schermata
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - stageWidth) / 2);
        stage.setY((screenBounds.getHeight() - stageHeight) / 2);

        //mostra la schermata di login
        stage.setTitle("Lista Segnalazioni");
        stage.setScene(scene);
        stage.setMinWidth(stageWidth + 50);
        stage.setMinHeight(stageHeight);
        stage.show();
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
    }

    @FXML
    private void indietro() {

    }
}
