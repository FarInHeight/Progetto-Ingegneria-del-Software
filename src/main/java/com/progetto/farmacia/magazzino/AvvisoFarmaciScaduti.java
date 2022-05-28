package com.progetto.farmacia.magazzino;

import com.progetto.entity.Farmacia;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Classe che implementa la boundary {@code AvvisoFarmaciScaduti}
 */
public class AvvisoFarmaciScaduti extends Application implements Initializable {

    private static String farmaciScaduti;
    private static String farmaciQuasiScaduti;
    private static Farmacia farmacia;

    @FXML
    private TextArea farmaciScadutiText;
    @FXML
    private TextArea farmaciQuasiScadutiText;
    @FXML
    private Text usernameLabel;
    /**
     * Costruisce una {@code AvvisoFarmaciScaduti}
     */
    public AvvisoFarmaciScaduti() {super();}
    /**
     * Costruisce una {@code AvvisoFarmaciScaduti} in base ai farmaci della farmacia che sono scaduti e che stanno per scadere
     * @param farmaciScaduti farmaci che sono scaduti
     * @param farmaciQuasiScaduti farmaci che stanno per scadere (entro due settimane)
     */
    public AvvisoFarmaciScaduti(String farmaciScaduti, String farmaciQuasiScaduti, Farmacia farmacia) {
        this.setFarmaciScaduti(farmaciScaduti);
        this.setFarmaciQuasiScaduti(farmaciQuasiScaduti);
        this.setFarmacia(farmacia);
    }

    private void setFarmaciScaduti(String farmaciScaduti) {
        if(farmaciScaduti == null) {
            throw new NullPointerException("Farmaci scaduti = null");
        }
        AvvisoFarmaciScaduti.farmaciScaduti = farmaciScaduti;
    }
    private void setFarmaciQuasiScaduti(String farmaciQuasiScaduti) {
        if(farmaciQuasiScaduti == null) {
            throw new NullPointerException("Farmaci scaduti = null");
        }
        AvvisoFarmaciScaduti.farmaciQuasiScaduti = farmaciQuasiScaduti;
    }
    private void setFarmacia(Farmacia farmacia) {
        if(farmacia == null) {
            throw new NullPointerException("Farmacia = null");
        }
        AvvisoFarmaciScaduti.farmacia = farmacia;
    }
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("avvisoFarmaciScaduti.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);

        double stageWidth = 600;
        double stageHeight = 600;

        Stage subStage = new Stage();
        //centra la schermata
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        subStage.setX((screenBounds.getWidth() - stageWidth) / 2);
        subStage.setY((screenBounds.getHeight() - stageHeight) / 2);

        subStage.setTitle("Avviso Farmaci Scaduti");
        subStage.setScene(scene);
        subStage.setMinWidth(stageWidth);
        subStage.setMinHeight(stageHeight);
        subStage.initOwner(stage); //imposto come proprietario del Riepilogo la Lista Spedizioni
        subStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.farmaciScadutiText.setText(AvvisoFarmaciScaduti.farmaciScaduti);
        this.farmaciQuasiScadutiText.setText(AvvisoFarmaciScaduti.farmaciQuasiScaduti);
        this.usernameLabel.setText(AvvisoFarmaciScaduti.farmacia.getNome());
    }

    @FXML
    public void chiudi(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();  // chiudo l'avviso
    }
}
