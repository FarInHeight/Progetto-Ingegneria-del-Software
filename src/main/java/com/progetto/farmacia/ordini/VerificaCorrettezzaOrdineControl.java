package com.progetto.farmacia.ordini;

import com.progetto.entity.*;
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
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * classe che si occupa di verificare la correttezza di un ordine e rgistrarlo nel dbms
 */
public class VerificaCorrettezzaOrdineControl {

    private Stage stage; //stage del form ordine
    private Farmacia farmacia;

    private ListaOrdini refListaOrdini;  //riferimento a lista ordini

    private EntryListaOrdini entry;  // entry dell'ordine eventuale da eliminare

    private ArrayList<Farmaco> farmaci;  //farmaci richiesti
    private LinkedList<Lotto> lotti;  //lotti presenti nel magazzino azienda

    private ArrayList<Farmaco> farmaciParzialmenteDisponibili; //farmaci con disponibilità parziale
    private ArrayList<Lotto> lottiParzialmenteDisponibili; //lotti relativi a farmaci con disponibilità parziale
    private ArrayList<Farmaco> farmaciDisponibili;  //farmaci disponibili
    private ArrayList<Lotto> lottiDisponibili;  //lotti relativi a farmaci disponibili
    private ArrayList<Farmaco> farmaciNonDisponibili;  //farmaci non disponibili
    private ArrayList<String> farmaciScadenza;  //farmaci non disponibili
    private LocalDate dataConsegna;

    public VerificaCorrettezzaOrdineControl(ArrayList<Farmaco> farmaci, Farmacia farmacia, Stage stage, LocalDate dataConsegna) {
        this.setStage(stage);
        this.setFarmaci(farmaci);
        this.setFarmacia(farmacia);
        this.farmaciParzialmenteDisponibili = new ArrayList<>();
        this.lottiParzialmenteDisponibili = new ArrayList<>();
        this.farmaciDisponibili = new ArrayList<>();
        this.lottiDisponibili = new ArrayList<>();
        this.farmaciNonDisponibili = new ArrayList<>();
        this.setDataConsegna(dataConsegna);
    }

    public VerificaCorrettezzaOrdineControl(ArrayList<Farmaco> farmaci, Farmacia farmacia, Stage stage, EntryListaOrdini entry, ListaOrdini refListaOrdini) {
        this.setStage(stage);
        this.setFarmaci(farmaci);
        this.setFarmacia(farmacia);
        this.setEntry(entry);
        this.setRefListaOrdini(refListaOrdini);
        this.farmaciParzialmenteDisponibili = new ArrayList<>();
        this.lottiParzialmenteDisponibili = new ArrayList<>();
        this.farmaciDisponibili = new ArrayList<>();
        this.lottiDisponibili = new ArrayList<>();
        this.farmaciNonDisponibili = new ArrayList<>();
    }

    private void setRefListaOrdini(ListaOrdini refListaOrdini){
        if(refListaOrdini == null){
            throw new NullPointerException("ref lista ordini = null");
        }
        this.refListaOrdini = refListaOrdini;
    }

    private void setEntry(EntryListaOrdini entry) {
        if (entry == null) {
            throw new IllegalArgumentException("entry dell'ordine = null");
        }
        this.entry = entry;
    }

    private void setStage(Stage stage) {
        if (stage == null) {
            throw new NullPointerException("stage = null");
        }
        this.stage = stage;
    }

    private void setFarmacia(Farmacia farmacia) {
        if (farmacia == null) {
            throw new NullPointerException("farmacia ? null");
        }
        this.farmacia = farmacia;
    }

    private void setFarmaci(ArrayList<Farmaco> farmaci) {
        if (farmaci == null) {
            throw new NullPointerException("farmaci == null");
        }
        this.farmaci = farmaci;
    }

    private void setLotti(LinkedList<Lotto> lotti) {
        if (lotti == null) {
            throw new NullPointerException("lotti == null");
        }
        this.lotti = lotti;
    }

    public void setDataConsegna(LocalDate dataConsegna) {
        if (dataConsegna == null) {
            throw new NullPointerException("data consegna = null");
        }
        this.dataConsegna = dataConsegna;
    }

    private void ottieniLotti() {
        InterfacciaFarmacia db = new InterfacciaFarmacia();
        this.setLotti(db.getLotti());
    }

