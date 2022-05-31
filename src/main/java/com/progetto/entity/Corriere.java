package com.progetto.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 *  Classe che modella il concetto di {@code Corriere} dell'Azienda Farmaceutica.
 */
public class Corriere {
    private int idCorriere;
    private String nominativo;
    private LocalDate dataNascita;
    private String email;
    private String recapitoTelefonico;

    /**
     * Istanzia un oggetto di tipo {@code Corriere}
     */
    public Corriere() {
    }

    /**
     * Istanzia un oggetto di tipo {@code Corriere} dati in input l'id, il nominativo, la data di nascita, l'email e il recapito telefonico
     * @param idCorriere id del corriere
     * @param nominativo nominativo del corriere
     * @param dataNascita data di nascita del corriere
     * @param email email del corriere
     * @param recapitoTelefonico recapito telefonico del corriere
     */
    public Corriere(int idCorriere, String nominativo, LocalDate dataNascita, String email, String recapitoTelefonico) {
        this.setIdCorriere(idCorriere);
        this.setNominativo(nominativo);
        this.setDataNascita(dataNascita);
        this.setEmail(email);
        this.setRecapitoTelefonico(recapitoTelefonico);
    }

    /**
     * Istanzia un oggetto di tipo {@code Corriere} dato in input il risultato di una query sul database
     * @param corriere tupla del database contenente le informazioni associate ad un corriere
     * @throws SQLException se si verifica un problema con il database
     */
    public Corriere(ResultSet corriere) throws SQLException {
        setIdCorriere(corriere.getInt("id_corriere"));
        setNominativo(corriere.getString("nominativo"));
        setDataNascita(corriere.getDate("data_nascita").toLocalDate());
        setRecapitoTelefonico(corriere.getString("recapito_telefonico"));
        setEmail(corriere.getString("email"));
    }

    /**
     * Permette di settare l'id del corriere
     * @param idCorriere id del corriere
     */
    public void setIdCorriere(int idCorriere) {
        if (idCorriere < 0) {
            throw new IllegalArgumentException("idCorriere < 0");
        }
        this.idCorriere = idCorriere;
    }

    /**
     * Permette di settare il nominativo del corriere
     * @param nominativo il nominativo del corriere
     */
    public void setNominativo(String nominativo) {
        if (nominativo == null) {
            throw new NullPointerException("nominativo = null");
        }
        this.nominativo = nominativo;
    }

    /**
     * Permette di settare la data di nascita del corriere
     * @param dataNascita data di nascita del corriere
     */
    public void setDataNascita(LocalDate dataNascita) {
        if (dataNascita == null) {
            throw new NullPointerException("dataNascita = null");
        }
        this.dataNascita = dataNascita;
    }

    /**
     * Permette di settare l'email del corriere
     * @param email email del corriere
     */
    public void setEmail(String email) {
        if (email == null) {
            throw new NullPointerException("email = null");
        }
        this.email = email;
    }

    /**
     * Permette di settare il recapito telefonico del corriere
     * @param recapitoTelefonico recapito telefonico del corriere
     */
    public void setRecapitoTelefonico(String recapitoTelefonico) {
        if (recapitoTelefonico == null) {
            throw new NullPointerException("recapitoTelefonico = null");
        }
        this.recapitoTelefonico = recapitoTelefonico;
    }

    /**
     * Ritorna l'id del corriere
     * @return un {@code int} contenente l'id del corriere
     */
    public int getIdCorriere() {
        return idCorriere;
    }

    /**
     * Ritorna il nominativo del corriere
     * @return un oggetto di tipo {@code String} contenente il nominativo del corriere
     */
    public String getNominativo() {
        return nominativo;
    }

    /**
     * Ritorna l'email del corriere
     * @return un oggetto di tipo {@code String} contenente l'email del corriere
     */
    public String getEmail() {
        return email;
    }

    /**
     * Ritorna il recapito telefonico del corriere
     * @return un oggetto di tipo {@code String} contenente il recapito telefonico del corriere
     */
    public String getRecapitoTelefonico() {
        return recapitoTelefonico;
    }

    /**
     * Ritorna la data di nascita del corriere
     * @return un oggetto di tipo {@code LocalDate} contenente la data di nascita del corriere
     */
    public LocalDate getDataNascita() {
        return dataNascita;
    }
}
