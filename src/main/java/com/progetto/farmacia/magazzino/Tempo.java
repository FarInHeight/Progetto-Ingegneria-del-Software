package com.progetto.farmacia.magazzino;

import com.progetto.entity.Farmacia;
import com.progetto.farmacia.ordini.VerificaRegistrazioniFarmaciControl;
import javafx.concurrent.Task;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Classe che implementa la boundary {@code Tempo}. Essa si occupa di schedulare l'avvio
 * automatico dei casi d'uso periodici stabiliti durante la fase di analisi.
 */
public class Tempo {
    private static Tempo instance;
    private int counter;
    private Farmacia farmacia;

    private Tempo(Farmacia farmacia) {
        this.setFarmacia(farmacia);
        this.setCounter(0);
    }

    /**
     * Metodo per ottenere l'unico oggetto di classe {@code Tempo}
     * @param farmacia farmacia che ha effettuato il login nel Sistema
     */
    public static Tempo getInstance(Farmacia farmacia) {
        if(Tempo.instance == null) {
            Tempo.instance = new Tempo(farmacia);
        }
        return Tempo.instance;
    }
    private void setCounter(int counter) {
        this.counter = counter;
    }

    private int getCounter() {
        return this.counter;
    }
    private void setFarmacia(Farmacia farmacia) {
        if(farmacia == null){
            throw new NullPointerException("Farmacia in Tempo boundary = null");
        }
        this.farmacia = farmacia;
    }

    /**
     * Metodo di avvio di un oggetto di classe {@code Tempo}.
     */
    public void start() {
        if(this.getCounter() == 0) {
            this.setCounter(1);
            // PER VERIFICA ESAURIMENTO FARMACI
            if( LocalDate.now().atTime(18, 0).isBefore( LocalDateTime.now() ) ) {
                this.runVerificaEsaurimentoFarmaci();
            } else {
                long millis = ChronoUnit.MILLIS.between(LocalDateTime.now(), LocalDate.now().atTime(18, 0));
                Task<Void> sleeper = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try { Thread.sleep(millis); }
                        catch (InterruptedException e) { }
                        return null;
                    }
                };
                sleeper.setOnSucceeded(event -> Tempo.this.runVerificaEsaurimentoFarmaci());
                new Thread(sleeper).start();
            }
            // PER VERIFICA REGISTRAZIONE FARMACI
            if( LocalDate.now().atTime(20, 0).isBefore( LocalDateTime.now() ) ) {
                this.runVerificaRegistrazioneFarmaci();
            } else {
                long millis = ChronoUnit.MILLIS.between(LocalDateTime.now(), LocalDate.now().atTime(20, 0));
                Task<Void> sleeper = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try { Thread.sleep(millis); }
                        catch (InterruptedException e) { }
                        return null;
                    }
                };
                sleeper.setOnSucceeded(event -> Tempo.this.runVerificaRegistrazioneFarmaci());
                new Thread(sleeper).start();
            }

            // PER RIMUOVI FARMACI SCADUTI
            // se le ore 8:00 di oggi sono passate
            if( LocalDate.now().atTime(8, 0).isBefore( LocalDateTime.now() ) ) {
                this.runRimuoviFarmaciScaduti();
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
                sleeper.setOnSucceeded(event -> Tempo.this.runRimuoviFarmaciScaduti());
                new Thread(sleeper).start();
            }
        }
    }

    private void runRimuoviFarmaciScaduti() {
        RimuoviFarmaciScadutiControl control = new RimuoviFarmaciScadutiControl(this.farmacia);
        control.start();
    }

    private void runVerificaRegistrazioneFarmaci() {
        VerificaRegistrazioniFarmaciControl control = new VerificaRegistrazioniFarmaciControl(this.farmacia);
        control.start();
    }

    private void runVerificaEsaurimentoFarmaci() {
        VerificaEsaurimentoFarmaciControl control = new VerificaEsaurimentoFarmaciControl();
        control.start();
    }
}
