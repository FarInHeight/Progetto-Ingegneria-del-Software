package com.progetto.entity;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EntryListaSegnalazioni {
    private int idSegnalazione;
    private int idOrdine;
    private String nomeFarmacia;
    private String data;
    private FlowPane strumenti;

    /**
     * Costruttore per istanziare una entry della {code ListaSegnalazioni}
     * @param idSegnalazione id della segnalazione
     * @param idOrdine id dell'ordine per cui è stata generata la segnalazione
     * @param nomeFarmacia nome della farmacia che ha effettuato la segnalazione
     * @param data data in cui è stata generata segnalazione
     */
    public EntryListaSegnalazioni(int idSegnalazione, int idOrdine, String nomeFarmacia, LocalDate data) {
        this.setIdSegnalazione(idSegnalazione);
        this.setIdOrdine(idOrdine);
        this.setNomeFarmacia(nomeFarmacia);
        this.setData(data.format(DateTimeFormatter.ofPattern("d/MM/uuuu")));
        Button espandi = new Button("Espandi");
        espandi.setBackground(Background.fill(Color.rgb(38, 189, 27)));
        espandi.setStyle("-fx-text-fill: white");
        Button rimuovi = new Button("Rimuovi");
        rimuovi.setBackground(Background.fill(Color.rgb(255, 79, 66)));
        rimuovi.setStyle("-fx-text-fill: white");
        FlowPane flow = new FlowPane();
        flow.getChildren().addAll(espandi, rimuovi);
        flow.setAlignment(Pos.CENTER);
        flow.setHgap(10); // dae8fc
        this.strumenti = flow;
    }

    private void setIdSegnalazione(int idSegnalazione) {
        if(idSegnalazione < 1) {
            throw new IllegalArgumentException("L'ID della segnalazione non può assumere valori <= 1");
        }
        this.idSegnalazione = idSegnalazione;
    }

    private void setIdOrdine(int idOrdine) {
        if(idOrdine < 1) {
            throw new IllegalArgumentException("L'ID dell'ordine non può assumere valori <= 1");
        }
        this.idOrdine = idOrdine;
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

    public int getIdSegnalazione() {
        return idSegnalazione;
    }

    public int getIdOrdine() {
        return idOrdine;
    }

    public String getNomeFarmacia() {
        return nomeFarmacia;
    }

    public String getData() {
        return data;
    }

    public FlowPane getStrumenti() {
        return strumenti;
    }
}
