package com.progetto.farmacia.ordini;

import com.progetto.entity.EntryListaOrdini;
import com.progetto.entity.Farmacia;
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
 * rappresenta li lista degli ordini effettuati dalla farmacia
 */
public class ListaOrdini extends Application implements Initializable {
    private static Farmacia farmacia;
    private static TableView<EntryListaOrdini> ref;  // riferimento per poter apportare modifiche dall'esterno
    @FXML
    private Text usernameLabel;
    @FXML
    private TableView<EntryListaOrdini> lista;
    @FXML
    private TableColumn<EntryListaOrdini, Integer> idOrdine;
    @FXML
    private TableColumn<EntryListaOrdini, String> farmaci;
    @FXML
    private TableColumn<EntryListaOrdini, String> stato;
    @FXML
    private TableColumn<EntryListaOrdini, String> tipo;
    @FXML
    private TableColumn<EntryListaOrdini, String> dataConsegna;
    @FXML
    private TableColumn<EntryListaOrdini, FlowPane> strumenti;
    @FXML
    private Button indietroButton;
    @FXML
    private Button fattoButton;
    private static VisualizzaOrdiniControl control;
    private static VerificaRegistrazioniFarmaciControl controlVerifica;
    private static ArrayList<EntryListaOrdini> ordini;
    private Stage stage;
    /**
     * Costruisce una {@code ListaOrdini}
     */
    public ListaOrdini(){
        super();
    }

    /**
     * Costruisce una {@code ListaOrdini} avendo in input la farmacia a cui si riferisce, la lista degli ordini da
     * mostrare a schermo e la control che ha istanziato l'oggetto.
     * @param farmacia farmacia che vuole visualizzare i proprio ordini
     * @param ordini lista di ordini da mostrare a schermo
     * @param control control che ha dato origine alla lista degli ordini
     */
    public ListaOrdini(Farmacia farmacia, ArrayList<EntryListaOrdini> ordini, VerificaRegistrazioniFarmaciControl control) {
        this.setFarmacia(farmacia);
        this.setOrdini(ordini);
        this.setControlVerifica(control);
    }
    /**
     * Costruisce una {@code ListaOrdini} avendo in input la farmacia a cui si riferisce, la lista degli ordini da
     * mostrare a schermo e la control che ha istanziato l'oggetto.
     * @param farmacia farmacia che vuole visualizzare i proprio ordini
     * @param ordini lista di ordini da mostrare a schermo
     * @param control control che ha dato origine alla lista degli ordini
     */
    public ListaOrdini(Farmacia farmacia, ArrayList<EntryListaOrdini> ordini, VisualizzaOrdiniControl control) {
        this.setFarmacia(farmacia);
        this.setOrdini(ordini);
        this.setControl(control);
    }

    public static ArrayList<EntryListaOrdini> getOrdini() {
        return ordini;
    }

    private void setFarmacia(Farmacia farmacia) {
        if(farmacia == null) {
            throw new NullPointerException("Farmacia in Visualizza Ordini = null");
        }
        ListaOrdini.farmacia = farmacia;
    }

    private void setOrdini(ArrayList<EntryListaOrdini> ordini) {
        if(ordini == null) {
            throw new NullPointerException("Lista di ordini = null");
        }
        ListaOrdini.ordini = ordini;
    }

    private void setControl(VisualizzaOrdiniControl control) {
        if(control == null) {
            throw new NullPointerException("Control di ListaOrdini = null");
        }
        ListaOrdini.control = control;
    }

    private void setControlVerifica(VerificaRegistrazioniFarmaciControl control) {
        if(control == null) {
            throw new NullPointerException("Control di ListaOrdini = null");
        }
        ListaOrdini.controlVerifica = control;
    }

    /**
     * Metodo utilizzato per visualizzare la {@code ListaOrdini} a schermo
     * @param stage stage della lista
     * @throws IOException se il caricamento del file {@code fxml} non è andato a buon fine
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("listaOrdini.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 450);

        double stageWidth = 800;
        double stageHeight = 500;

        this.stage = new Stage();

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        this.stage.setX((screenBounds.getWidth() - stageWidth) / 2);
        this.stage.setY((screenBounds.getHeight() - stageHeight) / 2);

        this.stage.setTitle("Lista Ordini");
        this.stage.setScene(scene);
        this.stage.setMinWidth(stageWidth);
        this.stage.setMinHeight(stageHeight);
        this.stage.initOwner(stage);
        this.stage.show();

        if(ListaOrdini.controlVerifica != null) {
            this.stage.setWidth(stageWidth);
            this.stage.setHeight(stageHeight);
            this.stage.setMaxWidth(stageWidth);
            this.stage.setMaxHeight(stageHeight);
        }
    }

    /**
     * Metodo utilizzato per personalizzare la {@code ListaOrdini} della Farmacia
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.usernameLabel.setText(ListaOrdini.farmacia.getNome());
        this.idOrdine.setCellValueFactory(new PropertyValueFactory<>("idOrdine"));
        this.farmaci.setCellValueFactory(new PropertyValueFactory<>("farmaciStringa"));
        this.stato.setCellValueFactory(new PropertyValueFactory<>("stato"));
        this.tipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        this.dataConsegna.setCellValueFactory(new PropertyValueFactory<>("dataConsegna"));
        this.strumenti.setCellValueFactory(new PropertyValueFactory<>("strumenti"));

        for(EntryListaOrdini entry : ListaOrdini.ordini) {
            this.lista.getItems().add(entry);
        }
        ListaOrdini.ref = this.lista;

        if(ListaOrdini.control == null) {
            this.indietroButton.setVisible(false);
            this.indietroButton.setManaged(false);
        } else {
            this.fattoButton.setVisible(false);
            this.fattoButton.setManaged(false);
        }
    }

    public static void update(){
        ListaOrdini.ref.getItems().clear();
        for(EntryListaOrdini entry : ListaOrdini.ordini) {
            ListaOrdini.ref.getItems().add(entry);
        }
    }

    /**
     * ritorna un riferimento alla lista ordini
     * @return un oggetto {@code TableView<EntryListaOrdini>} che rappresenta la lista ordini
     */
    public static TableView<EntryListaOrdini> getRef() {
        return ListaOrdini.ref;
    }

    @FXML
    private void indietro(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();  // prendo lo stage corrente
        ListaOrdini.control.clickSuIndietro(stage);
    }

    @FXML
    private void fatto(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();  // prendo lo stage corrente
        ListaOrdini.controlVerifica.clickSuFatto(stage);
    }

    public void cancellaOrdine (EntryListaOrdini entry) throws IOException{
        CancellaOrdineControl cancOrdCtrl = new CancellaOrdineControl(entry,this.stage);
        cancOrdCtrl.start();
        update();
    }

    public void modificaOrdine (EntryListaOrdini entry) throws IOException{
        ModificaOrdineControl modOrdCtrl = new ModificaOrdineControl(ListaOrdini.farmacia,entry,this.stage);
        modOrdCtrl.start();
        update();
    }

    public void caricaOrdine (EntryListaOrdini entry) throws IOException{
        RegistrazioneFarmaciRicevutiControl registrazioneFarmaciRicevutiControl = new RegistrazioneFarmaciRicevutiControl(entry, this.stage);
        registrazioneFarmaciRicevutiControl.start();
        update();
    }

}
