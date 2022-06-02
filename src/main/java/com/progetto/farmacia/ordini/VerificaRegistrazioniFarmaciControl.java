package com.progetto.farmacia.ordini;

import com.progetto.entity.EntryListaOrdini;
import com.progetto.entity.Farmacia;
import com.progetto.interfacciaDatabase.InterfacciaFarmacia;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Classe che modella la control {@code VerificaRegistrazioniFarmaciControl} che si occupa di gestire la registrazione
 * dei farmaci ricevuti dalla farmacia
 */
public class VerificaRegistrazioniFarmaciControl {
    private Farmacia farmacia;
    private boolean done;

    private InterfacciaFarmacia db;
    private ArrayList<EntryListaOrdini> lista;
    private ListaOrdini listaOrdini;
    /**
     * Istanzia un oggetto di tipo {@code VerificaRegistrazioniFarmaciControl} dato in input la farmacia
     * a cui fa riferimento
     * @param farmacia farmacia per cui bisogna controllare gli ordini consegnati ma non caricati
     */
    public VerificaRegistrazioniFarmaciControl(Farmacia farmacia) {
        this.setFarmacia(farmacia);
        this.setInterfacciaFarmacia(new InterfacciaFarmacia());
        this.done = false;
    }
    private void setFarmacia(Farmacia farmacia) {
        if(farmacia == null){
            throw new NullPointerException("Farmacia in Rimuovi Farmaci Scaduti Control = null");
        }
        this.farmacia = farmacia;
    }

    private void setInterfacciaFarmacia(InterfacciaFarmacia db) {
        if(db == null){
            throw new NullPointerException("Interfaccia Farmacia = null");
        }
        this.db = db;
    }

    /**
     * Permette di avviare la control che gestisce la registrazione dei farmaci
     */
    public void start() {
        this.checkPerContinuare();
        if( ! this.done ) {
            AvvisoMancataRegistrazione avviso = new AvvisoMancataRegistrazione(this);
            try {
                avviso.start(new Stage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Metodo tramite il quale un oggetto di tipo {@code AvvisoMancataRegistrazione} avvisa la {@code VerificaRegistrazioniFarmaciControl}
     * del click sul pulsante {@code continua} e distrugge la {@code AvvisoMancataRegistrazione}.
     * Il metodo è stato creato senza modificatore di visibilità affinché possa essere invocato soltanto da classi
     * che si trovano nello stesso package.
     * @param stage stage dell' avviso {@code AvvisoMancataRegistrazione}
     */
    void clickSuContinua(Stage stage) {
        stage.close();
        for(EntryListaOrdini entry : this.lista) {
            Button carica = new Button("CARICA");
            carica.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    VerificaRegistrazioniFarmaciControl.this.clickSuCarica(entry);
                }
            });
            carica.setBackground(Background.fill(Color.rgb(38, 180, 27)));
            carica.setStyle("-fx-text-fill: white");
            carica.setPrefWidth(80);
            FlowPane flow = new FlowPane();
            flow.getChildren().addAll(carica);
            flow.setAlignment(Pos.CENTER);
            flow.setHgap(10);
            flow.setVgap(10);
            entry.setStrumenti(flow);
        }
        this.listaOrdini = new ListaOrdini(this.farmacia, this.lista, this);
        try {
            this.listaOrdini.start(new Stage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metodo tramite il quale un oggetto di tipo {@code ListaOrdini} avvisa la {@code VerificaRegistrazioniFarmaciControl}
     * del click sul pulsante {@code fatto} e distrugge la {@code ListaOrdini}.
     * Il metodo è stato creato senza modificatore di visibilità affinché possa essere invocato soltanto da classi
     * che si trovano nello stesso package.
     * @param stage stage dell'avviso {@code AvvisoMancataRegistrazione}
     */
    void clickSuFatto(Stage stage) {
        stage.close();
        this.start();
    }
    @SuppressWarnings("RedundantIfStatement")
    private void checkPerContinuare() {
        this.lista = this.db.getOrdiniNonCaricati(this.farmacia.getIdFarmacia());
        if( ! lista.isEmpty() ) {
            this.done = false;
        } else {
            this.done = true;
        }
    }

    /**
     * Metodo tramite il quale un oggetto di tipo {@code ListaOrdini} avvisa la {@code VerificaRegistrazioniFarmaciControl}
     * del click sul pulsante {@code carica} e distrugge la {@code ListaOrdini}.
     * Il metodo è stato creato senza modificatore di visibilità affinché possa essere invocato soltanto da classi
     * che si trovano nello stesso package.
     * @param entry entry sulla quale si preme il pulsante
     */
    void clickSuCarica(EntryListaOrdini entry) {
        this.listaOrdini.caricaOrdine(entry);
    }
}
