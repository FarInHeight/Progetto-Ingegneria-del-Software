package com.progetto.farmacia.magazzino;

import com.progetto.entity.EntryMagazzinoFarmacia;
import com.progetto.farmacia.SchermataPrincipaleFarmacia;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Classe che gestisce la schermata in cui il farmacista può inserire le quantità di farmaco da rimuovere
 */
public class SchermataRimozioneQuantita extends Application implements Initializable {

    @FXML
    private Text usernameLabel;
    @FXML
    private Text infoFarmaco;
    @FXML
    private Text quantitaAttuale;
    @FXML
    private Pane spinnerPane;

    private static Spinner<Integer> spinner;
    private static EntryMagazzinoFarmacia farmaco;
    private static RimuoviFarmaciControl control;

    /**
     * Costruttore
     */
    public SchermataRimozioneQuantita(){super();}

    /**
     * Costruttore
     * @param farmaco farmaco di cui rimuovere una quantita
     * @param control control che permette di tornare alla schermata magazzino
     */
    public SchermataRimozioneQuantita(EntryMagazzinoFarmacia farmaco, RimuoviFarmaciControl control){
        super();
        setControl(control);
        setFarmaco(farmaco);
    }

    /**
     * Getter per il farmaco
     * @return farmaco
     */
    public static EntryMagazzinoFarmacia getFarmaco() {
        return farmaco;
    }

    /**
     * Setter per il farmaco
     * @param farmaco farmaco
     */
    public static void setFarmaco(EntryMagazzinoFarmacia farmaco) {
        if (farmaco == null) {
            throw new NullPointerException("farmaco = null");
        }
        SchermataRimozioneQuantita.farmaco = farmaco;
    }

    /**
     * Getter per la control
     * @return control
     */
    public static RimuoviFarmaciControl getControl() {
        return control;
    }

    /**
     * Setter per la control
     * @param control control
     */
    public static void setControl(RimuoviFarmaciControl control) {
        if (control == null) {
            throw new NullPointerException("control = null");
        }
        SchermataRimozioneQuantita.control = control;
    }

    /**
     * Permette di mostrare la schermata da cui rimuovere le quantita di un farmaco
     * @param stage stage della schermata
     * @throws IOException lanciata se il caricamento del file fxml non è andato a buon fine
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("schermataRimozioneQuantita.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 300);

        double stageWidth = 500;
        double stageHeight = 340;

        Stage subStage = new Stage();
        //centra la schermata
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        subStage.setX((screenBounds.getWidth() - stageWidth) / 2);
        subStage.setY((screenBounds.getHeight() - stageHeight) / 2);

        //mostra la schermata di rimozione quantita
        subStage.setTitle("Rimuovi Farmaco");
        subStage.setScene(scene);
        subStage.setMinWidth(stageWidth + 50);
        subStage.setMinHeight(stageHeight);
        subStage.show();
        subStage.setOnCloseRequest(event -> { Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Per uscire dal programma effettua il logout.");
            alert.setTitle("Chiusura Programma");
            alert.setHeaderText("AVVISO");
            event.consume();
            alert.showAndWait();
        });
    }

    /**
     * Metodo che inizializza la schermata inserendo i dati relativi al farmaco in questione
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Inizializzo le scritte
        this.usernameLabel.setText(SchermataPrincipaleFarmacia.getFarmacia().getNome());
        this.infoFarmaco.setText(getFarmaco().getNome() + "\t" + getFarmaco().getPrincipioAttivo() + "\t" + getFarmaco().getDataScadenza());
        this.quantitaAttuale.setText(Integer.toString(getFarmaco().getQuantita()));
        //Creo lo spinner e lo aggiungo
        SchermataRimozioneQuantita.spinner = new Spinner<>();
        SchermataRimozioneQuantita.spinner.setEditable(true);
        SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, getFarmaco().getQuantita());
        SchermataRimozioneQuantita.spinner.setValueFactory(spinnerValueFactory);
        this.spinnerPane.getChildren().add(spinner);

    }

    /**
     * Metodo che interagisce con la control per la rimozione della quantita specificata
     * @param event evento associato alla pressione del {@code button} conferma rimozione
     */
    @FXML
    private void confermaRimozione(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        int quantitaDaRimuovere = spinner.getValue();
        getControl().clickSuConferma(stage,quantitaDaRimuovere);
    }

    /**
     * Permette di tornare indietro e visualizzare la {@code SchermataMagazzino}
     * @param event evento associato alla pressione del {@code button} indietro
     */
    @FXML
    private void indietro(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        getControl().clickSuIndietro(stage);
    }
}
