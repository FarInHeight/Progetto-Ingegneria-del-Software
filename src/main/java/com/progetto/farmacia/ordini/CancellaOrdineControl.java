package com.progetto.farmacia.ordini;

import com.progetto.entity.EntryFormOrdine;
import com.progetto.entity.EntryListaOrdini;
import com.progetto.interfacciaDatabase.InterfacciaFarmacia;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Modella la control {@code CancellaOrdineControl} che si occupa di cancellare gli ordini
 */
public class CancellaOrdineControl {

    private EntryListaOrdini entry;
    private Stage stage;  //stage di lista ordini

    /**
     * Istanzia un oggetto di tipo {@code CancellaOrdineControl} dato in input la entry di lista ordini da eliminare
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

    /**
     * Permette di avviare la control che si occupa di cancellare l'ordine
     * @throws IOException se il caricamento del file {@code fxml} della schermata non è andato a buon fine
     */
    public void start() throws IOException {
        InterfacciaFarmacia db = new InterfacciaFarmacia();
        db.modificaFarmaciOrdinati(this.entry.getIdOrdine());
        db.cancellaOrdine(this.entry.getIdOrdine());
        MessaggioConfermaEliminazioneOrdine confermaEliminazione = new MessaggioConfermaEliminazioneOrdine();
        confermaEliminazione.start(this.stage);
        ListaOrdini.getOrdini().remove(entry);
    }
}
