package com.progetto.azienda.magazzino;

import com.progetto.interfacciaDatabase.InterfacciaAzienda;
import com.progetto.entity.Lotto;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Classe che si occupa di rimuovere tutti i {@code lotti} scauti dal database dell'Azienda
 */
public class RimuoviLottiScadutiControl {

    /**
     * Rimuove tutti i Lotti del database la cui data di scadenza preceda la data odierna
     */
    public void rimuoviLottiScaduti() {

        InterfacciaAzienda db = new InterfacciaAzienda();
        ArrayList<Lotto> lotti = db.getLotti();

        for (Lotto lotto : lotti) {
            if(lotto.getDataScadenza().compareTo(LocalDate.now()) < 0) {
                db.rimuoviLotto(lotto.getIdLotto());
            }
        }
    }

}
