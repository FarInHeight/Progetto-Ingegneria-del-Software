package com.progetto.interfacciaDatabase;

import javafx.stage.Stage;
import java.io.IOException;

/**
 * Classe che modella la control {@code CadutaConnessioneControl} che gestisce la caduta della connessione
 */
public class CadutaConnessioneControl{

    /**
     * Permette di avviare la control
     */
    void start(){
        Stage stage = new Stage();
        ErroreConnessione err = new ErroreConnessione();
        err.start(stage);
    }
}