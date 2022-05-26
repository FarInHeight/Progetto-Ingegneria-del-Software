package com.progetto.entity;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;

public class EntryListaSpedizioni {

    private Ordine ordine;
    private final FlowPane strumenti;

    /**
     * getter per l'Ordine
     * @return un {@code Ordine}
     */
    public Ordine getOrdine() {
        return ordine;
    }

    public void setOrdine(Ordine ordine) {
        if (ordine == null) {
            throw new NullPointerException("ordine = null");
        }
        this.ordine = ordine;
    }

    /**
     * getter per gli strumenti relativi alla spedizione
     * @return ritorna un {@code FlowPane} contenete gli strumenti relativi alla spedizione
     */
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

    /**
     * getter per l'id dell'ordine
     * @return ritorna un {@code int} contenente l'id dell'ordine
     */
    public int getIdOrdine() {
        return this.ordine.getIdOrdine();
    }

    /**
     * getter per il nome della farmacia
     * @return ritorna una {@code String} contenente il nome della farmacia
     */
    public String getNomeFarmacia() {
        return this.ordine.getNomeFarmacia();
    }

    /**
     * getter per l'indirizzo di consegna
     * @return ritorna una {@code String} contenete l'indirizzo di consegna
     */
    public String getIndirizzoConsegna() {
        return this.ordine.getIndirizzoConsegna();
    }

}
