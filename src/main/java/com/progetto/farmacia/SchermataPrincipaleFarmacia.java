package com.progetto.farmacia;

import com.progetto.entity.Farmacia;
import com.progetto.farmacia.autenticazione.LogoutControl;
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

public class SchermataPrincipaleFarmacia extends Application implements Initializable {

    private static Farmacia farmacia;
    @FXML
    private Text usernameLabel;

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
    @FXML
    private void logout (ActionEvent event) throws IOException{
        LogoutControl logoutControl = new LogoutControl(event);
    }

    private void setFarmacia(Farmacia farmacia){
        if(farmacia == null){
            throw new NullPointerException("farmacia = null");
        }
        SchermataPrincipaleFarmacia.farmacia = farmacia;
    }

    /**
     * permette di mostrare la schermata principale della farmacia
     * @param stage stage della schermata
     * @throws IOException se il caricamento del file fxml non è andato a buon fine
     */
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.usernameLabel.setText(SchermataPrincipaleFarmacia.farmacia.getNome());
    }
}
