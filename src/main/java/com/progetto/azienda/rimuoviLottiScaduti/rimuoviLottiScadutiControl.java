package com.progetto.azienda.rimuoviLottiScaduti;

import com.progetto.entity.Lotto;
import com.progetto.dbInterface.InterfacciaAzienda;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class rimuoviLottiScadutiControl {

    public void rimuoviLottiScaduti() {

        ResultSet lotti = InterfacciaAzienda.getLotti();

        try {
            if (lotti != null) {
                while (lotti.next()) {
                    LocalDate data_scadenza = lotti.getDate("Data_scadenza").toLocalDate();
                    if (data_scadenza.compareTo(LocalDate.now()) < 0) {
                        InterfacciaAzienda.removeLotto(lotti.getInt("ID_lotto"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }



    }

}
