package com.progetto.interfacciaDatabase;

import com.progetto.entity.EntryFormOrdine;
import com.progetto.entity.EntryListaSegnalazioni;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Classe che contiene i metodi necessari per richiedere dati al database dell'Azienda per conto di un {@code AddettoAzienda}
 */
public class InterfacciaAddetto {
    /**
     * Getter per ottenere le segnalazioni presenti nel database come oggetti di tipo {@code EntryListaSegnalazioni}
     * @return lista di segnalazioni
     */
    public ArrayList<EntryListaSegnalazioni> getSegnalazioni() {
        // EntryListaSegnalazioni(int idSegnalazione, int idOrdine, String riepilogoOrdine, int idFarmacia, String nomeFarmacia,
        // String recapitoTelefonicoFarmacia, String commento, LocalDate data)
        ArrayList<EntryListaSegnalazioni> segnalazioni = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbazienda", "root","password")){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select s.id_segnalazione, o.id_ordine," +
                    " c.n_farmaci, l.farmaco_nome, f.id_farmacia, f.nome, f.recapito_telefonico, " +
                    " s.commento, s.data_generazione" +
                    " from segnalazione as s, ordine as o, composizione as c, lotto as l, farmacia as f" +
                    " where s.ordine_id_ordine = o.id_ordine and o.id_ordine = c.ordine_id_ordine and" +
                    " c.lotto_id_lotto = l.id_lotto and o.farmacia_id_farmacia = f.id_farmacia");
            while(resultSet.next()) {
                int idSegnalazione = resultSet.getInt("id_segnalazione");
                int idOrdine = resultSet.getInt("id_ordine");
                String riepilogoOrdine = resultSet.getString("n_farmaci") + " " + resultSet.getString("farmaco_nome");
                int idFarmacia = resultSet.getInt("id_farmacia");
                String nomeFarmacia = resultSet.getString("nome");
                String recapitoTelefonicoFarmacia = resultSet.getString("recapito_telefonico");
                String commento = resultSet.getString("commento");
                LocalDate data = resultSet.getDate("data_generazione").toLocalDate();
                EntryListaSegnalazioni entry = checkInLista(idSegnalazione, segnalazioni);
                if(entry == null) {
                    segnalazioni.add(new EntryListaSegnalazioni(idSegnalazione, idOrdine, riepilogoOrdine, idFarmacia, nomeFarmacia, recapitoTelefonicoFarmacia, commento, data));
                } else {
                    entry.setRiepilogoOrdine(entry.getRiepilogoOrdine() + "\n" + riepilogoOrdine);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return segnalazioni;
    }

    private EntryListaSegnalazioni checkInLista(int idSegnalazione, ArrayList<EntryListaSegnalazioni> segnalazioni) {
        for(int i = 0; i < segnalazioni.size(); ++i) {
            EntryListaSegnalazioni entry = segnalazioni.get(i);
            if(entry.getIdSegnalazione() == idSegnalazione) {
                return entry;
            }
        }
        return null;
    }

    /**
     * Metodo che permette di eliminare una segnalazione nel database dell'Azienda conoscendone l'ID
     * @param idSegnalazione ID della segnalazione
     */
    public void eliminaSegnalazione(int idSegnalazione) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dbAzienda", "root","password")){
            PreparedStatement statement = connection.prepareStatement("delete from segnalazione where id_segnalazione = ?");
            statement.setInt(1, idSegnalazione);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
}
