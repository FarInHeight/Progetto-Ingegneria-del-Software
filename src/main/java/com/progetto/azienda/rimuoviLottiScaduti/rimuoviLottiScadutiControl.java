package com.progetto.azienda.rimuoviLottiScaduti;

import com.progetto.dbInterface.InterfacciaAzienda;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class rimuoviLottiScadutiControl {

    /**
     * Rimuove tutti i Lotti del database la cui data di scadenza preceda la data odierna
     */
    public void rimuoviLottiScaduti() {

        InterfacciaAzienda db = new InterfacciaAzienda();
        ResultSet lotti = db.getLotti();

        try {
            if (lotti != null) {
                while (lotti.next()) {
                    LocalDate data_scadenza = lotti.getDate("Data_scadenza").toLocalDate();
                    if (data_scadenza.compareTo(LocalDate.now()) < 0) {
                        db.rimuoviLotto(lotti.getInt("ID_lotto"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
