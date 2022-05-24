package com.progetto.farmacia.autenticazione;
import com.progetto.dbInterface.InterfacciaAutenticazione;
import com.progetto.entity.Farmacia;
import com.progetto.farmacia.SchermataPrincipaleFarmacia;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.security.auth.login.CredentialException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Control che gestisce l'autenticazione della farmacia
 */
public class AutenticazioneFarmaciaControl {

    /**
     * istanzia l'oggetto dati in input l'id della farmacia e la password
     * @param idFarmacia id della farmacia
     * @param password password inserita dall'utente
     * @param event evento che rappresenta il click del tasto login
     * @exception IOException se non è possibile caricare il file fxml della schermata dell'errore
     */
    public AutenticazioneFarmaciaControl(TextField idFarmacia, TextField password, ActionEvent event) throws IOException {
        String pwd = this.creaDigest(password.getText());
        try {
            int id = Integer.parseInt(idFarmacia.getText());
            this.verificaCredenziali(this.getCredenziali(id,pwd));
        } catch (NumberFormatException e) { //id farmacia inserito in un formato non corretto
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //ottiene stage corrente
            ErroreAutenticazione errAut = new ErroreAutenticazione(0);
            errAut.start(stage);
        } catch (CredentialException e){ //credenziali non corrette
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //ottiene stage corrente
            ErroreAutenticazione errAut = new ErroreAutenticazione(1);
            errAut.start(stage);
        }
    }

    private String creaDigest(String password){
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        md.update(password.getBytes(StandardCharsets.UTF_8));
        byte[] digest = md.digest();
        String hex = String.format("%064x", new BigInteger(1, digest));
        return hex;  //la stringa ha 64 caratteri
    }

    private Farmacia getCredenziali (int idFarmacia, String password) throws CredentialException {
        InterfacciaAutenticazione intAut = new InterfacciaAutenticazione();
        return intAut.getCredenziali(idFarmacia, password);
    }

    private void verificaCredenziali(Farmacia farmacia) throws CredentialException{
        if(farmacia != null){
            try {
                Farmacia farm = farmacia.clone();
                SchermataPrincipaleFarmacia schermataPrincipaleFarmacia = new SchermataPrincipaleFarmacia(farm);
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        else{
            throw new CredentialException("Credenziali non corrette");
        }
    }
}
