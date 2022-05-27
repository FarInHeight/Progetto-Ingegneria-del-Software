package com.progetto.interfacciaDatabase;

import com.progetto.entity.EntryFormOrdine;
import com.progetto.entity.EntryListaSegnalazioni;
import com.progetto.entity.Farmaco;
import com.progetto.entity.EntryMagazzinoFarmacia;
import com.progetto.entity.Farmaco;
import com.progetto.farmacia.SchermataPrincipaleFarmacia;

import java.sql.*;
import java.util.ArrayList;

/**
 * Classe che contiene i metodi necessari per richiedere dati al database dell'Azienda e della Catena Farmaceutica
 * per conto di un {@code Farmacista}
 */
public class InterfacciaFarmacia {
    /**
     * Getter per ottenere un lista di oggetti della classe {@code Farmaco} che l'Azienda può produrre
     * @return lista di segnalazioni
     */
    public ArrayList<EntryFormOrdine> getFarmaci() {
        ArrayList<EntryFormOrdine> lista = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbazienda", "root","password")){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from farmaco");
            while(resultSet.next()) {
                String nome = resultSet.getString("nome");
                String principio_attivo = resultSet.getString("principio_attivo");
                lista.add(new EntryFormOrdine(nome, principio_attivo));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;

    /**
     * Metodo che ritorna entry della {@code SchermataMagazzino} contenenti tutti i farmaci nel magazzino della farmacia
     * @return entry contenenti i farmaci nel magazzino
     */
    public ArrayList<EntryMagazzinoFarmacia> getFarmaciMagazzino() {
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
