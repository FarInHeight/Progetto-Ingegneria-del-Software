package com.progetto.interfacciaDatabase;

import com.progetto.entity.EntryListaSpedizioni;
import com.progetto.entity.LottoOrdinato;
import com.progetto.entity.Ordine;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class InterfacciaCorriere {

    /**
     * Ritorna tutti gli ordini in elaborazione con data di consegna odierna
     * @return ordini in elaborazione con data di consegna odierna
     */
    public ArrayList<EntryListaSpedizioni> getOrdiniGiornalieri() {
        ArrayList<EntryListaSpedizioni> spedizioni = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dbAzienda", "root","password")){
            PreparedStatement statement = connection.prepareStatement("select * from ordine,composizione,lotto,farmacia " +
                    "where stato = 1 AND id_ordine = ordine_id_ordine AND id_lotto = lotto_id_lotto AND farmacia_id_farmacia = id_farmacia AND data_consegna = ?" +
                    "ORDER BY id_ordine");
            statement.setDate(1,Date.valueOf(LocalDate.now()));
            ResultSet resultOrdini = statement.executeQuery();
            int previousID = -1;
            while(resultOrdini.next()) {
                if (previousID == resultOrdini.getInt("id_ordine")) {
                    spedizioni.get(spedizioni.size()-1).getOrdine().addLotto(new LottoOrdinato(resultOrdini));
                } else {
                    spedizioni.add(new EntryListaSpedizioni(new Ordine(resultOrdini)));
                }
                previousID = resultOrdini.getInt("id_ordine");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return spedizioni;
    }

    public void modificaStatoInSpedizione(int id_ordine) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dbAzienda", "root","password")){
            PreparedStatement statement = connection.prepareStatement("update ordine set stato = 2 where id_ordine = ?");
            statement.setInt(1,id_ordine);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
