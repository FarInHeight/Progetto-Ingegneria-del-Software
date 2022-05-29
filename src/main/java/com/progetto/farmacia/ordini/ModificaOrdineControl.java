package com.progetto.farmacia.ordini;

import com.progetto.entity.EntryFormOrdine;
import com.progetto.entity.EntryListaOrdini;
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
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * control che gestisce la creazione di un ordine
 */
public class ModificaOrdineControl {

    private Farmacia farmacia;
    private Stage stage;
    private FormModificaOrdine formOrdine;
    private ElencoModificaFarmaci elenco;
    private EntryListaOrdini entry;


    /**
     * Costruttore che permette di creare una {@code ModificaOrdineControl}
     * @param stage stage della schermata lista ordini
     * @throws IOException se il caricamento del file fxml della schermata non è andato a buon fine
     */
    public ModificaOrdineControl(Farmacia farmacia, EntryListaOrdini entry, Stage stage){
        this.setFarmacia(farmacia);
        this.setEntry(entry);
        this.setStage(stage);
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
        rimuovi.setBackground(Background.fill(Color.rgb(255, 79, 66)));
        rimuovi.setStyle("-fx-text-fill: white");
        rimuovi.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ModificaOrdineControl.this.clickSuRimuovi(entry);
            }
        });
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
        aggiungi.setBackground(Background.fill(Color.rgb(0, 0, 200)));
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

    void clickSuRimuovi(EntryFormOrdine entry) {
        this.setPulsantiListaFarmaci(entry);
        this.elenco.aggiungiFarmaco(entry);
        this.formOrdine.rimuoviFarmaco(entry);
    }
    private void clickSuAggiungi(EntryFormOrdine entry) {
        this.setPulsantiFormOrdine(entry);
        this.elenco.rimuoviFarmaco(entry);
        this.formOrdine.aggiungiFarmaco(entry);
    }

    public void clickSuAggungiFarmaci(Stage stage) throws IOException {
        InterfacciaFarmacia db = new InterfacciaFarmacia();
        ArrayList<EntryFormOrdine> farmaci = db.getFarmaciEntry();
        for(EntryFormOrdine entry : farmaci) {
            this.setPulsantiListaFarmaci(entry);
        }
        if(this.elenco == null) {
            this.elenco = new ElencoModificaFarmaci(this,this.farmacia,farmaci);
        }
        this.elenco.start(stage);
    }

    void clickSuConferma(Stage substage){
        substage.close();
        this.formOrdine.getStage().show();
    }

    void clickSuIndietro(Stage substage){
        substage.close();
        this.stage.show();
    }

    public void start() throws IOException{
        this.stage.hide();
        this.formOrdine = new FormModificaOrdine(this.farmacia,this,this.entry);
        this.formOrdine.start(this.stage);
    }
}
