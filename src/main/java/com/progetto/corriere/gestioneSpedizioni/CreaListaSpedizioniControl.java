package com.progetto.corriere.gestioneSpedizioni;

import com.progetto.interfacciaDatabase.InterfacciaCorriere;
import com.progetto.entity.Ordine;

import java.util.ArrayList;

public class CreaListaSpedizioniControl {

    public void creaLista() {

        InterfacciaCorriere db = new InterfacciaCorriere();
        ArrayList<Ordine> spedizioni = new ArrayList<>();
        spedizioni = db.getOrdiniGiornalieri();
        ListaSpedizioni listaSpedizioni = new ListaSpedizioni(spedizioni);
        ListaSpedizioni.launch();
    }
}
