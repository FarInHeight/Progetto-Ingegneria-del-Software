package com.progetto.dbInterface;

import com.progetto.entity.EntryListaSegnalazioni;

import java.sql.*;
import java.util.ArrayList;

public class InterfacciaAddetto {
    public ArrayList<EntryListaSegnalazioni> getSegnalazioni() {
        // EntryListaSegnalazioni(int idSegnalazione, int idOrdine, String riepilogoOrdine, String nomeFarmacia,
        // String recapitoTelefonicoFarmacia, String commento, LocalDate data)
        ArrayList<EntryListaSegnalazioni> segnalazioni = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbazienda", "root","password")){
            PreparedStatement statement = connection.prepareStatement("select * " +
                    "from segnalazione as s, ordine as o, composizione as c, lotto as l, farmaco as f" +
                    " where s.ordine_id_ordine = o.id_ordine and o.id_ordine = c.ordine_id_ordine and" +
                    "c.lotto_id_lotto = l.id_lotto and l.farmaco_nome = f.nome");

            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return segnalazioni;
    }
}
