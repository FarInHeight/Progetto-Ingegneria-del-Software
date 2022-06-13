package com.progetto.azienda;

import com.progetto.azienda.magazzino.RimuoviLottiScadutiControl;
import com.progetto.azienda.produzione.GestioneProduzioneControl;
import javafx.application.Application;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Gestisce il sistema di rifornimento dell'azienda.
 * Fa eseguire periodicamente i metodi assocaiti alla produzione di nuovi {@code lotti} e la rimozione di lotti scaduti
 */
public class Tempo extends Application {
    /**
     * metodo che permette di avviare il sistema di rifornimento dell'azienda
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
    @SuppressWarnings("JavadocDeclaration")

    static private void verificaUltimoRifornimento() {
        GestioneProduzioneControl gestioneProduzioneControl = new GestioneProduzioneControl();
        gestioneProduzioneControl.start();
    }

    static private void verificaUltimaRimozioneLotti() {
        RimuoviLottiScadutiControl rimuoviLottiScadutiControl = new RimuoviLottiScadutiControl();
        rimuoviLottiScadutiControl.start();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Tempo.verificaUltimoRifornimento();
        long millisRifornimento = ChronoUnit.MILLIS.between(LocalDateTime.now(), LocalDateTime.now().plusWeeks(1));
        ScheduledService<Void> rifornimento = new ScheduledService<Void>() {
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    protected Void call() {
                        Tempo.verificaUltimoRifornimento();
                        return null;
                    }
                };
            }
        };
        rifornimento.setPeriod(Duration.millis(millisRifornimento));
        rifornimento.start();

        //Una volta al giorno
        Tempo.verificaUltimaRimozioneLotti();
        long millisRimozione = ChronoUnit.MILLIS.between(LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        ScheduledService<Void> rimozione = new ScheduledService<Void>() {
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    protected Void call() {
                        Tempo.verificaUltimaRimozioneLotti();
                        return null;
                    }
                };
            }
        };
        rimozione.setPeriod(Duration.millis(millisRimozione));
        rimozione.start();
    }
}
