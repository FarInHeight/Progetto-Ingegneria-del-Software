package com.progetto.addetto;

import com.progetto.addetto.autenticazione.LogoutControl;
import com.progetto.entity.AddettoAzienda;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Classe che implementa la boundary {@code SchermataPrincipaleAddettoAzienda}
 */
public class SchermataPrincipaleAddettoAzienda extends Application implements Initializable {
    @FXML
    private static AddettoAzienda addetto;

    @FXML
    private Text usernameLabel;

    /**
     * Costruisce una {@code SchermataPricipaleAddettoAzienda}
     */
    public SchermataPrincipaleAddettoAzienda(){
        super();
    }
    /**
     * Costruisce una {@code SchermataPricipaleAddettoAzienda} data in input un oggetto di tipo {@code AddettoAzienda}
     * @param addetto farmacia associata alla schermata
     */
    public SchermataPrincipaleAddettoAzienda(AddettoAzienda addetto){
        super();
        this.setAddettoAZienda(addetto);
    }

    private void setAddettoAZienda(AddettoAzienda addetto){
        if(addetto == null){
            throw new NullPointerException("Addetto dell'Azienda = null");
        }
        SchermataPrincipaleAddettoAzienda.addetto = addetto;
    }

    /**
     * Metodo utilizzato per visualizzare la {@code SchermataPrincipaleAddettoAzienda} a schermo
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("schermataPrincipaleAddettoAzienda.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        double stageWidth = 600;
        double stageHeight = 400;

        //centra la schermata
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - stageWidth) / 2);
        stage.setY((screenBounds.getHeight() - stageHeight) / 2);

        //mostra la schermata di login
        stage.setTitle("Homepage");
        stage.setScene(scene);
        stage.setMinWidth(stageWidth);
        stage.setMinHeight(stageHeight);
        stage.show();
    }

    @FXML
    private void creaOrdine() {

    }

    @FXML
    private void visualizzaSegnalazioni() {

    }

    @FXML
    private void logout(ActionEvent event) throws IOException {
        LogoutControl logoutControl = new LogoutControl(event);
    }

    /**
     * Metodo utilizzato per personalizzare la {@code SchermataPrincipaleAddettoAzienda} dell'Addetto
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.usernameLabel.setText(SchermataPrincipaleAddettoAzienda.addetto.getNominativo());
    }
}
