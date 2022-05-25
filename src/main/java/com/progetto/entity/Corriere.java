package com.progetto.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *  Classe che modella il concetto di {@code Corriere} dell'Azienda Farmaceutica.
 */
public class Corriere {

    private int idAddetto;
    private String nominativo;
    private LocalDate dataNascita;
    private String email;
    private String recapitoTelefonico;

    /**
     * costruttore di un {@code Corriere}
     */
    public Corriere(){

    }
    /**
     * costruisce un {@code Corriere} dati in input l'id, il nominativo, la data di nascita, l'email e il recapito telefonico
     */
    public Corriere(int idAddetto, String nominativo, LocalDate dataNascita, String email, String recapitoTelefonico){
        this.setIdAddetto(idAddetto);
        this.setNominativo(nominativo);
        this.setDataNascita(dataNascita);
        this.setEmail(email);
        this.setRecapitoTelefonico(recapitoTelefonico);
    }

    /**
     * setter per l'id del corriere
     * @param idAddetto id del corriere
     */
    public void setIdAddetto(int idAddetto) {
        if(idAddetto < 1) {
            throw new IllegalArgumentException("Id Corriere non valido");
        }
        this.idAddetto = idAddetto;
    }

    /**
     * setter per il nominativo del corriere
     * @param nominativo nominativo del corriere
     */
    public void setNominativo(String nominativo) {
        if(nominativo == null) {
            throw new NullPointerException("Nominativo del corriere = null");
        }
        this.nominativo = nominativo;
    }

    /**
     * setter per la data di nascita del corriere
     * @param dataNascita data di nascita del corriere
     */
    public void setDataNascita(LocalDate dataNascita) {
        if(dataNascita == null) {
            throw new NullPointerException("Data di nascita del corriere = null");
        }
        this.dataNascita = dataNascita;
    }

    /**
     * setter per l'email del corriere
     * @param email email del corriere
     */
    public void setEmail(String email) {
        if(email == null) {
            throw new NullPointerException("Email del corriere = null");
        }
        this.email = email;
    }

    /**
     * setter per il recapito telefonico del corriere
     * @param recapitoTelefonico recapito telefonico del corriere
     */
    public void setRecapitoTelefonico(String recapitoTelefonico) {
        if(recapitoTelefonico == null) {
            throw new NullPointerException("Recapito telefonico del corriere = null");
        }
        this.recapitoTelefonico = recapitoTelefonico;
    }

    /**
     * getter pe l'id del corriere
     * @return {@code int} contenente l'id del corriere
     */
    public int getIdAddetto() {
        return idAddetto;
    }

    /**
     * getter per il nominativo del corriere
     * @return {@code String} contenente l'id del corriere
     */
    public String getNominativo() {
        return nominativo;
    }

    /**
     * getter per la data di nascita del corriere
     * @return {@code String} contenente la data di nascita del corriere
     */
    public LocalDate getDataNascita() {
        return dataNascita;
    }

    /**
     * getter per l'email del corriere
     * @return {@code String} contenente l'email del corriere
     */
    public String getEmail() {
        return email;
    }

    /**
     * getter per il recapito telefonico del corriere
     * @return {@code String} contenente il recapito telefonico del corriere
     */
    public String getRecapitoTelefonico() {
        return recapitoTelefonico;
    }
}