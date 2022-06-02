package com.progetto.interfacciaDatabase;

import com.progetto.entity.EntryListaSpedizioni;
import com.progetto.entity.LottoOrdinato;
import com.progetto.entity.Ordine;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Classe che permette al corriere di interfacciarsi col database
 */
public class InterfacciaCorriere {

    /**
     * Ritorna tutti gli ordini in elaborazione con data di consegna odierna
     * @return oggetto di tipo {@code ArrayList<EntryListaSpedizioni>} contenente gli ordini in elaborazione con data di consegna odierna
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

    /**
     * Modifica lo stato di un ordine in elaborazione, cambiandolo in spedizione
     * @param idOrdine ordine di cui modificare lo stato
     */
    public void modificaStatoInSpedizione(int idOrdine) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dbAzienda", "root","password")){
            PreparedStatement statement = connection.prepareStatement("update ordine " +
                    "set stato = 2 " +
                    "where id_ordine = ? AND stato = 1");
            statement.setInt(1,idOrdine);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Modifica lo stato di un ordine in spedizione, cambiandolo in elaborazione
     * @param idOrdine ordine di cui modificare lo stato
     */
    public void modificaStatoInElaborazione(int idOrdine) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dbAzienda", "root","password")){
            PreparedStatement statement = connection.prepareStatement("update ordine " +
                    "set stato = 1 " +
                    "where id_ordine = ? AND stato = 2");
            statement.setInt(1,idOrdine);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Modifica lo stato di un ordine in spedizione, cambiandolo in consegnato
     * @param idOrdine ordine di cui modificare lo stato
     */
    public void modificaStatoInConsegnato(int idOrdine, String nominativo) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dbAzienda", "root","password")){
            PreparedStatement statement = connection.prepareStatement("update ordine " +
                    "set stato = 4, firma_consegna = ?" +
                    "where id_ordine = ? AND stato = 2");
            statement.setInt(2,idOrdine);
            statement.setString(1,nominativo);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Rimuove dai lotti del database i farmaci consegnati con un ordine
     * @param lotto lotto associato ad un ordine di cui rimuovere farmaci
     */
    public void rimuoviLottiConsegnati(LottoOrdinato lotto) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dbAzienda", "root","password")){
            PreparedStatement statementQuantitaLotti = connection.prepareStatement("select * " +
                    "from lotto " +
                    "where id_lotto = ?");
            statementQuantitaLotti.setInt(1,lotto.getIdLotto());
            ResultSet lottoRicevuto = statementQuantitaLotti.executeQuery();
            lottoRicevuto.next();
            int nuovaQuantitaContenuta = lottoRicevuto.getInt("n_contenuti") - lotto.getQuantitaOrdine();
            int nuovaQuantitaOrdinata = lottoRicevuto.getInt("n_ordinati") - lotto.getQuantitaOrdine();

            PreparedStatement statement = connection.prepareStatement("update lotto " +
                    "set n_contenuti = ?, n_ordinati = ? " +
                    "where id_lotto = ?");
            statement.setInt(1,nuovaQuantitaContenuta);
            statement.setInt(2,nuovaQuantitaOrdinata);
            statement.setInt(3,lotto.getIdLotto());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Crea un ordine in stato di prenotazione a partire ad un ordine consegnato
     * @param ordine ordine consegnato
     */
    public void prenotaOrdine(Ordine ordine) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dbAzienda", "root","password")){

            //Ottengo il nuovo id ordine
            int ultimoIdOrdine = getLastIdOrdine();
            //Inserisco l'Ordine
            PreparedStatement statement = connection.prepareStatement("insert into ordine values (?,?,?,3,?,null,?)");
            statement.setInt(1,ultimoIdOrdine+1);
            statement.setDate(2,Date.valueOf(LocalDate.now().plusWeeks(ordine.getPeriodo())));
            statement.setInt(2,ordine.getTipo());
            statement.setInt(3,ordine.getPeriodo());
            statement.setInt(4,ordine.getIdFarmacia());
            statement.executeUpdate();

            //Aggiungo i lotti vuoti
            ArrayList<LottoOrdinato> lottiDaPrenotare = ordine.getLottiContenuti();
            for (LottoOrdinato lotto : lottiDaPrenotare) {
                //Ottengo l'ultimo id lotto
                int ultimoIdLotto = getLastIdLotto();

                //aggiungo il lotto vuoto
                statement = connection.prepareStatement("insert into lotto values (?,?,0,0,?)");
                statement.setInt(1,ultimoIdLotto+1);
                statement.setDate(2,Date.valueOf(LocalDate.now().plusYears(2)));
                statement.setString(3,lotto.getNomeFarmaco());
                statement.executeUpdate();

                //collego il lotto all'ordine
                statement = connection.prepareStatement("insert into composizione values (?,?,?)");
                statement.setInt(1,lotto.getQuantitaOrdine());
                statement.setInt(2,ultimoIdOrdine+1);
                statement.setInt(3,ultimoIdLotto+1);
                statement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getLastIdOrdine() {
        int lastId = 0;
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dbAzienda", "root","password")) {
            Statement statementOrdine = connection.createStatement();
            ResultSet ultimoOrdine = statementOrdine.executeQuery("select id_ordine " +
                    "from ordine " +
                    "order by id_ordine desc " +
                    "limit 1");
            if (ultimoOrdine.next()) {
                lastId = ultimoOrdine.getInt("id_ordine");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lastId;
    }

    private int getLastIdLotto() {
        int lastId = 0;
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dbAzienda", "root","password")) {
            Statement statementLotto = connection.createStatement();
            ResultSet ultimoLotto = statementLotto.executeQuery("select id_lotto " +
                    "from lotto " +
                    "order by id_lotto desc " +
                    "limit 1");
            if (ultimoLotto.next()) {
                lastId = ultimoLotto.getInt("id_lotto");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lastId;
    }

}
