package com.progetto.corriere.gestioneSpedizioni;

import com.progetto.addetto.segnalazioni.ListaSegnalazioni;
import com.progetto.corriere.SchermataPrincipaleCorriere;
import com.progetto.entity.*;
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

public class ListaSpedizioni extends Application implements Initializable {

    @FXML
    private Text usernameLabel;

    @FXML
    private TableView<EntryListaSpedizioni> lista;

    @FXML
    private TableColumn<EntryListaSegnalazioni, Integer> idOrdine;
    @FXML
    private TableColumn<EntryListaSegnalazioni, String> nomeFarmacia;
    @FXML
    private TableColumn<EntryListaSegnalazioni, String> indirizzoConsegna;
    @FXML
    private TableColumn<EntryListaSegnalazioni, FlowPane> strumenti;

    private static ArrayList<EntryListaSpedizioni> spedizioni;

    private static CreaListaSpedizioniControl creaListaSpedizioniControl;

    public ListaSpedizioni(){ }

    public ListaSpedizioni(ArrayList<EntryListaSpedizioni> spedizioni, CreaListaSpedizioniControl creaListaSpedizioniControl) {
        super();
        setSpedizioni(spedizioni);
        setCreaListaSpedizioniControl(creaListaSpedizioniControl);
    }

    public void setSpedizioni(ArrayList<EntryListaSpedizioni> spedizioni) {
        if (spedizioni == null) {
            throw new NullPointerException("spedizioni = null");
        }
        ListaSpedizioni.spedizioni = spedizioni;
    }

    public void setCreaListaSpedizioniControl(CreaListaSpedizioniControl creaListaSpedizioniControl) {
        if (creaListaSpedizioniControl == null) {
            throw new NullPointerException("control = null");
        }
        ListaSpedizioni.creaListaSpedizioniControl = creaListaSpedizioniControl;
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("listaSpedizioni.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 400);

        double stageWidth = 800;
        double stageHeight = 400;

        Stage subStage = new Stage();
        //centra la schermata
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        subStage.setX((screenBounds.getWidth() - stageWidth) / 2);
        subStage.setY((screenBounds.getHeight() - stageHeight) / 2);

        //mostra la schermata di login
        subStage.setTitle("Lista Spedizioni");
        subStage.setScene(scene);
        subStage.setMinWidth(stageWidth + 50);
        subStage.setMinHeight(stageHeight);
        subStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.usernameLabel.setText(SchermataPrincipaleCorriere.getCorriere().getNominativo());
        idOrdine.setCellValueFactory(new PropertyValueFactory<>("idOrdine"));
        nomeFarmacia.setCellValueFactory(new PropertyValueFactory<>("nomeFarmacia"));
        indirizzoConsegna.setCellValueFactory(new PropertyValueFactory<>("indirizzoConsegna"));
        strumenti.setCellValueFactory(new PropertyValueFactory<>("strumenti"));

        if (ListaSpedizioni.spedizioni!=null) {
            for(EntryListaSpedizioni spedizione : ListaSpedizioni.spedizioni) {
                this.lista.getItems().add(spedizione);
            }
        }

    }

    @FXML
    private void indietro(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();  // prendo lo stage corrente
        creaListaSpedizioniControl.clickSuIndietro(stage);
    }

}
