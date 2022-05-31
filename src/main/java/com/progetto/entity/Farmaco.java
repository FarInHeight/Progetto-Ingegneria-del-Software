package com.progetto.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Classe che modella un {@code Farmaco} trattato dall'Azienda o presente nel magazzino della Farmacia.
 */
public class Farmaco {


    private String nome;
    private String principioAttivo;
    private int tipo;
    private LocalDate dataScadenza;
    private int quantita;

    /**
     * Istanzia un oggetto di tipo {@code Farmaco} dato in input il nome, il principio attivo e il tipo
     * @param nome nome del farmaco
     * @param principioAttivo principio attivo del farmaco
     * @param tipo tipo del farmaco (0 per farmaci da banco, 1 per farmaci particolari)
     */
    public Farmaco(String nome, String principioAttivo, int tipo) {
        this.setNome(nome);
        this.setPrincipioAttivo(principioAttivo);
        this.setTipo(tipo);
    }

    /**
     * Istanzia un oggeto di tipo {@code Farmaco} dato in input il nome, il principio attivo, il tipo, la data di scadenza,
     * e la quantita
     * @param nome nome del farmaco
     * @param principioAttivo principio attivo del farmaco
     * @param tipo tipo del farmaco (0 per farmaci da banco, 1 per farmaci particolari)
     * @param dataScadenza data di scadenza del farmaco
     * @param quantita quantita del faramco
     */
    public Farmaco(String nome, String principioAttivo, int tipo, LocalDate dataScadenza, int quantita) {
        this.nome = nome;
        this.principioAttivo = principioAttivo;
        this.tipo = tipo;
        this.dataScadenza = dataScadenza;
        this.quantita = quantita;
    }

    /**
     * Istanzia un oggetto di tipo {@code Farmaco} dato in input il nome e il principio attivo
     * @param nomeFarmaco nome del farmaco
     * @param principioAttivo principio attivo del farmaco
     */
    public Farmaco(String nomeFarmaco, int quantita, String principioAttivo){
        this.setNome(nomeFarmaco);
        this.setPrincipioAttivo(principioAttivo);
        this.setQuantita(quantita);
    }

    /**
     * Istanzia un oggetto di tipo {@code farmaco} data in input un risultato di una query sul database
     * @param farmaco risultato della query
     * @throws SQLException in caso di errore nel parsing tra tipo SQL e tipo JAVA viene lanciata un'eccezione
     */
    public Farmaco(ResultSet farmaco) throws SQLException {
        this.setNome(farmaco.getString("nome"));
        this.setPrincipioAttivo(farmaco.getString("principio_attivo"));
        this.setDataScadenza(farmaco.getDate("data_scadenza").toLocalDate());
        this.setTipo(farmaco.getInt("tipo"));
        this.setQuantita(farmaco.getInt("quantita"));
    }

    /**
     * Permette di settare il nome del Farmaco
     * @param nome nome del farmaco
     */
    public void setNome(String nome) {
        if(nome == null) {
            throw new NullPointerException("Nome del farmaco = null");
        }
        this.nome = nome;
    }

    /**
     * Permette di settare il principio attivo del farmaco
     * @param principioAttivo principio attivo del farmaco
     */
    public void setPrincipioAttivo(String principioAttivo) {
        if(principioAttivo == null) {
            throw new NullPointerException("Principio attivo del Farmaco = null");
        }
        this.principioAttivo = principioAttivo;
    }

    /**
     * Permette di settare la data di scadenza del farmaco
     * @param dataScadenza data di scadenza del farmaco
     */
    public void setDataScadenza(LocalDate dataScadenza) {
        if(dataScadenza == null) {
            throw new NullPointerException("Data di scadenza del farmaco = null");
        }
        this.dataScadenza = dataScadenza;
    }

    /**
     * Permette di settare il tipo del Farmaco
     * @param tipo tipo del farmaco
     */
    public void setTipo(int tipo) {
        if(tipo < 0 || tipo > 1) {
            throw new IllegalArgumentException("Tipo del Farmaco non valido");
        }
        this.tipo = tipo;
    }

    /**
     * Permette di settare la quantità del farmaco
     * @param quantita quantità del farmaco
     */
    public void setQuantita(int quantita) {
        if(quantita < 0){
            throw new IllegalArgumentException("Qunatità del Farmaco non valido");
        }
        this.quantita = quantita;
    }

    /**
     * Ritorna il nome del farmaco
     * @return oggetto di tipo {@code String} contenente il nome del farmaco
     */
    public String getNome() {
        return nome;
    }

    /**
     * Ritorna il principio attivo del farmaco
     * @return oggetto di tipo {@code String} contenente il principio attivo del farmaco
     */
    public String getPrincipioAttivo() {
        return principioAttivo;
    }

    /**
     * Ritorna il tipo del farmaco
     * @return {@code int} contenente il tipo del farmaco
     */
    public int getTipo() {
        return tipo;
    }

    /**
     * Ritorna la data di scadenza del farmaco
     * @return oggetto di tipo {@code LocalDate} contenente la data di scadenza del farmaco
     */
    public LocalDate getDataScadenza() {
        return dataScadenza;
    }

    /**
     * Ritorna la quantità di farmaco
     * @return {@code int} contenente la quantità di faramco
     */
    public int getQuantita() {
        return quantita;
    }

}
