package com.progetto.addetto;

import com.progetto.entity.AddettoAzienda;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;


public class SchermataPrincipaleAddettoAzienda extends Application /*implements Initializable*/ {

    private AddettoAzienda addetto;

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
        this.addetto = addetto;
    }

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

    public void creaOrdine() {

    }

    public void visualizzaSegnalazioni() {

    }

    public void logout() {

    }
}
