package com.progetto.farmacia.ordine;

import com.progetto.entity.Farmacia;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;

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
     * @param event evento di pressione del pulsante cre ordine
     * @throws IOException se il caricamento del file fxml del form ordine non è andato a buon fine
     */
    public void start(ActionEvent event) throws IOException{
        this.stage.hide();
        this.formOrdine = new FormOrdine(this.farmacia,this);
        this.formOrdine.start(this.stage);
    }
}