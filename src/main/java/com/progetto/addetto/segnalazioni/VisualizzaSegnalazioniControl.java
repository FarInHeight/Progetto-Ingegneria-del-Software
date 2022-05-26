package com.progetto.addetto.segnalazioni;

import com.progetto.entity.AddettoAzienda;
import com.progetto.entity.EntryListaSegnalazioni;
import com.progetto.interfacciaDatabase.InterfacciaAddetto;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class VisualizzaSegnalazioniControl {

    private AddettoAzienda addetto;

    // evento associato al click sul pulsante visualizzaSegnalazioni
    private ActionEvent event;

    // stage associato all'evento
    private Stage stage;

    /**
     * Costruttore della classe {@code VisualizzaSegnalazioniControl} che prende in input un oggetto di classe {@code AddettoAzienda}
     * e l'evento associato al click sul pulsante {@code visualizzaSegnalazioni}
     * @param addetto addetto dell'azienda
     * @param event evento di click
     */
    public VisualizzaSegnalazioniControl(AddettoAzienda addetto, ActionEvent event) {
        this.setAddettoAzienda(addetto);
        this.setEvent(event);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();  // prendo lo stage corrente
        this.setStage(stage);
        this.start();
    }

    private void setStage(Stage stage) {
        if(stage == null) {
            throw new NullPointerException("Stage della Schermata Principale Addetto Azienda = null");
        }
        this.stage = stage;
    }
    private void setAddettoAzienda(AddettoAzienda addetto) {
        if(addetto == null) {
            throw new NullPointerException("Addetto dell'azienda = null");
        }
        this.addetto = addetto;
    }

    private void setEvent(ActionEvent event) {
        if(event == null) {
            throw new NullPointerException("Event = null");
        }
        this.event = event;
    }

    private void start() {
        InterfacciaAddetto db = new InterfacciaAddetto();
        ArrayList<EntryListaSegnalazioni> segnalazioni = db.getSegnalazioni();
        this.stage.hide();  // nascondo lo stage attuale riferito alla schermata principale di addetto
        ListaSegnalazioni lista = new ListaSegnalazioni(this.addetto, segnalazioni, this);
        try {
            lista.start(this.stage);
        } catch(IOException exc) {
            exc.printStackTrace();
        }
    }

    /**
     * Metodo tramite il quale un oggetto di tipo {@code ListaSegnalazioni} avvisa la {@code VisualizzaSegnalazioniControl}
     * del click sul pulsante {@code indietro} e distrugge la ListaSegnalazioni.
     * Il metood è stato creato senza
     * @param substage sotto-stage della ListaSegnalazionio da distuggere
     */
    void clickSuIndietro(Stage substage) {
        substage.close();
        this.stage.show();
    }
}
