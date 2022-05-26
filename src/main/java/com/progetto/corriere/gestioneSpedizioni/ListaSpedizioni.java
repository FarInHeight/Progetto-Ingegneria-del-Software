package com.progetto.corriere.gestioneSpedizioni;

import com.progetto.entity.Ordine;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ListaSpedizioni extends Application {

    ArrayList<Ordine> spedizioni;

    public ListaSpedizioni(ArrayList<Ordine> spedizioni) {
        setSpedizioni(spedizioni);
    }

    public void setSpedizioni(ArrayList<Ordine> spedizioni) {
        if (spedizioni == null) {
            throw new NullPointerException("spedizioni = null");
        }
        this.spedizioni = spedizioni;
    }

    @Override
    public void start(Stage stage) throws Exception {

    }
}
