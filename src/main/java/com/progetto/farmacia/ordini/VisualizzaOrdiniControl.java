package com.progetto.farmacia.ordini;

import com.progetto.entity.EntryListaOrdini;
import com.progetto.entity.Farmacia;
import com.progetto.entity.Farmaco;
import com.progetto.entity.Ordine;
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
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;


/**
 * Classe che implementa la control {@code VisualizzaOrdiniControl} che si occuap di visualizzare gli ordini della farmacia
 */
public class VisualizzaOrdiniControl {
    private Farmacia farmacia;
    @SuppressWarnings("FieldCanBeLocal")
    private ActionEvent event;
    private Stage stage;
    private ListaOrdini listaOrdini;

    /**
     * Istanzia un oggetto di tipo {@code VisualizzaOrdiniControl} data in input la farmacia alla quale
     * fa riferimento e l'evento che ha generato la creazione
     * @param farmacia farmacia di riferimento
     * @param event evento che ha creato la control
     */
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
        carica.setBackground(Background.fill(Color.rgb(38, 180, 27)));
        carica.setStyle("-fx-text-fill: white");
        carica.setPrefWidth(80);
        if(entry.getOrdine().getStato() != 4) {
            carica.setVisible(false);
            carica.setManaged(false);
        }
        Button modifica = new Button("MODIFICA");
        modifica.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                VisualizzaOrdiniControl.this.clickSuModifica(entry);
            }
        });
        modifica.setBackground(Background.fill(Color.rgb(190, 190, 120)));
        modifica.setStyle("-fx-text-fill: white");
        modifica.setPrefWidth(80);
        if(LocalDate.now().plusDays(2).isAfter( entry.getOrdine().getDataConsegna() ) || entry.getOrdine().getStato() == 4) {
            modifica.setVisible(false);
            modifica.setManaged(false);
        }
        Button cancella = new Button("CANCELLA");
        cancella.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                VisualizzaOrdiniControl.this.clickSuCancella(entry);
            }
        });
        cancella.setBackground(Background.fill(Color.rgb(210, 79, 66)));
        cancella.setStyle("-fx-text-fill: white");
        cancella.setPrefWidth(80);
        // se la data di consegna è entro due giorni successivi oppure l'ordine è periodico
        if(LocalDate.now().plusDays(2).isAfter( entry.getOrdine().getDataConsegna() ) || entry.getOrdine().getTipo() == 1 || entry.getOrdine().getStato() == 4) {
            cancella.setVisible(false);
            cancella.setManaged(false);
        }
        FlowPane flow = new FlowPane();
        flow.getChildren().addAll(carica, modifica, cancella);
        flow.setAlignment(Pos.CENTER);
        flow.setHgap(10);
        flow.setVgap(10);
        entry.setStrumenti(flow);
    }

    /**
     * Permette di avviare la control che gestisce la visualizzazione degli ordini della farmacia
     */
    public void start() {
        ArrayList<EntryListaOrdini> listaDaVisualizzare = this.getOrdiniDaVisualizzare();
        this.stage.hide();
        this.listaOrdini = new ListaOrdini(this.farmacia, listaDaVisualizzare, this);
        try {
            this.listaOrdini.start(this.stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<EntryListaOrdini> getOrdiniDaVisualizzare() {
        InterfacciaFarmacia db = new InterfacciaFarmacia();
        ArrayList<EntryListaOrdini> ordini = db.getOrdini(this.farmacia.getIdFarmacia());
        ArrayList<EntryListaOrdini> listaDaVisualizzare = new ArrayList<>();
        for(EntryListaOrdini entry : ordini) {
            EntryListaOrdini newEntry = this.entryDaCompattare(entry);
            this.setPulsanti(newEntry);
            listaDaVisualizzare.add(newEntry);
        }
        return listaDaVisualizzare;
    }
    private EntryListaOrdini entryDaCompattare(EntryListaOrdini entry) {
        Ordine ordine = new Ordine(entry.getIdOrdine(), entry.getOrdine().getStato(), new ArrayList<>(), entry.getOrdine().getTipo(), entry.getOrdine().getPeriodo(), entry.getOrdine().getDataConsegna(), entry.getOrdine().getNomeFarmacia(), entry.getOrdine().getIndirizzoConsegna());
        EntryListaOrdini newEntry = new EntryListaOrdini(ordine);
        for(Farmaco farmaco : entry.getFarmaci()) {
            Farmaco candidato = this.ordineContieneFarmaco(farmaco, newEntry.getFarmaci());
            if (candidato != null) {
                candidato.setQuantita( candidato.getQuantita() + farmaco.getQuantita());
            } else {
                newEntry.getFarmaci().add(new Farmaco(farmaco.getNome(), farmaco.getPrincipioAttivo(), farmaco.getTipo(), farmaco.getDataScadenza(), farmaco.getQuantita()));
            }
        }
        return newEntry;
    }

    private Farmaco ordineContieneFarmaco(Farmaco farmaco, ArrayList<Farmaco> lista) {
        for(Farmaco entry : lista) {
            if(entry.getNome().strip().equals(farmaco.getNome().strip())) {
                return entry;
            }
        }
        return null;
    }

    /**
     * Metodo tramite il quale un oggetto di tipo {@code ListaOrdini} avvisa la {@code VisualizzaOrdiniControl}
     * del click sul pulsante {@code indietro} e distrugge la {@code ListaOrdini}.
     * Il metodo è stato creato senza modificatore di visibilità affinché possa essere invocato soltanto da classi
     * che si trovano nello stesso package.
     * @param substage sotto-stage della {@code ListaOrdini} da distuggere
     */
    void clickSuIndietro(Stage substage) {
        substage.close();
        this.stage.show();
    }

    /**
     * Metodo che viene richiamato quando si fa un click sul pulsante {@code carica} di una entry della {@code ListaOrdini}.
     * @param entry ordine da caricare
     */
    void clickSuCarica(EntryListaOrdini entry) {
        this.listaOrdini.caricaOrdine(entry);
    }

    /**
     * Metodo che viene richiamato quando si fa un click sul pulsante {@code modifica} di una entry della {@code ListaOrdini}.
     * @param entry ordine da modificare
     */
    void clickSuModifica(EntryListaOrdini entry) {
        try {
            this.listaOrdini.modificaOrdine(entry);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo che viene richiamato quando si fa un click sul pulsante {@code cancella} di una entry della {@code ListaOrdini}.
     * @param entry ordine da rimuovere
     */
    void clickSuCancella(EntryListaOrdini entry) {
        try {
            this.listaOrdini.cancellaOrdine(entry);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
