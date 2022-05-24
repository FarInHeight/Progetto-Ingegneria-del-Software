package com.progetto.dbInterface;
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
    public int getCredenziali(int idFarmacia, String password){
        int id = -1;
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbAzienda", "root","password")){
            PreparedStatement statement = connection.prepareStatement("select ID_farmacia from Farmacia where ID_farmacia = ? and Password = ?");
            statement.setInt(1,idFarmacia);
            statement.setString(2,password);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                id = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
}
