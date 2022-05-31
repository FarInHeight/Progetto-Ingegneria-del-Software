package com.progetto.entity;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *  Classe che modella il concetto di {@code Ordine}
*/
public class Ordine {
    private int idOrdine;
    private int stato;
    private ArrayList<Farmaco> farmaci;
    private int tipo;
    private int periodo;
    private LocalDate dataConsegna;
    private String nomeFarmacia;
    private String indirizzoConsegna;
    private int idFarmacia;
    private ArrayList<LottoOrdinato> lottiContenuti;

    /**
     * Istanzia un oggetto di tipo {@code Ordine} dato in input l'id, lo stato, i farmaci contenuti, il tipo,
     * il periodo (se l'ordine è periodico), la data di consegna, il nome della farmacia che ha effettuato l'ordine,
     * l'indirizzo di consegna della farmacia
     * @param idOrdine id dell'ordine
     * @param stato stato dell'ordine (Elaborazione [1], Spedizione [2], Prenotato [3], Consegnato [4], Caricato [5])
     * @param farmaci farmaci contenuti nell'ordine
     * @param tipo tipo dell'ordine (periodico [1], non periodico [2])
     * @param periodo periodo che intercorre tra due ordini periodici successivi (solo se l'ordine è periodico)
     * @param dataConsegna data di consegna dell'ordine
     * @param nomeFarmacia nome della farmacia che ha effettuato l'ordine
     * @param indirizzoConsegna indirizzo di consegna della farmacia che ha effettuato l'ordine
     */
    public Ordine(int idOrdine, int stato, ArrayList<Farmaco> farmaci, int tipo, int periodo, LocalDate dataConsegna, String nomeFarmacia, String indirizzoConsegna) {
        this.setIdOrdine(idOrdine);
        this.setStato(stato);
        this.setFarmaci(farmaci);
        this.setTipo(tipo);
        this.setPeriodo(periodo);
        this.setDataConsegna(dataConsegna);
        this.setNomeFarmacia(nomeFarmacia);
        this.setIndirizzoConsegna(indirizzoConsegna);
    }

    /**
     * Istanzia un oggetto di tipo {@code Ordine} dato in input il risultato di una query sul database
     * @param ordine risultato della query composta da una sola tupla
     * @throws SQLException in caso di errore nel parsing tra tipo SQL e tipo JAVA viene lanciata un'eccezione
     */
    public Ordine(ResultSet ordine) throws SQLException {
        this.setIdOrdine(ordine.getInt("id_ordine"));
        this.setStato(ordine.getInt("stato"));
        this.setLottiContenuti(ordine);
        this.setTipo(ordine.getInt("tipo"));
        this.setPeriodo(ordine.getInt("periodo"));
        this.setDataConsegna(ordine.getDate("data_consegna"));
        this.setNomeFarmacia(ordine.getString("nome"));
        this.setIndirizzoConsegna(ordine.getString("indirizzo"));
        this.setIdFarmacia(ordine.getInt("id_farmacia"));
    }

    /**
     * Permette di settare l'ID dell'ordine
     * @param idOrdine id dell'ordine
     */
    public void setIdOrdine(int idOrdine) {
        if(idOrdine < 1) {
            throw new IllegalArgumentException("Id Ordine non valido");
        }
        this.idOrdine = idOrdine;
    }

    /**
     * Permette di settare lo stato dell'ordine
     * @param stato stato dell'ordine (Elaborazione [1], Spedizione [2], Prenotato [3], Consegnato [4], Caricato [5])
     */
    public void setStato(int stato) {
        if(stato < 1 || stato > 5) {
            throw new IllegalArgumentException("Nessuno stato delll'ordine corrispondente trovato");
        }
        this.stato = stato;
    }

    /**
     * Permette di settare la lista di farmaci contenuti nell'ordine
     * @param farmaci lista di farmaci
     */
    public void setFarmaci(ArrayList<Farmaco> farmaci) {
        if(farmaci == null) {
            throw new NullPointerException("ArrayList di Farmaci = null");
        }
        ArrayList<Farmaco> copiaFarmaci = new ArrayList<>();
        for(Farmaco farmaco : farmaci) {
            if(farmaco != null) {
                copiaFarmaci.add(farmaco);
            }
        }
        this.farmaci = copiaFarmaci;
    }

    /**
     * Permette di settare il tipo dell'ordine
     * @param tipo tipo dell'ordine (periodico [1], non periodico [2])
     */
    public void setTipo(int tipo) {
        if(tipo < 1 || tipo > 2) {
            throw new IllegalArgumentException("Nessun tipo dell'ordine corrispondente trovato");
        }
        this.tipo = tipo;
    }

    /**
     * Permette di settare il periodo dell'ordine.
     * @param periodo periodo dell'ordine
     */
    public void setPeriodo(int periodo) {
        if(periodo < 0) {
            throw new IllegalArgumentException("Periodo negativo non ammesso");
        }
        this.periodo = periodo;
    }

    /**
     * Permette di settare la data di consegna dell'ordine.
     * @param dataConsegna data di consegna
     */
    public void setDataConsegna(LocalDate dataConsegna) {
        if(dataConsegna == null) {
            throw new NullPointerException("Data di consegna = null");
        }
        this.dataConsegna = dataConsegna;
    }

