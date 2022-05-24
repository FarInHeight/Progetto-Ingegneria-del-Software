package com.progetto.farmacia;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

/**
 * control che gestisce il logout
 */
public class LogoutControl{

    @FXML
    private void logout(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //ottiene stage corrente
        stage.close();
    }
}
