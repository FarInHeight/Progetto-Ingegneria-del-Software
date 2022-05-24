package com.progetto.dbInterface;
import com.progetto.entity.AddettoAzienda;
import com.progetto.entity.Farmacia;

import java.sql.*;

/**
 * contiene i metodi necessari ad effettuare l'autenticazione con il database
 */
public class InterfacciaAutenticazione {

    /**
     * ritorna il tupla della tabella {@code Farmacia} corrispondente alle credenziali inserite
     * @param idFarmacia id della farmacia
     * @param password password della farmacia
     * @return un {@code int} contenente l'id corrispondente alle credenziali inserite (se non sono corrette ritorna -1)
     */
    public Farmacia getCredenzialiFarmacia(int idFarmacia, String password){
        Farmacia farmacia = new Farmacia();
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbAzienda", "root","password")){
            PreparedStatement statement = connection.prepareStatement("select ID_farmacia from Farmacia where ID_farmacia = ? and Password = ?");
            statement.setInt(1,idFarmacia);
            statement.setString(2,password);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
               farmacia.setIdFarmacia(resultSet.getInt(1));
               farmacia.setNome(resultSet.getString(2));
               farmacia.setIndirizzo(resultSet.getString(3));
               farmacia.setRecapitoTelefonico(resultSet.getString(4));
            }
            else{
                farmacia = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return farmacia;
    }

    /*public AddettoAzienda getCredenzialiAddettoAzienda(int idAddetto, String password){
        AddettoAzienda farmacia = new AddettoAzienda();
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbAzienda", "root","password")){
            PreparedStatement statement = connection.prepareStatement("select ID_farmacia from Farmacia where ID_farmacia = ? and Password = ?");
            statement.setInt(1,idFarmacia);
            statement.setString(2,password);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                farmacia.setIdFarmacia(resultSet.getInt(1));
                farmacia.setNome(resultSet.getString(2));
                farmacia.setIndirizzo(resultSet.getString(3));
                farmacia.setRecapitoTelefonico(resultSet.getString(4));
            }
            else{
                farmacia = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return farmacia;
    }*/
}
