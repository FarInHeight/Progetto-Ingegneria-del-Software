package com.progetto.farmacia.ordine;

import com.progetto.addetto.segnalazioni.ListaSegnalazioni;
import com.progetto.entity.AddettoAzienda;
import com.progetto.entity.Farmaco;
import javafx.application.Application;
import javafx.collections.ObservableList;
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
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 *  classe che rappresenta il form che permette di effettuare ordini
 */
public class FormOrdine extends Application implements Initializable {

    private static String nomeFarmacia;

    @FXML
    private Text usernameLabel;

    @FXML
    private static Farmaco farmaco;

    @FXML
    private TableView<Farmaco> lista;

    @FXML
    private TableColumn<Farmaco, String> nomeFarmaco;

    @FXML
    private TableColumn<Farmaco, String> principioAttivo;

    @FXML
    private TableColumn<AddettoAzienda, String> strumenti;

    /**
     * costruisce un {@code FormOrdine}
     */
    public FormOrdine(){
        super();
    }

    /**
     * costruisce un {@code FormOrdine} dato in input il nome della farmacia
     * @param nomeFarmacia nome della farmacia
     */
    public FormOrdine(String nomeFarmacia){
        FormOrdine.nomeFarmacia = nomeFarmacia;
    }

    @FXML
    private void indietro(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //ottiene stage corrente
        stage.close();
    }

    /**
     * permette di mostrare a schermo il form per effettuare gli ordini
     * @param stage stage della schermata principale
     * @throws IOException se il caricamento del file fxml del form ordine non è andato a buon fine
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("formOrdine.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        double subStageWidth = 600;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //LocalDate data = LocalDate.of(2022,2,2);
        //Farmaco farm = new Farmaco("Farmaco a","Pinrcipio b", 0,data,3);
        //FormOrdine.farmaco = farm;
        this.usernameLabel.setText(FormOrdine.nomeFarmacia);
        this.nomeFarmaco.setCellValueFactory(new PropertyValueFactory<>("nome"));
        this.principioAttivo.setCellValueFactory(new PropertyValueFactory<>("principioAttivo"));
        this.lista.getItems().add(FormOrdine.farmaco);
    }
}
