package com.progetto.azienda.produzione;

import com.progetto.interfacciaDatabase.InterfacciaAzienda;
import com.progetto.entity.Lotto;
import com.progetto.entity.Ordine;
import java.util.ArrayList;

/** Classe che modella la control {@code GestioneProduzioneControl} che gestisce la produzione di farmaci da parte dell'azienda.
 * Produce nuovi farmaci in base ai farmaci già nel magazzino e alla percentule di farmaci ordinati.
 * Prdouce i farmaci necessari per far partire gli ordini in stato di prenotazione
 */
public class GestioneProduzioneControl {
    /** Permette di avviare la gestione della produzione dell'azienda
     */
    public void start() {

        InterfacciaAzienda db = new InterfacciaAzienda();

        aggiungiLottiProdotti(db);
        aggiungiLottiPrenotati(db);

    }

    /*
     Aggiunge nuovi Lotti appena prodotti al database dell'Azienda.
     La quantità di Farmaco contenuta in ogni Lotto dipende dalla quantità di Farmaci di quel tipo già ordinati.
     */
    private void aggiungiLottiProdotti(InterfacciaAzienda db) {
        ArrayList<Lotto> lotti = db.getLotti();

        for(Lotto lotto : lotti) {
            if (lotto.getQuantitaContenuta() != 0) {
                Lotto newLotto = Lotto.lottoProdotto(lotto);
                if (controllaQuantita(lotto)) {
                    db.addLotto(newLotto);
                } else {
                    newLotto.setQuantitaContenuta((int) (newLotto.getQuantitaContenuta() * 1.1));
                    db.addLotto(newLotto);
                }
            }
        }
    }

    /*
     Inserisce Faramci appena prodotti nei Lotti degli Ordini in Prenotazione.
     Modifica quindi lo stato degli Ordini in Elaborazione.
     */
    private void aggiungiLottiPrenotati(InterfacciaAzienda db) {

        ArrayList<Ordine> ordini = db.getOrdiniPrenotati();

        for(Ordine ordine : ordini) {
            db.updateQuantitaLotti(ordine);
            db.modificaStatoInElaborazione(ordine.getIdOrdine());
        }
    }

    /*
     Verifica se la quantità di Farmaci ordinati in un Lotto non superi la metà dei Farmaci contenuti
     * il paramentro in input è il lotto su cui effettuare la verifica
     * ritorna true se la quantità ordinata è minore della metà del totale, false altrimenti
     */
    private boolean controllaQuantita(Lotto lotto) {
        return (lotto.getQuantitaOrdinata() <= (0.5*lotto.getQuantitaContenuta()));
    }



}
