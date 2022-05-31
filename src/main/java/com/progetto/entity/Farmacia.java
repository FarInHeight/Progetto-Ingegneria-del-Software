package com.progetto.entity;

/**
 *  Classe che modella il concetto di {@code Farmacia} della Catena Farmaceutica.
 */
public class Farmacia implements Cloneable{
    private int idFarmacia;
    private String nome;
    private String indirizzo;
    private String recapitoTelefonico;

    /**
     * Istanzia un oggetto di tipo {@code Farmacia}
     */
    public Farmacia(){

    }

    /**
     * Istanzia un oggetto di tipo {@code Farmacia} dati in input l'id, il nome, l'indirizzo e il recapito telefonico
     * @param idFarmacia id della farmacia
     * @param nome nome della farmacia
     * @param indirizzo indirizzo della farmacia
     * @param recapitoTelefonico recapito telefonico della farmacia
     */
    public Farmacia(int idFarmacia, String nome, String indirizzo, String recapitoTelefonico){
        this.setIdFarmacia(idFarmacia);
        this.setNome(nome);
        this.setIndirizzo(indirizzo);
        this.setRecapitoTelefonico(recapitoTelefonico);
    }

    /**
     * Permette di setttare l'id della farmacia
     * @param idFarmacia id della farmacia
     */
    public void setIdFarmacia(int idFarmacia) {
       if(idFarmacia < 0){
           throw new IllegalArgumentException("L'id della farmacia non può essere negativo");
       }
       this.idFarmacia = idFarmacia;
    }

    /**
     * Permette di settare il nome della farmacia
     * @param nome nome della farmacia
     */
    public void setNome(String nome) {
        if(nome == null){
            throw new NullPointerException("Nome della farmacia = null");
        }
        this.nome = nome;
    }

    /**
     * Permette di settare l'indirizzo della farmacia
     * @param indirizzo indirizzo della farmacia
     */
    public void setIndirizzo(String indirizzo) {
        if(indirizzo == null){
            throw new NullPointerException("Indirizzo della farmacia = null");
        }
        this.indirizzo = indirizzo;
    }

    /**
     * Permette di settare il recapito telefonico della famraica
     * @param recapitoTelefonico recapito telefonico della farmacia
     */
    public void setRecapitoTelefonico(String recapitoTelefonico) {
        if(recapitoTelefonico == null){
            throw new NullPointerException("Recapito telefonico della farmacia = null");
        }
        this.recapitoTelefonico = recapitoTelefonico;
    }

    /**
     * Ritorna l'id della farmacia
     * @return {@code int} contenente l'id della farmacia
     */
    public int getIdFarmacia() {
        return idFarmacia;
    }

    /**
     * Ritorna il nome della farmacia
     * @return oggetto di tipo {@code String} contenente il nome della farmacia
     */
    public String getNome() {
        return nome;
    }

    /**
     * Ritorna l'indirizzo della farmacia
     * @return oggetto di tipo {@code String} contenente l'indirizzo della farmacia
     */
    public String getIndirizzo() {
        return indirizzo;
    }

    /**
     * Ritorna il recapito telefonico
     * @return oggetto di tipo {@code String} contenente il recapito telefonico della farmacia
     */
    public String getRecapitoTelefonico() {
        return recapitoTelefonico;
    }

    /**
     * Implementazione del metodo clone
     * @return oggetto di tipo {@code Farmacia}
     * @throws CloneNotSupportedException se la classe non implementa {@code Cloneable}
     */
    @Override
    public Farmacia clone() throws CloneNotSupportedException {
        return (Farmacia)super.clone();
    }
}
