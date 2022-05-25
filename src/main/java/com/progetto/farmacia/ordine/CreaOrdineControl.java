package com.progetto.farmacia.ordine;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Control che gestisce la creazione di un ordine
 */
public class CreaOrdineControl{

    public CreaOrdineControl(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //ottiene stage corrente
        FormOrdine formOrdine = new FormOrdine();
        formOrdine.start(stage);
    }
}
