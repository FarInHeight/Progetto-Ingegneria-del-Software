package com.progetto.farmacia.ordini;

import com.progetto.entity.Farmacia;
import com.progetto.entity.Farmaco;
import com.progetto.entity.Lotto;
import com.progetto.entity.Ordine;
import com.progetto.interfacciaDatabase.InterfacciaFarmacia;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

/**
 * classe che si occupa di verificare la correttezza di un ordine e rgistrarlo nel dbms
 */
public class VerificaCorrettezzaOrdineControl {

    private static Stage stage; //stage del form ordine
    private static Farmacia farmacia;

    private ArrayList<Farmaco> farmaci;  //farmaci richiesti
    private ArrayList<Lotto> lotti;  //lotti presentin nel magazzino azienda

    private static ArrayList<Farmaco> farmaciParzialmenteDisponibili; //farmaci con disponibilità parziale
    private static ArrayList<Lotto> lottiParzialmenteDisponibili; //lotti relativi a farmaci con disponibilità parziale
    private static ArrayList<Farmaco> farmaciDisponibili;  //farmaci disponibili
    private static ArrayList<Lotto> lottiDisponibili;  //lotti relativi a farmaci disponibili
    private static ArrayList<Farmaco> farmaciNonDisponibili;  //farmaci non disponibili

    public VerificaCorrettezzaOrdineControl(ArrayList<Farmaco> farmaci,Farmacia farmacia, Stage stage){
        this.setStage(stage);
        this.setFarmaci(farmaci);
        this.setFarmacia(farmacia);
        VerificaCorrettezzaOrdineControl.farmaciParzialmenteDisponibili = new ArrayList<>();
        VerificaCorrettezzaOrdineControl.lottiParzialmenteDisponibili = new ArrayList<>();
        VerificaCorrettezzaOrdineControl.farmaciDisponibili = new ArrayList<>();
        VerificaCorrettezzaOrdineControl.lottiDisponibili = new ArrayList<>();
        VerificaCorrettezzaOrdineControl.farmaciNonDisponibili = new ArrayList<>();
    }

    private void setStage(Stage stage){
        if(stage == null){
            throw new NullPointerException("stage = null");
        }
        VerificaCorrettezzaOrdineControl.stage = stage;
    }
    private void setFarmacia(Farmacia farmacia){
        if(farmacia == null){
            throw new NullPointerException("farmacia ? null");
        }
        VerificaCorrettezzaOrdineControl.farmacia = farmacia;
    }
    private void setFarmaci(ArrayList<Farmaco> farmaci){
        if(farmaci == null){
            throw new NullPointerException("farmaci == null");
        }
        this.farmaci = farmaci;
    }

    private void setLotti(ArrayList<Lotto> lotti) {
        if(lotti == null){
            throw new NullPointerException("lotti == null");
        }
        this.lotti = lotti;
    }

    private void ottieniLotti(){
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

        for (Farmaco farmaco : this.farmaci) {  //verifico le quantità
            int numeroFarmaci = 0;
            ArrayList<Lotto> lottiTemp = new ArrayList<>();
            for (int i = 0; i < lottiRichiesti.size(); i++) {
                if (farmaco.getNome().compareTo(lottiRichiesti.get(i).getNomeFarmaco()) == 0) {
                    lottiTemp.add(lottiRichiesti.get(i));
                    numeroFarmaci += lottiRichiesti.get(i).getQuantitaContenuta() - lottiRichiesti.get(i).getQuantitaOrdinata();
                }
                if (numeroFarmaci >= farmaco.getQuantita()) {  //ci sono tutti i farmaci
                    VerificaCorrettezzaOrdineControl.farmaciDisponibili.add(farmaco);
                    for (Lotto lottoTemp : lottiTemp) {
                        VerificaCorrettezzaOrdineControl.lottiDisponibili.add(lottoTemp);
                    }
                    break;
                }
                if (i == lottiRichiesti.size() - 1 && numeroFarmaci != 0 && numeroFarmaci < farmaco.getQuantita()) {  //non ci sono abbastanza farmaci
                    VerificaCorrettezzaOrdineControl.farmaciParzialmenteDisponibili.add(farmaco);
                    for (Lotto lottoTemp : lottiTemp) {
                        VerificaCorrettezzaOrdineControl.lottiParzialmenteDisponibili.add(lottoTemp);
                    }
                    break;
                }
                if (i == lottiRichiesti.size() - 1 && numeroFarmaci == 0) {  //non c'è nessun farmaco in magazzino
                    VerificaCorrettezzaOrdineControl.farmaciNonDisponibili.add(farmaco);
                }
            }
        }
    }

