package com.progetto.dbInterface;

import com.progetto.entity.LottoOrdinato;
import com.progetto.entity.Ordine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class InterfacciaCorriere {
    public ArrayList<Ordine> getOrdiniGiornalieri() {
        ArrayList<Ordine> spedizioni = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbAzienda", "azienda","pwd")){
            Statement statement = connection.createStatement();
            ResultSet resultOrdini = statement.executeQuery("select ID_ordine, ID_farmacia, Indirizzo from Ordine,Composizione,Lotto, Farmacia where Stato = 3 AND ID_ordine = Ordine_ID_ordine AND ID_lotto = Lotto_ID_lotto AND Farmacia_ID_farmacia = ID_farmacia ORDER BY ID_ordine");
            int previousID = -1;
            while(resultOrdini.next()) {
                if (previousID == resultOrdini.getInt("ID_ordine")) {
                    spedizioni.get(spedizioni.size()-1).addLotto(new LottoOrdinato(resultOrdini.getInt("ID_lotto"),resultOrdini.getInt("N_farmaci")));
                } else {
                    spedizioni.add(new Ordine(resultOrdini));
                }
                previousID = resultOrdini.getInt("ID_ordine");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return spedizioni;
    }
}
