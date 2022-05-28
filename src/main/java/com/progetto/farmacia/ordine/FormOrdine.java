package com.progetto.farmacia.ordine;

import com.progetto.entity.EntryFormOrdine;
import com.progetto.entity.Farmacia;
import com.progetto.entity.Farmaco;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
 *  classe che rappresenta il form che permette di effettuare ordini
 */
public class FormOrdine extends Application implements Initializable {

    private static Farmacia farmacia;

    private static CreaOrdineControl control;

    private static TableView<EntryFormOrdine> ref;  // riferimento per poter apportare modifiche dall'esterno
    private static Stage stage;
    private static ArrayList<EntryFormOrdine> farmaci;
    @FXML
    private Text usernameLabel;

    @FXML
    private TableView<EntryFormOrdine> lista;

    @FXML
    private TableColumn<EntryFormOrdine, String> nomeFarmaco;

    @FXML
    private TableColumn<EntryFormOrdine, String> principioAttivo;

    @FXML
    private TableColumn<EntryFormOrdine, FlowPane> strumenti;


    /**
     * costruisce un {@code FormOrdine}
     */
    public FormOrdine(){
        super();
    }

    /**
     * costruisce un {@code FormOrdine} dato in input il nome della farmacia e la contrl {@code CreaOrdineControl}
     * @param farmacia entity farmacia
     * @param control control di crea ordine
     */
    public FormOrdine(Farmacia farmacia, CreaOrdineControl control){
        this.setFarmacia(farmacia);
        this.setControl(control);
        this.setFarmaci(new ArrayList<EntryFormOrdine>());
    }

    private void setFarmaci(ArrayList<EntryFormOrdine> farmaci) {
        if(farmaci == null){
            throw new NullPointerException("Farmaci = null");
        }
        FormOrdine.farmaci = farmaci;
    }
    private void setControl(CreaOrdineControl control){
        if(control == null){
            throw new NullPointerException("control = null");
        }
        FormOrdine.control = control;
    }

    private void setFarmacia(Farmacia farmacia) {
        if(farmacia == null){
            throw new NullPointerException("farmacia = null");
        }
        FormOrdine.farmacia = farmacia;
    }

    /**
     * Metodo per ottenere lo stage del form ordine e permettere ad un oggeto di classe {@code FormOrdineControl}
     * di mostrarlo. Il metodo è stato creato senza modificatore di visibilità affinché possa essere invocato soltanto da classi
     * che si trovano nello stesso package.
     * @return stage della lista
     */
    Stage getStage(){
        return this.stage;
    }

    @FXML
    private void aggiungiFarmaci(ActionEvent event) throws IOException{
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //ottiene stage corrente
        FormOrdine.control.clickSuAggungiFarmaci(stage);
    }

    @FXML
    private void indietro(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //ottiene stage corrente
        FormOrdine.control.clickSuIndietro(stage);
    }

    @FXML
    private void invia(ActionEvent event){
        ArrayList<Farmaco> farmaci = new ArrayList<>();

        for(int i = 0;; i++) {
            if(this.lista.getColumns().get(0).getCellData(i) == null){
                break;
            }
            String nomeFarmaco = this.lista.getColumns().get(0).getCellData(i).toString();
            String princpioAttivo = this.lista.getColumns().get(1).getCellData(i).toString();
            Spinner<Integer> spinner = (Spinner<Integer>) this.strumenti.getCellData(0).getChildren().get(0);
            int quantita = spinner.getValue();
            farmaci.add(new Farmaco(nomeFarmaco, quantita, princpioAttivo));
        }

        VerificaCorrettezzaOrdineControl verCorrOrdCtrl = new VerificaCorrettezzaOrdineControl(farmaci,FormOrdine.farmacia,this.getStage());
        verCorrOrdCtrl.start();
    }

    /**
     * Metodo usato per mostrare a schermo il form per effettuare gli ordini
     * @param stage stage della schermata principale
     * @throws IOException se il caricamento del file fxml del form ordine non è andato a buon fine
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("formOrdine.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        double stageWidth = 800;
        double stageHeight = 400;

        this.stage = new Stage();

        //centra la schermata
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        this.stage.setX((screenBounds.getWidth() - stageWidth) / 2);
        this.stage.setY((screenBounds.getHeight() - stageHeight) / 2);

        this.stage.setTitle("Form ordine");
        this.stage.setScene(scene);
        this.stage.setHeight(stageHeight);
        this.stage.setWidth(stageWidth);
        this.stage.setMinWidth(stageWidth);
        this.stage.setMinHeight(stageHeight);
        this.stage.initOwner(stage); //imposto come proprietario dello stage dell'errore lo stage della schermata di login passato in input
        this.stage.show();
    }

    /**
     * Metodo usato per inzializzare il {@code FormOrdine}
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.usernameLabel.setText(FormOrdine.farmacia.getNome());
        this.principioAttivo.setCellValueFactory(new PropertyValueFactory<>("principioAttivo"));
        this.nomeFarmaco.setCellValueFactory(new PropertyValueFactory<>("nomeFarmaco"));
        this.strumenti.setCellValueFactory(new PropertyValueFactory<>("strumenti"));
        FormOrdine.ref = this.lista;
    }

    public void aggiungiFarmaco(EntryFormOrdine entry) {
        if(entry == null) {
            throw new NullPointerException("Entry in aggiungi farmaco = null");
        }
        if(!FormOrdine.farmaci.contains(entry)) {
            FormOrdine.farmaci.add(entry);
            FormOrdine.ref.getItems().add(entry);
        }
    }

    public void rimuoviFarmaco(EntryFormOrdine entry) {
        if(entry == null) {
            throw new NullPointerException("Entry in rimuovi farmaco di FormOrdine = null");
        }
        FormOrdine.farmaci.remove(entry);
        FormOrdine.ref.getItems().remove(entry);
    }
}
