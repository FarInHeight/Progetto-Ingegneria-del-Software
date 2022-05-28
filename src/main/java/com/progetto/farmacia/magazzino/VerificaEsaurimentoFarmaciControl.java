package com.progetto.farmacia.magazzino;

import com.progetto.entity.Farmaco;
import com.progetto.interfacciaDatabase.InterfacciaFarmacia;

import java.util.ArrayList;

/**
 *
 */
public class VerificaEsaurimentoFarmaciControl {

    private InterfacciaFarmacia db;

    public VerificaEsaurimentoFarmaciControl(){
        db = new InterfacciaFarmacia();
    }

    public void verificaEsaurimento(){
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
