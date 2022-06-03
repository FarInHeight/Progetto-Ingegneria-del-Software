package com.progetto.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Classe che modella il concetto di Lotto associato ad un Ordine.
 * Oltre agli attributi di Lotto è presente la quantità di Farmaci del Lotto contenuti in un particolare Ordine
 */
public class LottoOrdinato extends Lotto{

    private int quantitaOrdine;

    /**
     * Istanzia un oggetto di tipo {@code LottoOrdinato} dato in input l'id e la quantità di farmaco contenuta nell'ordine
     * @param idLotto identificativo unico del lotto
     * @param quantitaOrdine quantità di Farmaco del Lotto contenuta nel particolare Ordine
     */
    public LottoOrdinato(int idLotto, int quantitaOrdinata, int quantitaOrdine) {
        this.setQuantitaOrdinata(quantitaOrdinata);
        this.setIdLotto(idLotto);
        this.quantitaOrdine = quantitaOrdine;
    }

    /**
     * Istanzia un oggetto di tipo {@code LottoOrdinato} dato in input il risultato di una query sul database
     * @param lotto lotto nel database da cui creare l'oggetto lotto
     */
    public LottoOrdinato(ResultSet lotto) throws SQLException {
        super(lotto);
        setQuantitaOrdine(lotto.getInt("n_farmaci"));
    }

    /**
     * Ritorna la quantità di Farmaci del Lotto contenuti nell'Ordine a cui è associato
     * @return {@code int} contenente la quantità di farmaci del lotto contenuta nell'Ordine
     */
    public int getQuantitaOrdine() {
        return quantitaOrdine;
    }

    /**
     * Permette di settare la quantita di farmaco associata ad un ordine
     * @param quantitaOrdine qunatita di farmaco associata ad un ordine
     */
    public void setQuantitaOrdine(int quantitaOrdine) {
        if (quantitaOrdine < 0) {
            throw new IllegalArgumentException("quantitaOrdine = 0");
        }
        this.quantitaOrdine = quantitaOrdine;
    }
}
