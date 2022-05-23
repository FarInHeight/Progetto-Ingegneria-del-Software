package com.progetto.farmacia.autenticazione;

import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
/**
 * Control che gestisce l'autenticazione della farmacia
 */
public class AutenticazioneFarmaciaControl {
    private int idFarmacia;
    private String password;

    /**
     * istanzia l'oggetto dati in input l'id della farmacia e la password
     * @param idFarmacia id della farmacia
     * @param password password inserita dall'utente
     */
    public AutenticazioneFarmaciaControl(TextField idFarmacia,TextField password){
        this.idFarmacia = Integer.parseInt(idFarmacia.getText());
        this.password = password.getText();
        this.getCredenziali();
    }

    private void getCredenziali(){
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "password");
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * from addetto")) {
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1));
                System.out.println(resultSet.getString(2));
                System.out.println(resultSet.getString(3));
                System.out.println(resultSet.getString(4));

            }
        } catch(Exception e){
            e.printStackTrace();
        }

    }
}
