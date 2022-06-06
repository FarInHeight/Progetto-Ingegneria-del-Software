package com.progetto.farmacia.magazzino;

import com.progetto.entity.Farmaco;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Classe che modella una entry della {@code SchermataMagazzino}
 */
public class EntryMagazzinoFarmacia {

    private Farmaco farmaco;
    private final FlowPane strumenti;

    /**
     * Ritorna il farmaco associato alla entry
     * @return oggetto di tipo {@code Farmaco}
     */
    public Farmaco getFarmaco() {
        return farmaco;
    }

    /**
     * Permette di settare il farmaco associato alla entry
     * @param farmaco farmaco da settare
     */
    public void setFarmaco(Farmaco farmaco) {
        if (farmaco == null) {
            throw new NullPointerException("farmaco = null");
        }
        this.farmaco = farmaco;
    }

    /**
     * Ritorna gli strumenti della entry
     * @return oggetto di tipo {@code FlowPane} contenete gli strumenti relativi alla entry
     */
    public FlowPane getStrumenti() {
        return strumenti;
    }

    /**
     * Permette di settare gli strumenti della entry
     * @param strumenti strumenti della entry
     */
    public void setStrumenti(Button strumenti) {
        this.strumenti.getChildren().add(strumenti);
        this.strumenti.setAlignment(Pos.CENTER);
    }

    /**
     * Ritorna il nome del farmaco
     * @return oggetto di tipo {@code String} contenente il nome del farmaco
     */
    public String getNome() {
        return getFarmaco().getNome();
    }

    /**
     * Ritorna il principio attivo del farmaco
     * @return oggetto di tipo {@code String} contenente il principio attivo del farmaco
     */
    public String getPrincipioAttivo() {
        return getFarmaco().getPrincipioAttivo();
    }

    /**
     * Ritorna il tipo del farmaco
     * @return {@code int} contenente tipo del farmaco
     */
    public int getTipo() {
        return getFarmaco().getTipo();
    }

    /**
     * Ritorna la data di scadenza del farmaco
     * @return oggetto di tipo {@code String} contenente la data di scadenza del farmaco
     */
    public String getDataScadenza() {
        return getFarmaco().getDataScadenza().format(DateTimeFormatter.ofPattern("d/MM/uuuu"));
    }

    /**
     * Ritorna la data di scadenza del farmaco
     * @return oggetto di tipo {@code LocalDate} contenente la data di scadenza del farmaco
     */

    public LocalDate getDataScadenzaNonFormattata(){
        return this.farmaco.getDataScadenza();
    }

    /**
     * Ritorna la quantita di farmaco
     * @return {@code int} contenente la quantita di farmaco
     */
    public int getQuantita() {
        return getFarmaco().getQuantita();
    }

    /**
     * Istanzia un oggetto di tipo {@code EntryMagazzinoFarmacia} dato in input un farmaco
     * @param farmaco farmaco da associare alla entry
     */
    public EntryMagazzinoFarmacia(Farmaco farmaco) {
        setFarmaco(farmaco);
        strumenti = new FlowPane();
    }

}
