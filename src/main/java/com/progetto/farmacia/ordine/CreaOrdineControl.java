package com.progetto.farmacia.ordine;

import com.progetto.entity.EntryFormOrdine;
import com.progetto.entity.Farmacia;
import com.progetto.interfacciaDatabase.InterfacciaFarmacia;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableView;
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
    private ActionEvent event;
    private Stage stage;
    private FormOrdine formOrdine;


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
        Button rimuovi = new Button("RIMUOVI");
        rimuovi.setBackground(Background.fill(Color.rgb(255, 79, 66)));
        rimuovi.setStyle("-fx-text-fill: white");
        rimuovi.setOnAction(event -> {
            TableView<EntryFormOrdine> tabella = (TableView<EntryFormOrdine>) ((Node) event.getSource()).getParent().getParent().getParent().getParent().getParent().getParent().getParent();
            EntryFormOrdine item = tabella.getSelectionModel().getSelectedItem();
            tabella.getItems().remove(item);
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

    private void setPulsantiFromOrdine(EntryFormOrdine entry){
        Button rimuovi = new Button("RIMUOVI");
        rimuovi.setBackground(Background.fill(Color.rgb(255, 79, 66)));
        rimuovi.setStyle("-fx-text-fill: white");
        rimuovi.setOnAction(event -> {
            TableView<EntryFormOrdine> tabella = (TableView<EntryFormOrdine>) ((Node) event.getSource()).getParent().getParent().getParent().getParent().getParent().getParent().getParent();
            EntryFormOrdine item = tabella.getSelectionModel().getSelectedItem();
            tabella.getItems().remove(item);
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

    public void mostraElencoFarmaci() throws IOException {
        InterfacciaFarmacia db = new InterfacciaFarmacia();
        ArrayList<EntryFormOrdine> farmaci = db.getFarmaci();
        for(EntryFormOrdine entry : farmaci) {
            this.setPulsantiListaFarmaci(entry);
        }
        ElencoFarmaci elencoFarmaci = new ElencoFarmaci(this,this.farmacia,farmaci);
        elencoFarmaci.start(this.stage);
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

    /**
     * metodo di avvio della control
     * @throws IOException se il caricamento del file fxml del form ordine non è andato a buon fine
     */
    public void start() throws IOException{
        this.stage.hide();
        this.formOrdine = new FormOrdine(this.farmacia,this);
        this.formOrdine.start(this.stage);
    }
}