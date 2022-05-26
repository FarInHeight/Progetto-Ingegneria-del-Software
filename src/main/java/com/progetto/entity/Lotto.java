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

    public Lotto(){

    }

    /**
     * Costruttore per un Lotto contenuto nel DBMSAzienda.
     *
     * @param idLotto identificativo unico del lotto
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
     * Cotruttore che genera un oggetto Lotto a partire da un oggetto ResultSet, ottenuto in seguito a una query SQL.
     *
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
     * Getter per l'attributo idLotto
     * @return l'identificatore unico del Lotto
     */
    public int getIdLotto() {
        return this.idLotto;
    }

    /**
     * Getter per il nome del Farmaco contenuto nel Lotto
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
     * Setter per l'ID del Lotto
     * @param idLotto id del lotto
     */
    public void setIdLotto(int idLotto) {
        if (idLotto < 0) {
            throw new IllegalArgumentException("idLotto < 0");
        }
        this.idLotto = idLotto;
    }

    /**
     * Setter per il nome del farmaco contenuto
     * @param nomeFarmaco nome del Faramco contenuto nel lotto
     */
    public void setNomeFarmaco(String nomeFarmaco) {
        if (nomeFarmaco == null) {
            throw new NullPointerException("nomeFarmaco = null");
        }
        this.nomeFarmaco = nomeFarmaco;
    }

    /**
     * Setter per la data di scadenza del Lotto
     * @param dataScadenza nuova data di scadenza
     */
    public void setDataScadenza(LocalDate dataScadenza) {
        if (dataScadenza == null) {
            throw new NullPointerException("dataScadenza = null");
        }
        this.dataScadenza = dataScadenza;
    }

    /**
     * Setter per la data di scadenza del Lotto
     * @param dataScadenza nuova data di scadenza
     */
    public void setDataScadenza(Date dataScadenza) {
        if (dataScadenza == null) {
            this.dataScadenza = null;
        } else {
            this.dataScadenza = dataScadenza.toLocalDate();
        }
    }

    /**
     * Setter per la quantità di Farmaco contenuta nel Lotto
     * @param quantitaContenuta nuova quantità
     */
    public void setQuantitaContenuta(int quantitaContenuta) {

        if (quantitaContenuta < 0) {
            throw new IllegalArgumentException("quantitaContenuta < 0");
        }
        this.quantitaContenuta = quantitaContenuta;
    }

    /**
     * Setter per la quantità di Farmaco ordinata dal Lotto
     * @param quantitaOrdinata nuova quantità
     */
    public void setQuantitaOrdinata(int quantitaOrdinata) {

        if (quantitaOrdinata < 0) {
            throw new IllegalArgumentException("quantitaOrdinata < 0");
        }
        this.quantitaOrdinata = quantitaOrdinata;
    }

    /**
     * Crea un nuovo Lotto da aggiungere al database dell'Azienda a partire da un {@code Lotto} preesistente.
     * @param lotto Lotto contente il Farmco che è stato appena prodotto
     * @return nuovo Lotto
     */
    public static Lotto lottoProdotto(Lotto lotto) {
        return new Lotto(lotto.getIdLotto(), lotto.getNomeFarmaco(), lotto.getDataScadenza().plusYears(2), lotto.getQuantitaContenuta(), 0);
    }


}


