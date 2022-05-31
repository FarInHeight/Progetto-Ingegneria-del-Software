package com.progetto.farmacia.ordini;

import com.progetto.entity.EntryFormOrdine;
import com.progetto.entity.Farmacia;
import com.progetto.interfacciaDatabase.InterfacciaFarmacia;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
 * Control che gestisce la creazione di un ordine
 */
public class CreaOrdineControl{
    private Farmacia farmacia;

    private ListaOrdini refListaOrdini;
    private ActionEvent event;
    private Stage stage;
    private FormOrdine formOrdine;

    private ElencoFarmaci elenco;


    /**
     * Costruttore che permette di creare una {@code CreaOrdineControl}
     * @param event evento di pressione del pulsante crea ordine
     * @throws IOException se il caricamento del file fxml della schermata non è andato a buon fine
     */

    public CreaOrdineControl(Farmacia farmacia, ActionEvent event) throws IOException{
        this.setFarmacia(farmacia);
        this.setEvent(event);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        this.setStage(stage);
    }

    private void setFarmacia(Farmacia farmacia) {
        if(farmacia == null){
            throw new NullPointerException("farmacia = null");
        }
        this.farmacia = farmacia;
    }

    private void setEvent(ActionEvent event){
        if(event == null){
            throw new NullPointerException("event = null");
        }
        this.event = event;
    }

    private void setStage(Stage stage) {
        if(stage == null){
            throw new NullPointerException("event = null");
        }
        this.stage = stage;
    }

    private void setPulsantiListaFarmaci(EntryFormOrdine entry){
        Button aggiungi = new Button("AGGIUNGI");
        aggiungi.setBackground(Background.fill(Color.rgb(0, 0, 200)));
        aggiungi.setStyle("-fx-text-fill: white");
        aggiungi.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CreaOrdineControl.this.clickSuAggiungi(entry);
            }
        });
        FlowPane flow = new FlowPane();
        flow.getChildren().addAll(aggiungi);
        flow.setAlignment(Pos.CENTER);
        flow.setHgap(10); // dae8fc
        entry.setStrumenti(flow);
    }

    private void setPulsantiFormOrdine(EntryFormOrdine entry){
        Button rimuovi = new Button("RIMUOVI");
        rimuovi.setBackground(Background.fill(Color.rgb(255, 79, 66)));
        rimuovi.setStyle("-fx-text-fill: white");
        rimuovi.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CreaOrdineControl.this.clickSuRimuovi(entry);
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

    private void clickSuRimuovi(EntryFormOrdine entry) {
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
            this.elenco = new ElencoFarmaci(this,this.farmacia,farmaci);
        }
        this.elenco.start(stage);
    }

    /**
     * ritorna lo stage corrente
     * @return oggetto di tipo {@code Stage} contenente lo stage corrente
     */
    public Stage getStage() {
        return this.stage;
    }

    /**
     * Metodo tramite il quale un oggetto di tipo {@code FromOrdine} avvisa la {@code FormOrdineControl}
     * del click sul pulsante {@code indietro} e distrugge il form ordine.
     * Il metodo è stato creato senza modificatore di visibilità affinché possa essere invocato soltanto da classi
     * che si trovano nello stesso package.
     * @param substage sotto-stage del form ordine da distuggere
     */
    void clickSuIndietro(Stage substage){
        substage.close();
        this.stage.show();
    }

    void clickSuConferma(Stage substage){
        substage.close();
        this.formOrdine.getStage().show();
    }

    /**
     * Metodo di avvio della control
     * @throws IOException se il caricamento del file {@code fxml} del form ordine non è andato a buon fine
     */
    public void start() throws IOException{
        this.stage.hide();
        this.formOrdine = new FormOrdine(this.farmacia,this);
        this.formOrdine.start(this.stage);
    }
}