package com.progetto.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Classe che descrive un {@code Farmaco} trattato dall'Azienda o presente nel magazzino della Farmacia.
 */
public class Farmaco {


    private String nome;
    private String principioAttivo;
    private int tipo;
    private LocalDate dataScadenza;
    private int quantita;

    /**
     * Costruttore per un Farmaco trattato dall'Azienda.
     * L'attributo dataScadenda è inizializzato a null e l'attributo quantita è inizializzato a -1.
     *
     * @param nome nome del farmaco
     * @param principioAttivo principio attivo del farmaco
     * @param tipo tipo del farmaco, 0 per farmaci da banco e 1 per farmaci particolari
     */
    public Farmaco(String nome, String principioAttivo, int tipo) {
        this.setNome(nome);
        this.setPrincipioAttivo(principioAttivo);
        this.setTipo(tipo);
    }

    /**
     * Costruttore di un {@code Farmaco} presente nel magazzino della Farmacia.
     *
     * @param nome nome del farmaco
     * @param principioAttivo principio attivo del farmaco
     * @param tipo tipo del farmaco, 0 per farmaci da banco e 1 per farmaci particolari
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
     * costruttore di un {@code Farmaco}
     * @param nomeFarmaco nome del farmaco
     * @param principioAttivo principio attivo del farmaco
     */
    public Farmaco(String nomeFarmaco, int quantita, String principioAttivo){
        this.setNome(nomeFarmaco);
        this.setPrincipioAttivo(principioAttivo);
        this.setQuantita(quantita);
    }

    /**
     * costruttre di un {@code farmaco} a partire dal risultato di una query sul database
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
     * setter per il nome del Farmaco
     * @param nome nome del farmaco
     */
    public void setNome(String nome) {
        if(nome == null) {
            throw new NullPointerException("Nome del farmaco = null");
        }
        this.nome = nome;
    }

    /**
     * setter per il principio attivo del farmaco
     * @param principioAttivo principio attivo del farmaco
     */
    public void setPrincipioAttivo(String principioAttivo) {
        if(principioAttivo == null) {
            throw new NullPointerException("Principio attivo del Farmaco = null");
        }
        this.principioAttivo = principioAttivo;
    }

    /**
     * Setter per la data di scadenza del farmaco
     * @param dataScadenza data di scadenza del farmaco
     */
    public void setDataScadenza(LocalDate dataScadenza) {
        if(dataScadenza == null) {
            throw new NullPointerException("Data di scadenza del farmaco = null");
        }
        this.dataScadenza = dataScadenza;
    }

    /**
     * setter per il tipo del Farmaco
     * @param tipo tipo del farmaco
     */
    public void setTipo(int tipo) {
        if(tipo < 0 || tipo > 1) {
            throw new IllegalArgumentException("Tipo del Farmaco non valido");
        }
        this.tipo = tipo;
    }

    /**
     * setter per la quantità del farmaco
     * @param quantita quantità del farmaco
     */
    public void setQuantita(int quantita) {
        if(quantita < 0){
            throw new IllegalArgumentException("Qunatità del Farmaco non valido");
        }
        this.quantita = quantita;
    }

    /**
     * Getter per il nome del farmaco
     * @return nome del farmaco
     */
    public String getNome() {
        return nome;
    }

    /**
     * Getter per il principio attivo del farmaco
     * @return principio attivo del farmaco
     */
    public String getPrincipioAttivo() {
        return principioAttivo;
    }

    /**
     * Getter per il tipo del farmaco
     * @return tipo del farmaco
     */
    public int getTipo() {
        return tipo;
    }

    /**
     * Getter per la data di scadenza del farmaco presente nel magazzino della farmacia
     * @return data di scadenza del farmaco
     */
    public LocalDate getDataScadenza() {
        return dataScadenza;
    }

    /**
     * Getter per la quantita di farmaco presente nel magazzino della {@code farmacia}
     * @return quantita di faramco presente nel magazzino della farmacia
     */
    public int getQuantita() {
        return quantita;
    }

}
