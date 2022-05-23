package com.progetto.entity;

import java.time.LocalDate;

public class Lotto {

    private int idLotto;
    private Farmaco farmaco;
    private LocalDate dataScadenza;
    private int quantitaContenuta;
    private int quantitaOrdinata;

    public Lotto(int idLotto, Farmaco farmaco, LocalDate dataScadenza, int quantitaContenuta, int quantitaOrdinata) {
        this.idLotto = idLotto;
        this.farmaco = farmaco;
        this.dataScadenza = dataScadenza;
        this.quantitaContenuta = quantitaContenuta;
        this.quantitaOrdinata = quantitaOrdinata;
    }

    public LocalDate getDataScadenza() {
        return this.dataScadenza;
    }
}
