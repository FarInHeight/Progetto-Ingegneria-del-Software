package com.progetto.addetto.autenticazione;


import com.progetto.addetto.SchermataPrincipaleAddettoAzienda;
import com.progetto.addetto.segnalazioni.ListaSegnalazioni;
import com.progetto.entity.AddettoAzienda;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class FakeMain extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        AddettoAzienda addetto = new AddettoAzienda(1, "Piero Rossi", LocalDate.of(2020, 10, 10), "dsad", "faòinf");
        ListaSegnalazioni sch = new ListaSegnalazioni(addetto);
        try {
            sch.start(new Stage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
