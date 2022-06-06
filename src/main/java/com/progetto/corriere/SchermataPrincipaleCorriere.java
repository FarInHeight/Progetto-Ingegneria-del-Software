package com.progetto.corriere;

import com.progetto.addetto.autenticazione.LogoutControl;
import com.progetto.corriere.gestioneSpedizioni.CreaListaSpedizioniControl;
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
 * Classe che modella la boundary {@code SchermataPrincipaleCorriereBoundary}
 */
public class SchermataPrincipaleCorriere extends Application implements Initializable{

    @FXML
    private static Corriere corriere;

    @FXML
    private Text usernameLabel;

    /**
     * Istanzia un oggetto di tipo {@code SchermataPrincipaleCorriere}
     */

    public SchermataPrincipaleCorriere(){}

    /**
     * Istanzia un oggetto di tipo {@code SchermataPrincipaleCorriere} dato in input il corriere che ha effettuato il ogin
     * @param corriere oggetto di tipo {@code Corriere} relativo al orriere
     */
    public SchermataPrincipaleCorriere(Corriere corriere){
        super();
        this.setCorriere(corriere);
    }

    private void setCorriere(Corriere corriere){
        if(corriere == null){
            throw new NullPointerException("corriere = null");
        }
        SchermataPrincipaleCorriere.corriere = corriere;
    }

    /**
     * Ritorna il corriere associato alla schermata
     * @return oggetto di tipo {@code corriere} relativo al corriere associato alla schermata
     */
    public static Corriere getCorriere() {
        return corriere;
    }

    /**
     * Permette di mostrare a schermo la {@code SchermataPrincipaleCorriere}
     * @param stage stage corrente
     * @throws IOException lanciata se il caricamento del file fxml non è andato a buon fine
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
        stage.setOnCloseRequest(event -> { Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Per uscire dal programma effettua il logout.");
            alert.setTitle("Chiusura Programma");
            alert.setHeaderText("AVVISO");
            event.consume();
            alert.showAndWait();
        });
    }

    /*
     Metodo per effettuare il logout e tornare alla SchermataAutenticazioneForm
     */
    @FXML
    private void logout(ActionEvent event) throws IOException {
        LogoutControl logoutControl = new LogoutControl(event);
    }

    /**
     * Permette di personalizzare la {@code SchermataPrincipaleAddettoAzienda} dell'Addetto
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.usernameLabel.setText(SchermataPrincipaleCorriere.corriere.getNominativo());
    }

    /*
     * Metodo usato per iniziare la creazione della ListaSpedizioni
    */
    @FXML
    private void visualizzaListaSpedizioni(ActionEvent event) throws IOException {
        CreaListaSpedizioniControl creaListaSpedizioniControl = new CreaListaSpedizioniControl(event);
        creaListaSpedizioniControl.start();
    }

}
