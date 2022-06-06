package com.progetto.addetto.segnalazioni;

import com.progetto.entity.Segnalazione;
import javafx.scene.layout.FlowPane;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Classe che modella una entry dalla {@code ListaSegnalazioni}
 */
public class EntryListaSegnalazioni {
    @SuppressWarnings("FieldMayBeFinal")
    private Segnalazione segnalazione;
    private String riepilogoOrdine;
    private int idFarmacia;
    private String recapitoTelefonicoFarmacia;
    private String nomeFarmacia;
    private String data;
    private FlowPane strumenti;

    /**
     * Istanzia un oggetto di tipo {@code EntryListaSegnalazioni} dati in input l'id della segnalazione, l'id dell'ordine,
     * il riepilogo dell'ordine associato alla segnalazione, l'id della farmacia che ha generato la segnalazione,
     * il nome della farmacia, il recapito telefonico della farmacia, il commento del farmacista e la data di generazione
     * della segnalazione
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
     * Permette di settare i pulsanti {@code espandi} e {@code rimuovi} di una {@code EntryListaSegnalazioni} presente
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
     * Permette di settare il riepilogo dell'ordine associato alla segnalazione
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
     * Ritorna l'ID della segnalazione
     * @return {@code int} contenente l'ID della segnalazione
     */
    public int getIdSegnalazione() {
        return this.segnalazione.getIdSegnalazione();
    }

    /**
     * Ritorna l'ID dell'ordine a cui fa riferimento la segnalazione
     * @return {@code int} contenente l'id dell'ordine
     */
    public int getIdOrdine() {
        return this.segnalazione.getRefOrdine();
    }

    /**
     * Ritorna il nome della farmacia che ha effettuato la segnalazione
     * @return un oggetto di tipo {@code String} contenente il nome della farmacia
     */
    public String getNomeFarmacia() {
        return this.nomeFarmacia;
    }

    /**
     * Ritorna la data in cui è stata creata la segnalazione
     * @return un oggetto di tipo {@code LocalDate} contenente la data della segnalazione
     */
    public String getData() {
        return this.data;
    }

    /**
     * Ritorna i pulsanti Espandi e Rimuovi inerenti alla segnalazione
     * @return oggetto di tipo {@code FlowPane} contenente i pulsanti Espandi e Rimuovi
     */
    public FlowPane getStrumenti() {
        return strumenti;
    }

    /**
     * Ritorna la segnalazione associata alla entry
     * @return un oggetto di tipo {@code Segnalazione} contenente la segnalazione associata alla entry
     */
    public Segnalazione getSegnalazione() {
        return this.segnalazione;
    }

    /**
     * Ritorna il riepilogo dell'ordine associato alla segnalazione
     * @return un oggetto di tipo {@code String} contenente il riepilogo dell'ordine
     */
    public String getRiepilogoOrdine() {
        return riepilogoOrdine;
    }

    /**
     * Ritorna il recapito telefonico della farmacia che ha effettuato la segnalazione
     * @return un oggetto di tipo {@code String} contenente il recapito telefonico della farmacia
     */
    public String getRecapitoTelefonicoFarmacia() {
        return this.recapitoTelefonicoFarmacia;
    }

    /**
     * Ritorna l'ID della farmacia che ha effettuato la segnalazione
     * @return {@code int} contenente l'id della farmacia
     */
    public int getIdFarmacia() {
        return idFarmacia;
    }
}
