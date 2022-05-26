package com.progetto.azienda;

import com.progetto.azienda.magazzino.RimuoviLottiScadutiControl;
import com.progetto.azienda.produzione.GestioneProduzioneControl;

import java.util.Timer;
import java.util.TimerTask;

public class MainAzienda {

    public static void main(String[] args) {

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                GestioneProduzioneControl gestioneProduzioneControl = new GestioneProduzioneControl();
                gestioneProduzioneControl.gestioneProduzione();
            }
        }, 0, 604800000);//Una volta a settimana

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                RimuoviLottiScadutiControl rimuoviLottiScadutiControl = new RimuoviLottiScadutiControl();
                rimuoviLottiScadutiControl.rimuoviLottiScaduti();
            }
        }, 0, 86400000);//Una volta al giorno

    }

}
