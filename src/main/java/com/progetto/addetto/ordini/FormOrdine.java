package com.progetto.addetto.ordini;

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
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Classe che modella la boundary {@code FormOrdine}
 */
public class FormOrdine extends Application implements Initializable {

    private static Farmacia farmacia;

    private static CreaOrdineControl control;

    private static TableView<EntryFormOrdine> ref;  // riferimento per poter apportare modifiche dall'esterno
    private static Stage stage;
    private static ArrayList<EntryFormOrdine> farmaci;
    @FXML
    private DatePicker data;
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
     * Istanzia un oggetto di tipo {@code FormOrdine}
     */
    public FormOrdine(){
        super();
    }

    /**
     * Istanzia un oggetto di tipo {@code FormOrdine} dati in input il nome della farmacia e la control che gestisce la creazione
     * di un ordine
     * @param farmacia entity farmacia
     * @param control control che gestisce la creazione di un ordine
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
     * Ritorna il riferimneto alla schermata della schermata
     * @return oggetto di tipo {@code Stage} riferito alla schermata
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
            Spinner<Integer> spinner = (Spinner<Integer>) this.strumenti.getCellData(i).getChildren().get(0);
            int quantita = spinner.getValue();
            farmaci.add(new Farmaco(nomeFarmaco, quantita, princpioAttivo));
        }
        if(this.data != null && Period.between(this.data.getValue(), LocalDate.now()).getDays() < 0){
            VerificaCorrettezzaOrdineControl verCorrOrdCtrl = new VerificaCorrettezzaOrdineControl(farmaci, FormOrdine.farmacia,this.getStage(), this.data.getValue(), this);
            verCorrOrdCtrl.start();
        }

    }

    /**
     * Permette di mostrare a schermo il form per effettuare gli ordini
     * @param stage stage della schermata principale
     * @throws IOException se il caricamento del file {@code fxml} del form ordine non è andato a buon fine
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("formOrdine.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        double stageWidth = 800;
        double stageHeight = 400;

        FormOrdine.stage = new Stage();

        //centra la schermata
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        FormOrdine.stage.setX((screenBounds.getWidth() - stageWidth) / 2);
        FormOrdine.stage.setY((screenBounds.getHeight() - stageHeight) / 2);

        FormOrdine.stage.setTitle("Form Ordine");
        FormOrdine.stage.setScene(scene);
        FormOrdine.stage.setHeight(stageHeight);
        FormOrdine.stage.setWidth(stageWidth);
        FormOrdine.stage.setMinWidth(stageWidth);
        FormOrdine.stage.setMinHeight(stageHeight);
        FormOrdine.stage.initOwner(stage); //imposto come proprietario dello stage dell'errore lo stage della schermata di login passato in input
        FormOrdine.stage.show();
        FormOrdine.stage.setOnCloseRequest(event -> { Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Per uscire dal programma effettua il logout.");
            alert.setTitle("Chiusura Programma");
            alert.setHeaderText("AVVISO");
            event.consume();
            alert.showAndWait();
        });
    }

    /**
     * Permette di per inzializzare il {@code FormOrdine}
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.usernameLabel.setText(FormOrdine.farmacia.getNome());
        this.principioAttivo.setCellValueFactory(new PropertyValueFactory<>("principioAttivo"));
        this.nomeFarmaco.setCellValueFactory(new PropertyValueFactory<>("nomeFarmaco"));
        this.strumenti.setCellValueFactory(new PropertyValueFactory<>("strumenti"));
        this.data.setValue(LocalDate.now().plusDays(3));
        FormOrdine.ref = this.lista;
    }

    /**
     * Permette di aggiungere il farmaco al form ordine dato in input la entry del form
     * @param entry entry del form ordine da aggiungere
     */
    public void aggiungiFarmaco(EntryFormOrdine entry) {
        if(entry == null) {
            throw new NullPointerException("Entry in aggiungi farmaco = null");
        }
        if(!FormOrdine.farmaci.contains(entry)) {
            FormOrdine.farmaci.add(entry);
            FormOrdine.ref.getItems().add(entry);
        }
    }
    /**
     * Permette di rimuovere il farmaco al form ordine dato in input la entry del form
     * @param entry entry del form ordine da rimuovere
     */

    public void rimuoviFarmaco(EntryFormOrdine entry) {
        if(entry == null) {
            throw new NullPointerException("Entry in rimuovi farmaco di FormOrdine = null");
        }
        FormOrdine.farmaci.remove(entry);
        FormOrdine.ref.getItems().remove(entry);
    }


    /**
     * Motodo utilizzato per avvisare la {@code CreaOrdineControl} che l'utente ha cliccato su {@code indietro}.
     * Il metodo è stato creato senza modificatore di visibilità affinché possa essere invocato soltanto da classi
     * che si trovano nello stesso package.
     */
    void indietro() {
        FormOrdine.control.clickSuIndietro(stage);
    }
}

