package com.progetto.farmacia.magazzino;

import com.progetto.entity.Farmacia;
import javafx.application.Application;
import javafx.stage.Stage;

public class FakeMain extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        String f = "ciao";
        String fq= "Peri";
        AvvisoFarmaciScaduti a = new AvvisoFarmaciScaduti(f, fq, new Farmacia(1, "ciao", "a", "b"));
        Stage s = new Stage();
        try {
            a.start(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
