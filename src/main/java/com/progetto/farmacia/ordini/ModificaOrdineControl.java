package com.progetto.farmacia.ordini;

import com.progetto.entity.EntryFormOrdine;
import com.progetto.entity.Farmacia;
import com.progetto.entity.Farmaco;
import com.progetto.interfacciaDatabase.InterfacciaFarmacia;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Classe che modella la control {@code ModificaOrdineControl} che gestisce la modifica degli ordini
 */
public class ModificaOrdineControl {

    private Farmacia farmacia;
    private Stage stage;
    private ListaOrdini refListaOrdini;
    private FormModificaOrdine formOrdine;
    private ElencoModificaFarmaci elenco;
    private EntryListaOrdini entry;


    /**
     * Istanzia un oggetto di tipo {@code ModificaOrdineControl}
     * @param stage stage della schermata lista ordini
     */
    public ModificaOrdineControl(Farmacia farmacia, EntryListaOrdini entry, Stage stage, ListaOrdini refListaOrdini){
        this.setFarmacia(farmacia);
        this.setEntry(entry);
        this.setStage(stage);
        this.setRefListaOrdini(refListaOrdini);
    }

    private void setRefListaOrdini(ListaOrdini refListaOrdini){
        if(refListaOrdini == null){
            throw new NullPointerException("ref lista ordini = null");
        }
        this.refListaOrdini = refListaOrdini;
    }

    private void setEntry(EntryListaOrdini entry){
        if(entry == null){
            throw new NullPointerException("entry = null");
        }
        this.entry = entry;
    }

    private void setFarmacia(Farmacia farmacia) {
        if(farmacia == null){
            throw new NullPointerException("farmacia = null");
        }
        this.farmacia = farmacia;
    }

    private void setStage(Stage stage) {
        if(stage == null){
            throw new NullPointerException("stage = null");
        }
        this.stage = stage;
    }

