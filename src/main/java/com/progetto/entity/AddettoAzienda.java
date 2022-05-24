package com.progetto.entity;

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
     * Costruttore di un {@code AddettoAzienda}.
     * @param idAddetto id dell'Addetto
     * @param nominativo nominativo dell'Addetto ("Nome Cognome")
     * @param dataNascita data di nascita dell'Addetto
     * @param email email dell'Addetto
     * @param recapitoTelefonico numero di telefono dell'Addetto
     */
    public AddettoAzienda(int idAddetto, String nominativo, LocalDate dataNascita, String email, String recapitoTelefonico) {
        this.idAddetto = idAddetto;
        this.nominativo = nominativo;
        this.dataNascita = dataNascita;
        this.email = email;
        this.recapitoTelefonico = recapitoTelefonico;
    }

    /**
     * Setter per impostare l'ID dell'Addetto
     * @param idAddetto id dell'Addetto
     * @throws IllegalArgumentException se l'argomento è minore di 0
     */
    public void setIdAddetto(int idAddetto) {
        if(idAddetto < 1) {
            throw new IllegalArgumentException("Id Ordine non valido");
        }
        this.idAddetto = idAddetto;
    }

    /**
     * Setter per impostare il nominativo dell'Addetto ("Nome Cognome")
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
     * Setter per impostare la data di nascita dell'Addetto
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
     * Setter per impostare l'email dell'Addetto
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
     * Setter per impostare il recapito telefonico dell'Addetto
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
     * Implementazione del metodo {@code clone} ereditato dalla classe {@code Object}
     * @return copia dell'AddettoAzienda
     * @throws CloneNotSupportedException
     */
    @Override
    public AddettoAzienda clone() throws CloneNotSupportedException {
        return (AddettoAzienda)super.clone();
    }

    /**
     * Getter per ottenere il nominativo dell'Addetto ("Nome Cognome")
     * @return nominativo dell'Addetto
     */
    public String getNominativo() {
        return nominativo;
    }
}
