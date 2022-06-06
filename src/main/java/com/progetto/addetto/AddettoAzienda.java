package com.progetto.addetto;

import java.time.LocalDate;

/**
 *  Classe che modella il concetto di {@code AddettoAzienda} dell'Azienda Farmaceutica.
 */
public class AddettoAzienda {
    private int idAddetto;
    private String nominativo;
    private LocalDate dataNascita;
    private String email;
    private String recapitoTelefonico;

    /**
     * Istanzia un oggetto di tipo {@code AddettoAzienda} dati in input l'id, il nominativo, la data di nascita, l'email e il recapito telefonico
     * @param idAddetto id dell'Addetto
     * @param nominativo nominativo dell'Addetto ("Nome Cognome")
     * @param dataNascita data di nascita dell'Addetto
     * @param email email dell'Addetto
     * @param recapitoTelefonico numero di telefono dell'Addetto
     */
    public AddettoAzienda(int idAddetto, String nominativo, LocalDate dataNascita, String email, String recapitoTelefonico) {
        this.setIdAddetto(idAddetto);
        this.setNominativo(nominativo);
        this.setDataNascita(dataNascita);
        this.setEmail(email);
        this.setRecapitoTelefonico(recapitoTelefonico);
    }

    /**
     * Istanzia un oggetto di tipo {@code AddettoAzienda}.
     */
    public AddettoAzienda() {

    }
    /**
     * Permette di settare l'ID dell'Addetto
     * @param idAddetto id dell'Addetto
     * @throws IllegalArgumentException se l'argomento è minore di 0
     */
    public void setIdAddetto(int idAddetto) {
        if(idAddetto < 1) {
            throw new IllegalArgumentException("Id Addetto non valido");
        }
        this.idAddetto = idAddetto;
    }

    /**
     * Permette di settare il nominativo dell'Addetto ("Nome Cognome")
     * @param nominativo nominativo dell'Addetto
     * @throws NullPointerException se l'argomento è {@code null}
     */
    public void setNominativo(String nominativo) {
        if(nominativo == null) {
            throw new NullPointerException("Nominativo dell'Addetto = null");
        }
        this.nominativo = nominativo;
    }

    /**
     * Permette di settare la data di nascita dell'Addetto
     * @param dataNascita nominativo dell'Addetto
     * @throws NullPointerException se l'argomento è {@code null}
     */
    public void setDataNascita(LocalDate dataNascita) {
        if(dataNascita == null) {
            throw new NullPointerException("Data di nascita = null");
        }
        this.dataNascita = dataNascita;
    }

    /**
     * Permette di settare l'email dell'Addetto
     * @param email email dell'Addetto
     * @throws NullPointerException se l'argomento è {@code null}
     */
    public void setEmail(String email) {
        if(email == null) {
            throw new NullPointerException("Email dell'Addetto = null");
        }
        this.email = email;
    }

    /**
     * Permette di settare il recapito telefonico dell'Addetto
     * @param recapitoTelefonico recapito telefonico dell'Addetto
     * @throws NullPointerException se l'argomento è {@code null}
     */
    public void setRecapitoTelefonico(String recapitoTelefonico) {
        if(recapitoTelefonico == null) {
            throw new NullPointerException("Recapito telefonico = null");
        }
        this.recapitoTelefonico = recapitoTelefonico;
    }

    /**
     * Ritorna l'id dell'Addetto
     * @return un {@code int} contenente l'id dell'addetto
     */
    public int getIdAddetto() {
        return idAddetto;
    }

    /**
     * Ritorna la data di nascita dell'Addetto
     * @return un oggetto di tipo {@code LocalDate} contenente la data di nascita
     */
    public LocalDate getDataNascita() {
        return dataNascita;
    }

    /**
     * Ritorna l'email dell'Addetto
     * @return un oggetto di tipo {@code String} contenente l'email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Ritorna il recapito telefonico dell'Addetto
     * @return un oggetto di tipo {@code String} contenente il recapito telefonico
     */
    public String getRecapitoTelefonico() {
        return recapitoTelefonico;
    }

    /**
     * Ritorna il nominativo dell'Addetto ("Nome Cognome")
     * @return un oggetto di tipo {@code String} contenente il nominativo dell'Addetto
     */
    public String getNominativo() {
        return nominativo;
    }

    /**
     * Implementazione del metodo {@code clone} ereditato dalla classe {@code Object}
     * @return un oggetto di tipo {@code AddettoAzienda} contenente la copia dell'AddettoAzienda
     */
    @Override
    public AddettoAzienda clone() {
        return new AddettoAzienda(this.getIdAddetto(), this.getNominativo(), this.getDataNascita(), this.getEmail(), this.getRecapitoTelefonico());
    }


}
