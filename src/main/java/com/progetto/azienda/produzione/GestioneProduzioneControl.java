package com.progetto.azienda.produzione;

import com.progetto.dbInterface.InterfacciaAzienda;
import com.progetto.entity.Lotto;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class GestioneProduzioneControl {

    public void GestioneProduzione() {

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
        ResultSet lotti = db.getLotti();
        try {
            if (lotti!=null) {
                while (lotti.next()) {
                    if (lotti.getInt("N_quantita") != 0) {
                        Lotto newLotto = new Lotto(lotti);
                        newLotto.setDataScadenza(LocalDate.now().plusYears(2));
                        newLotto.setQuantitaOrdinata(0);
                        if (controllaQuantita(lotti)) {
                            db.addLotto(newLotto);
                        } else {
                            newLotto.setQuantitaContenuta((int) (newLotto.getQuantitaContenuta() * 1.1));
                            db.addLotto(newLotto);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inserisce Faramci appena prodotti nei Lotti degli Ordini in Prenotazione.
     * Modifica quindi lo stato degli Ordini in Elaborazione.
     *
     * @param db istanza dell'interfaccia col database
     */
    private void aggiungiLottiPrenotati(InterfacciaAzienda db) {

        ResultSet ordini = db.getOrdiniPrenotati();
        try {
            if (ordini != null) {
                while(ordini.next()) {
                    db.updateQuantitaLotto(ordini.getInt("N_farmaci"), ordini.getInt("ID_lotto"), Date.valueOf(LocalDate.now().plusYears(2)));
                    db.cambiaStatoInElaborazione(ordini.getInt("ID_ordine"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Verifica se la quantità di Farmaci ordinati in un Lotto non superi la metà dei Farmaci contenuti
     * @param lotto lotto su cui effettuare al verifica
     * @return true se la quantità ordinata è minore della metà del totale, false altrimenti
     * @throws SQLException in caso di errore nel parsing tra tipo SQL e tipo JAVA viene lanciata un'eccezione
     */
    private boolean controllaQuantita(ResultSet lotto) throws SQLException {
        return (lotto.getInt("N_ordinati") <= (0.5*lotto.getInt("N_contenuti")));
    }



}
