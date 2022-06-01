package com.progetto.farmacia.ordini;

import com.progetto.entity.*;
import com.progetto.farmacia.magazzino.SchermataMagazzino;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 *  Classe che modella il form che permette di effettuare ordini
 */
public class FormModificaOrdine extends Application implements Initializable {

    private static Farmacia farmacia;

    private static ListaOrdini refListaOrdini;

    private static ModificaOrdineControl control;
    private static EntryListaOrdini entry;
    private static TableView<EntryFormOrdine> ref;  // riferimento per poter apportare modifiche dall'esterno
    private static Stage stage;
    private static ArrayList<EntryFormOrdine> farmaci;
    @FXML
    private Text usernameLabel;
    @FXML
    private Button aggungiFarmaciButton;

    @FXML
    private Pane pane;

    @FXML
    private Text periodoLabel;

    @FXML
    private TableView<EntryFormOrdine> lista;

    @FXML
    private TableColumn<EntryFormOrdine, String> nomeFarmaco;

    @FXML
    private TableColumn<EntryFormOrdine, String> principioAttivo;

    @FXML
    private TableColumn<EntryFormOrdine, FlowPane> strumenti;


    /**
     * Istanzia un oggetto di tipo {@code FormModificaOrdine}
     */
    public FormModificaOrdine(){
        super();
    }

    /**
     * Istanzia un oggetto di tipo {@code FormModificaOrdine}} dato in input il nome della farmacia e la contrl che si occupa di modificare gli ordini
     * @param farmacia entity farmacia
     * @param control control che gestisce la modifica degli ordini
     * @param entry entry della lista ordini relativa all'ordine da modificare
     * @param  refListaOrdini riferimento alla lsita degli ordini
     */
    public FormModificaOrdine(Farmacia farmacia, ModificaOrdineControl control, EntryListaOrdini entry, ListaOrdini refListaOrdini){
        this.setFarmacia(farmacia);
        this.setControl(control);
        this.setEntry(entry);
        this.setRefListaOrdini(refListaOrdini);
        FormModificaOrdine.farmaci = new ArrayList<>();
    }


    private void setRefListaOrdini(ListaOrdini refListaOrdini){
        if(refListaOrdini == null){
            throw new NullPointerException("ref lista ordini = null");
        }
        FormModificaOrdine.refListaOrdini = refListaOrdini;
    }

    private void setEntry(EntryListaOrdini entry){
        if(entry == null){
            throw new NullPointerException("entry = null");
        }
        FormModificaOrdine.entry = entry;
    }

    private void setControl(ModificaOrdineControl control){
        if(control == null){
            throw new NullPointerException("control = null");
        }
        FormModificaOrdine.control = control;
    }

    private void setFarmacia(Farmacia farmacia) {
        if(farmacia == null){
            throw new NullPointerException("farmacia = null");
        }
        FormModificaOrdine.farmacia = farmacia;
    }

    /**
     * Metodo per ottenere lo stage del form modifica ordine e permettere ad un oggeto di classe {@code FormModificaOrdineControl}
     * di mostrarlo. Il metodo è stato creato senza modificatore di visibilità affinché possa essere invocato soltanto da classi
     * che si trovano nello stesso package.
     * @return stage della lista
     */
    Stage getStage(){
        return FormModificaOrdine.stage;
    }

    @FXML
    private void aggiungiFarmaci(ActionEvent event) throws IOException{
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //ottiene stage corrente
        FormModificaOrdine.control.clickSuAggungiFarmaci(stage);
    }

