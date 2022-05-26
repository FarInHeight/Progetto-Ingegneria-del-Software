package com.progetto.corriere.gestioneSpedizioni;

import com.progetto.entity.EntryListaSpedizioni;
import com.progetto.interfacciaDatabase.InterfacciaCorriere;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Crea la lista delle spedizioni odierne in base alle informazioni contenute nel database
 */
public class CreaListaSpedizioniControl {

    private ArrayList<EntryListaSpedizioni> spedizioni;
    private Stage stage;
    public CreaListaSpedizioniControl(Event event) {
        this.setStage((Stage) ((Node) event.getSource()).getScene().getWindow());
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

    public void creaLista() throws IOException {
        InterfacciaCorriere db = new InterfacciaCorriere();
        setSpedizioni(db.getOrdiniGiornalieri());
        ListaSpedizioni listaSpedizioni = new ListaSpedizioni(spedizioni, this);
        /*for (EntryListaSpedizioni spedizione : spedizioni) {
            db.modificaStatoInSpedizione(spedizione.getOrdine().getIdOrdine());
        }*/
        this.stage.hide();
        listaSpedizioni.start(this.stage);
    }

    public void clickSuIndietro(Stage substage) {
        substage.close();
        this.stage.show();
    }
}
