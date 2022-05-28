package com.progetto.entity;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *  Classe che modella il concetto di {@code Ordine} nel Sistema.
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
     * Costruttore di un {@code Ordine}.
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
     * Costruttore che genera un Ordine a partire dal risultato di una query sul database
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
     * Setter per impostare l'ID dell'ordine
     * @param idOrdine id dell'ordine
     */
    public void setIdOrdine(int idOrdine) {
        if(idOrdine < 1) {
            throw new IllegalArgumentException("Id Ordine non valido");
        }
        this.idOrdine = idOrdine;
    }

    /**
     * Setter per impostare lo stato dell'ordine (Elaborazione [1], Spedizione [2], Prenotato [3], Consegnato [4], Caricato [5]).
     * @param stato stato dell'ordine
     */
    public void setStato(int stato) {
        if(stato < 1 || stato > 5) {
            throw new IllegalArgumentException("Nessuno stato delll'ordine corrispondente trovato");
        }
        this.stato = stato;
    }

    /**
     * Setter per impostare la lista di farmaci contenuti nell'ordine
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
     * Setter per impostare il tipo dell'ordine (periodico [1], non periodico [2]).
     * @param tipo tipo dell'ordine
     */
    public void setTipo(int tipo) {
        if(tipo < 1 || tipo > 2) {
            throw new IllegalArgumentException("Nessun tipo dell'ordine corrispondente trovato");
        }
        this.tipo = tipo;
    }

    /**
     * Setter per impostare il periodo dell'ordine.
     * @param periodo periodo dell'ordine
     */
    public void setPeriodo(int periodo) {
        if(periodo < 0) {
            throw new IllegalArgumentException("Periodo negativo non ammesso");
        }
        this.periodo = periodo;
    }

    /**
     * Setter per impostare la data di consegna dell'ordine.
     * @param dataConsegna data di consegna
     */
    public void setDataConsegna(LocalDate dataConsegna) {
        if(dataConsegna == null) {
            throw new NullPointerException("Data di consegna = null");
        }
        this.dataConsegna = dataConsegna;
    }

    /**
     * Setter per impostare la data di consegna dell'ordine.
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
     * Setter per impostare il nome della farmacia che ha effettuato l'ordine.
     * @param nomeFarmacia nome della farmacia
     */
    public void setNomeFarmacia(String nomeFarmacia) {
        if(nomeFarmacia == null) {
            throw new NullPointerException("Nome della Farmacia = null");
        }
        this.nomeFarmacia = nomeFarmacia;
    }

    /**
     * Setter per impostare l'indirizzo di consegna dell'ordine.
     * @param indirizzoConsegna indirizzo di consegna
     */
    public void setIndirizzoConsegna(String indirizzoConsegna) {
        if(indirizzoConsegna == null) {
            throw new NullPointerException("Indirizzo di Consegna = null");
        }
        this.indirizzoConsegna = indirizzoConsegna;
    }

    /**
     * Setter per l'id della farmacia che ha generato l'ordine
     * @param idFarmacia id della farmacia che ha generato l'ordine
     */
    public void setIdFarmacia(int idFarmacia) {
        if (idFarmacia < 0){
            throw new IllegalArgumentException("idFarmacia < 0");
        }
        this.idFarmacia = idFarmacia;
    }

    /**
     * Setter per impostare i Lotti associati ad un Ordine
     * @param ordine tupla contenente informazioni su un Lotto associato ad un Ordine
     * @throws SQLException in caso di errore nel parsing tra tipo SQL e tipo JAVA viene lanciata un'eccezione
     */
    public void setLottiContenuti(ResultSet ordine) throws SQLException {
        ArrayList<LottoOrdinato> lottiOrdinati = new ArrayList<>();
        lottiOrdinati.add(new LottoOrdinato(ordine));
        this.lottiContenuti = lottiOrdinati;
    }

    /**
     * Getter per ottenere l'ID dell'ordine.
     * @return ID dell'ordine
     */
    public int getIdOrdine() {
        return idOrdine;
    }

    /**
     * Getter per ottenere lo stato dell'ordine come intero (Elaborazione [1], Spedizione [2], Prenotato [3], Consegnato [4], Caricato [5]).
     * @return stato dell'ordine
     */
    public int getStato() {
        return this.stato;
    }

    /**
     * Getter per ottenere lo stato dell'ordine come stringa (Elaborazione [1], Spedizione [2], Prenotato [3], Consegnato [4], Caricato [5]).
     * @return stato dell'ordine
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
     * Getter per ottenere la lista di farmaci contenuti nell'ordine.
     * @return lista di farmaci
     */
    public ArrayList<Farmaco> getFarmaci() {
        return this.farmaci;
    }

    /**
     * Getter per ottenere il tipo dell'ordine come intero.
     * @return tipo dell'ordine
     */
    public int getTipo() {
        return this.tipo;
    }

    /**
     * Getter per ottenere il tipo dell'ordine come stringa.
     * @return tipo dell'ordine
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
     * Getter per ottenere il periodo dell'ordine.
     * @return periodo dell'ordine
     */
    public int getPeriodo() {
        return periodo;
    }

    /**
     * Getter per ottenere la data di consegna dell'ordine
     * @return data di consegna
     */
    public LocalDate getDataConsegna() {
        return dataConsegna;
    }

    /**
     * Getter per ottenere il nome della farmacia che ha effettuato l'ordine.
     * @return nome della farmacia
     */
    public String getNomeFarmacia() {
        return nomeFarmacia;
    }

    /**
     * Getter per ottenere l'indirizzo di consegna dell'ordine
     * @return  indirizzo di consegna
     */
    public String getIndirizzoConsegna() {
        return indirizzoConsegna;
    }

    /**
     * Getter per l'id della farmacia che ha generato l'ordine
     * @return id della farmacia che ha generato l'ordine
     */
    public int getIdFarmacia() {
        return idFarmacia;
    }

    /**
     * Getter per i Lotti associati in un Ordine
     * @return tutti i Lotti associati
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
