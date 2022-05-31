package com.progetto.entity;

import javafx.scene.layout.FlowPane;

/**
 * classe che rappresenta una entry nella tabella del form Ordine
 */
public class EntryFormOrdine {
    private String nomeFarmaco;
    private String principioAttivo;
    private FlowPane strumenti;

    /**
     * Costruttore per istanziare una entry della {@code FormOrdine}
     * @param nomeFarmaco nome del farmaco
     * @param principioAttivo principio attivo del farmaco
     */
    public EntryFormOrdine(String nomeFarmaco, String principioAttivo) {
        this.setNomeFarmaco(nomeFarmaco);
        this.setPrincipioAttivo(principioAttivo);
    }

    /**
     * setter per gli strumenti di una entry di {@code FormOrdine}
     * @param strumenti strumenti della entry
     */
    public void setStrumenti(FlowPane strumenti){
        if(strumenti == null){
            throw new NullPointerException("strumenti == null");
        }
        this.strumenti = strumenti;
    }

    private void setNomeFarmaco(String nomeFarmaco) {
        if (nomeFarmaco == null) {
            throw new NullPointerException("Nome del Farmaco = null");
        }
        this.nomeFarmaco = nomeFarmaco;
    }

    private void setPrincipioAttivo(String principioAttivo) {
        if (principioAttivo == null) {
            throw new NullPointerException("Principio attivo del Farmaco = null");
        }
        this.principioAttivo = principioAttivo;
    }

    /**
     * getter per ottenere il nome del farmaco
     * @return {@code String} contenente il nome del farmaco
     */
    public String getNomeFarmaco() {
        return nomeFarmaco;
    }

    /**
     * getter per ottenere il principio attivo del farmaco
     * @return {@code String} contenente il principio attivo del farmaco
     */
    public String getPrincipioAttivo() {
        return principioAttivo;
    }

    /**
     * getter per ottenere il pulsante rimuovi e lo spinner inerenti al farmco
     * @return {@code FlowPane} contenente il pulsante rimuovi e lo spinner
     */
    public FlowPane getStrumenti() {
        return strumenti;
    }
}