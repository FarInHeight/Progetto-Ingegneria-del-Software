package com.progetto.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 *  Classe che modella il concetto di {@code Lotto} nel magazzino dell'Azienda
 */
public class Lotto {

    private int idLotto;
    private String nomeFarmaco;
    private LocalDate dataScadenza;
    private int quantitaContenuta;
    private int quantitaOrdinata;

    /**
     * Costruttore per un Lotto contenuto nel DBMSAzienda.
     * <p>
     * Non effettua alcun controllo sui parametri passati.
     *
     * @param idLotto identificativo unico del lotto
     * @param nomeFarmaco riferimento all'oggetto Farmaco contenuto nel Lotto
     * @param dataScadenza data di scadenza del Lotto
     * @param quantitaContenuta quantità di Farmaco contenuti nel Lotto
     * @param quantitaOrdinata quantità di Farmaco del Lotto già ordinata
     */
    public Lotto(int idLotto, String nomeFarmaco, LocalDate dataScadenza, int quantitaContenuta, int quantitaOrdinata) {
        this.idLotto = idLotto;
        this.nomeFarmaco = nomeFarmaco;
        this.dataScadenza = dataScadenza;
        this.quantitaContenuta = quantitaContenuta;
        this.quantitaOrdinata = quantitaOrdinata;
    }

    /**
     * Cotruttore che genera un oggetto Lotto a partire da un oggetto ResultSet, ottenuto in seguito a una query SQL.
     *
     * @param lotto risultato della query
     * @throws SQLException in caso di errore nel parsing tra tipo SQL e tipo JAVA viene lanciata un'eccezione
     */
    public Lotto(ResultSet lotto) throws SQLException {
        this.idLotto = lotto.getInt("ID_lotto");
        this.nomeFarmaco = lotto.getString("Farmaco_Nome");
        this.dataScadenza = lotto.getDate("Data_scadenza").toLocalDate();
        this.quantitaContenuta = lotto.getInt("N_contenuti");
        this.quantitaOrdinata = lotto.getInt("N_ordinati");
    }

    /**
     * Getter per l'attributo idLotto
     *
     * @return l'identificatore unico del Lotto
     */
    public int getIdLotto() {
        return this.idLotto;
    }

    /**
     * Getter per il nome del Farmaco contenuto nel Lotto
     *
     * @return nome del Farmaco contenuto
     */
    public String getNomeFarmaco() {
        return this.nomeFarmaco;
    }

    /**
     * Getter per la data di scadenza del Lotto
     * @return data di scadenza del Lotto
     */
    public LocalDate getDataScadenza() {
        return this.dataScadenza;
    }

    /**
     * Getter per la quantità di Farmaco contenuta del Lotto
     * @return la quantità di Farmaco contenuta nel Lotto
     */
    public int getQuantitaContenuta() {
        return this.quantitaContenuta;
    }

    /**
     * Getter per la quantità di Farmaco già ordinata dal Lotto
     * @return la quantità di Farmaco ordinata dal Lotto
     */
    public int getQuantitaOrdinata() {
        return this.quantitaOrdinata;
    }

    /**
     * Setter per la data di scadenza del Lotto
     * @param dataScadenza nuova data di scadenza
     */
    public void setDataScadenza(LocalDate dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    /**
     * Setter per la quantità di Farmaco contenuta nel Lotto
     * @param quantitaContenuta nuova quantità
     */
    public void setQuantitaContenuta(int quantitaContenuta) {
        this.quantitaContenuta = quantitaContenuta;
    }

    /**
     * Setter per la quantità di Farmaco ordinata dal Lotto
     * @param quantitaOrdinata nuova quantità
     */
    public void setQuantitaOrdinata(int quantitaOrdinata) {
        this.quantitaOrdinata = quantitaOrdinata;
    }
}


