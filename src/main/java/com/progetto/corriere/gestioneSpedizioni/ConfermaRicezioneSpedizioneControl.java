package com.progetto.corriere.gestioneSpedizioni;

import com.progetto.entity.EntryListaSpedizioni;
import com.progetto.entity.LottoOrdinato;
import com.progetto.interfacciaDatabase.InterfacciaCorriere;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ConfermaRicezioneSpedizioneControl {

    private Stage stage;
    private EntryListaSpedizioni spedizione;
    private ListaSpedizioni listaSpedizioni;

    public ConfermaRicezioneSpedizioneControl(Stage stage, EntryListaSpedizioni spedizione, ListaSpedizioni listaSpedizioni) {
        setStage(stage);
        setSpedizione(spedizione);
        setListaSpedizioni(listaSpedizioni);
    }

    public ListaSpedizioni getListaSpedizioni() {
        return listaSpedizioni;
    }

    public void setListaSpedizioni(ListaSpedizioni listaSpedizioni) {
        if (listaSpedizioni == null) {
            throw new NullPointerException("listaSpedizioni = null");
        }
        this.listaSpedizioni = listaSpedizioni;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        if (stage == null) {
            throw new NullPointerException("stage = null");
        }
        this.stage = stage;
    }

    public EntryListaSpedizioni getSpedizione() {
        return spedizione;
    }

    public void setSpedizione(EntryListaSpedizioni spedizione) {
        if (spedizione == null) {
            throw new NullPointerException("spedizione = null");
        }
        this.spedizione = spedizione;
    }

    public void mostraRiepilogo() {
        RiepilogoOrdineForm riepilogoOrdineForm = new RiepilogoOrdineForm(getSpedizione(), this);
        getStage().hide();
        try {
            riepilogoOrdineForm.start(getStage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void confermaRicezione(String nominativoFarmacista) {
        InterfacciaCorriere db = new InterfacciaCorriere();
        db.modificaStatoInConsegnato(spedizione.getIdOrdine(), nominativoFarmacista);
        ArrayList<LottoOrdinato> lotti = spedizione.getOrdine().getLottiContenuti();
        for (LottoOrdinato lotto : lotti) {
            db.rimuoviLottiConsegnati(lotto);
        }
        ListaSpedizioni.getSpedizioni().remove(spedizione);
    }

    public void clickSuIndietro(Stage substage) {
        substage.close();
        listaSpedizioni.initialize(null,null);
        getStage().show();
    }

}
