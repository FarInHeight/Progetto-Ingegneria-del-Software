package com.progetto.farmacia.autenticazione;
import com.progetto.dbInterface.InterfacciaAutenticazione;
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
    private int idFarmacia;
    private String password;

    /**
     * istanzia l'oggetto dati in input l'id della farmacia e la password
     * @param idFarmacia id della farmacia
     * @param password password inserita dall'utente
     * @param event evento che rappresenta il click del tasto login
     * @exception IOException se non è possibile caricare il file fxml della schermata dell'errore
     */
    public AutenticazioneFarmaciaControl(TextField idFarmacia, TextField password, ActionEvent event) throws IOException {
        this.password = this.getDigest(password.getText());
        try {
            this.idFarmacia = Integer.parseInt(idFarmacia.getText());
            this.verificaCredenziali(this.getCredenziali(this.idFarmacia,this.password));
        } catch (NumberFormatException e) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //ottiene stage corrente
            ErroreAutenticazione errAut = new ErroreAutenticazione(0);
            errAut.start(stage);
        } catch (CredentialException e){
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //ottiene stage corrente
            ErroreAutenticazione errAut = new ErroreAutenticazione(1);
            errAut.start(stage);
        }
    }

    private String getDigest(String password){
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

    private int getCredenziali (int idFarmacia, String password) throws CredentialException {
        InterfacciaAutenticazione intAut = new InterfacciaAutenticazione();
        return intAut.getCredenziali(idFarmacia, password);
    }

    private void verificaCredenziali(int id) throws CredentialException{
        if(id != -1){
            //crea schermata principale farmacia
            //crea entity per token sessione
        }
        else{
            throw new CredentialException();
        }
    }
}
