package com.progetto.farmacia.magazzino;

import com.progetto.entity.EntryMagazzinoFarmacia;
import com.progetto.interfacciaDatabase.InterfacciaFarmacia;
import javafx.stage.Stage;

/**
 * Classe che permette al farmacista di rimuovere farmaci venduti dal suo magazzino
 */
public class RimuoviFarmaciControl {

    private EntryMagazzinoFarmacia farmaco;
    private Stage stage;
    private SchermataMagazzino schermataMagazzino;
    private final InterfacciaFarmacia db;

    /**
     * Istanzia un oggetto di tipo {@code RimuoviFarmaciControl}
     * @param farmaco farmaco da rimuovere
     * @param stage stage attuale
     * @param schermataMagazzino schermata contenente le informazioni sui farmaci presenti nel magazzino
     */
    public RimuoviFarmaciControl(EntryMagazzinoFarmacia farmaco, Stage stage,SchermataMagazzino schermataMagazzino) {
        setFarmaco(farmaco);
        setStage(stage);
        setSchermataMagazzino(schermataMagazzino);
        db = new InterfacciaFarmacia();
    }

    /**
     * Getter per il farmaco da rimuovere
     * @return farmaco
     */
    public EntryMagazzinoFarmacia getFarmaco() {
        return farmaco;
    }

    /**
     * Setter per il farmaco da rimuovere
     * @param farmaco farmaco
     */
    public void setFarmaco(EntryMagazzinoFarmacia farmaco) {
        if (farmaco == null) {
            throw new NullPointerException("farmaco = null");
        }
        this.farmaco = farmaco;
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
     * Getter per la schermata con le informazioni sul magazzino
     * @return schermata magazzino
     */
    public SchermataMagazzino getSchermataMagazzino() {
        return schermataMagazzino;
    }

    /**
     * Getter per la schermata con le informazioni sul magazzino
     * @param schermataMagazzino schermata magazzino
     */
    public void setSchermataMagazzino(SchermataMagazzino schermataMagazzino) {
        if (schermataMagazzino == null) {
            throw new NullPointerException("schermataMagazzino = null");
        }
        this.schermataMagazzino = schermataMagazzino;
    }

    /**
     * Metodo che mostra una schermata in cui inserire la quantità di farmaco che si intende rimuovere
     */
    public void clickSuRimuoviFarmaco(){
        SchermataRimozioneQuantita schermataRimozioneQuantita = new SchermataRimozioneQuantita(farmaco, this);
        this.getStage().hide();
        try {
            schermataRimozioneQuantita.start(getStage());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Metodo che rimuove la quantità scelta dal database della catena
     * @param stage stage corrente
     * @param quantitaDaRimuovere quantita che si intende rimuovere dal magazzino
     */
    public void clickSuConferma(Stage stage, int quantitaDaRimuovere){
        //Modifica la quantita nel db
        db.rimuoviQuantita(getFarmaco().getNome(),getFarmaco().getFarmaco().getDataScadenza(), quantitaDaRimuovere, getFarmaco().getQuantita());
        //Modifica la quantita in locale
        getFarmaco().getFarmaco().setQuantita(getFarmaco().getQuantita() - quantitaDaRimuovere);
        //Torna alla schermata magazzino
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
