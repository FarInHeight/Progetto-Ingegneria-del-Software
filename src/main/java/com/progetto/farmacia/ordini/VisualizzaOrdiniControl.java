package com.progetto.farmacia.ordini;

import com.progetto.entity.EntryListaOrdini;
import com.progetto.entity.Farmacia;
import com.progetto.interfacciaDatabase.InterfacciaFarmacia;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;


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

    private void setPulsanti(EntryListaOrdini entry) {
        // creazione dei pulsanti
        Button carica = new Button("CARICA");
        carica.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                VisualizzaOrdiniControl.this.clickSuCarica(entry);
            }
        });
        carica.setBackground(Background.fill(Color.rgb(38, 189, 27)));
        carica.setStyle("-fx-text-fill: white");
        Button modifica = new Button("MODIFICA");
        modifica.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                VisualizzaOrdiniControl.this.clickSuModifica(entry);
            }
        });
        modifica.setBackground(Background.fill(Color.rgb(150, 120, 0)));
        modifica.setStyle("-fx-text-fill: white");
        Button cancella = new Button("CANCELLA");
        cancella.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                VisualizzaOrdiniControl.this.clickSuModifica(entry);
            }
        });
        cancella.setBackground(Background.fill(Color.rgb(255, 79, 66)));
        cancella.setStyle("-fx-text-fill: white");
        FlowPane flow = new FlowPane();
        flow.getChildren().addAll(carica, modifica, cancella);
        flow.setAlignment(Pos.CENTER);
        flow.setHgap(10);
        flow.setVgap(10);
        entry.setStrumenti(flow);
    }

    /**
     * Metodo di avvio di un oggetto di classe {@code VisualizzaOrdiniControl}
     */
    public void start() {
        InterfacciaFarmacia db = new InterfacciaFarmacia();
        ArrayList<EntryListaOrdini> ordini = db.getOrdini(this.farmacia.getIdFarmacia());
        for(EntryListaOrdini entry : ordini) {
            this.setPulsanti(entry);
        }
        this.stage.hide();
        this.listaOrdini = new ListaOrdini(this.farmacia, ordini, this);
        try {
            this.listaOrdini.start(this.stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
     * Metodo che viene richiamato quasi si fa un click sul pulsante {@code carica} di una entry della {@code ListaOrdini}.
     * @param entry segnalazione da rimuovere
     */
    void clickSuCarica(EntryListaOrdini entry) {

    }

    /**
     * Metodo che viene richiamato quasi si fa un click sul pulsante {@code modifica} di una entry della {@code ListaOrdini}.
     * @param entry segnalazione da rimuovere
     */
    void clickSuModifica(EntryListaOrdini entry) {

    }

    /**
     * Metodo che viene richiamato quasi si fa un click sul pulsante {@code cancella} di una entry della {@code ListaOrdini}.
     * @param entry segnalazione da rimuovere
     */
    void clickSuCancella(EntryListaOrdini entry) {

    }
}
