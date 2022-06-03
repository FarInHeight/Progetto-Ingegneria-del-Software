package com.progetto.interfacciaDatabase;

import com.progetto.entity.*;
import com.progetto.farmacia.SchermataPrincipaleFarmacia;
import javafx.scene.control.Spinner;
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
            CadutaConnessioneControl c = new CadutaConnessioneControl();
            c.start();
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
            CadutaConnessioneControl c = new CadutaConnessioneControl();
            c.start();
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
            CadutaConnessioneControl c = new CadutaConnessioneControl();
            c.start();
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
        } catch (SQLException e) {
            CadutaConnessioneControl c = new CadutaConnessioneControl();
            c.start();
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
        } catch (SQLException e) {
            CadutaConnessioneControl c = new CadutaConnessioneControl();
            c.start();
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
        } catch (SQLException e) {
            CadutaConnessioneControl c = new CadutaConnessioneControl();
            c.start();
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
        } catch (SQLException e) {
            CadutaConnessioneControl c = new CadutaConnessioneControl();
            c.start();
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
            if (resulFarmaci.next()) {
                quantita = resulFarmaci.getInt("totale");
            }
        } catch (SQLException e) {
            CadutaConnessioneControl c = new CadutaConnessioneControl();
            c.start();
        }
        return quantita;
    }

    /**
     * Aggiunge un ordine in prenotazione con 200 quantità del farmaco specificato.
     * Associa all'ordine dei nuovi lotti
     * @param nome nome del farmaco da ordinare
     */
    public void prenotaOrdineDaBanco(String nome) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dbAzienda", "root","password")){

            //Ottengo l'ultimo id ordine
            int ultimoIdOrdine = this.getLastIdOrdine();

            //Inserisco l'Ordine
            PreparedStatement statement = connection.prepareStatement("insert into ordine values (?,?,1,3,0,null,?)");
            statement.setInt(1,ultimoIdOrdine+1);
            statement.setDate(2,Date.valueOf(LocalDate.now().plusDays(8)));
            statement.setInt(3,SchermataPrincipaleFarmacia.getFarmacia().getIdFarmacia());
            statement.executeUpdate();

            //Ottengo l'ultimo id lotto
            int ultimoIdLotto = getLastIdLotto();

            //aggiungo il lotto vuoto
            statement = connection.prepareStatement("insert into lotto values (?,null,0,0,?)");
            statement.setInt(1,ultimoIdLotto+1);
            statement.setString(2,nome);
            statement.executeUpdate();

            //collego il lotto all'ordine
            statement = connection.prepareStatement("insert into composizione values (200,?,?)");
            statement.setInt(1,ultimoIdOrdine+1);
            statement.setInt(2,ultimoIdLotto+1);
            statement.executeUpdate();

        } catch (SQLException e) {
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
            CadutaConnessioneControl c = new CadutaConnessioneControl();
            c.start();
        }
        return lastId;
    }

    /**
     * Aggiunge un ordine non periodico in prenotazione con quantita e farmaco specificato in input
     * Associa all'ordine dei nuovi lotti
     * @param farmaci farmaci da ordinare
     */
    public void prenotaOrdineNonPeriodico(ArrayList<Farmaco> farmaci, LocalDate dataConsegna) {
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
            statement.setInt(3,SchermataPrincipaleFarmacia.getFarmacia().getIdFarmacia());
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
            CadutaConnessioneControl c = new CadutaConnessioneControl();
            c.start();
        }
    }

    /**
     * Crea un ordine in stato di eleaborazione
     * @param lottiDisponibili lotti disponibili nel magazzino dell'azienda
     * @param farmaciDisponibili farmaci disponibili nel magazzino dell'azienda
     */
    @SuppressWarnings("IfStatementWithIdenticalBranches")
    public void elaboraOrdine(ArrayList<Lotto> lottiDisponibili, ArrayList<Farmaco> farmaciDisponibili, LocalDate dataConsegna) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dbAzienda", "root", "password")) {
            //Ottengo l'id dell'ordine
            int ultimoIdOrdine = getLastIdOrdine();

            //Inserisco l'Ordine
            PreparedStatement statement = connection.prepareStatement("insert into ordine values (?,?,2,1,0,null,?)");
            statement.setInt(1, ultimoIdOrdine+1);
            statement.setDate(2, Date.valueOf(dataConsegna));
            statement.setInt(3, SchermataPrincipaleFarmacia.getFarmacia().getIdFarmacia());
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
            CadutaConnessioneControl c = new CadutaConnessioneControl();
            c.start();
        }
    }

    /**
     * Aggiorna la quantita di farmaci ordinati nei lotti nel magazzino dell'azienda
     * @param lotti lotti da aggiornare
     * @param farmaci farmaci contenuti negli ordini
     */
    @SuppressWarnings("IfStatementWithIdenticalBranches")
    public void aggiornaLotti(ArrayList<Lotto> lotti, ArrayList<Farmaco> farmaci) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dbAzienda", "root", "password")) {
            PreparedStatement statement = connection.prepareStatement("update lotto set n_ordinati = ? where id_lotto = ?");
            for(Farmaco farmaco : farmaci){
                int quantitaOrdinataFarmaco = farmaco.getQuantita();
                for(Lotto lotto : lotti){
                    if(farmaco.getNome().compareTo(lotto.getNomeFarmaco()) == 0){
                        PreparedStatement statementPrecedente = connection.prepareStatement("select n_ordinati from lotto where id_lotto = ?");
                        statementPrecedente.setInt(1,lotto.getIdLotto());
                        ResultSet result = statementPrecedente.executeQuery();
                        result.next();
                        int nOrdinatiPrecedente = result.getInt("n_ordinati");
                        if(lotto.getQuantitaContenuta()-lotto.getQuantitaOrdinata() >= quantitaOrdinataFarmaco){
                            statement.setInt(1, quantitaOrdinataFarmaco+nOrdinatiPrecedente);
                            statement.setInt(2,lotto.getIdLotto());
                            statement.executeUpdate();
                        } else {
                            quantitaOrdinataFarmaco -= (lotto.getQuantitaContenuta()-lotto.getQuantitaOrdinata());
                            statement.setInt(1,lotto.getQuantitaContenuta());
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
                    "and o.farmacia_id_farmacia = ? and o.stato <> 5;");
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
            CadutaConnessioneControl c = new CadutaConnessioneControl();
            c.start();
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
     * Detrae il numero dei farmaci ordini dai lotti riguardanti l'ordine in elaborazione corrispondente all'id passato in input
     * @param idOrdine id dell'ordine
     */
    public void modificaFarmaciOrdinati(int idOrdine){
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dbAzienda", "root", "password")) {
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
        }catch (SQLException e){
            CadutaConnessioneControl c = new CadutaConnessioneControl();
            c.start();
        }
    }

    /**
     * Cancella l'ordine raltivo all'id ordine passato in input
     * @param idOrdine id dell'ordine da eliminare
     */
    public void cancellaOrdine(int idOrdine, int stato){
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dbAzienda", "root", "password")) {
            if(stato == 3) {
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
            } else {
                //elimino composizione
                PreparedStatement eliminazioneComposizione = connection.prepareStatement("delete from composizione where ordine_id_ordine = ?");
                eliminazioneComposizione.setInt(1,idOrdine);
                eliminazioneComposizione.executeUpdate();
            }
            //elimino ordine
            PreparedStatement eliminazioneOrdine = connection.prepareStatement("delete from ordine where id_ordine = ?");
            eliminazioneOrdine.setInt(1,idOrdine);
            eliminazioneOrdine.executeUpdate();
        }catch (SQLException e){
            CadutaConnessioneControl c = new CadutaConnessioneControl();
            c.start();
        }
    }

    /**
     * Inserisce nel database della catena farmaceutica i farmaci caricati
     * @param farmaciCaricati farmaci caricati
     */
    @SuppressWarnings("unchecked")
    public void caricaFarmaci(ArrayList<EntryMagazzinoFarmacia> farmaciCaricati) {

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dbCatena", "root","password")){
            for (EntryMagazzinoFarmacia farmaco:farmaciCaricati) {
                PreparedStatement statement = connection.prepareStatement("select * from farmaco " +
                        "where nome = ? and data_scadenza = ? and farmacia_id_farmacia = ?");
                statement.setString(1,farmaco.getNome());
                statement.setDate(2,Date.valueOf(farmaco.getDataScadenzaNonFormattata()));
                statement.setInt(3,SchermataPrincipaleFarmacia.getFarmacia().getIdFarmacia());
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    statement = connection.prepareStatement("update farmaco " +
                            "set quantita = ? " +
                            "where farmacia_id_farmacia = ? AND nome = ? AND data_scadenza = ?");
                    int quantitaAttuale = resultSet.getInt("quantita");
                    statement.setInt(1,(quantitaAttuale+(((Spinner<Integer>)farmaco.getStrumenti().getChildren().get(0)).getValue())));
                    statement.setString(3,farmaco.getNome());
                    statement.setDate(4,Date.valueOf(farmaco.getDataScadenzaNonFormattata()));
                    statement.setInt(2,SchermataPrincipaleFarmacia.getFarmacia().getIdFarmacia());
                    statement.executeUpdate();
                } else {
                    statement = connection.prepareStatement("insert into farmaco " +
                            "values (?,?,?,?,?,?)");
                    statement.setString(1,farmaco.getNome());
                    statement.setDate(2,Date.valueOf(farmaco.getDataScadenzaNonFormattata()));
                    statement.setString(3,farmaco.getPrincipioAttivo());
                    statement.setInt(4,farmaco.getTipo());
                    statement.setInt(5,(((Spinner<Integer>)farmaco.getStrumenti().getChildren().get(0)).getValue()));
                    statement.setInt(6,SchermataPrincipaleFarmacia.getFarmacia().getIdFarmacia());
                    statement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            CadutaConnessioneControl c = new CadutaConnessioneControl();
            c.start();
        }

    }

    /**
     * Modifica lo stato di un ordine in Caricato
     * @param id_ordine identificativo dell'Ordine
     */
    public void modificaStatoInCaricato(int id_ordine) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dbAzienda", "root","password")){
            PreparedStatement statement = connection.prepareStatement("update ordine set stato = 5 where id_ordine = ?");
            statement.setInt(1,id_ordine);
            statement.executeUpdate();
        } catch (SQLException e) {
            CadutaConnessioneControl c = new CadutaConnessioneControl();
            c.start();
        }
    }

    /**
     * Metodo utilizzato per aggiungere una {@code Segnalazione} per un particolare {@code Ordine} nel database dell'Azienda
     * @param idOrdine ordine da segnalare
     * @param commento commento del farmacista
     * @param dataGenerazione data di generazione della segnalazione
     */
    public void aggiungiSegnalazione(int idOrdine, String commento, LocalDate dataGenerazione) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dbAzienda", "root","password")){
            PreparedStatement statement = connection.prepareStatement("insert segnalazione values (null, ?, ?, ?);");
            statement.setString(1,commento);
            statement.setDate(2, Date.valueOf(dataGenerazione));
            statement.setInt(3, idOrdine);
            statement.executeUpdate();
        } catch (SQLException e) {
            CadutaConnessioneControl c = new CadutaConnessioneControl();
            c.start();
        }
    }

    /**
     * Metodo utilizzato per ottenere gli ordini consegnati ma non caricati del giorno corrente dal database dell'Azienda
     * @param idFarmacia ID della {@code Farmacia} che sta richiedendo gli ordini
     * @return lista di {@code EntryListaOrdini}
     */
    public ArrayList<EntryListaOrdini> getOrdiniNonCaricati(int idFarmacia) {
        ArrayList<EntryListaOrdini> ordini = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbazienda", "root","password")) {
            PreparedStatement statement = connection.prepareStatement("SELECT o.id_ordine, c.n_farmaci, l.farmaco_nome, " +
                    "o.tipo, o.stato, o.periodo, o.data_consegna, f.nome, f.indirizzo, " +
                    "far.principio_attivo, far.tipo as tipo_farmaco, l.data_scadenza FROM ordine as o, " +
                    "composizione as c, lotto as l, farmacia as f, farmaco as far WHERE o.id_ordine = c.ordine_id_ordine " +
                    "and c.lotto_id_lotto = l.id_lotto and o.farmacia_id_farmacia = f.id_farmacia and far.nome = l.farmaco_nome " +
                    "and o.farmacia_id_farmacia = ? and o.stato = 4 and o.data_consegna = ?;");
            // stato consegnato e data di consegna pari ad oggi
            statement.setInt(1, idFarmacia);
            statement.setDate(2, Date.valueOf(LocalDate.now()));
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
            CadutaConnessioneControl c = new CadutaConnessioneControl();
            c.start();
        }
        return ordini;
    }

    /**
     * Permette la modifica di un ordine periodico dato in input l'id dell'ordine da modificare, i farmaci presenti nell'ordine e il periodo dell'ordine
     * @param idOrdine id dell'ordine da modificare
     * @param farmaci farmaci relativi all'ordine
     * @param periodo periodo dell'ordine
     */
    public void modificaOrdinePeriodico(int idOrdine, LocalDate dataConsegna,ArrayList<Farmaco> farmaci, int periodo) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dbAzienda", "root","password")){
            //Creo il nuovo ordine
            //Ottengo il nuovo id ordine
            int ultimoIdOrdine = getLastIdOrdine();

            //Inserisco l'Ordine
            PreparedStatement statement = connection.prepareStatement("insert into ordine values (?,?,1,3,?,null,?)");
            statement.setInt(1,ultimoIdOrdine+1);
            statement.setDate(2,Date.valueOf(dataConsegna.plusWeeks(periodo)));
            statement.setInt(3,periodo);
            statement.setInt(4,SchermataPrincipaleFarmacia.getFarmacia().getIdFarmacia());
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

            //Modifico il vecchio
            statement = connection.prepareStatement("update ordine set periodo = 0 where id_ordine = ?");
            statement.setInt(1,idOrdine);
            statement.executeUpdate();
        } catch (SQLException e) {
            CadutaConnessioneControl c = new CadutaConnessioneControl();
            c.start();
        }
    }

    public ArrayList<LottoOrdinato> getLottiAssociati(int idOrdine) {
        ArrayList<LottoOrdinato> lotti = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dbAzienda", "root","password")) {
            PreparedStatement statementOrdine = connection.prepareStatement("select * " +
                    "from composizione,lotto " +
                    "where ordine_id_ordine = ? and lotto_id_lotto = id_lotto");
            statementOrdine.setInt(1,idOrdine);
            ResultSet infoOrdine = statementOrdine.executeQuery();
            while (infoOrdine.next()) {
                lotti.add(new LottoOrdinato(infoOrdine.getInt("id_lotto"), infoOrdine.getInt("n_ordinati"), infoOrdine.getInt("n_farmaci")));
            }
        } catch (SQLException e) {
            CadutaConnessioneControl c = new CadutaConnessioneControl();
            c.start();
        }
        return lotti;

    }

    public void ricreaOrdine(ArrayList<LottoOrdinato> lottiModifica) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dbAzienda", "root","password")) {
            PreparedStatement statementOrdine = connection.prepareStatement("update lotto set n_ordinati = ? where id_lotto = ?");
            for (LottoOrdinato lottoOrdinato:lottiModifica) {
                statementOrdine.setInt(1,lottoOrdinato.getQuantitaOrdine() + lottoOrdinato.getQuantitaOrdinata());
                statementOrdine.setInt(2,lottoOrdinato.getIdLotto());
                statementOrdine.executeUpdate();
            }
        } catch (SQLException e) {
            CadutaConnessioneControl c = new CadutaConnessioneControl();
            c.start();
        }
    }
}