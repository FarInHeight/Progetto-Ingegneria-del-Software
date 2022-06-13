package com.progetto.farmacia.ordini;

import com.progetto.entity.*;
import com.progetto.entity.Farmaco;
import com.progetto.interfacciaDatabase.InterfacciaFarmacia;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Classe che si occupa di modellare la control {@code VerificaCorrettezzaControl} che si occupa di verificare la correttezza di un ordine e
 * di registrarlo nel database dell'azienda
 */
public class VerificaCorrettezzaOrdineControl {

    private Stage stage; //stage del form ordine
    private Farmacia farmacia;

    @SuppressWarnings("FieldCanBeLocal")
    private ListaOrdini refListaOrdini;  //riferimento a lista ordini
    private Object control;

    private EntryListaOrdini entry;  // entry dell'ordine eventuale da eliminare
    private ArrayList<LottoOrdinato> lottiModifica;

    private ArrayList<Farmaco> farmaci;  //farmaci richiesti
    private LinkedList<Lotto> lotti;  //lotti presenti nel magazzino azienda

    @SuppressWarnings("FieldMayBeFinal")
    private ArrayList<Farmaco> farmaciParzialmenteDisponibili; //farmaci con disponibilità parziale
    @SuppressWarnings("FieldMayBeFinal")
    private ArrayList<Lotto> lottiParzialmenteDisponibili; //lotti relativi a farmaci con disponibilità parziale
    @SuppressWarnings("FieldMayBeFinal")
    private ArrayList<Farmaco> farmaciDisponibili;  //farmaci disponibili
    @SuppressWarnings("FieldMayBeFinal")
    private ArrayList<Lotto> lottiDisponibili;  //lotti relativi a farmaci disponibili
    @SuppressWarnings("FieldMayBeFinal")
    private ArrayList<Farmaco> farmaciNonDisponibili;  //farmaci non disponibili
    private ArrayList<String> farmaciScadenza;  //farmaci non disponibili
    private LocalDate dataConsegna;

    /**
     * Istanzia un oggetto di tipo @code VerificaCorrettezzaControl} dati in input i farmaci da ordinare, la farmacia che ha effettuati l'ordine.
     * il riferimneto alla schermata del form ordine e la data di consegna deisderata
     * @param farmaci farmaci da ordinare
     * @param farmacia farmacia che ha effettuato l'ordine
     * @param stage riferimento alla schermata del form ordine
     * @param dataConsegna data consegna desiderata dalla farmacia
     */
    public VerificaCorrettezzaOrdineControl(ArrayList<Farmaco> farmaci, Farmacia farmacia, Stage stage, LocalDate dataConsegna, Object control) {
        this.setStage(stage);
        this.setFarmaci(farmaci);
        this.setFarmacia(farmacia);
        this.farmaciParzialmenteDisponibili = new ArrayList<>();
        this.lottiParzialmenteDisponibili = new ArrayList<>();
        this.farmaciDisponibili = new ArrayList<>();
        this.lottiDisponibili = new ArrayList<>();
        this.farmaciNonDisponibili = new ArrayList<>();
        this.setDataConsegna(dataConsegna);
        this.control = control;
    }