    private void setPulsantiFormOrdine(EntryFormOrdine entry){
        Button rimuovi = new Button("RIMUOVI");
        rimuovi.setBackground(new Background(new BackgroundFill(Color.rgb(255, 79, 66), null, null)));
        rimuovi.setStyle("-fx-text-fill: white");
        rimuovi.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ModificaOrdineControl.this.clickSuRimuovi(entry);
            }
        });
        if(this.entry.getOrdine().getTipo() == 1) {
            rimuovi.setVisible(false);
            rimuovi.setManaged(false);
        }
        Spinner<Integer> spinner = new Spinner<Integer>();
        spinner.setEditable(true);
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,Integer.MAX_VALUE,1);
        spinner.setValueFactory(valueFactory);
        spinner.setMaxWidth(100);
        FlowPane flow = new FlowPane();
        flow.getChildren().addAll(spinner, rimuovi);
        flow.setAlignment(Pos.CENTER);
        flow.setHgap(10); // dae8fc
        entry.setStrumenti(flow);
    }

    private void setPulsantiListaFarmaci(EntryFormOrdine entry){
        Button aggiungi = new Button("AGGIUNGI");
        aggiungi.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 200), null, null)));
        aggiungi.setStyle("-fx-text-fill: white");
        aggiungi.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ModificaOrdineControl.this.clickSuAggiungi(entry);
            }
        });
        FlowPane flow = new FlowPane();
        flow.getChildren().addAll(aggiungi);
        flow.setAlignment(Pos.CENTER);
        flow.setHgap(10); // dae8fc
        entry.setStrumenti(flow);
    }

    /**
     * Rimuove il farmaco relativo alla entry data in input dall'elenco dei farmaci presente nel form ordine
     * @param entry entry del form ordine da rimuovere
     */
    void clickSuRimuovi(EntryFormOrdine entry) {
        this.setPulsantiListaFarmaci(entry);
        if (this.elenco != null) {
            this.elenco.aggiungiFarmaco(entry);
        }else {
            InterfacciaFarmacia db = new InterfacciaFarmacia();
            ArrayList<EntryFormOrdine> farmaci = db.getFarmaciEntry();
            // tolgo i farmaci contenuti nell'ordine dalla lista completa
            for(int i = 0; i < this.entry.getFarmaci().size(); ++i) {
                this.rimuoviFarmacoContenuto(farmaci, this.entry.getFarmaci().get(i).getNome());
            }
            // imposto i pulsanti di rimozione
            for(EntryFormOrdine farmaco : farmaci) {
                this.setPulsantiListaFarmaci(farmaco);
            }
            farmaci.add(entry);  // aggiungo il farmaco appena rimosso dal form ordine
            this.elenco = new ElencoModificaFarmaci(this, this.farmacia, farmaci);
        }
        this.formOrdine.rimuoviFarmaco(entry);
    }
    private void clickSuAggiungi(EntryFormOrdine entry) {
        this.setPulsantiFormOrdine(entry);
        this.elenco.rimuoviFarmaco(entry);
        this.formOrdine.aggiungiFarmaco(entry);
    }

    /**
     * Metodo tramite il quale un oggetto di tipo {@code FromModificaOrdine} avvisa la {@code FormModificaOrdineControl}
     * del click sul pulsante {@code aggiungi farmaci}.
     * Il metodo è stato creato senza modificatore di visibilità affinché possa essere invocato soltanto da classi
     * che si trovano nello stesso package.
     * @param stage stage del form modifica ordine da distuggere
     */
    public void clickSuAggungiFarmaci(Stage stage) throws IOException {
        InterfacciaFarmacia db = new InterfacciaFarmacia();
        ArrayList<EntryFormOrdine> farmaci = db.getFarmaciEntry();
        int i = 0;
        while(i < farmaci.size()) {
            if(contieneFarmaco(this.entry.getFarmaci(), farmaci.get(i).getNomeFarmaco())) {
                this.setPulsantiListaFarmaci(farmaci.get(i));
                farmaci.remove(i);  // rimuovo la entry se è contenuta nell'ordine
            } else {
                this.setPulsantiListaFarmaci(farmaci.get(i));
                ++i;
            }
        }
        if(this.elenco == null)
            this.elenco = new ElencoModificaFarmaci(this, this.farmacia, farmaci);

        this.elenco.start(stage);
    }

    private boolean contieneFarmaco(ArrayList<Farmaco> lista, String nomeFarmaco) {
        for(Farmaco farmaco : lista) {
            if(farmaco.getNome().strip().equals(nomeFarmaco.strip())) {
                return true;
            }
        }
        return false;
    }

    private void rimuoviFarmacoContenuto(ArrayList<EntryFormOrdine> lista, String nomeFarmaco) {
        for(int i = 0; i < lista.size(); ++i) {
            if(lista.get(i).getNomeFarmaco().strip().equals(nomeFarmaco.strip())) {
                lista.remove(i);
            }
        }
    }

    /**
     * Metodo tramite il quale un oggetto di tipo {@code FromModificaOrdine} avvisa la {@code FormModificaOrdineControl}
     * del click sul pulsante {@code conferma} e distrugge il form modifica ordine.
     * Il metodo è stato creato senza modificatore di visibilità affinché possa essere invocato soltanto da classi
     * che si trovano nello stesso package.
     * @param substage sotto-stage del form ordine da distuggere
     */
    void clickSuConferma(Stage substage){
        substage.close();
        this.formOrdine.getStage().show();
    }
    /**
     * Metodo tramite il quale un oggetto di tipo {@code FromModificaOrdine} avvisa la {@code FormModificaOrdineControl}
     * del click sul pulsante {@code indietro} e distrugge il form modifica ordine.
     * Il metodo è stato creato senza modificatore di visibilità affinché possa essere invocato soltanto da classi
     * che si trovano nello stesso package.
     * @param substage sotto-stage del form modifica ordine da distuggere
     */

    void clickSuIndietro(Stage substage){
        substage.close();
        this.stage.show();
    }

    /**
     * Permette di avviare la control che gestisce la modifica di un ordine
     * @throws IOException se il caricamento del file {@code fxml} del form di modifica ordine non è andato a buon fine
     */
    public void start() throws IOException{
        this.stage.hide();
        this.formOrdine = new FormModificaOrdine(this.farmacia,this,this.entry,this.refListaOrdini);
        this.formOrdine.start(this.stage);
    }
}
