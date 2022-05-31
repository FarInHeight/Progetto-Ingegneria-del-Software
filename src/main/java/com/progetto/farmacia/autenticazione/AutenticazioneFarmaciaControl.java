package com.progetto.farmacia.autenticazione;
import com.progetto.farmacia.magazzino.Tempo;
import com.progetto.interfacciaDatabase.InterfacciaAutenticazione;
import com.progetto.entity.Farmacia;
import com.progetto.farmacia.SchermataPrincipaleFarmacia;
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
 * Control che gestisce l'autenticazione della farmacia
 */
public class AutenticazioneFarmaciaControl {
    private TextField idFarmacia;
    private PasswordField password;
    private ActionEvent event;

    /**
     * Istanzia un oggetto di tipo {@code AutenticazioneFarmaciaControl} dati in input l'id della farmacia, la password
     * e l'evento relativo alla pressione del tasto login
     * @param idFarmacia id della farmacia
     * @param password password inserita dall'utente
     * @param event evento che rappresenta il click del tasto login
     * @exception IOException se non è possibile caricare il file fxml della schermata dell'errore
     */
    public AutenticazioneFarmaciaControl(TextField idFarmacia, PasswordField password, ActionEvent event) throws IOException {
        this.setIdFarmacia(idFarmacia);
        this.setPassword(password);
        this.setEvent(event);
    }

    private void setIdFarmacia(TextField idFarmacia) {
        if(idFarmacia == null) {
            throw new NullPointerException("ID farmacia = null");
        }
        this.idFarmacia = idFarmacia;
    }

    private void setPassword(PasswordField password) {
        if(password == null) {
            throw new NullPointerException("Password farmacia = null");
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
    public void start(){
        try {
            String pwd = this.creaDigest(password.getText());
            try {
                int id = Integer.parseInt(idFarmacia.getText());
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //ottiene stage corrente
                this.verificaCredenziali(this.getCredenziali(id, pwd));
                stage.close(); //chiudo scgermata autenticazione
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
        }catch (IOException e){
            e.printStackTrace();
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
        return intAut.getCredenzialiFarmacia(idFarmacia, password);
    }

    private void verificaCredenziali(Farmacia farmacia) throws CredentialException{
        if(farmacia != null){
            if(farmacia.getNome().compareTo("passwordNonValida") == 0){
                throw new CredentialException("passwordNonValida");
            }
            try {
                SchermataPrincipaleFarmacia schermataPrincipaleFarmacia = new SchermataPrincipaleFarmacia(farmacia);
                Stage stage = new Stage();
                schermataPrincipaleFarmacia.start(stage);
                Tempo tempo = Tempo.getInstance(farmacia);
                tempo.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            throw new CredentialException("idNonValido");
        }
    }
}
