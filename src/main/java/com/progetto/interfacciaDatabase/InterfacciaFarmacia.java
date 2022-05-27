package com.progetto.interfacciaDatabase;

import com.progetto.entity.EntryFormOrdine;
import com.progetto.entity.Farmaco;
import com.progetto.entity.EntryMagazzinoFarmacia;
import com.progetto.farmacia.SchermataPrincipaleFarmacia;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Classe che contiene i metodi necessari per richiedere dati al database dell'Azienda e della Catena Farmaceutica
 * per conto di un {@code Farmacista}
 */
public class InterfacciaFarmacia {
    /**
     * Getter per ottenere un lista di oggetti della classe {@code EntryFormOrdine} riferiti ai farmaci presenti
     * nel datatabse dell'Azienda
     * @return lista di farmaci come entry del form ordine
     */
    public ArrayList<EntryFormOrdine> getFarmaciEntry() {
        ArrayList<EntryFormOrdine> lista = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbazienda", "root","password")){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from farmaco");
            while(resultSet.next()) {
                String nome = resultSet.getString("nome");
                String principioAttivo = resultSet.getString("principio_attivo");
                lista.add(new EntryFormOrdine(nome, principioAttivo));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Getter per ottenere un lista di oggetti della classe {@code Farmaco} per una particolare {@code Farmacia}
     * @return lista di farmaci
     */
    public ArrayList<Farmaco> getFarmaci(int idFarmacia) {
        ArrayList<Farmaco> lista = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbcatena", "root","password")){
            PreparedStatement statement = connection.prepareStatement("select * from farmaco where farmacia_id_farmacia = ?");
            statement.setInt(1, idFarmacia);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                String nome = resultSet.getString("nome");
                LocalDate dataScadenza = resultSet.getDate("data_scadenza").toLocalDate();
                String principio_attivo = resultSet.getString("principio_attivo");
                int tipo = resultSet.getInt("tipo");
                int quantita = resultSet.getInt("quantita");
                lista.add(new Farmaco(nome, principio_attivo, tipo, dataScadenza, quantita));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void rimuoviFarmaco(int idFarmacia, Farmaco farmaco) {
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbcatena", "root","password")){
            PreparedStatement statement = connection.prepareStatement("delete from farmaco where farmacia_id_farmacia = ? and nome = ? and data_scadenza = ?");
            statement.setInt(1, idFarmacia);
            statement.setString(2, farmaco.getNome());
            statement.setString(3, farmaco.getDataScadenza().toString());
            ResultSet resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
