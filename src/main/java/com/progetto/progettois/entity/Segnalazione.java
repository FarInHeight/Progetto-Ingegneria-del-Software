package com.progetto.progettois.entity;

public class Segnalazione {
    private int idSegnalazione;
    private String commento;
    private int refOrdine;

    public Segnalazione(int idSegnalazione, String commento, int refOrdine) {
        this.idSegnalazione = idSegnalazione;
        this.commento = commento;
        this.refOrdine = refOrdine;
    }

}
