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
     * @param event evento associato alla pressione del {@code button} visualizza spedizioni
     */
    public CreaListaSpedizioniControl(Event event) {
        this.setStage((Stage) ((Node) event.getSource()).getScene().getWindow());
        this.db = new InterfacciaCorriere();
    }

    /**
     * Setter per le spedizioni da inserire nella lista
     * @param spedizioni spedizioni da inserire nella lista
     */
    public void setSpedizioni(ArrayList<EntryListaSpedizioni> spedizioni) {
        this.spedizioni = spedizioni;
    }

    /**
     * Setter per lo stage corrente
     * @param stage stage corrente
     */
    private void setStage(Stage stage) {
        if (stage == null) {
            throw new NullPointerException("stage = null");
        }
        this.stage = stage;
    }

    /**
     * Getter per le spedizioni da inserire nella lista
     * @return spedizioni
     */
    public ArrayList<EntryListaSpedizioni> getSpedizioni() {
        return spedizioni;
    }

    /**
     * Getter per lo stage corrente
     * @return stage corrente
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Metodo che crea e mostra la lista delle spedizioni tramite un oggetto di tipo {@code ListaSpedizioni}
     * @throws IOException lanciata se il caricamento del file fxml non è andato a buon fine
     */
    public void clickSuVisualizzaSpedizioni() throws IOException {

        //Ottengo le entry della lista
        setSpedizioni(db.getOrdiniGiornalieri());
        //Associo alle entry i pulsanti
        for (EntryListaSpedizioni spedizione : spedizioni) {
            this.setPulsanti(spedizione);
        }
        //Creo la lista
        ListaSpedizioni listaSpedizioni = new ListaSpedizioni(spedizioni, this);
        //Metto gli ordini in stato di spedizione
        for (EntryListaSpedizioni spedizione : spedizioni) {
            db.modificaStatoInSpedizione(spedizione.getOrdine().getIdOrdine());
        }
        //Modifico la schermata corrente
        this.stage.hide();
        listaSpedizioni.start(this.stage);
    }

    /**
     * Metodo che crea e associa i pulsanti alle entry della lista spedizioni
     * @param spedizione entry della lista spedizioni
     */
    private void setPulsanti(EntryListaSpedizioni spedizione) {
        Button consegna = new Button("consegna");
        consegna.setBackground(Background.fill(Color.rgb(0, 0, 200)));
        consegna.setStyle("-fx-text-fill: white");
        spedizione.setStrumenti(consegna);
    }

    /**
     * Permette di tornare indietro e visualizzare la {@code SchermataPrincipaleCorriere}
     * Gli {@code ordini} non consegnati tornano in stato di elaborazione
     * @param substage stage attualmente mostrato
     */
    public void clickSuIndietro(Stage substage) {
        substage.close();
        for (EntryListaSpedizioni spedizione : spedizioni) {
            db.modificaStatoInElaborazione(spedizione.getOrdine().getIdOrdine());
        }
        this.stage.show();
    }
}
