package com.progetto.addetto.segnalazioni;

import com.progetto.entity.AddettoAzienda;

public class VisualizzaSegnalazioniControl {

    private AddettoAzienda addetto;

    /**
     * Costruttore della classe {@code VisualizzaSegnalazioniControl} che prende in input un oggetto di classe {@code AddettoAzienda}
     * @param addetto addetto dell'azienda
     */
    public VisualizzaSegnalazioniControl(AddettoAzienda addetto) {
        this.setAddettoAzienda(addetto);
        this.start();
    }

    private void setAddettoAzienda(AddettoAzienda addetto) {
        if(addetto == null) {
            throw new NullPointerException("Addetto dell'azienda = null");
        }
        this.addetto = addetto;
    }

    private void start() {

    }
}
