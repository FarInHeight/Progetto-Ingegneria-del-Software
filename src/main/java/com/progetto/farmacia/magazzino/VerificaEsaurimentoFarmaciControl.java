package com.progetto.farmacia.magazzino;

import com.progetto.entity.Farmaco;
import com.progetto.interfacciaDatabase.InterfacciaFarmacia;

import java.util.ArrayList;

/**
 * Classe che modella la control {@code VerificaEsaurimentoFarmaciControl } che crea ordini prenotati se i farmaci da banco
 * in magazzino scendono sotto una certa soglia. L'ordine viene creato solamente se non vi sono già abbastanza farmaci in arrivo con ordini esistenti
 */
public class VerificaEsaurimentoFarmaciControl {

    private InterfacciaFarmacia db;

    /**
     * Istanzia un oggetto di tipo {@code VerificaEsaurimentoFarmaciControl}
     */
    public VerificaEsaurimentoFarmaciControl(){
        db = new InterfacciaFarmacia();
    }

    /**
     * Permette di avviare la control in modo da verificare la quantità di farmaci da banco presente in magazzino.
     * Se la quantità è inferiore a 50 e non vi sono ordini in arrivo che contengono almeno 200 di quel farmaco viene creato un nuovo ordine.
     */
    public void start(){
        ArrayList<Farmaco> farmaci = db.getFarmaciDaBanco();
        for (Farmaco farmaco : farmaci) {
            if (farmaco.getQuantita() < 50) {
                if (db.controllaQuantitaOrdinata(farmaco.getNome()) < 200 ){
                    db.prenotaOrdineDaBanco(farmaco.getNome());
                }
            }
        }

    }


}
