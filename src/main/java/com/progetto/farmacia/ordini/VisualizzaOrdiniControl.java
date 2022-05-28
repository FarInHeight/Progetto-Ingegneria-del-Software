package com.progetto.farmacia.ordini;

import com.progetto.entity.Farmacia;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

/**
 * Classe che implementa la control {@code VisualizzaOrdiniControl}
 */
public class VisualizzaOrdiniControl {
    private Farmacia farmacia;
    private ActionEvent event;
    private Stage stage;
    private ListaOrdini listaOrdini;
    public VisualizzaOrdiniControl(Farmacia farmacia, ActionEvent event) {
        this.setFarmacia(farmacia);
        this.setEvent(event);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        this.setStage(stage);
    }

    private void setEvent(ActionEvent event) {
        if(event == null) {
            throw new NullPointerException("Event = null");
        }
        this.event = event;
    }

    private void setFarmacia(Farmacia farmacia) {
        if(farmacia == null) {
            throw new NullPointerException("Farmacia in Visualizza Ordini = null");
        }
        this.farmacia = farmacia;
    }

    private void setStage(Stage stage) {
        if(stage == null){
            throw new NullPointerException("event = null");
        }
        this.stage = stage;
    }

    /**
     * Metodo tramite il quale un oggetto di tipo {@code ListaOrdini} avvisa la {@code VisualizzaOrdiniControl}
     * del click sul pulsante {@code indietro} e distrugge la ListaOrdini.
     * Il metodo è stato creato senza modificatore di visibilità affinché possa essere invocato soltanto da classi
     * che si trovano nello stesso package.
     * @param substage sotto-stage della ListaOrdini da distuggere
     */
    void clickSuIndietro(Stage substage) {
        substage.close();
        this.stage.show();
    }
    /**
     * Metodo di avvio di un oggetto di classe {@code VisualizzaOrdiniControl}
     */
    public void start() {
        this.stage.hide();
        //this.listaOrdini = new ListaOrdini(this.farmacia,this);
        try {
            this.listaOrdini.start(this.stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
