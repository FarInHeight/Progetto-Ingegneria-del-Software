package com.progetto.addetto.segnalazioni;

import com.progetto.entity.AddettoAzienda;
import com.progetto.entity.EntryListaSegnalazioni;
import com.progetto.interfacciaDatabase.InterfacciaAddetto;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Classe che implementa la control {@code GestioneSegnalazioniControl}
 */
public class GestioneSegnalazioniControl {

    private AddettoAzienda addetto;

    // evento associato al click sul pulsante visualizzaSegnalazioni
    private ActionEvent event;

    // stage associato all'evento della schermata principale di addetto
    private Stage stage;

    private ListaSegnalazioni lista;

    /**
     * Costruttore della classe {@code GestioneSegnalazioniControl} che prende in input un oggetto di classe {@code AddettoAzienda}
     * e l'evento associato al click sul pulsante {@code visualizzaSegnalazioni}
     * @param addetto addetto dell'azienda
     * @param event evento di click
     */
    public GestioneSegnalazioniControl(AddettoAzienda addetto, ActionEvent event) {
        this.setAddettoAzienda(addetto);
        this.setEvent(event);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();  // prendo lo stage corrente
        this.setStage(stage);
    }

    private void setStage(Stage stage) {
        if(stage == null) {
            throw new NullPointerException("Stage della Schermata Principale Addetto Azienda = null");
        }
        this.stage = stage;
    }
    private void setAddettoAzienda(AddettoAzienda addetto) {
        if(addetto == null) {
            throw new NullPointerException("Addetto dell'azienda = null");
        }
        this.addetto = addetto;
    }

    private void setEvent(ActionEvent event) {
        if(event == null) {
            throw new NullPointerException("Event = null");
        }
        this.event = event;
    }

    private void setPulsanti(EntryListaSegnalazioni entry) {
        // creazione dei pulsanti
        Button espandi = new Button("Espandi");
        espandi.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GestioneSegnalazioniControl.this.clickSuEspandi(entry);
            }
        });
        espandi.setBackground(Background.fill(Color.rgb(38, 189, 27)));
        espandi.setStyle("-fx-text-fill: white");
        Button rimuoviSegnalazione = new Button("Rimuovi");
        rimuoviSegnalazione.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GestioneSegnalazioniControl.this.clickSuRimuovi(entry);
            }
        });
        rimuoviSegnalazione.setBackground(Background.fill(Color.rgb(255, 79, 66)));
        rimuoviSegnalazione.setStyle("-fx-text-fill: white");
        FlowPane flow = new FlowPane();
        flow.getChildren().addAll(espandi, rimuoviSegnalazione);
        flow.setAlignment(Pos.CENTER);
        flow.setHgap(10);
        entry.setStrumenti(flow);
    }

    /**
     * Metodo di avvio di un oggetto di classe {@code GestioneSegnalazioneControl}
     */
    public void start() {
        InterfacciaAddetto db = new InterfacciaAddetto();
        ArrayList<EntryListaSegnalazioni> segnalazioni = db.getSegnalazioni();
        for(EntryListaSegnalazioni entry : segnalazioni) {
            this.setPulsanti(entry);
        }
        this.stage.hide();  // nascondo lo stage attuale riferito alla schermata principale di addetto
        this.lista = new ListaSegnalazioni(this.addetto, segnalazioni, this);
        try {
            lista.start(this.stage);
        } catch(IOException exc) {
            exc.printStackTrace();
        }
    }

    /**
     * Metodo tramite il quale un oggetto di tipo {@code ListaSegnalazioni} avvisa la {@code GestioneSegnalazioniControl}
     * del click sul pulsante {@code indietro} e distrugge la ListaSegnalazioni.
     * Il metodo è stato creato senza modificatore di visibilità affinché possa essere invocato soltanto da classi
     * che si trovano nello stesso package.
     * @param substage sotto-stage della ListaSegnalazioni da distuggere
     */
    void clickSuIndietro(Stage substage) {
        substage.close();
        this.stage.show();
    }

    /**
     * Metodo che viene richiamato quasi si fa un click sul pulsante {@code espandi} di una entry della {@code ListaSegnalazioni}
     * e si visualizza a schermo un oggetto di classe {@code RiepilogoSegnalazione}.
     * Il metodo è stato creato senza modificatore di visibilità affinché possa essere invocato soltanto da classi
     * che si trovano nello stesso package.
     * @param segnalazione segnalazione da espandere
     */
     void clickSuEspandi(EntryListaSegnalazioni segnalazione) {
        RiepilogoSegnalazione riepilogo = new RiepilogoSegnalazione(segnalazione);  // mostra a video il riepilogo
        try {
            riepilogo.start(this.lista.getStage());  // faccio partire la schermata di riepilogo
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metodo che viene richiamato quasi si fa un click sul pulsante {@code rimuovi} di una entry della {@code ListaSegnalazioni}
     * e si visualizza a schermo un oggetto di classe {@code RichiestaConfermaRimozione}.
     * Il metodo è stato creato senza modificatore di visibilità affinché possa essere invocato soltanto da classi
     * che si trovano nello stesso package.
     * @param segnalazione segnalazione da rimuovere
     */
    void clickSuRimuovi(EntryListaSegnalazioni segnalazione) {
        RichiestaConfermaRimozione richiesta = new RichiestaConfermaRimozione(segnalazione, this);  // mostra a video un avviso di rimozione
        try {
            richiesta.start(this.lista.getStage());  // faccio partire la schermata di riepilogo
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metodo che viene richiamato quasi si fa un click sul pulsante {@code elimina} di una entry della {@code RichiestaConfermaRimozione}.
     * La {@code GestioneSegnalazioniControl} aggiorna il database dell'Azienda ed elimina la entry della segnalazione dalla {@code ListaSegnalazioni}.
     * @param substage stage della richiesta di conferma rimozione
     * @param segnalazione segnalazione da rimuovere
     */
     void clickSuElimina(Stage substage, EntryListaSegnalazioni segnalazione) {
        InterfacciaAddetto db = new InterfacciaAddetto();
        db.eliminaSegnalazione(segnalazione.getIdSegnalazione());
        this.lista.confermaEliminazione(segnalazione);
        substage.close();
    }
}
