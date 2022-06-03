package com.progetto.farmacia.ordini;

import com.progetto.entity.EntryListaOrdini;
import com.progetto.entity.Farmaco;
import com.progetto.interfacciaDatabase.InterfacciaFarmacia;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;
import java.time.LocalDate;

/**
 * Class che modella la control {@code CreaSegnalazioneControl}
 */
public class CreaSegnalazioneControl {

    private EntryListaOrdini ordine;
    private CreazioneSegnalazioneForm form;

    private RegistrazioneFarmaciRicevutiControl control;
    /**
     * Istanzia un oggetto di tipo {@code CreaSegnalazioneControl} dato in input un ordine da segnalare
     * @param ordine ordine da segnalare
     */
    public CreaSegnalazioneControl(EntryListaOrdini ordine, RegistrazioneFarmaciRicevutiControl control) {
        this.setOrdine(ordine);
        this.setControl(control);
    }
    private void setControl(RegistrazioneFarmaciRicevutiControl control) {
        if(control == null) {
            throw new NullPointerException("Riepilogo = null");
        }
        this.control = control;
    }

    private void setOrdine(EntryListaOrdini ordine) {
        if(ordine == null) {
            throw new NullPointerException("Ordine in CreaSegnalazioneControl = null");
        }
        this.ordine = ordine;
    }

    /**
     * Permette di avviare di un oggetto di tipo {@code CreaSegnalazioneControl}
     * @param event evento che ha provocato la creazione della control
     */
    public void start(ActionEvent event) {
        String riepilogo = "";
        for(Farmaco farmaco : ordine.getFarmaci()) {
            riepilogo += farmaco.getNome() + "\t" + farmaco.getQuantita();
        }
        this.form = new CreazioneSegnalazioneForm(riepilogo.stripTrailing(), this);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //ottiene stage corrente
        stage.close();
        try {
            this.form.start(new Stage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metodo tramite il quale un oggetto di tipo {@code CreazioneSegnalazioneForm} avvisa la {@code CreaSegnalazioneControl}
     * del click sul pulsante {@code inviaSegnalazione} e distrugge la {@code CreazioneSegnalazioneForm}.
     * Il metodo è stato creato senza modificatore di visibilità affinché possa essere invocato soltanto da classi
     * che si trovano nello stesso package.
     * @param stage stage della {@code CreazioneSegnalazioneForm} da distuggere
     */
    void clickSuInvia(Stage stage) {
        String commento = this.form.getSegnalazione();
        InterfacciaFarmacia db = new InterfacciaFarmacia();
        db.aggiungiSegnalazione(this.ordine.getIdOrdine(), commento, LocalDate.now());
        stage.close();
        ConfermaInvioSegnalazione conferma = new ConfermaInvioSegnalazione(this.control);
        try {
            conferma.start(new Stage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
