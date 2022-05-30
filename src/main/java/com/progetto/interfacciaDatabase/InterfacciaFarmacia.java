package com.progetto.interfacciaDatabase;

import com.progetto.entity.*;
import com.progetto.farmacia.SchermataPrincipaleFarmacia;

import java.io.PipedReader;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;

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

    /**
     * Metodo che permette di rimuovere un {@code Farmaco} dal database della Catena Farmaceutico noto l'ID della {@code Farmacia}
     * @param idFarmacia id della farmacia che intende rimuovere il farmaco
     * @param farmaco farmaco da rimuovere
     */
    public void rimuoviFarmaco(int idFarmacia, Farmaco farmaco) {
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbcatena", "root","password")){
            PreparedStatement statement = connection.prepareStatement("delete from farmaco where farmacia_id_farmacia = ? and nome = ? and data_scadenza = ?");
            statement.setInt(1, idFarmacia);
            statement.setString(2, farmaco.getNome());
            statement.setString(3, farmaco.getDataScadenza().toString());
            statement.executeUpdate();
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
    public LinkedList<Lotto> getLotti() {

        LinkedList<Lotto> lotti = new LinkedList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dbAzienda", "root","password")){
            Statement statement = connection.createStatement();
            ResultSet resultLotti = statement.executeQuery("select * from lotto where data_scadenza is not null order by data_scadenza asc");
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

    /**
     * Metodo che ritorna tutti i farmaci da banco nel magazzino della farmacia
     * @return farmaci da banco
     */
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

    /**
     * Metodo che ritorna, controllando tutti gli ordini, la quantita di un certo farmaco in arrivo alla farmacia
     * @param nome nome del farmaco
     * @return quantita in arrivo
     */
    public int controllaQuantitaOrdinata(String nome) {

        int quantita = 0;
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dbazienda", "root","password")){
            PreparedStatement statement = connection.prepareStatement("select sum(n_farmaci) as totale " +
                    "from ordine,composizione,lotto " +
                    "where id_ordine = ordine_id_ordine AND id_lotto = lotto_id_lotto AND farmacia_id_farmacia = ? AND farmaco_nome = ? AND stato <> 5");
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
            PreparedStatement statement = connection.prepareStatement("insert into ordine values (null,null,1,3,0,null,?)");
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

    /**
     * Aggiunge un ordine non periodico in prenotazione con quantita e farmaco specificato in input
     * Associa all'ordine dei nuovi lotti
     * @param farmaco farmaco da ordinare
     */
    public void prenotaOrdineNonPeriodico(Farmaco farmaco) {
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Aggiunge un ordine in elaborazione
     * Associa all'ordine dei lotti
     * @param ordine ordine da aggiungere
     * @param lotti lotti da collegare all'ordine
     * @param farmaci farmaci contenuti nell'ordine
     */
    public void elaboraOrdineNonPeriodico(Ordine ordine, ArrayList<Lotto> lotti, ArrayList<Farmaco> farmaci) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dbAzienda", "root", "password")) {
            //Inserisco l'Ordine
            PreparedStatement statement = connection.prepareStatement("insert into ordine values (null,?,2,1,0,null,?)");
            statement.setDate(1, Date.valueOf(ordine.getDataConsegna()));
            statement.setInt(2, SchermataPrincipaleFarmacia.getFarmacia().getIdFarmacia());
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
            e.printStackTrace();
        }
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
        }
    }

    /**
     * Getter per ottenere un lista di oggetti della classe {@code EntryListaOrdini} riferiti agli ordini presenti
     * nel datatabse dell'Azienda per una particolare farmacia
     * @param idFarmacia ID della farmacia
     * @return lista di farmaci come entry del form ordine
     */
    public ArrayList<EntryListaOrdini> getOrdini(int idFarmacia) {
        ArrayList<EntryListaOrdini> ordini = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbazienda", "root","password")) {
            PreparedStatement statement = connection.prepareStatement("SELECT o.id_ordine, c.n_farmaci, l.farmaco_nome, " +
                    "o.tipo, o.stato, o.periodo, o.data_consegna, f.nome, f.indirizzo, " +
                    "far.principio_attivo, far.tipo as tipo_farmaco, l.data_scadenza FROM ordine as o, " +
                    "composizione as c, lotto as l, farmacia as f, farmaco as far WHERE o.id_ordine = c.ordine_id_ordine " +
                    "and c.lotto_id_lotto = l.id_lotto and o.farmacia_id_farmacia = f.id_farmacia and far.nome = l.farmaco_nome " +
                    "and o.farmacia_id_farmacia = ?;");
            statement.setInt(1, idFarmacia);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                int idOrdine = resultSet.getInt("id_ordine");
                int nFarmaci = resultSet.getInt("n_farmaci");
                String nomeFarmaco = resultSet.getString("farmaco_nome");
                int tipo = resultSet.getInt("tipo");
                int stato = resultSet.getInt("stato");
                int periodo = resultSet.getInt("periodo");
                Date data = resultSet.getDate("data_consegna");
                if(data == null) continue;
                LocalDate dataConsegna = data.toLocalDate();
                String nomeFarmacia = resultSet.getString("nome");
                String indirizzoConsegna = resultSet.getString("indirizzo");
                String principioAttivo = resultSet.getString("principio_attivo");
                int tipoFarmaco = resultSet.getInt("tipo_farmaco");
                LocalDate dataScadenza = resultSet.getDate("data_scadenza").toLocalDate();
                EntryListaOrdini entry = checkInLista(idOrdine, ordini);
                Farmaco farmaco = new Farmaco(nomeFarmaco, principioAttivo, tipoFarmaco, dataScadenza, nFarmaci);
                if(entry == null) {
                    entry = new EntryListaOrdini(new Ordine(idOrdine, stato, new ArrayList<Farmaco>(), tipo, periodo, dataConsegna, nomeFarmacia, indirizzoConsegna));
                    entry.getFarmaci().add(farmaco);
                    ordini.add(entry);
                } else {
                    entry.getFarmaci().add(farmaco);
                }
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
        return ordini;
    }

    private EntryListaOrdini checkInLista(int idOrdine, ArrayList<EntryListaOrdini> ordini) {
        for(int i = 0; i < ordini.size(); ++i) {
            EntryListaOrdini entry = ordini.get(i);
            if(entry.getIdOrdine() == idOrdine) {
                return entry;
            }
        }
        return null;
    }

    /**
     * detrae il numero dei farmaci ordini dai lotti riguardanti la entry passata in input
     * @param entry entry dell'ordine
     */
    public void modificaFarmaciOrdinati(EntryListaOrdini entry){
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dbAzienda", "root", "password")) {
            //ottengo lotti coinvolti nell'ordine
            int idOrdine = entry.getIdOrdine();
            PreparedStatement composizione = connection.prepareStatement("select * from composizione where ordine_id_ordine = ?");
            composizione.setInt(1,idOrdine);
            ResultSet risultatoComposizione = composizione.executeQuery();
            while(risultatoComposizione.next()){
                int nFarmaci = risultatoComposizione.getInt("n_farmaci");
                int idLotto = risultatoComposizione.getInt("lotto_id_lotto");

                //ottengo i farmaci precedentemente ordinati
                PreparedStatement lottoPrecedente = connection.prepareStatement("select * from lotto where id_lotto = ?");
                lottoPrecedente.setInt(1,idLotto);
                ResultSet risultatoLottoPrecedente = lottoPrecedente.executeQuery();
                risultatoLottoPrecedente.next();
                int farmaciOrdinati = risultatoLottoPrecedente.getInt("n_ordinati");

                //aggiorno i farmaci ordinati
                PreparedStatement lottoNuovo = connection.prepareStatement("update lotto set n_ordinati = ? where id_lotto = ?");
                lottoNuovo.setInt(1,farmaciOrdinati-nFarmaci);
                lottoNuovo.setInt(2,idLotto);
                lottoNuovo.executeUpdate();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * cancella l'ordine raltivo alla entry di lista ordini passata in input
     * @param idOrdine id dell'ordine da elinimare
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
        }
    }
}