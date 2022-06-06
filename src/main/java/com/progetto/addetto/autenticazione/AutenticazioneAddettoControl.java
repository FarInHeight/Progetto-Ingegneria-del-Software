package com.progetto.addetto.autenticazione;

import com.progetto.addetto.SchermataPrincipaleAddettoAzienda;
import com.progetto.interfacciaDatabase.InterfacciaAutenticazione;
import com.progetto.addetto.AddettoAzienda;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.security.auth.login.CredentialException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Classe che implementa la control {@code AutenticazioneAddettoControl}
 */
public class AutenticazioneAddettoControl {

    private TextField idAddetto;
    private PasswordField password;
    private ActionEvent event;
    /**
     * Costruisce un oggetto di classe {@code AutenticazioneAddettoControl} dati in input l'id dell'Addetto dell'Azienda, la password
     * e l'evento relativo alla pressione del tasto login
     * @param idAddetto id dell'Addetto
     * @param password password inserita dall'utente
     * @param event evento che rappresenta il click del tasto login
     */
    public AutenticazioneAddettoControl(TextField idAddetto, PasswordField password, ActionEvent event) {
        this.setIdAddetto(idAddetto);
        this.setPassword(password);
        this.setEvent(event);
    }

    private void setIdAddetto(TextField idAddetto) {
        if(idAddetto == null) {
            throw new NullPointerException("ID addetto = null");
        }
        this.idAddetto = idAddetto;
    }

    private void setPassword(PasswordField password) {
        if(password == null) {
            throw new NullPointerException("Password addetto = null");
        }
        this.password = password;
    }

    private void setEvent(ActionEvent event) {
        if(event == null) {
            throw new NullPointerException("Event = null");
        }
        this.event = event;
    }

    /**
     * Permette di avviare la control
     */
    public void start() {
        try {
            String pwd = this.creaDigest(password.getText());
            try {
                int id = Integer.parseInt(idAddetto.getText());
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //ottiene stage corrente
                this.verificaCredenziali(this.getCredenziali(id, pwd));
                stage.close(); //chiudo schermata autenticazione
            } catch (NumberFormatException e) { //id farmacia inserito in un formato non corretto
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //ottiene stage corrente
                ErroreAutenticazione errAut = new ErroreAutenticazione(0);
                errAut.start(stage);
            } catch (CredentialException e) { //credenziali non corrette
                if (e.getMessage().compareTo("idNonValido") == 0) {
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //ottiene stage corrente
                    ErroreAutenticazione errAut = new ErroreAutenticazione(2);
                    errAut.start(stage);
                } else if (e.getMessage().compareTo("passwordNonValida") == 0) {
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //ottiene stage corrente
                    ErroreAutenticazione errAut = new ErroreAutenticazione(1);
                    errAut.start(stage);
                }
            }
        } catch(IOException exc) {
            exc.printStackTrace();
        }
    }
    @SuppressWarnings("UnnecessaryLocalVariable")
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

    private AddettoAzienda getCredenziali (int idAddetto, String password){
        InterfacciaAutenticazione intAut = new InterfacciaAutenticazione();
        return intAut.getCredenzialiAddettoAzienda(idAddetto, password);
    }

    private void verificaCredenziali(AddettoAzienda addettoAzienda) throws CredentialException{
        if(addettoAzienda != null){
            if(addettoAzienda.getNominativo().compareTo("passwordNonValida") == 0){
                throw new CredentialException("passwordNonValida");
            }
            try {
                SchermataPrincipaleAddettoAzienda schermataPrincipaleFarmacia = new SchermataPrincipaleAddettoAzienda(addettoAzienda);
                Stage stage = new Stage();
                schermataPrincipaleFarmacia.start(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            throw new CredentialException("idNonValido");
        }
    }
}
