package com.progetto.farmacia.magazzino;

import com.progetto.farmacia.SchermataPrincipaleFarmacia;
import com.progetto.interfacciaDatabase.InterfacciaFarmacia;
import javafx.stage.Stage;

/**
 * Classe che modella la control {@code RimuoviFarmaciContol} che permette al farmacista di rimuovere farmaci venduti dal suo magazzino
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
     * Ritorna il farmaco da rimuovere
     * @return oggetto di tipo {@code Farmaco} contenente il farmaco
     */
    public EntryMagazzinoFarmacia getFarmaco() {
        return farmaco;
    }

    /**
     * Permette di settare il farmaco da rimuovere
     * @param farmaco farmaco
     */
    public void setFarmaco(EntryMagazzinoFarmacia farmaco) {
        if (farmaco == null) {
            throw new NullPointerException("farmaco = null");
        }
        this.farmaco = farmaco;
    }

    /**
     * Ritorna il riferimento alla finestra corrente
     * @return oggetto di tipo {@code Stage} contenente il riferimento alla finestra corrente
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Permette di settare il riferimento alla finestra corrente
     * @param stage stage corrente
     */
    public void setStage(Stage stage) {
        if (stage == null) {
            throw new NullPointerException("stage = null");
        }
        this.stage = stage;
    }

    /**
     * Ritorna la schermata con le informazioni sul magazzino
     * @return oggetto di tipo {@code SchermataMagazzino} contenente la schermata magazzino
     */
    public SchermataMagazzino getSchermataMagazzino() {
        return schermataMagazzino;
    }

    /**
     * Permette di settare la schermata magazzino
     * @param schermataMagazzino schermata magazzino
     */
    public void setSchermataMagazzino(SchermataMagazzino schermataMagazzino) {
        if (schermataMagazzino == null) {
            throw new NullPointerException("schermataMagazzino = null");
        }
        this.schermataMagazzino = schermataMagazzino;
    }

    /**
     * Permette di msotrare una schermata in cui inserire la quantità di farmaco che si intende rimuovere
     */
    public void start(){
        SchermataRimozioneQuantita schermataRimozioneQuantita = new SchermataRimozioneQuantita(farmaco, this);
        this.getStage().hide();
        try {
            schermataRimozioneQuantita.start(getStage());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Permette di  rimuove la quantità scelta dal database della catena dato in input il riferimneto alla finestra corrente
     * e la quantità di farmaco da rimuovere
     * @param stage riferimento alla schermata corrente
     * @param quantitaDaRimuovere quantita di farmaco che si intende rimuovere dal magazzino
     */
    public void clickSuRimuovi(Stage stage, int quantitaDaRimuovere){
        //Modifica la quantita nel db
        if (controllaQuantita(quantitaDaRimuovere, this.farmaco.getQuantita())) {
            db.rimuoviQuantita(farmaco.getNome(), farmaco.getFarmaco().getDataScadenza(), quantitaDaRimuovere, farmaco.getQuantita());
        } else {
            db.rimuoviFarmaco(SchermataPrincipaleFarmacia.getFarmacia().getIdFarmacia(), this.farmaco.getFarmaco());
        }
        //Modifica la quantita in locale
        getFarmaco().getFarmaco().setQuantita(getFarmaco().getQuantita() - quantitaDaRimuovere);
        //Torna alla schermata magazzino
        clickSuIndietro(stage);
    }

    private boolean controllaQuantita(int quantitaDaRimuovere, int quantita) {
        return quantita > quantitaDaRimuovere;
    }

    /**
     * Permette di tornare indietro e visualizzare la {@code SchermataMagazzino}
     * @param substage riferimento alla schermata corrente
     */
    public void clickSuIndietro(Stage substage) {
        getSchermataMagazzino().update();
        substage.close();
        this.stage.show();
    }
}
