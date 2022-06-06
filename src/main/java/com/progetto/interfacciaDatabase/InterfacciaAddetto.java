package com.progetto.interfacciaDatabase;

import com.progetto.addetto.segnalazioni.EntryListaSegnalazioni;
import com.progetto.entity.*;
import com.progetto.entity.Farmaco;

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
            e.printStackTrace();
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
        } catch (Exception e) {
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
            CadutaConnessioneControl c = new CadutaConnessioneControl();
            c.start();
        }
    }

    /**
     * Aggiunge un ordine non periodico in prenotazione con quantita e farmaco specificato in input
     * Associa all'ordine dei nuovi lotti
     * @param farmaci farmaci da ordinare
     * @param dataConsegna data di consegna dell'ordine non periodico
     * @param idFarmacia ID della {@code Farmacia} che effettua l'ordine
     */
    public void prenotaOrdineNonPeriodico(ArrayList<Farmaco> farmaci, LocalDate dataConsegna, int idFarmacia) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dbAzienda", "root","password")){
            //Ottengo il nuovo id ordine
            int ultimoIdOrdine = getLastIdOrdine();

            //Inserisco l'Ordine
            PreparedStatement statement = connection.prepareStatement("insert into ordine values (?,?,2,3,0,null,?)");
            statement.setInt(1,ultimoIdOrdine+1);
            LocalDate dataConsegnaScelta;
            if (dataConsegna.isAfter(LocalDate.now().plusWeeks(1))) {
                dataConsegnaScelta = dataConsegna;
            } else {
                dataConsegnaScelta = LocalDate.now().plusDays(8);
            }
            statement.setDate(2,Date.valueOf(dataConsegnaScelta));
            statement.setInt(3, idFarmacia);
            statement.executeUpdate();

            //Ottengo l'ultimo id lotto
            int ultimoIdLotto = getLastIdLotto();

            for(Farmaco farmaco:farmaci) {
                ultimoIdLotto++;
                //aggiungo i lotti vuoti
                statement = connection.prepareStatement("insert into lotto values (?,?,0,0,?)");
                statement.setInt(1,ultimoIdLotto);
                statement.setDate(2,Date.valueOf(LocalDate.now().plusYears(1)));
                statement.setString(3,farmaco.getNome());
                statement.executeUpdate();

                //collego il lotto all'ordine
                statement = connection.prepareStatement("insert into composizione values (?,?,?)");
                statement.setInt(1,farmaco.getQuantita());
                statement.setInt(2,ultimoIdOrdine+1);
                statement.setInt(3,ultimoIdLotto);
                statement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            CadutaConnessioneControl c = new CadutaConnessioneControl();
            c.start();
        }
    }

    /**
     * Crea un ordine in stato di eleaborazione
     * @param lottiDisponibili lotti disponibili nel magazzino dell'azienda
     * @param farmaciDisponibili farmaci disponibili nel magazzino dell'azienda
     * @param dataConsegna data di consegna dell'ordine
     * @param idFarmacia ID della {@code Farmacia}
     */
    public void elaboraOrdine(ArrayList<Lotto> lottiDisponibili, ArrayList<Farmaco> farmaciDisponibili, LocalDate dataConsegna, int idFarmacia) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dbAzienda", "root", "password")) {
            //Ottengo l'id dell'ordine
            int ultimoIdOrdine = getLastIdOrdine();

            //Inserisco l'Ordine
            PreparedStatement statement = connection.prepareStatement("insert into ordine values (?,?,2,1,0,null,?)");
            statement.setInt(1, ultimoIdOrdine+1);
            statement.setDate(2, Date.valueOf(dataConsegna));
            statement.setInt(3, idFarmacia);
            statement.executeUpdate();

            //collego i lotti all'ordine
            for (Farmaco farmaco : farmaciDisponibili) {
                int quantitaOrdinataFarmaco = farmaco.getQuantita();
                for (Lotto lotto : lottiDisponibili) {
                    if (lotto.getNomeFarmaco().compareTo(farmaco.getNome()) == 0) {
                        if (lotto.getQuantitaContenuta() - lotto.getQuantitaOrdinata() > quantitaOrdinataFarmaco) {
                            statement = connection.prepareStatement("insert into composizione values (?,?,?)");
                            statement.setInt(1, lotto.getQuantitaOrdinata()+quantitaOrdinataFarmaco);
                            statement.setInt(2, ultimoIdOrdine+1);
                            statement.setInt(3, lotto.getIdLotto());
                            statement.executeUpdate();
                        } else {
                            quantitaOrdinataFarmaco -= (lotto.getQuantitaContenuta() - lotto.getQuantitaOrdinata());
                            statement = connection.prepareStatement("insert into composizione values (?,?,?)");
                            statement.setInt(1, lotto.getQuantitaContenuta()-lotto.getQuantitaOrdinata());
                            statement.setInt(2, ultimoIdOrdine+1);
                            statement.setInt(3, lotto.getIdLotto());
                            statement.executeUpdate();
                        }
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
            CadutaConnessioneControl c = new CadutaConnessioneControl();
            c.start();
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
        } catch (SQLException e) {
            e.printStackTrace();
            CadutaConnessioneControl c = new CadutaConnessioneControl();
            c.start();
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
        } catch (SQLException e) {
            e.printStackTrace();
            CadutaConnessioneControl c = new CadutaConnessioneControl();
            c.start();
        }
        return lastId;
    }

    /**
     * Detrae il numero dei farmaci ordini dai lotti riguardanti l'ordine corrispondente all'id passato in input
     * @param idOrdine id dell'ordine
     */
    public void modificaFarmaciOrdinati(int idOrdine){
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dbAzienda", "root", "password")) {

            //se l'ordine è prenotato devo rimuovere i lotti
            PreparedStatement determinazioneTipo = connection.prepareStatement("select * from ordine where id_ordine = ?");
            determinazioneTipo.setInt(1,idOrdine);
            ResultSet risultatoDeterminazioneTipo = determinazioneTipo.executeQuery();
            risultatoDeterminazioneTipo.next();
            if(risultatoDeterminazioneTipo.getInt("stato") == 3) {
                //ottengo lotti coinvolti nell'ordine
                PreparedStatement composizione = connection.prepareStatement("select * from composizione where ordine_id_ordine = ?");
                composizione.setInt(1, idOrdine);
                ResultSet risultatoComposizioneEliminazione = composizione.executeQuery();
                while (risultatoComposizioneEliminazione.next()) {
                    int idLotto = risultatoComposizioneEliminazione.getInt("lotto_id_lotto");
                    //Elimino la composizione
                    PreparedStatement rimuoviComposizione = connection.prepareStatement("delete from composizione where lotto_id_lotto = ? and ordine_id_ordine = ?");
                    rimuoviComposizione.setInt(1,idLotto);
                    rimuoviComposizione.setInt(2,idOrdine);
                    rimuoviComposizione.executeUpdate();
                    //elimino i lotti associati
                    PreparedStatement lottoNuovo = connection.prepareStatement("delete from lotto where id_lotto = ?");
                    lottoNuovo.setInt(1, idLotto);
                    lottoNuovo.executeUpdate();
                }
            }
            else{
                //ottengo lotti coinvolti nell'ordine
                PreparedStatement composizione = connection.prepareStatement("select * from composizione where ordine_id_ordine = ?");
                composizione.setInt(1,idOrdine);
                ResultSet risultatoComposizione = composizione.executeQuery();
                while(risultatoComposizione.next()) {
                    int nFarmaci = risultatoComposizione.getInt("n_farmaci");
                    int idLotto = risultatoComposizione.getInt("lotto_id_lotto");

                    //ottengo i farmaci precedentemente ordinati
                    PreparedStatement lottoPrecedente = connection.prepareStatement("select * from lotto where id_lotto = ?");
                    lottoPrecedente.setInt(1, idLotto);
                    ResultSet risultatoLottoPrecedente = lottoPrecedente.executeQuery();
                    risultatoLottoPrecedente.next();
                    int farmaciOrdinati = risultatoLottoPrecedente.getInt("n_ordinati");

                    //aggiorno i farmaci ordinati
                    PreparedStatement lottoNuovo = connection.prepareStatement("update lotto set n_ordinati = ? where id_lotto = ?");
                    lottoNuovo.setInt(1, farmaciOrdinati - nFarmaci);
                    lottoNuovo.setInt(2, idLotto);
                    lottoNuovo.executeUpdate();
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
            CadutaConnessioneControl c = new CadutaConnessioneControl();
            c.start();
        }
    }

    /**
     * Cancella l'ordine raltivo all'id ordine passato in input
     * @param idOrdine id dell'ordine da eliminare
     */
    public void cancellaOrdine(int idOrdine){
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dbAzienda", "root", "password")) {

            //elimino composizione
            PreparedStatement eliminazioneComposizione = connection.prepareStatement("delete from composizione where ordine_id_ordine = ?");
            eliminazioneComposizione.setInt(1,idOrdine);
            eliminazioneComposizione.executeUpdate();

            //elimino ordine
            PreparedStatement eliminazioneOrdine = connection.prepareStatement("delete from ordine where id_ordine = ?");
            eliminazioneOrdine.setInt(1,idOrdine);
            eliminazioneOrdine.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
            CadutaConnessioneControl c = new CadutaConnessioneControl();
            c.start();
        }
    }
}