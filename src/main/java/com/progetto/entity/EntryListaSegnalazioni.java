package com.progetto.entity;

import javafx.scene.layout.FlowPane;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EntryListaSegnalazioni {
    private Segnalazione segnalazione;
    private String riepilogoOrdine;
    private int idFarmacia;
    private String recapitoTelefonicoFarmacia;
    private String nomeFarmacia;
    private String data;
    private FlowPane strumenti;

    /**
     * Costruttore per istanziare una entry della {@code ListaSegnalazioni}
     * @param idSegnalazione id della segnalazione
     * @param idOrdine id dell'ordine per cui è stata generata la segnalazione
     * @param riepilogoOrdine riepilogo dell'ordine per cui è stata generata la segnalazione
     * @param  idFarmacia if della farmacia che ha generato la segnalazione
     * @param nomeFarmacia nome della farmacia che ha effettuato la segnalazione
     * @param  recapitoTelefonicoFarmacia recapito telefonico della farmacia che ha generato la segnalazione
     * @param  commento commento del farmacista
     * @param data data in cui è stata generata segnalazione
     */
    public EntryListaSegnalazioni(int idSegnalazione, int idOrdine, String riepilogoOrdine, int idFarmacia, String nomeFarmacia, String recapitoTelefonicoFarmacia, String commento, LocalDate data) {
        this.segnalazione = new Segnalazione(idSegnalazione, commento, idOrdine);
        this.setRiepilogoOrdine(riepilogoOrdine);
        this.setIdFarmacia(idFarmacia);
        this.setNomeFarmacia(nomeFarmacia);
        this.setRecapitoTelefonicoFarmacia(recapitoTelefonicoFarmacia);
        this.setData(data.format(DateTimeFormatter.ofPattern("d/MM/uuuu")));
    }

    /**
     * Metodo per impostare i pulsanti {@code espandi} e {@code rimuovi} di una {@code EntryListaSegnalazioni} presente
     * nella {@code ListaSegnalazioni}
     * @param strumenti pannello che contiene i pulsanti
     */
    public void setStrumenti(FlowPane strumenti) {
        if(strumenti == null) {
            throw new NullPointerException("Strumenti = null");
        }
        this.strumenti = strumenti;
    }

    private void setIdFarmacia(int idFarmacia) {
        if(idFarmacia < 1) {
            throw new IllegalArgumentException("ID farmacia < 1");
        }
        this.idFarmacia = idFarmacia;
    }
    private void setRecapitoTelefonicoFarmacia(String recapitoTelefonicoFarmacia) {
        if(recapitoTelefonicoFarmacia == null) {
            throw new NullPointerException("Recapito telefonico della farmacia = null");
        }
        this.recapitoTelefonicoFarmacia = recapitoTelefonicoFarmacia;
    }

    /**
     * Setter per impostare il riepilogo dell'ordine associato alla segnalazione
     * @param riepilogoOrdine riepilogo dell'ordine
     */
    public void setRiepilogoOrdine(String riepilogoOrdine) {
        if(riepilogoOrdine == null) {
            throw new NullPointerException("Riepilogo dell'ordine = null");
        }
        this.riepilogoOrdine = riepilogoOrdine;
    }


    private void setNomeFarmacia(String nomeFarmacia) {
        if(nomeFarmacia == null) {
            throw new NullPointerException("Nome della farmacia = null");
        }
        this.nomeFarmacia = nomeFarmacia;
    }

    private void setData(String data) {
        if(data == null) {
            throw new NullPointerException("Data = null");
        }
        this.data = data;
    }

    /**
     * Getter per ottenere l'ID della segnalazione
     * @return ID della segnalazione
     */
    public int getIdSegnalazione() {
        return this.segnalazione.getIdSegnalazione();
    }

    /**
     * Getter per ottenere l'ID dell'ordine a cui fa riferimento la segnalazione
     * @return id dell'ordine
     */
    public int getIdOrdine() {
        return this.segnalazione.getRefOrdine();
    }

    /**
     * Getter per ottenere il nome della farmacia che ha effettuato la segnalazione
     * @return nome della farmacia
     */
    public String getNomeFarmacia() {
        return this.nomeFarmacia;
    }

    /**
     * Getter per ottenere la data in cui è stata creata la segnalazione
     * @return data della segnalazione
     */
    public String getData() {
        return this.data;
    }

    /**
     * Getter per ottenere i pulsanti Espandi e Rimuovi inerenti alla segnalazione
     * @return pulsanti Espandi e Rimuovi
     */
    public FlowPane getStrumenti() {
        return strumenti;
    }

    /**
     * Getter per ottenere un oggetto {@code Segnalazione} riferito a {@code EntryListaSegnalazioni}
     * @return segnalazione associata alla entry
     */
    public Segnalazione getSegnalazione() {
        return this.segnalazione;
    }

    /**
     * Getter per ottenere il riepilogo dell'ordine associato alla segnalazione
     * @return riepilogo dell'ordine
     */
    public String getRiepilogoOrdine() {
        return riepilogoOrdine;
    }

    /**
     * Getter per ottenere il recapito telefonico della farmacia che ha effettuato la segnalazione
     * @return recapito telefonico della farmacia
     */
    public String getRecapitoTelefonicoFarmacia() {
        return this.recapitoTelefonicoFarmacia;
    }
}
