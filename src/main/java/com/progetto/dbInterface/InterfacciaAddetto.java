package com.progetto.dbInterface;

import com.progetto.entity.EntryListaSegnalazioni;

import java.sql.*;
import java.util.ArrayList;

public class InterfacciaAddetto {
    public ArrayList<EntryListaSegnalazioni> getSegnalazioni() {
        // EntryListaSegnalazioni(int idSegnalazione, int idOrdine, String riepilogoOrdine, int idFarmacia, String nomeFarmacia,
        // String recapitoTelefonicoFarmacia, String commento, LocalDate data)
        ArrayList<EntryListaSegnalazioni> segnalazioni = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbazienda", "root","password")){
            PreparedStatement statement = connection.prepareStatement("select s.id_segnalazione, o.id_ordine," +
                    " c.n_farmaci, l.farmaco_nome, f.id_farmacia, f.nome, f.recapito_telefonico, " +
                    " s.commento, s.data_generazione" +
                    "from segnalazione as s, ordine as o, composizione as c, lotto as l, farmacia as f" +
                    " where s.ordine_id_ordine = o.id_ordine and o.id_ordine = c.ordine_id_ordine and" +
                    "c.lotto_id_lotto = l.id_lotto and o.farmacia_id_farmacia = f.id_farmacia");
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return segnalazioni;
    }
}
