package com.progetto.farmacia;

import com.progetto.entity.Farmacia;
import javafx.application.Application;

public class SchermataPrincipaleFarmacia extends Application {

    private Farmacia farmacia;

    public SchermataPrincipaleFarmacia(Farmacia farmacia){
        this.setFarmacia(farmacia);
    }

    private void setFarmacia(Farmacia farmacia){
        if(farmacia == null){
            throw new NullPointerException("farmacia = null");
        }
        this.farmacia = farmacia;
    }
}