    private void verificaScadenza(){
        if(VerificaCorrettezzaOrdineControl.farmaciNonDisponibili.size() == 0 && VerificaCorrettezzaOrdineControl.farmaciParzialmenteDisponibili.size() == 0){  //ci sono abbastanza farmaci per soddisfare l'ordine
            ArrayList<Farmaco> farmaciDisponibiliAvvisoScadenza = new ArrayList<>();
            ArrayList<Lotto> lottiDisponibiliAvvisoScadenza = new ArrayList<>();
            for(Farmaco farmacoDisponibile : VerificaCorrettezzaOrdineControl.farmaciDisponibili){
                for(Lotto lottoDisponibile : VerificaCorrettezzaOrdineControl.lottiDisponibili){
                    if(lottoDisponibile.getNomeFarmaco().compareTo(farmacoDisponibile.getNome()) == 0){
                        if(Period.between(LocalDate.now(), lottoDisponibile.getDataScadenza()).getMonths() < 2){
                            farmaciDisponibiliAvvisoScadenza.add(farmacoDisponibile);
                            lottiDisponibiliAvvisoScadenza.add(lottoDisponibile);
                        }
                    }
                }
            }
            //se ci sono farmaci che scdranno fra meno di 2 mesi
            if(farmaciDisponibiliAvvisoScadenza.size() != 0) {
                String farmaciPerAvviso = "";
                for (Farmaco farmacoInScadenza : farmaciDisponibiliAvvisoScadenza) {
                    farmaciPerAvviso += (farmacoInScadenza.getNome() + "\t" + farmacoInScadenza.getPrincipioAttivo() + "\n");
                }
                AvvisoScadenza avvisoScadenza = new AvvisoScadenza(farmaciPerAvviso, VerificaCorrettezzaOrdineControl.farmacia);
                try {
                    avvisoScadenza.start(new Stage());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
                VerificaCorrettezzaOrdineControl.effettuaOrdine();
            }
        }
        else {//non ci sono abbastanza farmaci per soddisfare l'ordine
            SchermataErroreQuantita schermataErroreQuantita = new SchermataErroreQuantita();
            try {
                schermataErroreQuantita.start(VerificaCorrettezzaOrdineControl.stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void effettuaOrdineParziale(){
        ArrayList<Farmaco> farmaciParzialmenteDisponibiliAvvisoScadenza = new ArrayList<>();
        ArrayList<Lotto> lottiParzialmenteDisponibiliAvvisoScadenza = new ArrayList<>();
        for (Farmaco farmacoParzialmenteDisponibile : VerificaCorrettezzaOrdineControl.farmaciParzialmenteDisponibili) {
            for (Lotto lottoParzialmenteDisponibile : VerificaCorrettezzaOrdineControl.lottiParzialmenteDisponibili) {
                if (lottoParzialmenteDisponibile.getNomeFarmaco().compareTo(farmacoParzialmenteDisponibile.getNome()) == 0) {
                    if (Period.between(LocalDate.now(), lottoParzialmenteDisponibile.getDataScadenza()).getMonths()<2) {
                        farmaciParzialmenteDisponibiliAvvisoScadenza.add(farmacoParzialmenteDisponibile);
                        lottiParzialmenteDisponibiliAvvisoScadenza.add(lottoParzialmenteDisponibile);
                    }
                }
            }
        }

        //se ci sono farmaci che scadranno fra meno di 2 mesi
        if(farmaciParzialmenteDisponibiliAvvisoScadenza.size() != 0) {
            String farmaciPerAvviso = "";
            for (Farmaco farmacoInScadenza : farmaciParzialmenteDisponibiliAvvisoScadenza) {
                farmaciPerAvviso += (farmacoInScadenza.getNome() + "\t" + farmacoInScadenza.getPrincipioAttivo() + "\n");
            }

            AvvisoScadenza avvisoScadenza = new AvvisoScadenza(farmaciPerAvviso, VerificaCorrettezzaOrdineControl.farmacia);
            try {
                avvisoScadenza.start(new Stage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            VerificaCorrettezzaOrdineControl.effettuaOrdine();
        }
    }

    private static void effettuaOrdine(){
        InterfacciaFarmacia db = new InterfacciaFarmacia();
        if(VerificaCorrettezzaOrdineControl.farmaciDisponibili.size() == 0) {
            Ordine ordine = new Ordine(1, 1, VerificaCorrettezzaOrdineControl.farmaciDisponibili, 2, 1, LocalDate.now().plusDays(7), VerificaCorrettezzaOrdineControl.farmacia.getNome(), VerificaCorrettezzaOrdineControl.farmacia.getIndirizzo());
            db.elaboraOrdineNonPeriodico(ordine, VerificaCorrettezzaOrdineControl.lottiDisponibili, VerificaCorrettezzaOrdineControl.farmaciDisponibili);
            db.aggiornaLotti(VerificaCorrettezzaOrdineControl.lottiDisponibili, VerificaCorrettezzaOrdineControl.farmaciDisponibili);
        }
        if(VerificaCorrettezzaOrdineControl.farmaciParzialmenteDisponibili.size() != 0){
            Ordine ordine = new Ordine(1,1,VerificaCorrettezzaOrdineControl.farmaciParzialmenteDisponibili,2,1,LocalDate.now().plusDays(7),VerificaCorrettezzaOrdineControl.farmacia.getNome(),VerificaCorrettezzaOrdineControl.farmacia.getIndirizzo());
            db.elaboraOrdineNonPeriodico(ordine,VerificaCorrettezzaOrdineControl.lottiParzialmenteDisponibili,VerificaCorrettezzaOrdineControl.farmaciParzialmenteDisponibili);
            db.aggiornaLotti(VerificaCorrettezzaOrdineControl.lottiParzialmenteDisponibili,VerificaCorrettezzaOrdineControl.farmaciParzialmenteDisponibili);
            for(Farmaco farmaco : VerificaCorrettezzaOrdineControl.farmaciParzialmenteDisponibili){
                db.prenotaOrdine(farmaco.getNome());
            }
        }
        if(VerificaCorrettezzaOrdineControl.farmaciNonDisponibili.size() != 0){
            for(Farmaco farmaco : VerificaCorrettezzaOrdineControl.farmaciParzialmenteDisponibili){
                db.prenotaOrdine(farmaco.getNome());
            }
        }
        MessaggioConfermaOrdine messaggioConfermaOrdine = new MessaggioConfermaOrdine();
        try {
            messaggioConfermaOrdine.start(VerificaCorrettezzaOrdineControl.stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void clickSuConfermaOrdine(ActionEvent event){
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();  // chiudo l'avviso
        VerificaCorrettezzaOrdineControl.effettuaOrdine();
    }

    static void clickSuAnnullaOrdine(ActionEvent event){
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();  // chiudo l'avviso
    }

    static  void clickSuConferma(ActionEvent event){
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();  // chiudo l'avviso
        VerificaCorrettezzaOrdineControl.effettuaOrdineParziale();

    }
    public void start(){
        this.ottieniLotti();
        this.verificaQuantita();
        this.verificaScadenza();
    }
}
