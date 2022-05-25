package com.progetto.farmacia.autenticazione;

import com.progetto.farmacia.autenticazione.SchermataLoginForm;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * control che gestisce il logout
 */
public class LogoutControl{

    public LogoutControl(ActionEvent event) throws IOException{
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //ottiene stage corrente
        SchermataLoginForm schermataLoginForm = new SchermataLoginForm();
        Stage stageLogin = new Stage();
        schermataLoginForm.start(stageLogin);
        stage.close();
    }
}
