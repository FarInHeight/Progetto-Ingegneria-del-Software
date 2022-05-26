package com.progetto.azienda;

import com.progetto.azienda.magazzino.RimuoviLottiScadutiControl;
import com.progetto.azienda.produzione.GestioneProduzioneControl;

public class Test {

    public static void fakeMain(String[] args) {
        RimuoviLottiScadutiControl rimuoviLottiScadutiControl = new RimuoviLottiScadutiControl();
        GestioneProduzioneControl gestioneProduzioneControl = new GestioneProduzioneControl();


        rimuoviLottiScadutiControl.rimuoviLottiScaduti();

        gestioneProduzioneControl.gestioneProduzione();

    }


}
