package com.progetto.entity;

import javafx.scene.layout.FlowPane;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class EntryListaOrdini {
    private Ordine ordine;
    private FlowPane strumenti;

    public EntryListaOrdini(Ordine ordine) {
        this.setOrdine(ordine);
    }

    private void setOrdine(Ordine ordine) {
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

    public int getIdOrdine() {
        return this.ordine.getIdOrdine();
    }

    public ArrayList<Farmaco> getFarmaci() {
        return this.ordine.getFarmaci();
    }

    public String getFarmaciStringa() {
        String farmaci = "";
        for(Farmaco farmaco : this.ordine.getFarmaci()) {
            farmaci += farmaco.getNome() + " " + farmaco.getQuantita() + "\n";
        }
        return farmaci.stripTrailing();
    }

    public String getStato() {
        return this.ordine.getStatoStringa();
    }

    public String getTipo() {
        return this.ordine.getTipoStringa();
    }

    public String getDataConsegna() {
        return this.ordine.getDataConsegna().format(DateTimeFormatter.ofPattern("d/MM/uuuu"));
    }

    public FlowPane getStrumenti() {
        return this.strumenti;
    }
}
