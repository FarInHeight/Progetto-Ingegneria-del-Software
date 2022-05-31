package com.progetto.entity;

import javafx.scene.layout.FlowPane;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Classe che modella una entry dalla {@code ListaOrdini}
 */
public class EntryListaOrdini {
    private Ordine ordine;

    private FlowPane strumenti;

    /**
     * Istanzia un oggetto di tipo {@code EntryListaOrdini} dato in input l'ordine al quale si riferisce
     * @param ordine ordine associato alla entry
     */
    public EntryListaOrdini(Ordine ordine) {
        this.setOrdine(ordine);
    }

    /**
     * Permette di settare l'ordine associato alla entry
     * @param ordine ordine
     */
    public void setOrdine(Ordine ordine) {
        if(ordine == null) {
            throw new NullPointerException("Ordine in EntryListaOrdini = null");
        }
        this.ordine = ordine;
    }

    /**
     * Permette di impostare i pulsanti {@code carica}, {@code modifica} e {@code cancella} di una {@code EntryListaOrdini}
     * presente nella {@code ListaOrdini}
     * @param strumenti pannello che contiene i pulsanti
     */
    public void setStrumenti(FlowPane strumenti) {
        if(strumenti == null) {
            throw new NullPointerException("Strumenti in EntryListaOrdini = null");
        }
        this.strumenti = strumenti;
    }

    /**
     * Ritorna l'ID dell'ordine associato alla entry
     * @return {@code int} contenente l'ID dell'ordine
     */
    public int getIdOrdine() {
        return this.ordine.getIdOrdine();
    }

    /**
     * Ritorna i farmaci associati all'ordine della {@code EntryListaOrdini}
     * @return oggetto di tipo {@code ArrayList<Farmaco>} contenente i armaci contenuti nell'ordine
     */
    public ArrayList<Farmaco> getFarmaci() {
        return this.ordine.getFarmaci();
    }

    /**
     * Ritorna i farmaci associati all'ordine della {@code EntryListaOrdini} come stringa (nome e quantità)
     * @return oggetto di tipo {@code String} contenente i farmaci contenuti nell'ordine
     */
    public String getFarmaciStringa() {
        String farmaci = "";
        for(Farmaco farmaco : this.ordine.getFarmaci()) {
            farmaci += farmaco.getNome() + " " + farmaco.getQuantita() + "\n";
        }
        return farmaci.stripTrailing();
    }

    /**
     * Ritorna lo stato dell'ordine associato alla entry
     * @return oggetto di tipo {@code String} contenente lo stato dell'ordine
     */
    public String getStato() {
        return this.ordine.getStatoStringa();
    }

    /**
     * Ritorna il tipo dell'ordine associato alla entry
     * @return oggetto di tipo {@code String} contenente il tipo dell'ordine
     */
    public String getTipo() {
        return this.ordine.getTipoStringa();
    }

    /**
     * Ritorna la data di consegna dell'ordine associato alla entry
     * @return oggetto di tipo {@code String} contenente la data di consegna dell'ordine
     */
    public String getDataConsegna() {
        return this.ordine.getDataConsegna().format(DateTimeFormatter.ofPattern("d/MM/uuuu"));
    }

    /**
     * Ritorna i pulsanti associati alla {@code EntryListaOrdini}
     * @return oggetto di tipo {@code FlowPane} contenente i pulsanti associati alla entry
     */
    public FlowPane getStrumenti() {
        return this.strumenti;
    }

    /**
     * Ritorna l'ordine associato alla entry
     * @return oggetto di tipo {@code Ordine} relativo all'ordine associato alla entry
     */
    public Ordine getOrdine() {
        return ordine;
    }
}
