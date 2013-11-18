package com.starboardstudios.broadside.app;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.StreamCorruptedException;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.starboardstudios.broadside.gameunits.Crew;
import com.starboardstudios.broadside.gameunits.Model;
import com.starboardstudios.broadside.gameunits.ships.MainShip;
import com.starboardstudios.broadside.util.LevelManager;

/**
 * Created by alex on 10/16/13.
 */
public class BroadsideApplication extends Application {

    private Model globalModel;
    public Boolean load = false;
    
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
    public void saveModel(Context context)
    {
    	try {
			FileOutputStream fou = openFileOutput("savedLevel.bin", MODE_PRIVATE);
			OutputStreamWriter osw = new OutputStreamWriter(fou);
			osw.write(globalModel.getLevel());
			osw.write(globalModel.getBooty());
			osw.write(globalModel.numCrew);
			osw.flush();
			osw.close();
			
			Toast.makeText(context, "Data Saved", Toast.LENGTH_LONG).show();
		}  catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(context, "IOException", Toast.LENGTH_LONG).show();
		}
    }
    public void loadModel(Context context)
    {
    	try {
			FileInputStream fin = openFileInput("savedLevel.bin");
			InputStreamReader isr = new InputStreamReader(fin);
			int level = isr.read();
			int booty = isr.read();
			int numCrew = isr.read();
			isr.close();
			
			Toast.makeText(context, Integer.toString(level) 
					+ " " + Integer.toString(booty)
					+ " " + Integer.toString(numCrew), Toast.LENGTH_LONG).show();
			globalModel.addUnit(new MainShip(globalModel));
			globalModel.setPrevMainShip(new MainShip(globalModel));
			globalModel.setLevel(level);
			LevelManager.restartLevel(globalModel);
			globalModel.addUnit(globalModel.getMainShip().getMainCannon());
			globalModel.setBooty(booty);
			for (int c = numCrew; c > 0; c--)
				globalModel.addUnit(new Crew(context, globalModel));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(context, "You have no saved Games", Toast.LENGTH_LONG).show();
			globalModel.addUnit(new MainShip(globalModel));
			globalModel.setPrevMainShip(new MainShip(globalModel));
			globalModel.addUnit(globalModel.getMainShip().getMainCannon());
			// TODO finish testing crew
			globalModel.addUnit(new Crew(context, globalModel));
			globalModel.addUnit(new Crew(context, globalModel));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(context, "IOException", Toast.LENGTH_LONG).show();
		}
    }


}	
