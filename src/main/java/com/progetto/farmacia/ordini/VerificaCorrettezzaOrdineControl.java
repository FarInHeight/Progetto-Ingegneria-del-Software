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

    private int periodo;  //periodo dell'eventuale ordine periodico

    private EntryListaOrdini entry;  // entry dell'ordine eventuale da eliminare

    private ArrayList<Farmaco> farmaci;  //farmaci richiesti
    private LinkedList<Lotto> lotti;  //lotti presenti nel magazzino azienda

    private ArrayList<Farmaco> farmaciParzialmenteDisponibili; //farmaci con disponibilità parziale
    private ArrayList<Lotto> lottiParzialmenteDisponibili; //lotti relativi a farmaci con disponibilità parziale
    private ArrayList<Farmaco> farmaciDisponibili;  //farmaci disponibili
    private ArrayList<Lotto> lottiDisponibili;  //lotti relativi a farmaci disponibili
    private ArrayList<Farmaco> farmaciNonDisponibili;  //farmaci non disponibili

    public VerificaCorrettezzaOrdineControl(ArrayList<Farmaco> farmaci, Farmacia farmacia, Stage stage) {
        this.setStage(stage);
        this.setFarmaci(farmaci);
        this.setFarmacia(farmacia);
        this.farmaciParzialmenteDisponibili = new ArrayList<>();
        this.lottiParzialmenteDisponibili = new ArrayList<>();
        this.farmaciDisponibili = new ArrayList<>();
        this.lottiDisponibili = new ArrayList<>();
        this.farmaciNonDisponibili = new ArrayList<>();
    }

    public VerificaCorrettezzaOrdineControl(ArrayList<Farmaco> farmaci, Farmacia farmacia, Stage stage, EntryListaOrdini entry, int periodo, ListaOrdini refListaOrdini) {
        this.setStage(stage);
        this.setFarmaci(farmaci);
        this.setFarmacia(farmacia);
        this.setEntry(entry);
        this.setPeriodo(periodo);
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

    private void setPeriodo(int periodo) {
        if (periodo < 0) {
            throw new IllegalArgumentException("periodo < 0");
        }
        this.periodo = periodo;
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

    private void ottieniLotti() {
        InterfacciaFarmacia db = new InterfacciaFarmacia();
        this.setLotti(db.getLotti());
    }

    private void verificaQuantita() {
        ArrayList<Lotto> lottiRichiesti = new ArrayList<>(); //lotti che hanno i farmaci richiesti

        for (Farmaco farmaco : this.farmaci) {  //memorizzo i lotti che hanno i farmaci richiesti
            for (Lotto lotto : this.lotti) {
                if (lotto.getNomeFarmaco().compareTo(farmaco.getNome()) == 0 && (lotto.getQuantitaContenuta() - lotto.getQuantitaOrdinata()) > 0) {
                    lottiRichiesti.add(lotto);
                }
            }
        }

        if (lottiRichiesti.size() == 0) {  //se non ci sono lotti con i farmaci richiesti con farmaci disponibili
            for (Farmaco farmaco : this.farmaci) {
                this.farmaciNonDisponibili.add(farmaco);
            }
        }

        for (Farmaco farmaco : this.farmaci) {  //verifico le quantità
            int numeroFarmaci = 0;
            ArrayList<Lotto> lottiTemp = new ArrayList<>();
            for (int i = 0; i < lottiRichiesti.size(); i++) {
                if (farmaco.getNome().compareTo(lottiRichiesti.get(i).getNomeFarmaco()) == 0) {
                    lottiTemp.add(lottiRichiesti.get(i));
                    numeroFarmaci += lottiRichiesti.get(i).getQuantitaContenuta() - lottiRichiesti.get(i).getQuantitaOrdinata();
                }
                if (numeroFarmaci >= farmaco.getQuantita()) {  //ci sono tutti i farmaci
                    this.farmaciDisponibili.add(farmaco);
                    for (Lotto lottoTemp : lottiTemp) {
                        this.lottiDisponibili.add(lottoTemp);
                    }
                    break;
                }
                if (i == lottiRichiesti.size() - 1 && numeroFarmaci > 0 && numeroFarmaci < farmaco.getQuantita()) {  //non ci sono abbastanza farmaci
                    this.farmaciParzialmenteDisponibili.add(farmaco);
                    for (Lotto lottoTemp : lottiTemp) {
                        this.lottiParzialmenteDisponibili.add(lottoTemp);
                    }
                    break;
                }
                if (i == lottiRichiesti.size() - 1 && numeroFarmaci == 0) {  //non c'è nessun farmaco in magazzino
                    this.farmaciNonDisponibili.add(farmaco);
                }
            }
        }
    }

    private void verificaScadenza() {
        if (this.farmaciNonDisponibili.size() == 0 && this.farmaciParzialmenteDisponibili.size() == 0) {  //ci sono abbastanza farmaci per soddisfare l'ordine
            ArrayList<Farmaco> farmaciDisponibiliAvvisoScadenza = new ArrayList<>();
            ArrayList<Lotto> lottiDisponibiliAvvisoScadenza = new ArrayList<>();
            for (Farmaco farmacoDisponibile : this.farmaciDisponibili) {
                for (Lotto lottoDisponibile : this.lottiDisponibili) {
                    if (lottoDisponibile.getNomeFarmaco().compareTo(farmacoDisponibile.getNome()) == 0) {
                        if (Period.between(LocalDate.now(), lottoDisponibile.getDataScadenza()).getMonths() < 2) {
                            farmaciDisponibiliAvvisoScadenza.add(farmacoDisponibile);
                            lottiDisponibiliAvvisoScadenza.add(lottoDisponibile);
                        }
                    }
                }
            }
            //se ci sono farmaci che scdranno fra meno di 2 mesi
            if (farmaciDisponibiliAvvisoScadenza.size() != 0) {
                String farmaciPerAvviso = "";
                for (Farmaco farmacoInScadenza : farmaciDisponibiliAvvisoScadenza) {
                    farmaciPerAvviso += (farmacoInScadenza.getNome() + "\t" + farmacoInScadenza.getPrincipioAttivo() + "\n");
                }
                AvvisoScadenza avvisoScadenza = new AvvisoScadenza(farmaciPerAvviso, this.farmacia, this);
                try {
                    avvisoScadenza.start(new Stage());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                this.effettuaOrdine();
            }
        } else {//non ci sono abbastanza farmaci per soddisfare l'ordine
            SchermataErroreQuantita schermataErroreQuantita = new SchermataErroreQuantita(this);
            try {
                schermataErroreQuantita.start(this.stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void effettuaOrdineParziale() {
        ArrayList<Farmaco> farmaciParzialmenteDisponibiliAvvisoScadenza = new ArrayList<>();
        ArrayList<Lotto> lottiParzialmenteDisponibiliAvvisoScadenza = new ArrayList<>();
        for (Farmaco farmacoParzialmenteDisponibile : this.farmaciParzialmenteDisponibili) {
            for (Lotto lottoParzialmenteDisponibile : this.lottiParzialmenteDisponibili) {
                if (lottoParzialmenteDisponibile.getNomeFarmaco().compareTo(farmacoParzialmenteDisponibile.getNome()) == 0) {
                    if (Period.between(LocalDate.now(), lottoParzialmenteDisponibile.getDataScadenza()).getMonths() < 2) {
                        farmaciParzialmenteDisponibiliAvvisoScadenza.add(farmacoParzialmenteDisponibile);
                        lottiParzialmenteDisponibiliAvvisoScadenza.add(lottoParzialmenteDisponibile);
                    }
                }
            }
        }

        //se ci sono farmaci che scadranno fra meno di 2 mesi
        if (farmaciParzialmenteDisponibiliAvvisoScadenza.size() != 0) {
            String farmaciPerAvviso = "";
            for (Farmaco farmacoInScadenza : farmaciParzialmenteDisponibiliAvvisoScadenza) {
                farmaciPerAvviso += (farmacoInScadenza.getNome() + "\t" + farmacoInScadenza.getPrincipioAttivo() + "\n");
            }

            AvvisoScadenza avvisoScadenza = new AvvisoScadenza(farmaciPerAvviso, this.farmacia, this);
            try {
                avvisoScadenza.start(new Stage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            this.effettuaOrdine();
        }
    }

    private void effettuaOrdine() {
        InterfacciaFarmacia db = new InterfacciaFarmacia();
        if (this.entry != null && this.entry.getOrdine().getTipo() == 1) {  //l'ordine è periodico
            db.inserisciOrdinePeriodico(this.farmaci, this.periodo);  //registro nel db l'ordine periodico
            db.cancellaOrdinePeriodico(this.entry.getOrdine().getIdOrdine());  //cancello ordine vecchio

            //aggiornare lista ordini

        }
        if (this.farmaciDisponibili.size() != 0) {
            Ordine ordine = new Ordine(1, 1, this.farmaciDisponibili, 2, 1, LocalDate.now().plusDays(7), this.farmacia.getNome(), this.farmacia.getIndirizzo());
            db.aggiornaLotti(this.lottiDisponibili, this.farmaciDisponibili);
            db.elaboraOrdineNonPeriodico(ordine, this.lottiDisponibili, this.farmaciDisponibili);

            if (this.entry != null) {
                db.modificaFarmaciOrdinati(this.entry.getIdOrdine());
                db.cancellaOrdine(this.entry.getIdOrdine());

                //aggiornare lista ordini

            }
        }
        if (this.farmaciParzialmenteDisponibili.size() != 0) {
            Ordine ordine = new Ordine(1, 1, this.farmaciParzialmenteDisponibili, 2, 1, LocalDate.now().plusDays(7), this.farmacia.getNome(), this.farmacia.getIndirizzo());
            db.aggiornaLotti(this.lottiParzialmenteDisponibili, this.farmaciParzialmenteDisponibili);
            db.elaboraOrdineNonPeriodico(ordine, this.lottiParzialmenteDisponibili, this.farmaciParzialmenteDisponibili);
            for (Farmaco farmaco : this.farmaciParzialmenteDisponibili) {
                db.prenotaOrdineNonPeriodico(farmaco);
            }

            if (this.entry != null) {
                db.modificaFarmaciOrdinati(this.entry.getIdOrdine());
                db.cancellaOrdine(this.entry.getIdOrdine());

                //aggiornare lista ordini
            }
        }
        if (this.farmaciNonDisponibili.size() != 0) {
            for (Farmaco farmaco : this.farmaciNonDisponibili) {
                db.prenotaOrdineNonPeriodico(farmaco);
            }
        }
            //aggiornamento lista ordini
        ArrayList<EntryListaOrdini> ordini = db.getOrdini(this.farmacia.getIdFarmacia());
        ListaOrdini.getOrdini().clear();
        for (EntryListaOrdini entry : ordini) {
            this.setPulsanti(entry);
            ListaOrdini.getOrdini().add(entry);
        }

        ListaOrdini.update();

        MessaggioConfermaOrdine messaggioConfermaOrdine = new MessaggioConfermaOrdine();
        try {
            messaggioConfermaOrdine.start(this.stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void clickSuConfermaOrdine(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();  // chiudo l'avviso
        this.effettuaOrdine();
    }

    void clickSuAnnullaOrdine(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();  // chiudo l'avviso
    }

    void clickSuConferma(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();  // chiudo l'avviso
        this.effettuaOrdineParziale();

    }

    public void start() {
        if (this.entry != null && this.entry.getOrdine().getTipo() == 1) {
            this.effettuaOrdine();
        } else {
            this.ottieniLotti();
            this.verificaQuantita();
            this.verificaScadenza();
        }
    }

    private void setPulsantiLista(ArrayList<EntryListaOrdini> lista) {
        for(EntryListaOrdini entry : lista) {
            this.setPulsanti(entry);
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
