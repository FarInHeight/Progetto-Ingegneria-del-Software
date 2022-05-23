package com.progetto.progettois.entity;

import java.time.LocalDate;

public class Farmaco {
    private String nome;
    private String principioAttivo;
    private int tipo;
    private LocalDate dataScadenza;
    private int quantita;


    public Farmaco(String nome, String principioAttivo, int tipo) {
        this.nome = nome;
        this.principioAttivo = principioAttivo;
        this.tipo = tipo;
        this.dataScadenza = null;
        this.quantita = -1;
    }

    public Farmaco(String nome, String principioAttivo, int tipo, LocalDate dataScadenza, int quantita) {
        this.nome = nome;
        this.principioAttivo = principioAttivo;
        this.tipo = tipo;
        this.dataScadenza = dataScadenza;
        this.quantita = quantita;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }
}
