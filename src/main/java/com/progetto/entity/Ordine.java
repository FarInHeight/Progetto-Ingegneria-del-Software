package com.progetto.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Ordine {
    private int idOrdine;
    private int stato;
    private ArrayList<Farmaco> farmaci;
    private int tipo;
    private int periodo;
    private LocalDate dataConsegna;
    private String nomeFarmacia;
    private String indirizzoConsegna;

    public Ordine(int idOrdine, int stato, ArrayList<Farmaco> farmaci, int tipo, int periodo, LocalDate dataConsegna, String nomeFarmacia, String indirizzoConsegna) {
        this.setIdOrdine(idOrdine);
        this.setStato(stato);
        this.setFarmaci(farmaci);
        this.setTipo(tipo);
        this.setPeriodo(periodo);
        this.setDataConsegna(dataConsegna);
        this.setNomeFarmacia(nomeFarmacia);
        this.setIndirizzoConsegna(indirizzoConsegna);
    }

    private void setIdOrdine(int idOrdine) {
        if(idOrdine < 1) {
            throw new IllegalArgumentException("Id Ordine non valido");
        }
        this.idOrdine = idOrdine;
    }

    private void setStato(int stato) {
        if(stato < 1 || stato > 5) {
            throw new IllegalArgumentException("Nessuno stato delll'ordine corrispondente trovato");
        }
        this.stato = stato;
    }

    private void setFarmaci(ArrayList<Farmaco> farmaci) {
        if(farmaci == null) {
            throw new NullPointerException("ArrayList di Farmaci = null");
        }
        ArrayList<Farmaco> copiaFarmaci = new ArrayList<>();
        for(Farmaco farmaco : farmaci) {
            if(farmaco != null) {
                try {
                    copiaFarmaci.add(farmaco.clone());
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }

            }
        }
        this.farmaci = copiaFarmaci;
    }

    private void setTipo(int tipo) {
        if(tipo < 1 || tipo > 2) {
            throw new IllegalArgumentException("Nessun tipo dell'ordine corrispondente trovato");
        }
        this.tipo = tipo;
    }

    private void setPeriodo(int periodo) {
        if(periodo < 0) {
            throw new IllegalArgumentException("Periodo negativo non ammesso");
        }
        this.periodo = periodo;
    }

    private void setDataConsegna(LocalDate dataConsegna) {
        if(farmaci == null) {
            throw new NullPointerException("Data di consegna = null");
        }
        this.dataConsegna = dataConsegna;
    }

    private void setNomeFarmacia(String nomeFarmacia) {
        if(farmaci == null) {
            throw new NullPointerException("Nome della Farmacia = null");
        }
        this.nomeFarmacia = nomeFarmacia;
    }

    private void setIndirizzoConsegna(String indirizzoConsegna) {
        if(farmaci == null) {
            throw new NullPointerException("Indirizzo di Consegna = null");
        }
        this.indirizzoConsegna = indirizzoConsegna;
    }

    public int getIdOrdine() {
        return idOrdine;
    }

    public String getStato() {
        /*
            1 - Elaborazione
            2 - Spedizione
            3 - Prenotato
            4 - Consegnato
            5 - Caricato
         */
        switch (this.stato) {
            case 1: return "Elaborazione";
            case 2: return "Spedizione";
            case 3: return "Prenotato";
            case 4: return "Consegnato";
            case 5: return "Caricato";
            default: return "Stato non trovato";
        }
    }

    public ArrayList<Farmaco> getFarmaci() {
        ArrayList<Farmaco> copiaFarmaci = new ArrayList<>();
        for(Farmaco farmaco : this.farmaci) {
            if(farmaco != null) {
                try {
                    copiaFarmaci.add(farmaco.clone());
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }
        }
        return copiaFarmaci;
    }

    public String getTipo() {
        /*
            1 - periodico
            2 - non periodico
         */
        switch (this.tipo) {
            case 1: return "Periodico";
            case 2: return "Non periodico";
            default: return "Tipo non trovato";
        }
    }

    public int getPeriodo() {
        return periodo;
    }

    public LocalDate getDataConsegna() {
        return dataConsegna;
    }

    public String getNomeFarmacia() {
        return nomeFarmacia;
    }

    public String getIndirizzoConsegna() {
        return indirizzoConsegna;
    }

    public String[] getRiepilogo() {
        String []riepilogo = new String[8];
        riepilogo[0] = "" + this.getIdOrdine();
        riepilogo[1] = this.getStato();
        riepilogo[2] = "";  // da implementare i farmaci
        riepilogo[3] = this.getTipo();
        riepilogo[4] = "" + this.getPeriodo();
        riepilogo[5] = this.dataConsegna.format(DateTimeFormatter.ofPattern("d/MM/uuuu"));
        /*
        this.setIdOrdine(idOrdine);
        this.setStato(stato);
        this.setFarmaci(farmaci);
        this.setTipo(tipo);
        this.setPeriodo(periodo);
        this.setDataConsegna(dataConsegna);
        this.setNomeFarmacia(nomeFarmacia);
        this.setIndirizzoConsegna(indirizzoConsegna);
         */
        return riepilogo;
    }

    public static void main(String args[]) {
        //Ordine ordine = new Ordine(1, 1, null, 1, 1, null, "ciao", "casa");
        System.out.println(LocalDate.of(2022, 05, 23).format(DateTimeFormatter.ofPattern("d/MM/uuuu")));
    }
}
