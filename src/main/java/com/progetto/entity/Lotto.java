package com.progetto.entity;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 *  Classe che modella il concetto di {@code Lotto} nel magazzino dell'Azienda.
 */
public class Lotto {

    private int idLotto;
    private String nomeFarmaco;
    private LocalDate dataScadenza;
    private int quantitaContenuta;
    private int quantitaOrdinata;

    /**
     * Istanzia un oggetto di tipo {@code Lotto}
     */
    public Lotto(){

    }

    /**
     * Istanzia un oggetto di tipo {@code Lotto} dati in input l'id, il nome del farmaco, la data di scadenza, la quantità
     * di farmaco contenuta, la quantità di farmaco già ordinata
     * @param idLotto identificativo univoco del lotto
     * @param nomeFarmaco riferimento all'oggetto Farmaco contenuto nel Lotto
     * @param dataScadenza data di scadenza del Lotto
     * @param quantitaContenuta quantità di Farmaco contenuti nel Lotto
     * @param quantitaOrdinata quantità di Farmaco del Lotto già ordinata
     */
    public Lotto(int idLotto, String nomeFarmaco, LocalDate dataScadenza, int quantitaContenuta, int quantitaOrdinata) {
        setIdLotto(idLotto);
        setNomeFarmaco(nomeFarmaco);
        setDataScadenza(dataScadenza);
        setQuantitaContenuta(quantitaContenuta);
        setQuantitaOrdinata(quantitaOrdinata);
    }

    /**
     * Istanzia un oggetto di tipo {@code Lotto} dato in input un risultato di una query sul database
     * @param lotto risultato della query
     * @throws SQLException in caso di errore nel parsing tra tipo SQL e tipo JAVA viene lanciata un'eccezione
     */
    public Lotto(ResultSet lotto) throws SQLException {
        setIdLotto(lotto.getInt("id_lotto"));
        setNomeFarmaco(lotto.getString("farmaco_nome"));
        setDataScadenza(lotto.getDate("data_scadenza"));
        setQuantitaContenuta(lotto.getInt("n_contenuti"));
        setQuantitaOrdinata(lotto.getInt("n_ordinati"));
    }

    /**
     * Ritorna l'id del lotto
     * @return {@code int} contenente l'id del lotto
     */
    public int getIdLotto() {
        return this.idLotto;
    }

    /**
     * Ritorna il nome del Farmaco contenuto nel Lotto
     * @return oggetto di tipo {@code String} contenente il nome del Farmaco contenuto
     */
    public String getNomeFarmaco() {
        return this.nomeFarmaco;
    }

    /**
     * Ritorna la data di scadenza del Lotto
     * @return oggetto di tipo {@code LocalDate} contenente la data di scadenza del Lotto
     */
    public LocalDate getDataScadenza() {
        return this.dataScadenza;
    }

    /**
     * Ritorna la quantità di Farmaco contenuta del Lotto
     * @return {@code int} contenente la quantità di Farmaco contenuta nel Lotto
     */
    public int getQuantitaContenuta() {
        return this.quantitaContenuta;
    }

    /**
     * Ritorna la quantità di Farmaco già ordinata dal Lotto
     * @return {@code int} contenente la quantità di Farmaco ordinata dal Lotto
     */
    public int getQuantitaOrdinata() {
        return this.quantitaOrdinata;
    }

    /**
     * Permette di settare l'ID del Lotto
     * @param idLotto id del lotto
     */
    public void setIdLotto(int idLotto) {
        if (idLotto < 0) {
            throw new IllegalArgumentException("idLotto < 0");
        }
        this.idLotto = idLotto;
    }

    /**
     * Permette di settare il nome del farmaco contenuto
     * @param nomeFarmaco nome del Faramco contenuto nel lotto
     */
    public void setNomeFarmaco(String nomeFarmaco) {
        if (nomeFarmaco == null) {
            throw new NullPointerException("nomeFarmaco = null");
        }
        this.nomeFarmaco = nomeFarmaco;
    }

    /**
     * Permette di settare la data di scadenza del Lotto
     * @param dataScadenza data di scadenza del lotto
     */
    public void setDataScadenza(LocalDate dataScadenza) {
        if (dataScadenza == null) {
            throw new NullPointerException("dataScadenza = null");
        }
        this.dataScadenza = dataScadenza;
    }

    /**
     * Permette di settare la data di scadenza del Lotto
     * @param dataScadenza data di scadenza del lotto
     */
    public void setDataScadenza(Date dataScadenza) {
        if (dataScadenza == null) {
            this.dataScadenza = null;
        } else {
            this.dataScadenza = dataScadenza.toLocalDate();
        }
    }

    /**
     * Permette di settare la quantità di Farmaco contenuta nel Lotto
     * @param quantitaContenuta quantità contenuta del farmaco
     */
    public void setQuantitaContenuta(int quantitaContenuta) {

        if (quantitaContenuta < 0) {
            throw new IllegalArgumentException("quantitaContenuta < 0");
        }
        this.quantitaContenuta = quantitaContenuta;
    }

    /**
     * Permette di settare la quantità di Farmaco ordinata dal Lotto
     * @param quantitaOrdinata quantità del farmaco ordinata
     */
    public void setQuantitaOrdinata(int quantitaOrdinata) {

        if (quantitaOrdinata < 0) {
            throw new IllegalArgumentException("quantitaOrdinata < 0");
        }
        this.quantitaOrdinata = quantitaOrdinata;
    }

    /**
     * Istanzia un oggetto di tipo {@code Lotto} contenente un farmaco prodotto dato in input un lotto esistente
     * @param lotto Lotto contente il Farmaco che è stato appena prodotto
     * @return oggetto di tipo {@code Lotto} nuovo Lotto
     */
    public static Lotto lottoProdotto(Lotto lotto) {
        return new Lotto(lotto.getIdLotto(), lotto.getNomeFarmaco(), lotto.getDataScadenza().plusYears(2), lotto.getQuantitaContenuta(), 0);
    }
}