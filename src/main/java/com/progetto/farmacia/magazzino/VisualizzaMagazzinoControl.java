package com.progetto.farmacia.magazzino;

import com.progetto.entity.EntryMagazzinoFarmacia;
import com.progetto.interfacciaDatabase.InterfacciaFarmacia;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Classe che si occupa di implementare la control {@code VisualizzaMagazzinoControl} che richiedere e mostrare i farmaci
 * presenti nel magazzino di una farmacia
 */
public class VisualizzaMagazzinoControl {

    private Stage stage;
    private ArrayList<EntryMagazzinoFarmacia> farmaci;
    private final InterfacciaFarmacia db;

    /**
     * Istanzia un oggetto di tipo {@code VisualizzaMagazzinoControl}
     * @param event evento associato alla pressione del tasto visualizza magazzino
     */
    public VisualizzaMagazzinoControl(ActionEvent event) {
        this.setStage((Stage) ((Node) event.getSource()).getScene().getWindow());
        db = new InterfacciaFarmacia();
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
     * Ritorna i farmaci preenti in magazzino
     * @return oggetto di tipo {@code ArrayList<EntryMagazzinoFarmacia>} contenente i farmaci presenti in magazzino
     */
    public ArrayList<EntryMagazzinoFarmacia> getFarmaci() {
        return farmaci;
    }

    /**
     * Permette di settare i farmaci presenti in magazzino
     * @param farmaci farmaci nel magazzino
     */
    public void setFarmaci(ArrayList<EntryMagazzinoFarmacia> farmaci) {
        if (farmaci == null) {
            throw new NullPointerException("farmaci = null");
        }
        this.farmaci = farmaci;
    }

    /**
     * Richiede i farmaci dal database, crea la SchermataMagazzino e la mostra a schermo
     */
    public void clickSuVisualizzaMagazzino(){
        //Richiedo i farmaci
        setFarmaci(db.getFarmaciMagazzino());
        //Asscoio i pulsanti
        for (EntryMagazzinoFarmacia farmaco : getFarmaci()) {
            this.setPulsanti(farmaco);
        }
        //Creo la schermata
        SchermataMagazzino schermataMagazzino = new SchermataMagazzino(farmaci,this);
        //Modifico la schermata corrente
        this.getStage().hide();
        try {
            schermataMagazzino.start(this.stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * Permette di creare associare i pulsanti alle entry della schermata magazzino
     */
    private void setPulsanti(EntryMagazzinoFarmacia farmaco) {
        Button consegna = new Button("RIMUOVI FARMACO");
        consegna.setBackground(Background.fill(Color.rgb(255, 51, 51)));
        consegna.setStyle("-fx-text-fill: white");
        farmaco.setStrumenti(consegna);
    }

    /**
     * Permette di tornare indietro e visualizzare la {@code SchermataPrincipaleFarmacia}
     * @param substage riferimento alla finestra attualmente mostrata
     */
    public void clickSuIndietro(Stage substage) {
        substage.close();
        this.stage.show();
    }

}
