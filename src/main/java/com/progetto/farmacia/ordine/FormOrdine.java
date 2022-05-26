package com.progetto.farmacia.ordine;

import com.progetto.entity.EntryFormOrdine;
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
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

/**
 *  classe che rappresenta il form che permette di effettuare ordini
 */
public class FormOrdine extends Application implements Initializable {

    private static String nomeFarmacia;

    private static EntryFormOrdine entryFormOrdine;

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
     * costruisce un {@code FormOrdine} dato in input il nome della farmacia
     * @param usernameLabel nome della farmacia
     */
    public FormOrdine(Text usernameLabel){
        FormOrdine.nomeFarmacia = usernameLabel.getText();
    }

    @FXML
    private void indietro(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //ottiene stage corrente
        stage.close();
    }

    @FXML
    private void invia(ActionEvent event){
        TableView<Cell> tabella = (TableView<Cell>) (((Node) event.getSource()).getParent().getParent().getChildrenUnmodifiable()).get(0);
        LinkedList<Farmaco> farmaci = new LinkedList<Farmaco>();

        for(int i = 0;i<1; i++) {
            if(tabella.getColumns().get(0).getCellData(i) == null){
                break;
            }
            String NomeFarmaco = (String) tabella.getColumns().get(0).getCellData(i).toString();
            String princpioAttivo = (String) tabella.getColumns().get(1).getCellData(i).toString();
            farmaci.add(new Farmaco(NomeFarmaco, princpioAttivo));
        }
        //richiama verificaCorrettazzaFarmaci passando LinkedList<Farmaco>
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

        double subStageWidth = 800;
        double subStageHeight = 400;

        Stage subStage = new Stage();

        //centra la schermata
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        subStage.setX((screenBounds.getWidth() - subStageWidth) / 2);
        subStage.setY((screenBounds.getHeight() - subStageHeight) / 2);

        subStage.setTitle("Form ordine");
        subStage.setScene(scene);
        subStage.setHeight(subStageHeight);
        subStage.setWidth(subStageWidth);
        subStage.setMinWidth(subStageWidth);
        subStage.setMinHeight(subStageHeight);
        subStage.initOwner(stage); //imposto come proprietario dello stage dell'errore lo stage della schermata di login passato in input
        subStage.initModality(Modality.WINDOW_MODAL);  //blocco il focus sulla schermata delle'errore
        subStage.show();
    }

    /**
     * Metodo usato per inzializzare il {@code FormOrdine}
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.usernameLabel.setText(FormOrdine.nomeFarmacia);
        this.principioAttivo.setCellValueFactory(new PropertyValueFactory<>("principioAttivo"));
        this.nomeFarmaco.setCellValueFactory(new PropertyValueFactory<>("nomeFarmaco"));
        this.strumenti.setCellValueFactory(new PropertyValueFactory<>("strumenti"));

        entryFormOrdine = new EntryFormOrdine("farmaco A", "principiob");
        EntryFormOrdine e = new EntryFormOrdine("famraco B", "principioc");
        this.lista.getItems().add(entryFormOrdine);
        this.lista.getItems().add(e);

    }
}
