package com.progetto.entity;
/**
 *  Classe che modella il concetto di {@code Segnalazione} nel Sistema.
 */
public class Segnalazione {
    private int idSegnalazione;
    private String commento;
    private int refOrdine;

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
     * Getter per ottenere l'ID della segnalazione
     * @return id della segnalazione
     */
    public int getIdSegnalazione() {
        return idSegnalazione;
    }

    /**
     * Getter per ottenere il commento associato alla segnalazione
     * @return commento della segnalazione
     */
    public String getCommento() {
        return commento;
    }

    /**
     * Getter per ottenere il riferimento all'ordine della segnalazione
     * @return riferimento dell'ordine
     */
    public int getRefOrdine() {
        return refOrdine;
    }
}
