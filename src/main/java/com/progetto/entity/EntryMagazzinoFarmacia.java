package com.progetto.entity;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import java.time.format.DateTimeFormatter;

/**
 * Classe che modella una riga della {@code SchermataMagazzino} a partire da un {@code farmaco} nel magazzino della farmacia
 */
public class EntryMagazzinoFarmacia {

    private Farmaco farmaco;
    private final FlowPane strumenti;

    /**
     * Getter per il farmaco
     * @return farmaco
     */
    public Farmaco getFarmaco() {
        return farmaco;
    }

    /**
     * Setter per il farmaco
     * @param farmaco farmaco da settare
     */
    public void setFarmaco(Farmaco farmaco) {
        if (farmaco == null) {
            throw new NullPointerException("farmaco = null");
        }
        this.farmaco = farmaco;
    }

    /**
     * Getter per gli strumenti della entry
     * @return strumenti della entry
     */
    public FlowPane getStrumenti() {
        return strumenti;
    }

    /**
     * Setter per gli strumenti della entry
     * @param strumenti strumenti della entry
     */
    public void setStrumenti(Button strumenti) {
        this.strumenti.getChildren().add(strumenti);
        this.strumenti.setAlignment(Pos.CENTER);
    }

    /**
     * Getter per il nome del farmaco
     * @return nome del farmaco
     */
    public String getNome() {
        return getFarmaco().getNome();
    }

    /**
     * Getter per il principio attivo del farmaco
     * @return principio attivo del farmaco
     */
    public String getPrincipioAttivo() {
        return getFarmaco().getPrincipioAttivo();
    }

    /**
     * Getter per il tipo del farmaco
     * @return tipo del farmaco
     */
    public int getTipo() {
        return getFarmaco().getTipo();
    }

    /**
     * Getter per la data di scadenza del farmaco presente nel magazzino della farmacia
     * @return data di scadenza del farmaco
     */
    public String getDataScadenza() {
        return getFarmaco().getDataScadenza().format(DateTimeFormatter.ofPattern("d/MM/uuuu"));
    }

    /**
     * Getter per la quantita di farmaco presente nel magazzino della farmacia
     * @return quantita di faramco presente nel magazzino della farmacia
     */
    public int getQuantita() {
        return getFarmaco().getQuantita();
    }

    /**
     * Costruttore per una entry della SchermataMagazzino
     * @param farmaco farmaco contenuto nella entry
     */
    public EntryMagazzinoFarmacia(Farmaco farmaco) {
        setFarmaco(farmaco);
        strumenti = new FlowPane();
    }

}
