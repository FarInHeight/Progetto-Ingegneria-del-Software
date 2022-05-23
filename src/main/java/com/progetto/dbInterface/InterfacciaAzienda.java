package com.progetto.dbInterface;
import com.progetto.entity.Lotto;
import java.sql.*;

public class InterfacciaAzienda {

    /**
     * Metodo che ritorna tutti i Lotti contenuti nel database
     *
     * @return oggetto ResultSet contenente tutti i Lotti del database
     */
    public ResultSet getLotti() {

        ResultSet result = null;
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbAzienda", "azienda","pwd")){
            Statement statement = connection.createStatement();
            result = statement.executeQuery("select * from Lotto");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Rimuove un particolare Lotto dal database. Il Lotto è indicato tramite il suo id.
     *
     * @param id identificativo del Lotto da rimuovere
     */
    public void rimuoviLotto(int id) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbAzienda", "azienda","pwd")){
            PreparedStatement statement = connection.prepareStatement("delete from Lotto where ID_lotto = ?");
            statement.setInt(1,id);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Aggiunge un Lotto passato come parametro al database.
     *
     * @param lotto riferimento al Lotto da inserire
     */
    public void addLotto(Lotto lotto) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbAzienda", "azienda","pwd")){
            PreparedStatement statement = connection.prepareStatement("insert into Lotto (Data_scadenza, N_contenuti, N_ordinati, Farmaco_Nome) values (?,?,?,?)");
            statement.setDate(1,Date.valueOf(lotto.getDataScadenza()));
            statement.setInt(2,lotto.getQuantitaContenuta());
            statement.setInt(3,lotto.getQuantitaOrdinata());
            statement.setString(4,lotto.getNomeFarmaco());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Modifica la quantità di Farmaci contenuti e ordinati del Lotto specificato.
     * Modifica la data di scadenza in base a quando sono stati prodotti i Farmaci.
     *
     * @param quantita nuova quantità di Farmaci contenuti e ordinati
     * @param id identificatore del Lotto
     * @param data nuova data di scadenza
     */
    public void updateQuantitaLotto(int quantita, int id, Date data) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbAzienda", "azienda","pwd")){
            PreparedStatement statement = connection.prepareStatement("update Lotto set N_contenuti = ?, N_ordinati = ?, Data_scadenza = ? where ID_lotto = ?");
            statement.setInt(1,quantita);
            statement.setInt(2,quantita);
            statement.setDate(3,data);
            statement.setInt(4,id);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Ritorna tutti gli Ordini in stato di Prenotazione.
     *
     * @return tutti gli Ordini prenotati
     */
    public ResultSet getOrdiniPrenotati() {
        ResultSet result = null;
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbAzienda", "azienda","pwd")){
            Statement statement = connection.createStatement();
            result = statement.executeQuery("select ID_ordine, N_farmaci, ID_lotto from Ordine,Composizione,Lotto where Stato = 3 AND ID_ordine = Ordine_ID_ordine AND ID_lotto = Lotto_ID_lotto");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Modifica lo stato di un qualunque Ordine in Elaborazione
     *
     * @param id_ordine identificativo dell'Ordine da mandare in elaborazione
     */
    public void cambiaStatoInElaborazione(int id_ordine) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbAzienda", "azienda","pwd")){
            PreparedStatement statement = connection.prepareStatement("update Ordine set Stato = 1 where ID_ordine = ?");
            statement.setInt(1,id_ordine);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
