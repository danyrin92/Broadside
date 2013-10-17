package com.starboardstudios.broadside.app;

import android.app.Application;
import com.starboardstudios.broadside.gameunits.Model;

/**
 * Created by alex on 10/16/13.
 */
public class BroadsideApplication extends Application {

    private Model globalModel;

    public BroadsideApplication()
    {
        super();
        globalModel= new Model(this.getBaseContext());
    }
    public Model getModel()
    {
        return globalModel;
    }
    public void clearModel()
    {
           globalModel= new Model(this.getBaseContext());
    }


}
