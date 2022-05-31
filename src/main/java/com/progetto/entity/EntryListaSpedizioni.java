package com.progetto.entity;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;

/**
 * Classe che modella una entry della {@code ListaSpedizioni}
 */
public class EntryListaSpedizioni {

    private Ordine ordine;
    private FlowPane strumenti;

    /**
     * Istanzia un oggetto di tipo {@code EntryListaSpedizioni} dato in input un ordine
     * @param ordine ordine in spedizione
     */
    public EntryListaSpedizioni(Ordine ordine) {
        setOrdine(ordine);
        this.strumenti = new FlowPane();
    }

    /**
     * Ritorna l'ordine associato alla spedizione
     * @return oggetto di tipo {@code Ordine}
     */
    public Ordine getOrdine() {
        return ordine;
    }

    /**
     * Permette di settare l'ordine associato alla spedizione
     * @param ordine da impostare
     */
    public void setOrdine(Ordine ordine) {
        if (ordine == null) {
            throw new NullPointerException("ordine = null");
        }
        this.ordine = ordine;
    }

    /**
     * Ritorna gli strumenti relativi alla spedizione
     * @return oggetto di tipo {@code FlowPane} contenete gli strumenti relativi alla entry
     */
    public FlowPane getStrumenti() {
        return strumenti;
    }

    /**
     * Permette di settare gli strumenti della entry
     * @param strumenti strumenti
     */
    public void setStrumenti(Button strumenti) {
        this.strumenti.getChildren().add(strumenti);
        this.strumenti.setAlignment(Pos.CENTER);
    }

    /**
     * Ritorna l'id dell'ordine
     * @return {@code int} contenente l'id dell'ordine
     */
    public int getIdOrdine() {
        return this.ordine.getIdOrdine();
    }

    /**
     * Ritorna il nome della farmacia
     * @return oggetto di tipo {@code String} contenente il nome della farmacia
     */
    public String getNomeFarmacia() {
        return this.ordine.getNomeFarmacia();
    }

    /**
     * Ritorna l'indirizzo di consegna
     * @return oggetto di tipo {@code String} contenente l'indirizzo di consegna
     */
    public String getIndirizzoConsegna() {
        return this.ordine.getIndirizzoConsegna();
    }

}
