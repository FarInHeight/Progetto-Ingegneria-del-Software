package com.progetto.entity;
/**
 *  Classe che modella il concetto di {@code Segnalazione}
 */
public class Segnalazione {
    private int idSegnalazione;
    private String commento;
    private int refOrdine;

    /**
     * Istanzia un oggetto di tipo {@code Segnalazione} dato in input l'id, il commento del farmacista e l'id dell'ordine associato
     * @param idSegnalazione id della segnalazione
     * @param commento commento del farmacista
     * @param refOrdine identificativo dell'ordine associato alla segnalazione
     */
    public Segnalazione(int idSegnalazione, String commento, int refOrdine) {
        this.idSegnalazione = idSegnalazione;
        this.commento = commento;
        this.refOrdine = refOrdine;
    }

    private void setIdSegnalazione(int idSegnalazione) {
        if(idSegnalazione < 1) {
            throw new IllegalArgumentException("ID segnalazione < 1");
        }
        this.idSegnalazione = idSegnalazione;
    }

    private void setCommento(String commento) {
        if(commento == null) {
            throw new NullPointerException("Commento della segnalazione = null");
        }
        this.commento = commento;
    }

    private void setRefOrdine(int refOrdine) {
        if(refOrdine < 1) {
            throw new IllegalArgumentException("Riferimento ordine < 1");
        }
        this.refOrdine = refOrdine;
    }

    /**
     * Ritorna l'ID della segnalazione
     * @return {@code int} contenente l'id della segnalazione
     */
    public int getIdSegnalazione() {
        return idSegnalazione;
    }

    /**
     * Ritorna il commento associato alla segnalazione
     * @return oggetto di tipo {@code String} contenente il commento della segnalazione
     */
    public String getCommento() {
        return commento;
    }

    /**
     * Ritorna il riferimento all'ordine della segnalazione
     * @return {@code int} contenente il riferimento dell'ordine
     */
    public int getRefOrdine() {
        return refOrdine;
    }
}
