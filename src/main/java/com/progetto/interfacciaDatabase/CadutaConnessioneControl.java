package com.progetto.interfacciaDatabase;

import javafx.stage.Stage;
import java.io.IOException;

/**
 * Classe che rappresenta la control che gestisce la caduta della connessione
 */
public class CadutaConnessioneControl{

    /**
     * Permettere di avviare la control
     */
    void start(){
        Stage stage = new Stage();
        ErroreConnessione err = new ErroreConnessione();
        try {
            err.start(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}