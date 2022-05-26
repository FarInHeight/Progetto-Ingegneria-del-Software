package com.progetto.farmacia.ordine;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Control che gestisce la creazione di un ordine
 */
public class CreaOrdineControl{

    private Text usernameLabel;
    /**
     * Costruttore che permette di creare una {@code CreaOrdineControl}
     * @param event evento di pressione del pulsante crea ordine
     * @throws IOException se il caricamento del file fxml della schermata non è andato a buon fine
     */

    public CreaOrdineControl(ActionEvent event, Text usernameLabel) throws IOException{
        this.usernameLabel = usernameLabel;
        start(event);
    }

    private void start(ActionEvent event) throws IOException{
        Stage stage = new Stage();
        FormOrdine formOrdine = new FormOrdine(this.usernameLabel);
        formOrdine.start(stage);
    }
}