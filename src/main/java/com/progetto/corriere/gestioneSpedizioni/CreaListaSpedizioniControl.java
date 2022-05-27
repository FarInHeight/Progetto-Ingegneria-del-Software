package com.progetto.corriere.gestioneSpedizioni;

import com.progetto.entity.EntryListaSpedizioni;
import com.progetto.interfacciaDatabase.InterfacciaCorriere;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Crea la lista delle spedizioni odierne in base alle informazioni contenute nel database
 */
public class CreaListaSpedizioniControl {

    private ArrayList<EntryListaSpedizioni> spedizioni;
    private Stage stage;

    private final InterfacciaCorriere db;

    /**
     * istanzia la control che gestisce la lista spedizioni
     * @param event
     */
    public CreaListaSpedizioniControl(Event event) {
        this.setStage((Stage) ((Node) event.getSource()).getScene().getWindow());
        this.db = new InterfacciaCorriere();
    }

    public void setSpedizioni(ArrayList<EntryListaSpedizioni> spedizioni) {
        this.spedizioni = spedizioni;
    }

    private void setStage(Stage stage) {
        if (stage == null) {
            throw new NullPointerException("stage = null");
        }
        this.stage = stage;
    }

    public ArrayList<EntryListaSpedizioni> getSpedizioni() {
        return spedizioni;
    }

    public Stage getStage() {
        return stage;
    }

    public void creaLista() throws IOException {
        setSpedizioni(db.getOrdiniGiornalieri());
        for (EntryListaSpedizioni spedizione : spedizioni) {
            this.setPulsanti(spedizione);
        }
        ListaSpedizioni listaSpedizioni = new ListaSpedizioni(spedizioni, this);
        for (EntryListaSpedizioni spedizione : spedizioni) {
            db.modificaStatoInSpedizione(spedizione.getOrdine().getIdOrdine());
        }
        this.stage.hide();
        listaSpedizioni.start(this.stage);
    }

    private void setPulsanti(EntryListaSpedizioni spedizione) {
        Button consegna = new Button("consegna");
        consegna.setBackground(Background.fill(Color.rgb(0, 0, 200)));
        consegna.setStyle("-fx-text-fill: white");
        spedizione.setStrumenti(consegna);
    }

    public void clickSuIndietro(Stage substage) {
        substage.close();
        for (EntryListaSpedizioni spedizione : spedizioni) {
            db.modificaStatoInElaborazione(spedizione.getOrdine().getIdOrdine());
        }
        this.stage.show();
    }
}
