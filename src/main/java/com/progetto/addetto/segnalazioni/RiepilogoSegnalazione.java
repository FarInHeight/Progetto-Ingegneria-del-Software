package com.progetto.addetto.segnalazioni;

import com.progetto.addetto.ordini.CreaOrdineControl;
import com.progetto.entity.EntryListaSegnalazioni;
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
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Classe che implementa la boundary {@code RiepilogoSegnalazione}
 */
public class RiepilogoSegnalazione extends Application implements Initializable {

    private static EntryListaSegnalazioni entry;
    private static Stage stage;  // stage di ListaSegnalazioni
    @FXML
    private Text idText;

    @FXML
    private Text dataText;

    @FXML
    private TextArea riepilogoText;

    @FXML
    private Text farmaciaText;

    @FXML
    private TextArea commentoText;

    /**
     * Costruisce un {@code RiepilogoSegnalazione}
     */
    public RiepilogoSegnalazione(){
        super();
    }

    /**
     * Costruisce un {@code RiepilogoSegnalazione} secondo il contenuto di una {@code EntryListaSegnalazioni}
     * @param entry segnalazione che caratterizza il riepilogo
     */
    public RiepilogoSegnalazione(EntryListaSegnalazioni entry){
        super();
        this.setSegnalazione(entry);
    }

    private void setSegnalazione(EntryListaSegnalazioni entry) {
        if(entry == null) {
            throw new NullPointerException("Entry Lista Segnalazione in Riepilogo = null");
        }
        RiepilogoSegnalazione.entry = entry;
    }

    /**
     * Metodo utilizzato per visualizzare il {@code RiepilogoSegnalazione} a schermo
     * @param stage stage della schermata di riepilogo
     * @throws IOException se il caricamento del file {@code fxml} non è andato a buon fine
     */
    @Override
    public void start(Stage stage) throws IOException {
        RiepilogoSegnalazione.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("riepilogoSegnalazione.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 700);

        double stageWidth = 600;
        double stageHeight = 700;

        Stage subStage = new Stage();
        //centra la schermata
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        subStage.setX((screenBounds.getWidth() - stageWidth) / 2);
        subStage.setY((screenBounds.getHeight() - stageHeight) / 2);

        subStage.setTitle("Riepilogo Segnalazione");
        subStage.setScene(scene);
        subStage.setMinWidth(stageWidth);
        subStage.setMinHeight(stageHeight);
        subStage.initOwner(stage); //imposto come proprietario del Riepilogo la Lista Spedizioni
        subStage.initModality(Modality.WINDOW_MODAL);  //blocco il focus sulla schermata di Riepilogo
        subStage.show();
    }

    /**
     * Metodo utilizzato per personalizzare il contenuto di un {@code RiepilogoSegnalazione} secondo la segnalazione associata
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.idText.setText("ID: " + RiepilogoSegnalazione.entry.getIdSegnalazione());
        this.dataText.setText("DATA SEGNALAZIONE: " + RiepilogoSegnalazione.entry.getData());
        this.riepilogoText.setText(RiepilogoSegnalazione.entry.getRiepilogoOrdine());
        this.farmaciaText.setText(RiepilogoSegnalazione.entry.getNomeFarmacia() + "\n" + RiepilogoSegnalazione.entry.getRecapitoTelefonicoFarmacia());
        this.commentoText.setText(RiepilogoSegnalazione.entry.getSegnalazione().getCommento());
    }

    @FXML
    private void creaOrdine(ActionEvent event) {
        Farmacia farmacia = new Farmacia(RiepilogoSegnalazione.entry.getIdFarmacia(), RiepilogoSegnalazione.entry.getNomeFarmacia(), "", RiepilogoSegnalazione.entry.getRecapitoTelefonicoFarmacia());
        try {
            CreaOrdineControl control = new CreaOrdineControl(farmacia, RiepilogoSegnalazione.stage, event);
            control.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void chiudi(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();  // chiudo la finestra di riepilogo
    }
}