    private boolean verificaQuantita() { // deve ritornare true se ci sono abbastanza farmaci nei lotti e false altrimenti
        ArrayList<Lotto> lottiRichiesti = new ArrayList<>();

        //memorizzo tutti lotti che hanno disponibilità per i farmaci richiesti
        for (Farmaco farmaco : this.farmaci) {
            for (Lotto lotto : this.lotti) {
                if (lotto.getNomeFarmaco().compareTo(farmaco.getNome()) == 0 && (lotto.getQuantitaContenuta() - lotto.getQuantitaOrdinata()) > 0) {
                    lottiRichiesti.add(lotto);
                }
            }
        }

        //se non esistono lotti con disponibilità per tutti i farmaci richiesti esco subito
        if (lottiRichiesti.size() == 0) {
            this.farmaciNonDisponibili.addAll(farmaci);
            return false;
        }

        //verifico le quantità
        for (Farmaco farmaco : this.farmaci) {
            int numeroFarmaci = 0;
            ArrayList<Lotto> lottiTemp = new ArrayList<>();
            //Cerco tutti i lotti contenente il farmaco
            for (int i = 0; i < lottiRichiesti.size(); i++) {

                //Se il lotto contiene il farmaco lo aggiungo alla lista dei lotti
                if (farmaco.getNome().compareTo(lottiRichiesti.get(i).getNomeFarmaco()) == 0) {
                    lottiTemp.add(lottiRichiesti.get(i));
                    numeroFarmaci += lottiRichiesti.get(i).getQuantitaContenuta() - lottiRichiesti.get(i).getQuantitaOrdinata();
                }

                //Se ho la quantita necessaria di farmaco passo al successivo
                if (numeroFarmaci >= farmaco.getQuantita()) {
                    this.farmaciDisponibili.add(farmaco);
                    this.lottiDisponibili.addAll(lottiTemp);
                    break;
                }

                //Se non ci sono tutti i farmaci mi segno la quantita disponibile e la quantita mancante
                if (i == lottiRichiesti.size() - 1 && numeroFarmaci > 0) {
                    this.farmaciParzialmenteDisponibili.add(farmaco);
                    this.lottiParzialmenteDisponibili.addAll(lottiTemp);
                    this.farmaciNonDisponibili.add(new Farmaco(farmaco.getNome(),farmaco.getQuantita()-numeroFarmaci,farmaco.getPrincipioAttivo()));
                    break;
                }

                //Se non c'è nessun farmaco in magazzino segno il farmaco come non disponibile
                if (i == lottiRichiesti.size() - 1 && numeroFarmaci == 0) {
                    this.farmaciNonDisponibili.add(farmaco);
                    break;
                }
            }
        }

        return farmaciNonDisponibili.size() == 0;
    }

    private boolean verificaScadenza(ArrayList<Lotto> lottiDisponibili) {

        this.farmaciScadenza = new ArrayList<>();
        //Per ogni lotto, se la data di scadenza è troppo vicina aggiungo il farmaco contenuto alla lista
        for (Lotto lotto : lottiDisponibili) {
            if (Period.between(LocalDate.now(), lotto.getDataScadenza()).getMonths() < 2) {
                if (!farmaciScadenza.contains(lotto.getNomeFarmaco())) {
                    farmaciScadenza.add(lotto.getNomeFarmaco());
                }
            }
        }

        //Se la lista non è vuota creo l'avviso
        return farmaciScadenza.size() != 0;
    }

