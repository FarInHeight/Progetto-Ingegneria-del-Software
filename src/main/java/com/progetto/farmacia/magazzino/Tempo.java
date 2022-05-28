package com.progetto.farmacia.magazzino;

import com.progetto.entity.Farmacia;
import javafx.concurrent.Task;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Classe che implementa la boundary {@code AvvisoFarmaciScaduti}
 */
public class Tempo {
    private Farmacia farmacia;

    /**
     * Costruisce un oggetto di tipo  {@code AvvisoFarmaciScaduti}
     * @param farmacia farmacia che ha effettuato il login
     */
    public Tempo(Farmacia farmacia) {
        this.setFarmacia(farmacia);
    }

    private void setFarmacia(Farmacia farmacia) {
        if(farmacia == null){
            throw new NullPointerException("Farmacia in Rimuovi Farmaci Scaduti Control = null");
        }
        this.farmacia = farmacia;
    }

    /**
     * Metodo di avvio di un oggetto di classe {@code AvvisoFarmaciScaduti}
     */
    public void start() {
        // se le ore 8:00 di oggi sono passate
        if( LocalDate.now().atTime(8, 0).isBefore( LocalDateTime.now() ) ) {
            this.run();
        } else {
            long millis = ChronoUnit.MILLIS.between(LocalDateTime.now(), LocalDate.now().atTime(8, 0));
            Task<Void> sleeper = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    try { Thread.sleep(millis); }
                    catch (InterruptedException e) { }
                    return null;
                }
            };
            sleeper.setOnSucceeded(event -> Tempo.this.run());
            new Thread(sleeper).start();
        }
    }

    private void run() {
        RimuoviFarmaciScadutiControl control = new RimuoviFarmaciScadutiControl(Tempo.this.farmacia);
        control.start();
    }
}
