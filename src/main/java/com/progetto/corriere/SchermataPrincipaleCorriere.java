package com.progetto.corriere;


import com.progetto.entity.Corriere;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class SchermataPrincipaleCorriere extends Application {

    @FXML
    Text usernameLabel;

    private Corriere corriere;

    /**
     * costruisce una {@code SchermataPricipaleCorriere} data in input un oggetto di tipo {@code Corriere}
     * @param corriere corriere associata alla schermata
     */
    public SchermataPrincipaleCorriere(Corriere corriere){
        super();
        this.setCorriere(corriere);
    }

    /**
     * Setter per il parametro corriere
     * @param corriere corriere associato alla schermata
     */
    private void setCorriere(Corriere corriere){
        if(corriere == null){
            throw new NullPointerException("corriere = null");
        }
        this.corriere = corriere;
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("schermataPrincipaleCorriere.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        double stageWidth = 600;
        double stageHeight = 400;

        //centra la schermata
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - stageWidth) / 2);
        stage.setY((screenBounds.getHeight() - stageHeight) / 2);

        usernameLabel.setText(corriere.getNominativo());

        //mostra la schermata di login
        stage.setTitle("Homepage");
        stage.setScene(scene);
        stage.setMinWidth(stageWidth);
        stage.setMinHeight(stageHeight);
        stage.show();
    }

    public void visualizzaListaSpedizioni() {
        //CreaListaSpedizioniControl creaListaSpedizioniControl = new CreaListaSpedizioniControl();
        //creaListaSpedizioniControl.creaLista();
    }
}
