package com.progetto.corriere.gestioneSpedizioni;

import com.progetto.entity.LottoOrdinato;
import com.progetto.interfacciaDatabase.InterfacciaCorriere;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Classe che modella la control {@code ConfermaRicezioneSpedizioneControl} responsabile di gestie la consegna degli ordini al farmacista.
 */
public class ConfermaRicezioneSpedizioneControl {

    private Stage stage;
    private EntryListaSpedizioni spedizione;
    private ListaSpedizioni listaSpedizioni;

    /**
     * Istanzia un oggetto di tipo {@code ConfermaRicezioneSpedizioneControl}
     * @param stage oggetto di tipo {@code Stage} relativo alla schermata attuale
     * @param spedizione oggetto di tipo {@code EntryListaSpedizioni} relativo alla spedizione per cui mostrare il riepilogo
     * @param listaSpedizioni oggetto di tipo {@code ListaSpedizioni} che contiene la lista spedizioni
     */
    public ConfermaRicezioneSpedizioneControl(Stage stage, EntryListaSpedizioni spedizione, ListaSpedizioni listaSpedizioni) {
        setStage(stage);
        setSpedizione(spedizione);
        setListaSpedizioni(listaSpedizioni);
    }

    /**
     * Ritorna la lista spedizioni
     * @return oggetto di tipo {@code ListaSpedizioni } contenente la lista spedizioni
     */
    public ListaSpedizioni getListaSpedizioni() {
        return listaSpedizioni;
    }

    /**
     * Permette di settare la lista delle spedizioni
     * @param listaSpedizioni oggetto di tipo {@code ListaSpedizioni} contenente la lista spedizioni
     */
    public void setListaSpedizioni(ListaSpedizioni listaSpedizioni) {
        if (listaSpedizioni == null) {
            throw new NullPointerException("listaSpedizioni = null");
        }
        this.listaSpedizioni = listaSpedizioni;
    }

    /**
     * Ritorna il riferimento alla schermata corrente
     * @return oggetto di tipo {@code Stage} relativo alla schermata corrente
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Permette di settare lo schermata corrente
     * @param stage oggetto di tipo {@code Stage} relativo alla schermata corrente
     */
    public void setStage(Stage stage) {
        if (stage == null) {
            throw new NullPointerException("stage = null");
        }
        this.stage = stage;
    }

    /**
     * Ritorna la spedizione da consegnare
     * @return oggetto di tipo {@code EntryListaSpedizioni} relativo alla spedizione da consegnare
     */
    public EntryListaSpedizioni getSpedizione() {
        return spedizione;
    }

    /**
     * Permette di settare la spedizione da consegnare
     * @param spedizione oggetto di tipo {@code EntryListaSpedizioni} relativo alla spedizione da consegnare
     */
    public void setSpedizione(EntryListaSpedizioni spedizione) {
        if (spedizione == null) {
            throw new NullPointerException("spedizione = null");
        }
        this.spedizione = spedizione;
    }

    /**
     * Metodo che instanza un {@code RiepologoOrdineForm} per mostrare a schermo il riepologo della spedizione
     */
    public void start() {
        RiepilogoOrdineForm riepilogoOrdineForm = new RiepilogoOrdineForm(getSpedizione(), this);
        getStage().hide();
        try {
            riepilogoOrdineForm.start(getStage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Modifica lo stato dell' ordine nel database in consegnato, rimuove i farmaci consegnati dai lotti e se l'ordine è periodico crea un nuovo ordine.
     * Aggiorna inoltre la lista delle spedizioni
     *
     * @param nominativoFarmacista oggetto di tipo {@code String} contenente il nominativo del farmacista che ha firmato la consegna
     */
    public void clickSuConfermaRicezione(String nominativoFarmacista) {
        InterfacciaCorriere db = new InterfacciaCorriere();
        db.modificaStatoInConsegnato(spedizione.getIdOrdine(), nominativoFarmacista);
        ArrayList<LottoOrdinato> lotti = spedizione.getOrdine().getLottiContenuti();
        for (LottoOrdinato lotto : lotti) {
            db.rimuoviLottiConsegnati(lotto);
        }
        if (spedizione.getOrdine().getTipo() == 1 && spedizione.getOrdine().getPeriodo() != 0) {
            db.prenotaOrdine(spedizione.getOrdine());
        }
        ListaSpedizioni.getSpedizioni().remove(spedizione);
    }

    /**
     * Permette di tornare indietro e visualizzare la lista delle spedizioni
     * @param substage oggetto di tipo {@code Stage} realativo alla schermata corrente
     */
    public void clickSuIndietro(Stage substage) {
        substage.close();
        getListaSpedizioni().update();
        getStage().show();
    }

}
