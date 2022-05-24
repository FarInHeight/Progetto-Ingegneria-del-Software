package com.progetto.farmacia;

import com.progetto.entity.Farmacia;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class SchermataPrincipaleFarmacia extends Application {

    private Farmacia farmacia;

    /**
     * costruisce una {@code SchermataPricipaleFarmacia}
     */
    public SchermataPrincipaleFarmacia(){
        super();
    }
    /**
     * costruisce una {@code SchermataPricipaleFarmacia} data in input un oggetto di tipo {@code Farmacia}
     * @param farmacia farmacia associata alla schermata
     */
    public SchermataPrincipaleFarmacia(Farmacia farmacia){
        super();
        this.setFarmacia(farmacia);
    }

    private void setFarmacia(Farmacia farmacia){
        if(farmacia == null){
            throw new NullPointerException("farmacia = null");
        }
        this.farmacia = farmacia;
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("schermataPrincipaleFarmacia.fxml"));
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
}
