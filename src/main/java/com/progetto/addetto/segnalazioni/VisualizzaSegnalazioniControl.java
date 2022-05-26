package com.progetto.addetto.segnalazioni;

import com.progetto.entity.AddettoAzienda;
import javafx.event.ActionEvent;

public class VisualizzaSegnalazioniControl {

    private AddettoAzienda addetto;

    private ActionEvent event;

    /**
     * Costruttore della classe {@code VisualizzaSegnalazioniControl} che prende in input un oggetto di classe {@code AddettoAzienda}
     * @param addetto addetto dell'azienda
     */
    public VisualizzaSegnalazioniControl(AddettoAzienda addetto, ActionEvent event) {
        this.setAddettoAzienda(addetto);
        this.setEvent(event);
        this.start();
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

    }
}
