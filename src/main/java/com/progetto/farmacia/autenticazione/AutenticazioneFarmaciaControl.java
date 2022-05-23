package com.progetto.farmacia.autenticazione;

import javafx.scene.control.TextField;

public class AutenticazioneFarmaciaControl {
    private int idFarmacia;
    private String password;

    public AutenticazioneFarmaciaControl(TextField idFarmacia,TextField password){
        this.idFarmacia = Integer.parseInt(idFarmacia.getText());
        this.password = password.getText();
        System.out.println(this.idFarmacia);
    }
}