    @FXML
    private void indietro(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //ottiene stage corrente
        FormModificaOrdine.control.clickSuIndietro(stage);
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
            Spinner<Integer> spinner = (Spinner<Integer>) this.strumenti.getCellData(i).getChildren().get(0);
            int quantita = spinner.getValue();
            farmaci.add(new Farmaco(nomeFarmaco, quantita, princpioAttivo));
        }
        int periodo = 0;
        if(entry.getOrdine().getTipo() == 1) {
            periodo = ((Spinner<Integer>) this.pane.getChildren().get(1)).getValue();
        }
        VerificaCorrettezzaOrdineControl verCorrOrdCtrl = new VerificaCorrettezzaOrdineControl(farmaci, FormModificaOrdine.farmacia,this.getStage(),FormModificaOrdine.entry,periodo, this.refListaOrdini);
        verCorrOrdCtrl.start();
    }

    /**
     * Metodo usato per mostrare a schermo il form per modificare gli ordini
     * @param stage riferimento alla finestra della schermata principale
     * @throws IOException se il caricamento del file {@code fxml} del form modifica ordine non è andato a buon fine
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("formModificaOrdine.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        double stageWidth = 800;
        double stageHeight = 400;

        FormModificaOrdine.stage = new Stage();

        //centra la schermata
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        FormModificaOrdine.stage.setX((screenBounds.getWidth() - stageWidth) / 2);
        FormModificaOrdine.stage.setY((screenBounds.getHeight() - stageHeight) / 2);

        FormModificaOrdine.stage.setTitle("Form Ordine");
        FormModificaOrdine.stage.setScene(scene);
        FormModificaOrdine.stage.setHeight(stageHeight);
        FormModificaOrdine.stage.setWidth(stageWidth);
        FormModificaOrdine.stage.setMinWidth(stageWidth);
        FormModificaOrdine.stage.setMinHeight(stageHeight);
        FormModificaOrdine.stage.initOwner(stage); //imposto come proprietario dello stage dell'errore lo stage della schermata di login passato in input
        FormModificaOrdine.stage.show();
        FormModificaOrdine.stage.setOnCloseRequest(event -> { Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Per uscire dal programma effettua il logout.");
            alert.setTitle("Chiusura Programma");
            alert.setHeaderText("AVVISO");
            event.consume();
            alert.showAndWait();
        });
    }

    /**
     * Metodo usato per inizializzare il from per la modifica dell'ordine
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.usernameLabel.setText(FormModificaOrdine.farmacia.getNome());
        this.principioAttivo.setCellValueFactory(new PropertyValueFactory<>("principioAttivo"));
        this.nomeFarmaco.setCellValueFactory(new PropertyValueFactory<>("nomeFarmaco"));
        this.strumenti.setCellValueFactory(new PropertyValueFactory<>("strumenti"));

        if(FormModificaOrdine.entry.getOrdine().getTipo() == 1) {
            this.aggungiFarmaciButton.setVisible(false);
            this.aggungiFarmaciButton.setManaged(false);
        }
        //converti entry lista ordini in entry form ordine
        ArrayList<Farmaco> farmaciEntry = this.entry.getFarmaci();
        for(Farmaco farmaco : farmaciEntry){
            EntryFormOrdine e = new EntryFormOrdine(farmaco.getNome(),farmaco.getPrincipioAttivo());
            FormModificaOrdine.farmaci.add(e);
        }

        for(EntryFormOrdine entry: FormModificaOrdine.farmaci) {
            this.setPulsanti(entry);
            this.lista.getItems().add(entry);
        }
        FormModificaOrdine.ref = this.lista;
    }

    private void setPulsanti(EntryFormOrdine entry){
        Button rimuovi = new Button("RIMUOVI");
        rimuovi.setBackground(Background.fill(Color.rgb(255, 79, 66)));
        rimuovi.setStyle("-fx-text-fill: white");
        rimuovi.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FormModificaOrdine.control.clickSuRimuovi(entry);
            }
        });
        if(FormModificaOrdine.entry.getOrdine().getTipo() == 1) {
            rimuovi.setVisible(false);
            rimuovi.setManaged(false);
            this.periodoLabel.setVisible(true);
            Spinner<Integer> spinner = new Spinner<>();
            spinner.setEditable(true);
            SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,Integer.MAX_VALUE,1);
            spinner.setValueFactory(valueFactory);
            spinner.setMaxWidth(60);
            spinner.setTranslateX(140);
            spinner.setLayoutY(8);
            this.pane.getChildren().add(spinner);
        }
        Spinner<Integer> spinner = new Spinner<Integer>();
        spinner.setEditable(true);
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,Integer.MAX_VALUE,1);
        spinner.setValueFactory(valueFactory);
        spinner.setMaxWidth(100);
        FlowPane flow = new FlowPane();
        flow.getChildren().addAll(spinner, rimuovi);
        flow.setAlignment(Pos.CENTER);
        flow.setHgap(10); // dae8fc
        entry.setStrumenti(flow);
    }

    /**
     * Permette di rimuovere un farmaco dal from che permette la modifica dell'ordine
     * @param entry entry relativo al farmaco da aggiungere al from che permette la modifica dell'ordine
     */
    public void aggiungiFarmaco(EntryFormOrdine entry) {
        if(entry == null) {
            throw new NullPointerException("Entry in aggiungi farmaco = null");
        }
        if(!FormModificaOrdine.farmaci.contains(entry)) {
            FormModificaOrdine.farmaci.add(entry);
            FormModificaOrdine.ref.getItems().add(entry);
        }
    }

    /**
     * Permette di rimuovere un farmaco dal from che permette la modifica dell'ordine
     * @param entry entry relativo al farmaco da rimuovere dal from che permette la modifica dell'ordine
     * */

    public void rimuoviFarmaco(EntryFormOrdine entry) {
        if(entry == null) {
            throw new NullPointerException("Entry in rimuovi farmaco di FormOrdine = null");
        }
        FormModificaOrdine.farmaci.remove(entry);
        FormModificaOrdine.ref.getItems().remove(entry);
    }
}
