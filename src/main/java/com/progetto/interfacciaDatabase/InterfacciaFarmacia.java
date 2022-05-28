package com.progetto.interfacciaDatabase;

import com.progetto.entity.EntryFormOrdine;
import com.progetto.entity.Farmaco;
import com.progetto.entity.EntryMagazzinoFarmacia;
import com.progetto.entity.LottoOrdinato;
import com.progetto.entity.Lotto;
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

    /**
     * Metodo che ritorna tutti i {@code Lotti} attualmente contenuti nel database
     * @return ArrayList di Lotto contenente tutti i Lotti del database
     */
    public ArrayList<Lotto> getLotti() {

        ArrayList<Lotto> lotti = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dbAzienda", "root","password")){
            Statement statement = connection.createStatement();
            ResultSet resultLotti = statement.executeQuery("SELECT * " +
                    "FROM lotto " +
                    "WHERE data_scadenza IS NOT NULL ");
            while (resultLotti.next()) {
                lotti.add(new Lotto(resultLotti));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lotti;
    }


    /**
     * Metodo che rimuove delle quantita dai farmaci nel db della catena
     * @param nome nome del farmaco di cui modificare la quantita
     * @param dataScadenza data di scadenza del farmaco di cui modificare la quantita
     * @param quantitaDaRimuovere quantita da rimuovere
     * @param quantitaAttuale quantita attuale di farmaco nel magazzino
     */
    public void rimuoviQuantita(String nome, LocalDate dataScadenza, int quantitaDaRimuovere, int quantitaAttuale) {

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dbCatena", "root","password")){
            if (quantitaAttuale == quantitaDaRimuovere) {
                PreparedStatement statement = connection.prepareStatement("delete from farmaco " +
                        "where farmacia_id_farmacia = ? AND nome = ? AND data_scadenza = ?");
                statement.setInt(1,SchermataPrincipaleFarmacia.getFarmacia().getIdFarmacia());
                statement.setString(2,nome);
                statement.setDate(3,Date.valueOf(dataScadenza));
                statement.executeUpdate();
            } else if (quantitaAttuale > quantitaDaRimuovere) {
                PreparedStatement statement = connection.prepareStatement("update farmaco " +
                        "set quantita = ? " +
                        "where farmacia_id_farmacia = ? AND nome = ? AND data_scadenza = ?");
                statement.setInt(1,quantitaAttuale-quantitaDaRimuovere);
                statement.setInt(2,SchermataPrincipaleFarmacia.getFarmacia().getIdFarmacia());
                statement.setString(3,nome);
                statement.setDate(4,Date.valueOf(dataScadenza));
                statement.executeUpdate();
            } else {
                throw new IllegalArgumentException("quantita attuale < quantita da rimuovere");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Farmaco> getFarmaciDaBanco() {
        ArrayList<Farmaco> farmaci = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dbCatena", "root","password")){
            PreparedStatement statement = connection.prepareStatement("select * from farmaco where farmacia_id_farmacia = ? AND tipo = 0");
            statement.setInt(1,SchermataPrincipaleFarmacia.getFarmacia().getIdFarmacia());
            ResultSet resulFarmaci = statement.executeQuery();
            while (resulFarmaci.next()) {
                farmaci.add(new Farmaco(resulFarmaci));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return farmaci;
    }

    public int controllaQuantitaOrdinata(String nome) {

        int quantita = 0;
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dbazienda", "root","password")){
            PreparedStatement statement = connection.prepareStatement("select sum(n_farmaci) as totale " +
                    "from ordine,composizione,lotto " +
                    "where id_ordine = ordine_id_ordine AND id_lotto = lotto_id_lotto AND farmacia_id_farmacia = ? AND farmaco_nome = ?");
            statement.setInt(1,SchermataPrincipaleFarmacia.getFarmacia().getIdFarmacia());
            statement.setString(2,nome);
            ResultSet resulFarmaci = statement.executeQuery();
            resulFarmaci.next();
            quantita = resulFarmaci.getInt("totale");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return quantita;
    }

    /**
     * Aggiunge un ordine in prenotazione con 200 quantità del farmaco specificato.
     * Associa all'ordine dei nuovi lotti
     * @param nome nome del farmaco da ordinare
     */
    public void prenotaOrdine(String nome) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dbAzienda", "root","password")){
            //Inserisco l'Ordine
            PreparedStatement statement = connection.prepareStatement("insert into ordine values (null,null,2,3,0,null,?)");
            statement.setInt(1,SchermataPrincipaleFarmacia.getFarmacia().getIdFarmacia());
            statement.executeUpdate();

            //Ottengo il nuovo id
            Statement statementOrdine = connection.createStatement();
            ResultSet ultimoOrdine = statementOrdine.executeQuery("select id_ordine " +
                    "from ordine " +
                    "order by id_ordine desc " +
                    "limit 1");
            ultimoOrdine.next();
            int ultimoIdOrdine = ultimoOrdine.getInt("id_ordine");


            //Aggiungo il lotto vuoto
            //Ottengo l'ultimo id
            Statement statementLotto = connection.createStatement();
            ResultSet ultimoLotto = statementLotto.executeQuery("select id_lotto " +
                    "from lotto " +
                    "order by id_lotto desc " +
                    "limit 1");
            ultimoLotto.next();
            int ultimoIdLotto = ultimoLotto.getInt("id_lotto");

            //aggiungo il lotto vuoto
            statement = connection.prepareStatement("insert into lotto values (?,null,0,0,?)");
            statement.setInt(1,ultimoIdLotto+1);
            statement.setString(2,nome);
            statement.executeUpdate();

            //collego il lotto all'ordine
            statement = connection.prepareStatement("insert into composizione values (200,?,?)");
            statement.setInt(1,ultimoIdOrdine);
            statement.setInt(2,ultimoIdLotto+1);
            statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
