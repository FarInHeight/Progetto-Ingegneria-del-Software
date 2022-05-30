package com.progetto.farmacia.ordini;

import com.progetto.entity.EntryMagazzinoFarmacia;
import com.progetto.entity.Farmaco;
import com.progetto.entity.Ordine;
import com.progetto.interfacciaDatabase.InterfacciaFarmacia;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;

import java.util.ArrayList;

public class RegistrazioneFarmaciRicevutiControl {

    private Ordine ordine;
    private Stage stage;
    private final InterfacciaFarmacia db;

    public RegistrazioneFarmaciRicevutiControl(Ordine ordine, Stage stage) {
        setOrdine(ordine);
        setStage(stage);
        db = new InterfacciaFarmacia();
    }

    public Ordine getOrdine() {
        return ordine;
    }

    public void setOrdine(Ordine ordine) {
        if (ordine == null) {
            throw new NullPointerException("ordine = null");
        }
        this.ordine = ordine;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        if (stage == null) {
            throw new NullPointerException("stage = null");
        }
        this.stage = stage;
    }

    public void start() {
        ArrayList<EntryMagazzinoFarmacia> farmaci = new ArrayList<>();
        for (Farmaco farmaco:ordine.getFarmaci()) {
            farmaci.add(new EntryMagazzinoFarmacia(farmaco));
        }
        for (EntryMagazzinoFarmacia entry : farmaci) {
            setPulsanti(entry);
        }

        SchermataRegistrazioneFarmaci schermataRegistrazioneFarmaci = new SchermataRegistrazioneFarmaci(farmaci,this,getOrdine().getIdOrdine());
        getStage().hide();
        try {
            schermataRegistrazioneFarmaci.start(getStage());
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

    void clickSuConfermaRegistrazione(Stage stage) {
        ArrayList<Farmaco> farmaciConsegnati = ordine.getFarmaci();
        ArrayList<EntryMagazzinoFarmacia> farmaciCaricati = SchermataRegistrazioneFarmaci.getFarmaci();
        String farmaciMancanti = "";
        boolean caricamentoCorretto = controllaQuantita(farmaciConsegnati,farmaciCaricati,farmaciMancanti);
        db.caricaFarmaci(farmaciCaricati);
        db.modificaStatoInCaricato(getOrdine().getIdOrdine());
        try {
            if (!caricamentoCorretto) {
                AvvisoCaricamentoParziale avvisoCaricamentoParziale = new AvvisoCaricamentoParziale(farmaciMancanti);
                avvisoCaricamentoParziale.start(stage);
            } else {
                RegistrazioneCompletata registrazioneCompletata = new RegistrazioneCompletata();
                registrazioneCompletata.start(stage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean controllaQuantita(ArrayList<Farmaco> farmaciConsegnati, ArrayList<EntryMagazzinoFarmacia> farmaciCaricati, String farmaciMancanti) {
        for(int i=0; i<farmaciCaricati.size(); i++) {
            int quantitaConsegnata = farmaciConsegnati.get(i).getQuantita();
            int quantitaCaricata = ((Spinner<Integer>)farmaciCaricati.get(i).getStrumenti().getChildren().get(0)).getValue();
            if (quantitaCaricata != quantitaConsegnata) {
                farmaciMancanti += farmaciConsegnati.get(i).getNome() + "\t" + (quantitaConsegnata-quantitaCaricata) + "\n";
            }
        }
        return farmaciMancanti.equals("");
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
}