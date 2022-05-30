package com.progetto.farmacia.ordini;

import com.progetto.entity.EntryFormOrdine;
import com.progetto.entity.EntryListaOrdini;
import com.progetto.interfacciaDatabase.InterfacciaFarmacia;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * rappresenta la control che si occupa di cancellare gli ordini
 */
public class CancellaOrdineControl {

    private EntryListaOrdini entry;
    private Stage stage;  //stage di lista ordini

    /**
     * costruisce un oggetto {@code CancellaOrdineControl} dato in input la entry di lista ordini da eliminare
     * @param entry entry di lista ordini da eliminare
     */
    public CancellaOrdineControl(EntryListaOrdini entry, Stage stage){
        this.setEntry(entry);
        this.setStage(stage);
    }

    private void setStage(Stage stage) {
        if(stage == null){
            throw new NullPointerException("stage = null");
        }
        this.stage = stage;
    }

    private void setEntry(EntryListaOrdini entry){
        if(entry == null){
            throw new NullPointerException("entry di lsita ordini = null");
        }
        this.entry = entry;
    }

    public void start() throws IOException {
        InterfacciaFarmacia db = new InterfacciaFarmacia();
        db.modificaFarmaciOrdinati(this.entry);
        db.cancellaOrdine(this.entry);
        MessaggioConfermaEliminazioneOrdine confermaEliminazione = new MessaggioConfermaEliminazioneOrdine();
        confermaEliminazione.start(this.stage);
        ListaOrdini.getOrdini().remove(entry);
    }
}
