package com.starboardstudios.broadside.app;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;

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
    public void loadModel()
    {
    	String fileName = "modelFile.bin";
    	try {
			ObjectInputStream is = new ObjectInputStream(new FileInputStream(fileName));
			globalModel = (Model) is.readObject();
			is.close();
    	} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }


}
