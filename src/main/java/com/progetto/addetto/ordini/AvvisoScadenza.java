package com.progetto.addetto.ordini;

import com.progetto.entity.Farmacia;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Classe che implementa la boundary {@code AvvisoScadenza}
 */
public class AvvisoScadenza extends Application implements Initializable {

    private static Farmacia farmacia;
    private static String farmaciInScadenza;
    @FXML
    private Text usernameLabel;

    @FXML
    private TextArea farmaciInScadenzaText;

    private static VerificaCorrettezzaOrdineControl control;

    /**
     * costruire un oggetto {@code AvvisoScadenza}
     */
    public AvvisoScadenza(){
        super();
    }

    /**
     * costruisce un oggetto {@code AvvisoScadenza} dati in input i farmaci in scadenza, la farmacia che ha effettuato l'ordine
     * e la control che ha creato l'avviso
     * @param farmaciInScadenza farmaci in scadenza
     * @param farmacia farmaica che ha effettuato l'ordine
     * @param control control che ha creato l'avviso
     */
    public AvvisoScadenza(String farmaciInScadenza, Farmacia farmacia, VerificaCorrettezzaOrdineControl control){
        this.setFarmaciInScadenza(farmaciInScadenza);
        this.setFarmacia(farmacia);
        this.setControl(control);
    }

    private void setControl(VerificaCorrettezzaOrdineControl control) {
        if(control == null) {
            throw new NullPointerException("Control = null");
        }
        AvvisoScadenza.control = control;
    }

    @FXML
    private void confermaOrdine(ActionEvent event){
        AvvisoScadenza.control.clickSuConfermaOrdine(event);
    }

    @FXML
    private void annullaOrdine(ActionEvent event){
        AvvisoScadenza.control.clickSuAnnullaOrdine(event);
    }

    private void setFarmaciInScadenza(String farmaciInScadenza){
        if(farmaciInScadenza == null){
            throw new NullPointerException("farmaci in scadenza = null");
        }
        AvvisoScadenza.farmaciInScadenza = farmaciInScadenza;

    }

    private void setFarmacia(Farmacia farmacia){
        if(farmacia == null){
            throw new NullPointerException("farmacia = null");
        }
        AvvisoScadenza.farmacia = farmacia;
    }

    /**
     * Metodo utilizzato per visualizzare un oggetto di tipo {@code AvvisoScadenza} a schermo
     * @param stage stage del form ordine
     * @throws IOException se il caricamento del file {@code fxml} non è andato a buon fine
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("avvisoScadenza.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 650);

        double stageWidth = 630;
        double stageHeight = 630;

        //centra la schermata
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - stageWidth) / 2 );
        stage.setY((screenBounds.getHeight() - stageHeight) / 2);

        stage.setTitle("Avviso Scadenza");
        stage.setScene(scene);
        stage.setHeight(stageHeight);
        stage.setWidth(stageHeight);
        stage.setMinWidth(stageWidth);
        stage.setMinHeight(stageHeight);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        stage.setOnCloseRequest(event -> { Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Per uscire dal programma effettua il logout.");
            alert.showAndWait();
            alert.setTitle("Chiusura Programma");
            alert.setHeaderText("AVVISO");
            event.consume();
        });
    }

    /**
     * Metodo utilizzato per personalizzare un oggetto di tipo {@code AvvisoScadenza} in base ai farmaci in scadenza
     *  e alla farmacia che ha effettuato l'ordine
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.farmaciInScadenzaText.setText(AvvisoScadenza.farmaciInScadenza);
        this.usernameLabel.setText(AvvisoScadenza.farmacia.getNome());
    }
}

