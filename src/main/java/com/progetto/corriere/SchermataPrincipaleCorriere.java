package com.progetto.corriere;


import com.progetto.addetto.autenticazione.LogoutControl;
import com.progetto.entity.Corriere;
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

public class SchermataPrincipaleCorriere extends Application implements Initializable{

        @FXML
        private static Corriere corriere;

        @FXML
        private Text usernameLabel;

        /**
         * Costruisce una {@code SchermataPricipaleCorriere}
         */
        public SchermataPrincipaleCorriere(){
            super();
        }
        /**
         * Costruisce una {@code SchermataPricipaleCorriere} data in input un oggetto di tipo {@code Corriere}
         * @param corriere corriere associata alla schermata
         */
        public SchermataPrincipaleCorriere(Corriere corriere){
            super();
            this.setCorriere(corriere);
        }

        private void setCorriere(Corriere corriere){
            if(corriere == null){
                throw new NullPointerException("corriere = null");
            }
            com.progetto.corriere.SchermataPrincipaleCorriere.corriere = corriere;
        }

        /**
         * Metodo utilizzato per visualizzare la {@code SchermataPrincipaleCorriere} a schermo
         * @param stage
         * @throws IOException
         */
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

            //mostra la schermata di login
            stage.setTitle("Homepage");
            stage.setScene(scene);
            stage.setMinWidth(stageWidth + 20);
            stage.setMinHeight(stageHeight);
            stage.show();
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
            this.usernameLabel.setText(SchermataPrincipaleCorriere.corriere.getNominativo());
        }
}