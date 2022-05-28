package com.progetto.farmacia.magazzino;

import com.progetto.entity.Farmaco;
import com.progetto.interfacciaDatabase.InterfacciaFarmacia;

import java.util.ArrayList;

/**
 * Classe che crea ordini prenotati se i farmaci da banco in magazzino scendono sotto una certa soglia
 * L'ordine viene creato solamente se non vi sono già abbastanza farmaci in arrivo con ordini esistenti
 */
public class VerificaEsaurimentoFarmaciControl {

    private InterfacciaFarmacia db;

    /**
     * Costruttore per la control
     */
    public VerificaEsaurimentoFarmaciControl(){
        db = new InterfacciaFarmacia();
    }

    /**
     * Metodo che verifica la quantità di farmaci da banco presente in magazzino.
     * Se la quantità è inferiore a 50 e non vi sono ordini in arrivo che contengono almeno 200 di quel farmaco viene creato un nuovo ordine.
     */
    public void verificaEsaurimento(){
        System.out.println("verifica");
        ArrayList<Farmaco> farmaci = db.getFarmaciDaBanco();
        for (Farmaco farmaco : farmaci) {
            if (farmaco.getQuantita() < 50) {
                if (db.controllaQuantitaOrdinata(farmaco.getNome()) < 200 ){
                    db.prenotaOrdine(farmaco.getNome());
                }
            }
        }

    }


}