    private void creaAvvisoScadenza(int tipo) {
        String farmaciPerAvviso = "";
        for (String farmacoInScadenza : this.farmaciScadenza) {
            farmaciPerAvviso += farmacoInScadenza + "\n";
        }
        AvvisoScadenza avvisoScadenza = new AvvisoScadenza(farmaciPerAvviso, this.farmacia, this, tipo);
        try {
            avvisoScadenza.start(new Stage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void effettuaOrdineParziale() {
        InterfacciaFarmacia db = new InterfacciaFarmacia();

        //Creo l'ordine coi farmaci che ci sono
        db.elaboraOrdine(this.lottiParzialmenteDisponibili, this.farmaciParzialmenteDisponibili, this.dataConsegna);
        db.aggiornaLotti(this.lottiParzialmenteDisponibili, this.farmaciParzialmenteDisponibili);

        //Metto i farmaci restanti in un ordine prenotato
        db.prenotaOrdineNonPeriodico(this.farmaciNonDisponibili, this.dataConsegna);

        //Se siamo in modifica rimuove l'ordine precedente e ritorna i farmaci ai lotti
        if (this.entry != null) {
            db.modificaFarmaciOrdinati(this.entry.getIdOrdine());
            db.cancellaOrdine(this.entry.getIdOrdine());

            //aggiornamento lista ordini
            ArrayList<EntryListaOrdini> ordini = db.getOrdini(this.farmacia.getIdFarmacia());
            ListaOrdini.getOrdini().clear();
            for (EntryListaOrdini entry : ordini) {
                this.setPulsanti(entry);
                ListaOrdini.getOrdini().add(entry);
            }
            ListaOrdini.update();
        }

        //Mostra il messaggio di conferma
        MessaggioConfermaOrdine messaggioConfermaOrdine = new MessaggioConfermaOrdine(this);
        try {
            messaggioConfermaOrdine.start(this.stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void effettuaOrdine() {
        InterfacciaFarmacia db = new InterfacciaFarmacia();

        db.elaboraOrdine(this.lottiDisponibili, this.farmaciDisponibili, this.dataConsegna);
        db.aggiornaLotti(this.lottiDisponibili, this.farmaciDisponibili);

        //Se siamo in modifica rimuove l'ordine precedente e ritorna i farmaci ai lotti
        if (this.entry != null) {
            db.modificaFarmaciOrdinati(this.entry.getIdOrdine());
            db.cancellaOrdine(this.entry.getIdOrdine());

            //aggiornamento lista ordini
            ArrayList<EntryListaOrdini> ordini = db.getOrdini(this.farmacia.getIdFarmacia());
            ListaOrdini.getOrdini().clear();
            for (EntryListaOrdini entry : ordini) {
                this.setPulsanti(entry);
                ListaOrdini.getOrdini().add(entry);
            }
            ListaOrdini.update();
        }

        //Mostra il messaggio di conferma
        MessaggioConfermaOrdine messaggioConfermaOrdine = new MessaggioConfermaOrdine(this);
        try {
            messaggioConfermaOrdine.start(this.stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void clickSuConfermaOrdine(ActionEvent event, int tipo) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();  // chiudo l'avviso
        if (tipo == 0) {
            this.effettuaOrdine();
        } else {
            this.effettuaOrdineParziale();
        }

    }

    void clickSuAnnullaOrdine(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();  // chiudo l'avviso
    }

    void clickSuConferma(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();  // chiudo l'avviso
        if(verificaScadenza(this.lottiParzialmenteDisponibili)) {
            creaAvvisoScadenza(1);
        } else {
            effettuaOrdineParziale();
        }
    }

    public void start() {

        this.ottieniLotti();
        if (verificaQuantita()) {
            if (verificaScadenza(this.lottiDisponibili)) {
                creaAvvisoScadenza(0);
            } else {
                effettuaOrdine();
            }
        } else {
            SchermataErroreQuantita schermataErroreQuantita = new SchermataErroreQuantita(this);
            try {
                schermataErroreQuantita.start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void setPulsanti(EntryListaOrdini entry) {
        // creazione dei pulsanti
        Button carica = new Button("CARICA");
        carica.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                VerificaCorrettezzaOrdineControl.this.clickSuCarica(entry);
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
                VerificaCorrettezzaOrdineControl.this.clickSuModifica(entry);
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
                VerificaCorrettezzaOrdineControl.this.clickSuCancella(entry);
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
     * Metodo che viene richiamato quando si fa un click sul pulsante {@code carica} di una entry della {@code ListaOrdini}.
     * @param entry ordine da caricare
     */
    void clickSuCarica(EntryListaOrdini entry) {
        try {
            this.refListaOrdini.caricaOrdine(entry);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo che viene richiamato quando si fa un click sul pulsante {@code modifica} di una entry della {@code ListaOrdini}.
     * @param entry ordine da modificare
     */
    void clickSuModifica(EntryListaOrdini entry) {
        try {
            this.refListaOrdini.modificaOrdine(entry);
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
            this.refListaOrdini.cancellaOrdine(entry);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
