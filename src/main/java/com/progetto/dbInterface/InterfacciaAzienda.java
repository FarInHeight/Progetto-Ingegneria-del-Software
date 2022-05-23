package com.progetto.dbInterface;

import java.sql.*;

public class InterfacciaAzienda {

    public static ResultSet getLotti() {

        ResultSet result = null;
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbAzienda", "azienda","pwd");
            Statement statement = connection.createStatement();
            result = statement.executeQuery("select * from Lotto");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void removeLotto(int id) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbAzienda", "azienda","pwd");
            PreparedStatement statement = connection.prepareStatement("delete from Lotto where Data_scadenza = ?");
            statement.setInt(1,id);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
