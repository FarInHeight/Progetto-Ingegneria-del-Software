package com.progetto.interfacciaDatabase;

import com.progetto.entity.EntryMagazzinoFarmacia;
import com.progetto.entity.Farmaco;
import com.progetto.farmacia.SchermataPrincipaleFarmacia;

import java.sql.*;
import java.util.ArrayList;

/**
 * Classe che permette alla farmacia di interagire col database della catena
 */
public class InterfacciaFarmacia {

    /**
     * Metodo che ritorna entry della {@code SchermataMagazzino} contenenti tutti i farmaci nel magazzino della farmacia
     * @return entry contenenti i farmaci nel magazzino
     */
    public ArrayList<EntryMagazzinoFarmacia> getFarmaci() {
        ArrayList<EntryMagazzinoFarmacia> farmaci = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dbCatena", "root","password")){
            PreparedStatement statement = connection.prepareStatement("select * from farmaco where farmacia_id_farmacia = ?");
            statement.setInt(1,SchermataPrincipaleFarmacia.getFarmacia().getIdFarmacia());
            ResultSet resulFarmaci = statement.executeQuery();
            while (resulFarmaci.next()) {
                farmaci.add(new EntryMagazzinoFarmacia(new Farmaco(resulFarmaci)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return farmaci;
    }
}
