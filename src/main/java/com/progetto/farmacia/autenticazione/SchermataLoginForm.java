package com.progetto.farmacia.autenticazione;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Rappresenta la schermata di login della farmacia
 */
public class SchermataLoginForm extends Application {

    @FXML
    private TextField idFarmacia;
    @FXML
    private TextField password;
    @FXML
    private void login(){
        AutenticazioneFarmaciaControl autFarmContr = new AutenticazioneFarmaciaControl(this.idFarmacia,this.password);
    }

    /**
     * Permette di mostrare la schermata di login
     * @param stage stage della schermata
     * @throws IOException se il caricamento del file fxml non è andato a buon fine
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("schermataLoginForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        //centra la schermata
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - 600) / 2);
        stage.setY((screenBounds.getHeight() - 400) / 2);

        stage.setTitle("Effettua l'accesso");
        stage.setScene(scene);
        stage.setMinWidth(600);
        stage.setMinHeight(400);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
