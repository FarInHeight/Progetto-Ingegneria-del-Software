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
     * Costruttore per la classe {@code Corriere}
     */
    public Corriere() {
    }

    /**
     * Costruttore per la classe {@code Corriere}
     * @param idCorriere id del corriere
     * @param nominativo nominativo del corriere
     * @param dataNascita data di nascita del corriere
     * @param email email del corriere
     * @param recapitoTelefonico recapito telefonico del corriere
     */
    public Corriere(int idCorriere, String nominativo, LocalDate dataNascita, String email, String recapitoTelefonico) {
        setIdCorriere(idCorriere);
        setNominativo(nominativo);
        setDataNascita(dataNascita);
        setEmail(email);
        setRecapitoTelefonico(recapitoTelefonico);
    }

    /**
     * Costruttore per la classe {@code Corriere}
     *
     * @param corriere tupla del database contenente le informazioni associate ad un corriere
     */
    public Corriere(ResultSet corriere) throws SQLException {
        setIdCorriere(corriere.getInt("id_corriere"));
        setNominativo(corriere.getString("nominativo"));
        setDataNascita(corriere.getDate("data_nascita").toLocalDate());
        setRecapitoTelefonico(corriere.getString("recapito_telefonico"));
        setEmail(corriere.getString("email"));
    }

    /**
     * Setter per l'id del corriere
     * @param idCorriere id del corriere
     */
    public void setIdCorriere(int idCorriere) {
        if (idCorriere < 0) {
            throw new IllegalArgumentException("idCorriere < 0");
        }
        this.idCorriere = idCorriere;
    }

    /**
     * Setter per il nominativo del corriere
     * @param nominativo il nominativo del corriere
     */
    public void setNominativo(String nominativo) {
        if (nominativo == null) {
            throw new NullPointerException("nominativo = null");
        }
        this.nominativo = nominativo;
    }

    /**
     * Setter per la data di nascita del corriere
     * @param dataNascita data di nascita del corriere
     */
    public void setDataNascita(LocalDate dataNascita) {
        if (dataNascita == null) {
            throw new NullPointerException("dataNascita = null");
        }
        this.dataNascita = dataNascita;
    }

    /**
     * Setter per l'email del corriere
     * @param email email del corriere
     */
    public void setEmail(String email) {
        if (email == null) {
            throw new NullPointerException("email = null");
        }
        this.email = email;
    }

    /**
     * Setter per il recapito telefonico del corriere
     * @param recapitoTelefonico recapito telefonico del corriere
     */
    public void setRecapitoTelefonico(String recapitoTelefonico) {
        if (recapitoTelefonico == null) {
            throw new NullPointerException("recapitoTelefonico = null");
        }
        this.recapitoTelefonico = recapitoTelefonico;
    }

    /**
     * Getter per l'id del corriere
     * @return id del corriere
     */
    public int getIdCorriere() {
        return idCorriere;
    }

    /**
     * Getter per il nominativo del corriere
     * @return nominativo del corriere
     */
    public String getNominativo() {
        return nominativo;
    }

    /**
     * Getter per l'email del corriere
     * @return email del corriere
     */
    public String getEmail() {
        return email;
    }

    /**
     * Getter per il recapito telefonico del corriere
     * @return recapito telefonico del corriere
     */
    public String getRecapitoTelefonico() {
        return recapitoTelefonico;
    }

    /**
     * Getter per la data di nascita del corriere
     * @return data di nascita del corriere
     */
    public LocalDate getDataNascita() {
        return dataNascita;
    }
}
