package com.progetto.farmacia.magazzino;

import com.progetto.entity.EntryMagazzinoFarmacia;
import com.progetto.interfacciaDatabase.InterfacciaFarmacia;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class RimuoviFarmaciControl {

    private EntryMagazzinoFarmacia farmaco;
    private Stage stage;
    private SchermataMagazzino schermataMagazzino;
    private final InterfacciaFarmacia db;

    public RimuoviFarmaciControl(EntryMagazzinoFarmacia farmaco, Stage stage,SchermataMagazzino schermataMagazzino) {
        setFarmaco(farmaco);
        setStage(stage);
        setSchermataMagazzino(schermataMagazzino);
        db = new InterfacciaFarmacia();
    }

    public EntryMagazzinoFarmacia getFarmaco() {
        return farmaco;
    }

    public void setFarmaco(EntryMagazzinoFarmacia farmaco) {
        if (farmaco == null) {
            throw new NullPointerException("farmaco = null");
        }
        this.farmaco = farmaco;
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

    public SchermataMagazzino getSchermataMagazzino() {
        return schermataMagazzino;
    }

    public void setSchermataMagazzino(SchermataMagazzino schermataMagazzino) {
        if (schermataMagazzino == null) {
            throw new NullPointerException("schermataMagazzino = null");
        }
        this.schermataMagazzino = schermataMagazzino;
    }

    public void clickSuRimuoviFarmaco(){
        SchermataRimozioneQuantita schermataRimozioneQuantita = new SchermataRimozioneQuantita(farmaco, this);
        this.getStage().hide();
        try {
            schermataRimozioneQuantita.start(getStage());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void clickSuConferma(Stage stage, int quantitaDaRimuovere){
        db.rimuoviQuantita(getFarmaco().getNome(),getFarmaco().getFarmaco().getDataScadenza(), quantitaDaRimuovere, getFarmaco().getQuantita());
        getFarmaco().getFarmaco().setQuantita(getFarmaco().getQuantita() - quantitaDaRimuovere);
        clickSuIndietro(stage);
    }

    /**
     * Permette di tornare indietro e visualizzare la {@code SchermataMagazzino}
     * @param substage stage attualmente mostrato
     */
    public void clickSuIndietro(Stage substage) {
        substage.close();
        getSchermataMagazzino().update();
        this.stage.show();
    }
}
