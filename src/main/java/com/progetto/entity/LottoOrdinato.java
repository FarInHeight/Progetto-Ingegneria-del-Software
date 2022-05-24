package com.progetto.entity;

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
     * @param idLotto  identificativo unico del lotto
     * @param quantitaOrdine quantità di Farmaco del Lotto contenuta nel particolare Ordine
     */
    public LottoOrdinato(int idLotto, int quantitaOrdine) {
        this.setIdLotto(idLotto);
        this.quantitaOrdine = quantitaOrdine;
    }

    /**
     * Getter che ritorna la quantità di Farmaci del Lotto contenuti nell'Ordine a cui è associato
     *
     * @return qunatità contenuta nell'Ordine
     */
    public int getQuantitaOrdine() {
        return quantitaOrdine;
    }
}
