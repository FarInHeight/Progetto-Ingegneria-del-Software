package com.progetto.farmacia;

import com.progetto.entity.Farmacia;
import com.progetto.farmacia.autenticazione.LogoutControl;
import com.progetto.farmacia.magazzino.VisualizzaMagazzinoControl;
import com.progetto.farmacia.ordini.CreaOrdineControl;
import com.progetto.farmacia.ordini.VisualizzaOrdiniControl;
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
 * Classe che modella la boundary {@code SchermataPrincipaleFarmacia}
 */
public class SchermataPrincipaleFarmacia extends Application implements Initializable {

    private static Farmacia farmacia;
    @FXML
    private Text usernameLabel;

    /**
     * Istanzia un oggetto di tipo {@code SchermataPricipaleFarmacia}
     */
    public SchermataPrincipaleFarmacia(){
        super();
    }
    /**
     * Istanzia un oggetto di tipo {@code SchermataPricipaleFarmacia} dato in input un oggetto di tipo {@code Farmacia}
     * @param farmacia farmacia associata alla schermata
     */
    public SchermataPrincipaleFarmacia(Farmacia farmacia){
        super();
        this.setFarmacia(farmacia);
    }

    /**
     * Ritorna la farmacia associata alla schermata
     * @return oggetto di tipo {@code Farmacia} contenente la farmacia assocaita alla schermata
     */
    public static Farmacia getFarmacia() {
        return farmacia;
    }

    @FXML
    private void logout(ActionEvent event) throws IOException{
        LogoutControl logoutControl = new LogoutControl(event);
    }

    @FXML
    private void creaOrdine(ActionEvent event) throws IOException{
        CreaOrdineControl creaOrdineControl = new CreaOrdineControl(SchermataPrincipaleFarmacia.farmacia,event);
        creaOrdineControl.start();
    }

    @FXML
    private void visualizzaMagazzino(ActionEvent event) {
        VisualizzaMagazzinoControl visualizzaMagazzinoControl = new VisualizzaMagazzinoControl(event);
        visualizzaMagazzinoControl.start();
    }

    @FXML
    private void visualizzaOrdini(ActionEvent event) {
        VisualizzaOrdiniControl control = new VisualizzaOrdiniControl(SchermataPrincipaleFarmacia.farmacia, event);
        control.start();
    }

    private void setFarmacia(Farmacia farmacia){
        if(farmacia == null){
            throw new NullPointerException("farmacia = null");
        }
        SchermataPrincipaleFarmacia.farmacia = farmacia;
    }

    /**
     * Permette di mostrare la schermata principale della farmacia
     * @param stage stage della schermata
     * @throws IOException se il caricamento del file {@code fxml} non è andato a buon fine
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
        stage.setOnCloseRequest(event -> { Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Per uscire dal programma effettua il logout.");
            alert.setTitle("Chiusura Programma");
            alert.setHeaderText("AVVISO");
            event.consume();
            alert.showAndWait();
        });
    }

    /**
     * Permette di personalizzare la schermata principale della farmacia
     * @param url
     * @param resourceBundle
     */
    @SuppressWarnings("JavadocDeclaration")
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.usernameLabel.setText(SchermataPrincipaleFarmacia.farmacia.getNome());
    }
}
