package com.progetto.addetto;

import com.progetto.addetto.autenticazione.LogoutControl;
import com.progetto.addetto.segnalazioni.GestioneSegnalazioniControl;
import com.progetto.entity.AddettoAzienda;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
     * Costruisce una {@code SchermataPricipaleAddettoAzienda} dato in input un oggetto di tipo {@code AddettoAzienda}
     * @param addetto addetto che ha effettuato il login
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
     * Permette di mostrare a schermo la {@code SchermataPrincipaleAddettoAzienda}
     * @param stage stage della schermata
     * @throws IOException se il caricamento del file {@code fxml} non è andato a buon fine
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
        stage.setMinWidth(stageWidth + 20);
        stage.setMinHeight(stageHeight);
        stage.show();
        stage.setOnCloseRequest(event -> { Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Per uscire dal programma effettua il logout.");
            alert.setTitle("Chiusura Programma");
            alert.setHeaderText("AVVISO");
            event.consume();
            alert.showAndWait();
        });    }

    @FXML
    private void visualizzaSegnalazioni(ActionEvent event) {
        GestioneSegnalazioniControl visSegCtrl = new GestioneSegnalazioniControl(SchermataPrincipaleAddettoAzienda.addetto, event);
        visSegCtrl.start();
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
    @SuppressWarnings("JavadocDeclaration")
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.usernameLabel.setText(SchermataPrincipaleAddettoAzienda.addetto.getNominativo());
    }
}
