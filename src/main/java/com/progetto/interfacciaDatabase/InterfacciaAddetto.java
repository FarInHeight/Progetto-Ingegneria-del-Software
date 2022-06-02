package com.progetto.interfacciaDatabase;

import com.progetto.entity.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Classe che contiene i metodi necessari per richiedere dati al database dell'Azienda per conto di un {@code AddettoAzienda}
 */
public class InterfacciaAddetto {
    /**
     * Ritorna le segnalazioni presenti nel database
     * @return oggetto di tipo {@code ArrayList<EntryListaSegnalazioni>} contenente la lista delle segnalazioni
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
            CadutaConnessioneControl c = new CadutaConnessioneControl();
            c.start();
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
     * Permette di eliminare una segnalazione nel database dell'Azienda dato in inpit l'ID
     * @param idSegnalazione ID della segnalazione
     */
    public void eliminaSegnalazione(int idSegnalazione) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dbAzienda", "root","password")){
            PreparedStatement statement = connection.prepareStatement("delete from segnalazione where id_segnalazione = ?");
            statement.setInt(1, idSegnalazione);
            statement.executeUpdate();
        } catch (SQLException e) {
            CadutaConnessioneControl c = new CadutaConnessioneControl();
            c.start();
        }
    }

    /**
     * Ritorna i farmaci presenti nel datatabse dell'Azienda
     * @return oggetto di tipo {@code ArrayList<EntryFormOrdine>} contenente la lista di farmaci
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
            CadutaConnessioneControl c = new CadutaConnessioneControl();
            c.start();
        }
        return lista;
    }

    /**
     * Ritorna tutti i lotti attualmente contenuti nel database
     * @return oggetto di tipo {@code LinkedList<Lotto>} contenente tutti i Lotti del database
     */
    public LinkedList<Lotto> getLotti() {

        LinkedList<Lotto> lotti = new LinkedList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dbAzienda", "root","password")){
            Statement statement = connection.createStatement();
            ResultSet resultLotti = statement.executeQuery("select * from lotto where data_scadenza is not null order by data_scadenza asc");
            while (resultLotti.next()) {
                lotti.add(new Lotto(resultLotti));
            }
        } catch (SQLException e) {
            CadutaConnessioneControl c = new CadutaConnessioneControl();
            c.start();
        }
        return lotti;
    }

    /**
     * Aggiorna i lotti nel magazzino dell'azienda
     * @param lotti lotti da aggiornare
     * @param farmaci farmaci contenuti negli ordini
     */
    @SuppressWarnings("IfStatementWithIdenticalBranches")
    public void aggiornaLotti(ArrayList<Lotto> lotti, ArrayList<Farmaco> farmaci) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dbAzienda", "root", "password")) {
            //Inserisco l'Ordine
            PreparedStatement statement = connection.prepareStatement("update lotto set n_ordinati = ? where id_lotto = ?");
            for(Farmaco farmaco : farmaci){
                for(Lotto lotto : lotti){
                    if(farmaco.getNome().compareTo(lotto.getNomeFarmaco()) == 0){
                        PreparedStatement statementPrecedente = connection.prepareStatement("select n_ordinati from lotto where id_lotto = ?");
                        statementPrecedente.setInt(1,lotto.getIdLotto());
                        ResultSet result = statementPrecedente.executeQuery();
                        result.next();
                        int nOrdinatiPrecedente = result.getInt("n_ordinati");
                        if(lotto.getQuantitaContenuta()-lotto.getQuantitaOrdinata() > farmaco.getQuantita()){
                            statement.setInt(1, farmaco.getQuantita()+nOrdinatiPrecedente);
                            statement.setInt(2,lotto.getIdLotto());
                            statement.executeUpdate();
                        }
                        else{
                            statement.setInt(1,lotto.getQuantitaContenuta()-lotto.getQuantitaOrdinata()+nOrdinatiPrecedente);
                            statement.setInt(2,lotto.getIdLotto());
                            statement.executeUpdate();
                        }
                    }
                }
            }
        }catch (SQLException e){
            CadutaConnessioneControl c = new CadutaConnessioneControl();
            c.start();
        }
    }
    /**
     * Aggiunge un ordine in elaborazione
     * Associa all'ordine dei lotti
     * @param ordine ordine da aggiungere
     * @param lotti lotti da collegare all'ordine
     * @param farmaci farmaci contenuti nell'ordine
     * @param idFarmacia ID della farmacia per cui si sta creando l'ordine
     */
    @SuppressWarnings("IfStatementWithIdenticalBranches")
    public void elaboraOrdineNonPeriodico(Ordine ordine, ArrayList<Lotto> lotti, ArrayList<Farmaco> farmaci, int idFarmacia) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dbAzienda", "root", "password")) {
            //Inserisco l'Ordine
            PreparedStatement statement = connection.prepareStatement("insert into ordine values (null,?,2,1,0,null,?)");
            statement.setDate(1, Date.valueOf(ordine.getDataConsegna()));
            statement.setInt(2, idFarmacia);
            statement.executeUpdate();

            //Ottengo il nuovo id
            Statement statementOrdine = connection.createStatement();
            ResultSet ultimoOrdine = statementOrdine.executeQuery("select id_ordine " +
                    "from ordine " +
                    "order by id_ordine desc " +
                    "limit 1");
            ultimoOrdine.next();
            int ultimoIdOrdine = ultimoOrdine.getInt("id_ordine");

            //collego i lotti all'ordine
            for (Farmaco farmaco : farmaci) {
                for (Lotto lotto : lotti) {
                    if (lotto.getNomeFarmaco().compareTo(farmaco.getNome()) == 0) {
                        if (lotto.getQuantitaContenuta() - lotto.getQuantitaOrdinata() > farmaco.getQuantita()) {
                            statement = connection.prepareStatement("insert into composizione values (?,?,?)");
                            statement.setInt(1, farmaco.getQuantita());
                            statement.setInt(2, ultimoIdOrdine);
                            statement.setInt(3, lotto.getIdLotto());
                            statement.executeUpdate();
                        } else {
                            farmaco.setQuantita(farmaco.getQuantita()-(lotto.getQuantitaContenuta()-lotto.getQuantitaOrdinata()));
                            statement = connection.prepareStatement("insert into composizione values (?,?,?)");
                            statement.setInt(1, lotto.getQuantitaContenuta()-lotto.getQuantitaOrdinata());
                            statement.setInt(2, ultimoIdOrdine);
                            statement.setInt(3, lotto.getIdLotto());
                            statement.executeUpdate();
                        }
                    }
                }
            }

        }catch (SQLException e){
            CadutaConnessioneControl c = new CadutaConnessioneControl();
            c.start();
        }
    }

    /**
     * Aggiunge un ordine non periodico in prenotazione con quantita e farmaco specificato in input
     * Associa all'ordine dei nuovi lotti
     * @param farmaco farmaco da ordinare
     * @param idFarmacia ID della farmacia per cui si sta creando l'ordine
     */
    public void prenotaOrdineNonPeriodico(Farmaco farmaco, int idFarmacia) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dbAzienda", "root","password")){
            //Inserisco l'Ordine
            PreparedStatement statement = connection.prepareStatement("insert into ordine values (null,null,2,3,0,null,?)");
            statement.setInt(1, idFarmacia);
            statement.executeUpdate();

            //Ottengo il nuovo id
            Statement statementOrdine = connection.createStatement();
            ResultSet ultimoOrdine = statementOrdine.executeQuery("select id_ordine " +
                    "from ordine " +
                    "order by id_ordine desc " +
                    "limit 1");
            ultimoOrdine.next();
            int ultimoIdOrdine = ultimoOrdine.getInt("id_ordine");

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
            statement.setString(2,farmaco.getNome());
            statement.executeUpdate();

            //collego il lotto all'ordine
            statement = connection.prepareStatement("insert into composizione values (?,?,?)");
            statement.setInt(1,farmaco.getQuantita());
            statement.setInt(2,ultimoIdOrdine);
            statement.setInt(3,ultimoIdLotto+1);
            statement.executeUpdate();

        } catch (SQLException e) {
            CadutaConnessioneControl c = new CadutaConnessioneControl();
            c.start();
        }
    }
}