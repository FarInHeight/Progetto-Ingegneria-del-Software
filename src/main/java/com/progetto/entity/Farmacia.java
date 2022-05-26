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
     * costruttore di una {@code Farmacia}
     */
    public Farmacia(){

    }

    /**
     * costruttore di una {@code Farmacia} dati in input l'id, il nome, l'indirizzo e il recapito telefonico
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
     * setter per idFarmacia
     * @param idFarmacia id della farmacia
     */
    public void setIdFarmacia(int idFarmacia) {
       if(idFarmacia < 0){
           throw new IllegalArgumentException("L'id della farmacia non può essere negativo");
       }
       this.idFarmacia = idFarmacia;
    }

    /**
     * setter per il nome della farmacia
     * @param nome nome della farmacia
     */
    public void setNome(String nome) {
        if(nome == null){
            throw new NullPointerException("Nome della farmacia = null");
        }
        this.nome = nome;
    }

    /**
     * setter per l'indirizzo della farmacia
     * @param indirizzo indirizzo della farmacia
     */
    public void setIndirizzo(String indirizzo) {
        if(indirizzo == null){
            throw new NullPointerException("Indirizzo della farmacia = null");
        }
        this.indirizzo = indirizzo;
    }

    /**
     * setter per il recapito telefonico della famraica
     * @param recapitoTelefonico recapito telefonico della farmacia
     */
    public void setRecapitoTelefonico(String recapitoTelefonico) {
        if(recapitoTelefonico == null){
            throw new NullPointerException("Recapito telefonico della farmacia = null");
        }
        this.recapitoTelefonico = recapitoTelefonico;
    }

    /**
     * getter per l'id della farmacia
     * @return {@code int} contenente l'id della farmacia
     */
    public int getIdFarmacia() {
        return idFarmacia;
    }

    /**
     * getter per il nome della farmacia
     * @return {@code String} contenente il nome della farmacia
     */
    public String getNome() {
        return nome;
    }

    /**
     * getter per l'indirizzo della farmacia
     * @return {@code String} contenente l'indirizzo della farmacia
     */
    public String getIndirizzo() {
        return indirizzo;
    }

    /**
     * getter per il recapito telefonico
     * @return ritorna una {@code String} contenete il recapito telefonico della farmacia
     */
    public String getRecapitoTelefonico() {
        return recapitoTelefonico;
    }

    /**
     * implementazione del metodo clone
     * @return un oggetto di tipo {@code Farmacia}
     * @throws CloneNotSupportedException se classe non implementa {@code Cloneable}
     */
    @Override
    public Farmacia clone() throws CloneNotSupportedException {
        return (Farmacia)super.clone();
    }
}
