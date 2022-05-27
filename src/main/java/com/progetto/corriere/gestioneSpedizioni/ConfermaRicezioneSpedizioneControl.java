package com.progetto.corriere.gestioneSpedizioni;

import com.progetto.entity.EntryListaSpedizioni;
import com.progetto.entity.LottoOrdinato;
import com.progetto.interfacciaDatabase.InterfacciaCorriere;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Classe responsabile di gestie la consegna degli ordini al farmacista.
 */
public class ConfermaRicezioneSpedizioneControl {

    private Stage stage;
    private EntryListaSpedizioni spedizione;
    private ListaSpedizioni listaSpedizioni;

    /**
     * Costruttore per la ConfermaRicezioneSpedizioneControl
     * @param stage stage attuale
     * @param spedizione spedizione da per cui mostrare il riepilogo
     * @param listaSpedizioni schermata che contiene la lista spedizioni
     */
    public ConfermaRicezioneSpedizioneControl(Stage stage, EntryListaSpedizioni spedizione, ListaSpedizioni listaSpedizioni) {
        setStage(stage);
        setSpedizione(spedizione);
        setListaSpedizioni(listaSpedizioni);
    }

    /**
     * Getter per la schermata che contiene la lista spedizioni
     * @return schermata che contiene la lista spedizioni
     */
    public ListaSpedizioni getListaSpedizioni() {
        return listaSpedizioni;
    }

    /**
     * Setter per la schermata che contiene la lista spedizioni
     * @param listaSpedizioni schermata che contiene la lista spedizioni
     */
    public void setListaSpedizioni(ListaSpedizioni listaSpedizioni) {
        if (listaSpedizioni == null) {
            throw new NullPointerException("listaSpedizioni = null");
        }
        this.listaSpedizioni = listaSpedizioni;
    }

    /**
     * Getter per lo stage corrente
     * @return stage corrente
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Setter per lo stage corrente
     * @param stage stage corrente
     */
    public void setStage(Stage stage) {
        if (stage == null) {
            throw new NullPointerException("stage = null");
        }
        this.stage = stage;
    }

    /**
     * Getter per la spedizione da consegnare
     * @return spedizione da consegnare
     */
    public EntryListaSpedizioni getSpedizione() {
        return spedizione;
    }

    /**
     * Setter per la spedizione da consegnare
     * @param spedizione spedizione da consegnare
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
    public void mostraRiepilogo() {
        RiepilogoOrdineForm riepilogoOrdineForm = new RiepilogoOrdineForm(getSpedizione(), this);
        getStage().hide();
        try {
            riepilogoOrdineForm.start(getStage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Modifica lo stato dell'{@code ordine} nel database in consegnato, rimuove quindi i farmaci consegnati dai lotti
     * Se l'ordine è periodico crea un nuovo ordine
     * Aggiorna la lista delle spedizioni
     *
     * @param nominativoFarmacista nominativo del farmacista che ha firmato la consegna, da inviare la database
     */
    public void confermaRicezione(String nominativoFarmacista) {
        InterfacciaCorriere db = new InterfacciaCorriere();
        db.modificaStatoInConsegnato(spedizione.getIdOrdine(), nominativoFarmacista);
        ArrayList<LottoOrdinato> lotti = spedizione.getOrdine().getLottiContenuti();
        for (LottoOrdinato lotto : lotti) {
            db.rimuoviLottiConsegnati(lotto);
        }
        if (spedizione.getOrdine().getTipo() == 1) {
            db.prenotaOrdine(spedizione.getOrdine());
        }
        ListaSpedizioni.getSpedizioni().remove(spedizione);
    }

    /**
     * Permette di tornare indietro e visualizzare la {@code ListaSpedizioni} aggiornata
     * @param substage stage corrente
     */
    public void clickSuIndietro(Stage substage) {
        substage.close();
        getListaSpedizioni().update();
        getStage().show();
    }

}
