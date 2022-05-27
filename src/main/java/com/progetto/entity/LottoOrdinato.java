package com.progetto.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Classe che rappresenta un Lotto associato ad un Ordine.
 * Oltre agli attributi di Lotto è presente la quantità di Farmaci del Lotto contenuti in un particolare Ordine
 */
public class LottoOrdinato extends Lotto{

    private int quantitaOrdine;

    /**
     * Costruttore per il Lotto associato ad un Ordine
     *
     * @param idLotto identificativo unico del lotto
     * @param nomeFarmaco riferimento all'oggetto Farmaco contenuto nel Lotto
     * @param dataScadenza data di scadenza del Lotto
     * @param quantitaContenuta quantità di Farmaco contenuti nel Lotto
     * @param quantitaOrdinata quantità di Farmaco del Lotto già ordinata
     * @param quantitaOrdine quantità di Farmaco del Lotto contenuta nel particolare Ordine
     */
    public LottoOrdinato(int idLotto, String nomeFarmaco, LocalDate dataScadenza, int quantitaContenuta, int quantitaOrdinata, int quantitaOrdine) {
        super(idLotto, nomeFarmaco, dataScadenza, quantitaContenuta, quantitaOrdinata);
        this.quantitaOrdine = quantitaOrdine;
    }

    /**
     * Costruttore per il Lotto associato ad un Ordine
     *
     * @param lotto lotto nel database da cui creare l'oggetto lotto
     */
    public LottoOrdinato(ResultSet lotto) throws SQLException {
        super(lotto);
        setQuantitaOrdine(lotto.getInt("n_farmaci"));
    }

    /**
     * Getter che ritorna la quantità di Farmaci del Lotto contenuti nell'Ordine a cui è associato
     * @return qunatità contenuta nell'Ordine
     */
    public int getQuantitaOrdine() {
        return quantitaOrdine;
    }

    /**
     * Setter per la quantita di farmaco assocaita ad un ordine
     * @param quantitaOrdine qunatita di farmaco assocaita ad un ordine
     */
    public void setQuantitaOrdine(int quantitaOrdine) {
        if (quantitaOrdine < 0) {
            throw new IllegalArgumentException("quantitaOrdine = 0");
        }
        this.quantitaOrdine = quantitaOrdine;
    }
}
