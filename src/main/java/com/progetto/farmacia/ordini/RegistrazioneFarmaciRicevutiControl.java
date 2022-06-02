package com.progetto.farmacia.ordini;

import com.progetto.entity.EntryListaOrdini;
import com.progetto.entity.EntryMagazzinoFarmacia;
import com.progetto.entity.Farmaco;
import com.progetto.entity.Ordine;
import com.progetto.interfacciaDatabase.InterfacciaFarmacia;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Classe che modella la control {@code RegistrazioneFarmaciRicevutiControl} che gestisce la registrazione dei farmaci ricevuti
 */
public class RegistrazioneFarmaciRicevutiControl {

    private EntryListaOrdini entry;
    private Ordine ordine;
    private Stage stage;
    private final InterfacciaFarmacia db;
    private Stage stageRegistrazioneFarmaci;

    /**
     * Istanzia un oggetto di tipo {@code RegistrazioneFarmaciRicevutiControl} dato in input la entry dell'ordine da caricare
     * e il riferimento alla finestra corrente
     * @param entry ordine da caricare
     * @param stage stage corrente
     */
    public RegistrazioneFarmaciRicevutiControl(EntryListaOrdini entry, Stage stage) {
        setEntry(entry);
        setOrdine(entry.getOrdine());
        setStage(stage);
        db = new InterfacciaFarmacia();
    }

    private void setEntry(EntryListaOrdini entry) {
        if (entry == null) {
            throw new NullPointerException("entry = null");
        }
        this.entry = entry;
    }

    private void setOrdine(Ordine ordine) {
        if (ordine == null) {
            throw new NullPointerException("ordine = null");
        }
        this.ordine = ordine;
    }

    private void setStage(Stage stage) {
        if (stage == null) {
            throw new NullPointerException("stage = null");
        }
        this.stage = stage;
    }

    /**
     * Permette di avviare la control che gestisce la registrazione dei farmaci riveuti
     */
    public void start() {
        ArrayList<EntryMagazzinoFarmacia> farmaci = new ArrayList<>();
        for (Farmaco farmaco:ordine.getFarmaci()) {
            farmaci.add(new EntryMagazzinoFarmacia(farmaco));
        }
        for (EntryMagazzinoFarmacia entry : farmaci) {
            setPulsanti(entry);
        }

        SchermataRegistrazioneFarmaci schermataRegistrazioneFarmaci = new SchermataRegistrazioneFarmaci(farmaci,this,this.ordine.getIdOrdine());
        this.stage.hide();
        this.stageRegistrazioneFarmaci = new Stage();
        try {
            schermataRegistrazioneFarmaci.start(this.stageRegistrazioneFarmaci);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setPulsanti(EntryMagazzinoFarmacia entry) {
        Spinner<Integer> spinner = new Spinner<>();
        spinner.setEditable(true);
        SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, entry.getQuantita());
        spinner.setValueFactory(spinnerValueFactory);
        entry.getStrumenti().getChildren().add(spinner);
    }

    /**
     * Permette di caricare i farmaci nel db della catena e verifica che tutti i farmaci siano caricati
     */
    public void clickSuConfermaRegistrazione() {
        ArrayList<Farmaco> farmaciConsegnati = ordine.getFarmaci();
        ArrayList<EntryMagazzinoFarmacia> farmaciCaricati = SchermataRegistrazioneFarmaci.getFarmaci();
        String farmaciMancanti = controllaQuantita(farmaciConsegnati,farmaciCaricati);
        boolean caricamentoCorretto = farmaciMancanti.equals("");
        db.caricaFarmaci(farmaciCaricati);
        db.modificaStatoInCaricato(this.ordine.getIdOrdine());
        try {
            if (!caricamentoCorretto) {
                AvvisoCaricamentoParziale avvisoCaricamentoParziale = new AvvisoCaricamentoParziale(farmaciMancanti, entry, this);
                avvisoCaricamentoParziale.start(new Stage());
            } else {
                RegistrazioneCompletata registrazioneCompletata = new RegistrazioneCompletata(this);
                registrazioneCompletata.start(new Stage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String controllaQuantita(ArrayList<Farmaco> farmaciConsegnati, ArrayList<EntryMagazzinoFarmacia> farmaciCaricati ) {
        String farmaciMancanti = "";
        for(int i=0; i<farmaciCaricati.size(); i++) {
            int quantitaConsegnata = farmaciConsegnati.get(i).getQuantita();
            int quantitaCaricata = ((Spinner<Integer>)farmaciCaricati.get(i).getStrumenti().getChildren().get(0)).getValue();
            if (quantitaCaricata != quantitaConsegnata) {
                farmaciMancanti = farmaciMancanti.concat(farmaciConsegnati.get(i).getNome() + "\t" + (quantitaConsegnata-quantitaCaricata) + "\n");
            }
        }
        return farmaciMancanti;
    }

    /**
     * Metodo che viene richiamato quando si fa click sul pulsante indietro
     * @param substage sotto-stage della ListaOrdini da distuggere
     */
    void clickSuIndietro(Stage substage) {
        this.stageRegistrazioneFarmaci.close();
        ListaOrdini.update();
        this.stage.show();
    }

    /** Metodo che viene richiamata quando si fa click sul pulsante chiudi
     * @param substage sotto-stage della ListaOrdini da distruggere
     */
    void clickSuChiudi(Stage substage) {
        this.stageRegistrazioneFarmaci.close();
        substage.close();
        ListaOrdini.update();
        this.stage.show();
    }
}
