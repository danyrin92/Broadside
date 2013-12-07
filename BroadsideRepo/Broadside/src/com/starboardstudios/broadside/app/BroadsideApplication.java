package com.starboardstudios.broadside.app;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.starboardstudios.broadside.gameunits.Crew;
import com.starboardstudios.broadside.gameunits.Model;
import com.starboardstudios.broadside.gameunits.ships.MainShip;
import com.starboardstudios.broadside.gameunits.turrets.Cannon;
import com.starboardstudios.broadside.gameunits.turrets.LaserCannon;
import com.starboardstudios.broadside.gameunits.turrets.MineLauncher;
import com.starboardstudios.broadside.gameunits.turrets.MissileLauncher;
import com.starboardstudios.broadside.gameunits.turrets.Swivel;
import com.starboardstudios.broadside.gameunits.turrets.TorpedoLauncher;
import com.starboardstudios.broadside.gameunits.turrets.Turret;
import com.starboardstudios.broadside.util.LevelManager;

/**
 * Created by alex on 10/16/13.
 */
public class BroadsideApplication extends Application {

    private Model globalModel;
    public Boolean load = false;
    public String username;
    
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
			osw.write(globalModel.getNumTurrets());
			osw.flush();
			osw.close();
			
			ArrayList <Float> turrets = globalModel.getTurretPos();
			DataOutputStream out = 
					new DataOutputStream(openFileOutput("savedTurrets.bin", Context.MODE_PRIVATE));
			for(int i = 0; i < turrets.size(); i++)
				out.writeFloat(turrets.get(i));
			out.flush();
			out.close();
			
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
			int numTurrets = isr.read();
			isr.close();
			
			LevelManager.loadLevel(globalModel);
			Toast.makeText(context, Integer.toString(level) 
					+ " " + Integer.toString(booty)
					+ " " + Integer.toString(numCrew), Toast.LENGTH_LONG).show();
			MainShip mainShip = new MainShip(globalModel, true);
			globalModel.addUnit(mainShip);
			globalModel.setPrevMainShip(mainShip);
			for (int c = numCrew; c > 0; c--)
			{
				Crew crew = new Crew(this, globalModel);
				globalModel.addUnit(crew);
				float offset = (float) (.02*(mainShip.getCrew().size()-1));
				float x = (float)(mainShip.getX()+((globalModel.getScreenX()*.345)));
				float y = (float)(mainShip.getY() +((globalModel.getScreenX()*(.3-offset))));
				crew.setPosition(x,y);
				crew.update();
			}
			globalModel.setLevel(level);
			globalModel.addUnit(globalModel.getMainShip().getMainCannon());
			globalModel.setBooty(booty);
			
			fin = openFileInput("savedTurrets.bin");
			DataInputStream in = new DataInputStream(fin);
			Turret turret = null;
			for(int i = numTurrets; i > 0; i--) {
				int turretNum = (int) in.readFloat();
				int x = (int) in.readFloat();
				int y = (int) in.readFloat();
				switch (turretNum) {
				case 1:
					turret = new Cannon(globalModel, x, y);
					break;
				case 2:
					turret = new Swivel(globalModel);
					break;
				case 3:
					turret = new TorpedoLauncher(globalModel);
					break;
				case 4:
					turret = new MineLauncher(globalModel);
					break;
				case 5:
					turret = new MissileLauncher(globalModel);
					break;
				case 6:
					turret = new LaserCannon(globalModel);
					break;
				}
				globalModel.addUnit(turret);
			}
			in.close();

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