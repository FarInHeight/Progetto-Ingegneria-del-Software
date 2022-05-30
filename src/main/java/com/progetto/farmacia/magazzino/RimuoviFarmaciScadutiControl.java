package com.progetto.farmacia.magazzino;

import com.progetto.entity.Farmacia;
import com.progetto.entity.Farmaco;
import com.progetto.interfacciaDatabase.InterfacciaFarmacia;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Classe che implementa la control {@code RimuoviFarmaciScadutiControl}
 */
public class RimuoviFarmaciScadutiControl {
    private Farmacia farmacia;
    /**
     * Costruisce un oggetto di classe {@code RimuoviFarmaciScadutiControl}
     * @param
     */
    public RimuoviFarmaciScadutiControl(Farmacia farmacia) {
        this.setFarmacia(farmacia);
    }

    private void setFarmacia(Farmacia farmacia) {
        if(farmacia == null){
            throw new NullPointerException("Farmacia in Rimuovi Farmaci Scaduti Control = null");
        }
        this.farmacia = farmacia;
    }
    /**
     * Metodo di avvio di un oggetto di classe {@code RimuoviFarmaciScadutiControl}
     */
    public void start() {
        InterfacciaFarmacia db = new InterfacciaFarmacia();
        ArrayList<Farmaco> lista = db.getFarmaci(this.farmacia.getIdFarmacia());
        String farmaciScaduti = "";
        String farmciQuasiScaduti = "";
        int i = 0;
        while(i < lista.size()) {
            Farmaco farmaco = lista.get(i);
            // se la scadenza precedente ad oggi
            if( farmaco.getDataScadenza().isBefore( LocalDate.now() ) ) {
                farmaciScaduti += farmaco.getNome() + "\t" + farmaco.getQuantita() + "\n";
                db.rimuoviFarmaco(farmacia.getIdFarmacia(), farmaco);
            } // altrimenti se la data di scadenza è entro due settimane
            else if( LocalDate.now().plusWeeks(2) .isAfter( farmaco.getDataScadenza() ) ) {
                farmciQuasiScaduti += farmaco.getNome() + "\t" + farmaco.getQuantita() + "\t\t" +
                        farmaco.getDataScadenza().format(DateTimeFormatter.ofPattern("d/MM/uuuu")) + "\n";
            }
            ++i;
        }
        AvvisoFarmaciScaduti avviso = new AvvisoFarmaciScaduti(farmaciScaduti.stripTrailing(), farmciQuasiScaduti.stripTrailing(), this.farmacia);
        try {
            avviso.start(new Stage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
