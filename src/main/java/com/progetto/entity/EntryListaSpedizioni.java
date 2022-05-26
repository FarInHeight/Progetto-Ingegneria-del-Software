package com.progetto.entity;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;

public class EntryListaSpedizioni {

    private Ordine ordine;
    private final FlowPane strumenti;

    public Ordine getOrdine() {
        return ordine;
    }

    public void setOrdine(Ordine ordine) {
        if (ordine == null) {
            throw new NullPointerException("ordine = null");
        }
        this.ordine = ordine;
    }

    public FlowPane getStrumenti() {
        return strumenti;
    }

    public EntryListaSpedizioni(Ordine ordine) {
        setOrdine(ordine);
        Button consegna = new Button("consegna");
        //espandi.setBackground(Background.fill(Color.rgb(38, 189, 27)));
        //espandi.setStyle("-fx-text-fill: white");
        strumenti = new FlowPane();
        strumenti.getChildren().add(consegna);
        strumenti.setAlignment(Pos.CENTER);
    }

    public int getIdOrdine() {
        return this.ordine.getIdOrdine();
    }

    public String getNomeFarmacia() {
        return this.ordine.getNomeFarmacia();
    }

    public String getIndirizzoConsegna() {
        return this.ordine.getIndirizzoConsegna();
    }

}
