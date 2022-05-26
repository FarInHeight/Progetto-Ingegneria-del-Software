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

    public Corriere() {
    }

    public Corriere(int idCorriere, String nominativo, LocalDate dataNascita, String email, String recapitoTelefonico) {
        setIdCorriere(idCorriere);
        setNominativo(nominativo);
        setDataNascita(dataNascita);
        setEmail(email);
        setRecapitoTelefonico(recapitoTelefonico);
    }

    public Corriere(ResultSet corriere) throws SQLException {
        setIdCorriere(corriere.getInt("id_corriere"));
        setNominativo(corriere.getString("nominativo"));
        setDataNascita(corriere.getDate("data_nascita").toLocalDate());
        setRecapitoTelefonico(corriere.getString("recapito_telefonico"));
        setEmail(corriere.getString("email"));
    }

    public Corriere(String message) {
        setNominativo(message);
    }

    private LocalDate dataNascita;
    private String email;
    private String recapitoTelefonico;

    public void setIdCorriere(int idCorriere) {
        if (idCorriere < 0) {
            throw new IllegalArgumentException("idCorriere < 0");
        }
        this.idCorriere = idCorriere;
    }

    public void setNominativo(String nominativo) {
        if (nominativo == null) {
            throw new NullPointerException("nominativo = null");
        }
        this.nominativo = nominativo;
    }

    public void setDataNascita(LocalDate dataNascita) {
        if (dataNascita == null) {
            throw new NullPointerException("dataNascita = null");
        }
        this.dataNascita = dataNascita;
    }

    public void setEmail(String email) {
        if (email == null) {
            throw new NullPointerException("email = null");
        }
        this.email = email;
    }

    public void setRecapitoTelefonico(String recapitoTelefonico) {
        if (recapitoTelefonico == null) {
            throw new NullPointerException("recapitoTelefonico = null");
        }
        this.recapitoTelefonico = recapitoTelefonico;
    }

    public int getIdCorriere() {
        return idCorriere;
    }

    public String getNominativo() {
        return nominativo;
    }

    public String getEmail() {
        return email;
    }

    public String getRecapitoTelefonico() {
        return recapitoTelefonico;
    }

    public LocalDate getDataNascita() {
        return dataNascita;
    }
}