    /**
     * Permette di settare la data di consegna dell'ordine.
     * @param dataConsegna data di consegna
     */
    public void setDataConsegna(Date dataConsegna) {
        if(dataConsegna == null) {
            this.dataConsegna = null;
        } else {
            this.dataConsegna = dataConsegna.toLocalDate();
        }
    }


    /**
     * Permette di settare il nome della farmacia che ha effettuato l'ordine.
     * @param nomeFarmacia nome della farmacia
     */
    public void setNomeFarmacia(String nomeFarmacia) {
        if(nomeFarmacia == null) {
            throw new NullPointerException("Nome della Farmacia = null");
        }
        this.nomeFarmacia = nomeFarmacia;
    }

    /**
     * Permette di settare l'indirizzo di consegna dell'ordine.
     * @param indirizzoConsegna indirizzo di consegna
     */
    public void setIndirizzoConsegna(String indirizzoConsegna) {
        if(indirizzoConsegna == null) {
            throw new NullPointerException("Indirizzo di Consegna = null");
        }
        this.indirizzoConsegna = indirizzoConsegna;
    }

    /**
     * Permette di settare l'id della farmacia che ha generato l'ordine
     * @param idFarmacia id della farmacia che ha generato l'ordine
     */
    public void setIdFarmacia(int idFarmacia) {
        if (idFarmacia < 0){
            throw new IllegalArgumentException("idFarmacia < 0");
        }
        this.idFarmacia = idFarmacia;
    }

    /**
     * Permette di settare i Lotti associati ad un Ordine
     * @param ordine tupla contenente informazioni su un Lotto associato ad un Ordine
     * @throws SQLException in caso di errore nel parsing tra tipo SQL e tipo JAVA viene lanciata un'eccezione
     */
    public void setLottiContenuti(ResultSet ordine) throws SQLException {
        ArrayList<LottoOrdinato> lottiOrdinati = new ArrayList<>();
        lottiOrdinati.add(new LottoOrdinato(ordine));
        this.lottiContenuti = lottiOrdinati;
    }

    /**
     * Ritorna l'ID dell'ordine.
     * @return {@code int} contenente l'ID dell'ordine
     */
    public int getIdOrdine() {
        return idOrdine;
    }

    /**
     * Ritorna lo stato dell'ordine
     * @return {@code int} contenente lo stato dell'ordine (Elaborazione [1], Spedizione [2], Prenotato [3], Consegnato [4], Caricato [5])
     */
    public int getStato() {
        return this.stato;
    }

    /**
     * Ritorna lo stato dell'ordine
     * @return oggetto di tipo {@code String} contenente lo stato dell'ordine
     */
    public String getStatoStringa() {
        /*
            1 - Elaborazione
            2 - Spedizione
            3 - Prenotato
            4 - Consegnato
            5 - Caricato
         */
        switch (this.stato) {
            case 1: return "Elaborazione";
            case 2: return "Spedizione";
            case 3: return "Prenotato";
            case 4: return "Consegnato";
            case 5: return "Caricato";
            default: return "Stato non trovato";
        }
    }

    /**
     * Ritorna la lista di farmaci contenuti nell'ordine.
     * @return oggetto di tipo {@code ArrayList<Farmaco>} contenente i farmaci
     */
    public ArrayList<Farmaco> getFarmaci() {
        return this.farmaci;
    }

    /**
     * Ritorna il tipo dell'ordine come intero.
     * @return {@code int} contenente il tipo dell'ordine
     */
    public int getTipo() {
        return this.tipo;
    }

    /**
     * Ritorna il tipo dell'ordine come stringa.
     * @return oggetto di tipo {@code String} contenente il tipo dell'ordine
     */
    public String getTipoStringa() {
        /*
            1 - periodico
            2 - non periodico
         */
        switch (this.tipo) {
            case 1: return "Periodico";
            case 2: return "Non periodico";
            default: return "Tipo non trovato";
        }
    }

    /**
     * Ritorna il periodo dell'ordine.
     * @return {@code int} contenente il periodo dell'ordine
     */
    public int getPeriodo() {
        return periodo;
    }

    /**
     * Ritorna la data di consegna dell'ordine
     * @return oggetto di tipo {@code LocalDate} contenente la data di consegna
     */
    public LocalDate getDataConsegna() {
        return dataConsegna;
    }

    /**
     * Ritorna il nome della farmacia che ha effettuato l'ordine.
     * @return oggetto di tipo {@code String} contenente il nome della farmacia
     */
    public String getNomeFarmacia() {
        return nomeFarmacia;
    }

    /**
     * Ritorna l'indirizzo di consegna dell'ordine
     * @return oggetto di tipo {@code String} contenente l'indirizzo di consegna
     */
    public String getIndirizzoConsegna() {
        return indirizzoConsegna;
    }

    /**
     * Ritorna l'id della farmacia che ha generato l'ordine
     * @return {@code int} contenente l'id della farmacia che ha generato l'ordine
     */
    public int getIdFarmacia() {
        return idFarmacia;
    }

    /**
     * Ritorna i Lotti associati in un Ordine
     * @return oggetto di tipo {@code ArrayList<LottoOrdinato>} contenente tutti i Lotti associati all'ordine
     */
    public ArrayList<LottoOrdinato> getLottiContenuti() {
        return lottiContenuti;
    }

    /**
     * Aggiunge un Lotto all'insieme di Lotti assocaiti ad un Ordine
     * @param lotto Lotto da aggiungere all'Ordine
     */
    public void addLotto(LottoOrdinato lotto) {
        this.lottiContenuti.add(lotto);
    }
}
