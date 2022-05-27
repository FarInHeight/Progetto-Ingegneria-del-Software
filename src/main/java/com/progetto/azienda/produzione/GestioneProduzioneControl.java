package com.progetto.azienda.produzione;

import com.progetto.interfacciaDatabase.InterfacciaAzienda;
import com.progetto.entity.Lotto;
import com.progetto.entity.Ordine;
import java.util.ArrayList;

/**
 * Classe che gestisce la produzione di {@code farmaci} da parte dell'azienda.
 * Produce nuovi farmaci in base ai farmaci già nel magazzino e alla percentule di farmaci ordinati.
 * Prdouce i farmaci necessari per far partire gli {@code ordini} in stato di prenotazione
 */
public class GestioneProduzioneControl {

    public void gestioneProduzione() {

        InterfacciaAzienda db = new InterfacciaAzienda();

        aggiungiLottiProdotti(db);
        aggiungiLottiPrenotati(db);

    }

    /**
     * Aggiunge nuovi Lotti appena prodotti al database dell'Azienda.
     * La quantità di Farmaco contenuta in ogni Lotto dipende dalla quantità di Farmaci di quel tipo già ordinati.
     *
     * @param db istanza dell'interfaccia col database
     */
    private void aggiungiLottiProdotti(InterfacciaAzienda db) {
        ArrayList<Lotto> lotti = db.getLotti();

        for(Lotto lotto : lotti) {
            if (lotto.getQuantitaContenuta() != 0 && lotto.getQuantitaOrdinata() != 0) {
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

    /**
     * Inserisce Faramci appena prodotti nei Lotti degli Ordini in Prenotazione.
     * Modifica quindi lo stato degli Ordini in Elaborazione.
     *
     * @param db istanza dell'interfaccia col database
     */
    private void aggiungiLottiPrenotati(InterfacciaAzienda db) {

        ArrayList<Ordine> ordini = db.getOrdiniPrenotati();

        for(Ordine ordine : ordini) {
            db.updateQuantitaLotti(ordine);
            db.modificaStatoInElaborazione(ordine.getIdOrdine());
        }
    }

    /**
     * Verifica se la quantità di Farmaci ordinati in un Lotto non superi la metà dei Farmaci contenuti
     * @param lotto lotto su cui effettuare al verifica
     * @return true se la quantità ordinata è minore della metà del totale, false altrimenti
     */
    private boolean controllaQuantita(Lotto lotto) {
        return (lotto.getQuantitaOrdinata() <= (0.5*lotto.getQuantitaOrdinata()));
    }



}
