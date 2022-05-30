package com.progetto.entity;

import javafx.scene.layout.FlowPane;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Classe che rappresenta una entry dalla {@code ListaOrdini}
 */
public class EntryListaOrdini {
    private Ordine ordine;

    private FlowPane strumenti;

    /**
     * Costruttore della {@code EntryListaOrdini} che prende in input l'ordine al quale si riferisce
     * @param ordine
     */
    public EntryListaOrdini(Ordine ordine) {
        this.setOrdine(ordine);
    }

    public void setOrdine(Ordine ordine) {
        if(ordine == null) {
            throw new NullPointerException("Ordine in EntryListaOrdini = null");
        }
        this.ordine = ordine;
    }

    /**
     * Metodo per impostare i pulsanti {@code carica}, {@code modifica} e {@code cancella} di una {@code EntryListaOrdini}
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
     * Getter per ottenere l'ID dell'ordine associato alla entry
     * @return ID dell'ordine
     */
    public int getIdOrdine() {
        return this.ordine.getIdOrdine();
    }

    /**
     * Getter per ottenere i farmaci associati all'ordine della {@code EntryListaOrdini}
     * @return farmaci contenuti nell'ordine
     */
    public ArrayList<Farmaco> getFarmaci() {
        return this.ordine.getFarmaci();
    }

    /**
     * Getter per ottenere i farmaci associati all'ordine della {@code EntryListaOrdini} come stringa (nome e quantità)
     * @return farmaci contenuti nell'ordine
     */
    public String getFarmaciStringa() {
        String farmaci = "";
        for(Farmaco farmaco : this.ordine.getFarmaci()) {
            farmaci += farmaco.getNome() + " " + farmaco.getQuantita() + "\n";
        }
        return farmaci.stripTrailing();
    }

    /**
     * Getter per ottenere lo stato dell'ordine associato alla entry
     * @return stato dell'ordine
     */
    public String getStato() {
        return this.ordine.getStatoStringa();
    }

    /**
     * Getter per ottenere il tipo dell'ordine associato alla entry
     * @return tipo dell'ordine
     */
    public String getTipo() {
        return this.ordine.getTipoStringa();
    }

    /**
     * Getter per ottenere la data di consegna dell'ordine associato alla entry
     * @return data di consegna dell'ordine
     */
    public String getDataConsegna() {
        return this.ordine.getDataConsegna().format(DateTimeFormatter.ofPattern("d/MM/uuuu"));
    }

    /**
     * Getter per ottenere i pulsanti associati alla {@code EntryListaOrdini}
     * @return pulsanti associati alla entry
     */
    public FlowPane getStrumenti() {
        return this.strumenti;
    }

    /**
     * Getter per ottenere un oggetto di tipo {@code Ordine} associato alla entry
     * @return ordine associato alla entry
     */
    public Ordine getOrdine() {
        return ordine;
    }
}
