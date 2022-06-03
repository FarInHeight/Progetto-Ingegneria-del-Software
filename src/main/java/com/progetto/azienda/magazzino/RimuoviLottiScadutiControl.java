package com.progetto.azienda.magazzino;

import com.progetto.interfacciaDatabase.InterfacciaAzienda;
import com.progetto.entity.Lotto;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Classe che modella la control {@code RimuoviLottiScadutiControl}
 */
public class RimuoviLottiScadutiControl {

    /**
     * Rimuove tutti i Lotti del database la cui data di scadenza preceda la data odierna
     */
    public void start() {

        InterfacciaAzienda db = new InterfacciaAzienda();
        ArrayList<Lotto> lotti = db.getLotti();

        for (Lotto lotto : lotti) {
            if(controllaScadenza(lotto.getDataScadenza())) {
                db.rimuoviLotto(lotto.getIdLotto());
            }
        }
    }

    private boolean controllaScadenza(LocalDate dataScadenza) {
        return dataScadenza.compareTo(LocalDate.now()) < 0;
    }

}