    /**
     * Istanzia un oggetto di tipo @code VerificaCorrettezzaControl} dati in input i farmaci da ordinare, la farmacia che ha effettuato l'ordine,
     * il riferimneto alla schermata del form ordine, la entry corrispondente all'ordine da modificare, il riferimento alla lista ordini e la data di consegna deisderata
     * @param farmaci farmaci da ordinare
     * @param farmacia farmacia che ha effettuato l'ordine
     * @param stage riferimento alla schermata del form ordine
     * @param entry entry della lista ordini relativa all'ordine da modificare
     * @param refListaOrdini riferimento alla lista ordini
     * @param dataConsegna data di consegna desiderata dalla farmacia
     */
    public VerificaCorrettezzaOrdineControl(ArrayList<Farmaco> farmaci, Farmacia farmacia, Stage stage, EntryListaOrdini entry, ListaOrdini refListaOrdini, LocalDate dataConsegna, Object control) {
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
        this.setDataConsegna(dataConsegna);
        this.control = control;
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
            if (this.dataConsegna.plusMonths(2).isAfter(lotto.getDataScadenza())) {
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
        if(this.farmaciParzialmenteDisponibili.size() > 0 && this.farmaciDisponibili.size() > 0) {
            db.elaboraOrdine(this.lottiParzialmenteDisponibili, this.farmaciParzialmenteDisponibili, this.lottiDisponibili, this.farmaciDisponibili, this.dataConsegna);
            db.aggiornaLotti(this.lottiParzialmenteDisponibili, this.farmaciParzialmenteDisponibili);
        } else if(this.farmaciParzialmenteDisponibili.size() > 0) {
            db.elaboraOrdine(this.lottiParzialmenteDisponibili, this.farmaciParzialmenteDisponibili, this.dataConsegna);
            db.aggiornaLotti(this.lottiParzialmenteDisponibili, this.farmaciParzialmenteDisponibili);
        } else if(this.farmaciDisponibili.size()>0){
            db.elaboraOrdine(this.lottiDisponibili, this.farmaciDisponibili, this.dataConsegna);
            db.aggiornaLotti(this.lottiDisponibili, this.farmaciDisponibili);
        }

        //Metto i farmaci restanti in un ordine prenotato
        db.prenotaOrdineNonPeriodico(this.farmaciNonDisponibili, this.dataConsegna);

        //Se siamo in modifica aggiorniamo la lista ordini e rimuovo l'ordine precedente
        if (this.entry != null) {
            //aggiornamento lista ordini
            db.cancellaOrdine(this.entry.getIdOrdine(), this.entry.getOrdine().getStato());
            ListaOrdini.update();
        }

        //Mostra il messaggio di conferma
        MessaggioConfermaOrdine messaggioConfermaOrdine = new MessaggioConfermaOrdine(control);
        try {
            messaggioConfermaOrdine.start(this.stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void effettuaOrdine() {
        InterfacciaFarmacia db = new InterfacciaFarmacia();
        //Creo l'ordine
        db.elaboraOrdine(this.lottiDisponibili, this.farmaciDisponibili, this.dataConsegna);
        db.aggiornaLotti(this.lottiDisponibili, this.farmaciDisponibili);

        //Se siamo in modifica aggiorno la lista ordini e rimuovo l'ordine precedente
        if (this.entry != null) {
            db.cancellaOrdine(this.entry.getIdOrdine(), this.entry.getOrdine().getStato());
            ListaOrdini.update();
        }

        //Mostra il messaggio di conferma
        MessaggioConfermaOrdine messaggioConfermaOrdine = new MessaggioConfermaOrdine(control);
        try {
            messaggioConfermaOrdine.start(this.stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo che viene richiamato quando si fa un click sul pulsante {@code caonferma} dell'avviso scadenza
     * @param event evento relativo alla pressione del tasto conferma
     * @param tipo tipo dell'ordine
     */
    void clickSuConfermaOrdine(ActionEvent event, int tipo) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();  // chiudo l'avviso
        if (tipo == 0) {
            this.effettuaOrdine();
        } else {
            this.effettuaOrdineParziale();
        }

    }

    /**
     * Metodo che viene richiamato quando si fa un click sul pulsante {@code annulla} del form ordine
     * @param event evento relativo alla pressione del tasto annulla
     */
    void clickSuAnnullaOrdine(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();  // chiudo l'avviso
        if (entry != null && entry.getOrdine().getStato()!=3) {
            InterfacciaFarmacia db = new InterfacciaFarmacia();
            db.ricreaOrdine(lottiModifica);
        }
    }

    /**
     * Metodo che viene richiamato quando si fa un click sul pulsante {@code caonferma} dell'avviso quantità
     * @param event evento relativo alla pressione del tasto conferma
     */
    void clickSuConferma(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();  // chiudo l'avviso
        ArrayList<Lotto> lottiTotali = new ArrayList<>();
        if (!lottiDisponibili.isEmpty()){
            lottiTotali.addAll(lottiDisponibili);
        }
        if (!lottiParzialmenteDisponibili.isEmpty()){
            lottiTotali.addAll(lottiParzialmenteDisponibili);
        }
        if(verificaScadenza(lottiTotali)) {
            creaAvvisoScadenza(1);
        } else {
            this.effettuaOrdineParziale();
        }
    }
    /**
     * Permette di avviare la control che si occupa di verificare la correttezza di un ordine e registrarlo nel database dell'azienda
     */
    public void start() {
        InterfacciaFarmacia db = new InterfacciaFarmacia();
        if (entry != null && entry.getOrdine().getStato() != 3) {
            this.lottiModifica = db.getLottiAssociati(this.entry.getIdOrdine());
            db.modificaFarmaciOrdinati(this.entry.getIdOrdine());
        }

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
}