package com.progetto.farmacia.ordine;

import com.progetto.farmacia.SchermataPrincipaleFarmacia;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.FocusModel;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 *  classe che rappresenta il form che permette di effettuare ordini
 */
public class FormOrdine extends Application implements Initializable {

    private Text usernameLabel;
    private static String nomeFarmacia;

    /**
     * costruisce un {@code FormOrdine}
     */
    public FormOrdine(){
        super();
    }

    /**
     * costruisce un {@code FormOrdine} dato in input il nome della farmacia
     * @param nomeFarmacia
     */
    public FormOrdine(String nomeFarmacia){
        FormOrdine.nomeFarmacia = nomeFarmacia;
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
        this.usernameLabel.setText(FormOrdine.nomeFarmacia);
    }
}
