package com.progetto.farmacia.ordine;

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
import java.util.ArrayList;

/**
 * classe che si occupa di verificare la correttezza di un ordine e rgistrarlo nel dbms
 */
public class VerificaCorrettezzaOrdineControl {

    private Stage stage; //stage del form ordine
    private static Farmacia farmacia;

    private ArrayList<Farmaco> farmaci;  //farmaci richiesti
    private ArrayList<Lotto> lotti;  //lotti presentin nel magazzino azienda

    private ArrayList<Farmaco> farmaciParzialmenteDisponibili; //farmaci con disponibilità parziale
    private ArrayList<Lotto> lottiParzialmenteDisponibili; //lotti relativi a farmaci con disponibilità parziale
    private static ArrayList<Farmaco> farmaciDisponibili;  //farmaci disponibili
    private static ArrayList<Lotto> lottiDisponibili;  //lotti relativi a farmaci disponibili
    private static ArrayList<Farmaco> farmaciNonDisponibili;  //farmaci non disponibili

    public VerificaCorrettezzaOrdineControl(ArrayList<Farmaco> farmaci,Farmacia farmacia, Stage stage){
        this.setStage(stage);
        this.setFarmaci(farmaci);
        this.setFarmacia(farmacia);
        this.farmaciParzialmenteDisponibili = new ArrayList<>();
        this.lottiParzialmenteDisponibili = new ArrayList<>();
        VerificaCorrettezzaOrdineControl.farmaciDisponibili = new ArrayList<>();
        VerificaCorrettezzaOrdineControl.lottiDisponibili = new ArrayList<>();
        VerificaCorrettezzaOrdineControl.farmaciNonDisponibili = new ArrayList<>();
    }

    private void setStage(Stage stage){
        if(stage == null){
            throw new NullPointerException("stage = null");
        }
        this.stage = stage;
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
                    this.farmaciParzialmenteDisponibili.add(farmaco);
                    for (Lotto lottoTemp : lottiTemp) {
                        this.lottiParzialmenteDisponibili.add(lottoTemp);
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
        ArrayList<Farmaco> farmaciDisponibiliAvvisoScadenza = new ArrayList<>();
        ArrayList<Lotto> lottiDisponibiliAvvisoScadenza = new ArrayList<>();
        if(VerificaCorrettezzaOrdineControl.farmaciNonDisponibili.size() == 0 && this.farmaciParzialmenteDisponibili.size() == 0){  //ci sono abbastanza farmaci per soddisfare l'ordine
            for(Farmaco farmacoDisponibile : VerificaCorrettezzaOrdineControl.farmaciDisponibili){
                for(Lotto lottoDisponibile : VerificaCorrettezzaOrdineControl.lottiDisponibili){
                    if(lottoDisponibile.getNomeFarmaco().compareTo(farmacoDisponibile.getNome()) == 0){
                        if(Duration.between(LocalDate.now(), lottoDisponibile.getDataScadenza()).toDays() >= 59){
                            farmaciDisponibiliAvvisoScadenza.add(farmacoDisponibile);
                            lottiDisponibiliAvvisoScadenza.add(lottoDisponibile);
                        }
                    }
                }
            }

            String farmaciPerAvviso = "";
            for(Farmaco farmacoInScadenza : farmaciDisponibiliAvvisoScadenza){
                farmaciPerAvviso += (farmacoInScadenza.getNome() + "\t" + farmacoInScadenza.getPrincipioAttivo() + "\n");
            }

            AvvisoScadenza avvisoScadenza = new AvvisoScadenza(farmaciPerAvviso, VerificaCorrettezzaOrdineControl.farmacia);
            try {
                avvisoScadenza.start(this.stage);
            } catch (IOException e) {
                e.printStackTrace();
            }


            //il farmacista ha premuto il pulsante anulla per la scadenza (caso d'uso termina)


        }
        else {//non ci sono abbastanza farmaci per soddisfare l'ordine
            //viene mostrato a schermo la SchermataErroreQuantita


            //alternativa presione pulsante conferma per ricevere parte dei farmaci
            ArrayList<Farmaco> farmaciParzialmenteDisponibiliAvvisoScadenza = new ArrayList<>();
            ArrayList<Lotto> lottiParzialmenteDisponibiliAvvisoScadenza = new ArrayList<>();
            for (Farmaco farmacoParzialmenteDisponibile : this.farmaciParzialmenteDisponibili) {
                for (Lotto lottoParzialmenteDisponibile : this.lottiParzialmenteDisponibili) {
                    if (lottoParzialmenteDisponibile.getNomeFarmaco().compareTo(farmacoParzialmenteDisponibile.getNome()) == 0) {
                        if (Duration.between(LocalDate.now(), lottoParzialmenteDisponibile.getDataScadenza()).toDays() >= 59) {
                            farmaciParzialmenteDisponibiliAvvisoScadenza.add(farmacoParzialmenteDisponibile);
                            lottiParzialmenteDisponibiliAvvisoScadenza.add(lottoParzialmenteDisponibile);
                        }
                    }
                }
            }

            String farmaciPerAvviso = "";
            for(Farmaco farmacoInScadenza : farmaciParzialmenteDisponibiliAvvisoScadenza){
                farmaciPerAvviso += (farmacoInScadenza.getNome() + "\t" + farmacoInScadenza.getPrincipioAttivo() + "\n");
            }

            AvvisoScadenza avvisoScadenza = new AvvisoScadenza(farmaciPerAvviso, VerificaCorrettezzaOrdineControl.farmacia);
            try {
                avvisoScadenza.start(this.stage);
            } catch (IOException e) {
                e.printStackTrace();
            }


            //il farmacista ha premuto il pulsante conferma per le scadenze
            Ordine ordine = new Ordine(0,1,this.farmaciParzialmenteDisponibili,2,1,LocalDate.now().plusDays(7),this.farmacia.getNome(),this.farmacia.getIndirizzo());
            //aggiungi ordine nel db per i farmaci presenti
            //aggiornamento quantità lotti del db
            //aggiunta ordine nel db in stato di prenotato per i farmaci non presenti in magazzino
            //mostra messaggio di conferma ordine

            //il farmacista ha premuto il pulsante anulla per la scadenza (caso d'uso termina)

            //il farmacista ha premuto il pulsante annulla per ricevere parte dei farmaci (caso d'uso termina)
        }

    }

    static void clickSuConfermaOrdine(ActionEvent event){
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();  // chiudo l'avviso
        Ordine ordine = new Ordine(0,1,VerificaCorrettezzaOrdineControl.farmaciDisponibili,2,1,LocalDate.now().plusDays(7) ,VerificaCorrettezzaOrdineControl.farmacia.getNome(),VerificaCorrettezzaOrdineControl.farmacia.getIndirizzo());
        //aggiungi ordine nel db
        //aggiornamento quantità lotti del db
        //mostra messaggio conferma ordine

    }

    static void clickSuAnnullaOrdine(ActionEvent event){
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();  // chiudo l'avviso


    }


    public void start(){
        this.ottieniLotti();
        this.verificaQuantita();
        //verificaScadenza()
    }
}
