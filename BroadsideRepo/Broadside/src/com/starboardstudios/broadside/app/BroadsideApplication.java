package com.starboardstudios.broadside.app;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
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
    public String userlevel;
    public String userscore;
    public boolean soundOn = true;
    
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
    
    /**
     * SAVES ALL GAME UNITS CONTAINED IN THE MODEL
     * INSIDE THE PHONE MEMORY.
     * @param context
     */
    public void saveModel(Context context)
    {
    	try {
			FileOutputStream fou = openFileOutput("savedLevel.bin", MODE_PRIVATE);
			OutputStreamWriter osw = new OutputStreamWriter(fou);
			osw.write(globalModel.getLevel());
			osw.write(globalModel.getBooty());
			osw.write(globalModel.numCrew);
			osw.write(globalModel.getNumTurrets());
			osw.write(globalModel.getScore());
			osw.write(globalModel.getShipHealth());
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
    
    /**
     * LOADS ALL OF THE SAVED GAME UNITS FROM THE PHONE MEMORY AND
     * SAVES THEM INSIDE THE MODEL.
     * @param context
     */
    public void loadModel(Context context)
    {
    	try {
			FileInputStream fin = openFileInput("savedLevel.bin");
			InputStreamReader isr = new InputStreamReader(fin);
			int level = isr.read();
			int booty = isr.read();
			int numCrew = isr.read();
			int numTurrets = isr.read();
			int score = isr.read();
			int health = isr.read();
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
			globalModel.setScore(score);
			globalModel.setShipHealth(health);
			
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
					turret = new Swivel(globalModel, x, y);
					break;
				case 3:
					turret = new TorpedoLauncher(globalModel, x, y);
					break;
				case 4:
					turret = new MineLauncher(globalModel, x, y);
					break;
				case 5:
					turret = new MissileLauncher(globalModel, x, y);
					break;
				case 6:
					turret = new LaserCannon(globalModel, x, y);
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
    
    /**
     * SAVES THE USER'S HIGHEST SCORE INTO THE PHONE MEMORY
     * @param highScore
     */
    public void saveHighScore(int highScore) {
    	try {
    		FileOutputStream out = openFileOutput("highScore.bin", MODE_PRIVATE);
			OutputStreamWriter osw = new OutputStreamWriter(out);
			osw.write(highScore);
			osw.flush();
			osw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /**
     * RETRIEVES THE USER'S HIGHEST SCORE FROM THE PHONE MEMORY
     * @return
     */
    public int loadHighScore() {
    	int highScore = -1;
    	try {
    		FileInputStream in = openFileInput("highScore.bin");
			InputStreamReader isr = new InputStreamReader(in);
			highScore = isr.read();
			isr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return highScore;
    }
    
    /**
     * SAVES THE USERS'S NAME, HIGHEST LEVEL, AND HIGHEST SCORE TO THE DATABASE
     */
    public void saveUser(Context context) {
    	boolean firstSave = true;
		try {
			FileInputStream fin = openFileInput("firstSave.bin");
			DataInputStream in = new DataInputStream(fin);
			firstSave = in.readBoolean();
		} catch (FileNotFoundException e) {
			
			//ROW INSERTED INTO THE LEADER BOARD
			userlevel = Integer.toString(globalModel.getLevel() - 1);
			userscore = Integer.toString(globalModel.getScore());
			new InsertAsyncTask().execute((Void) null);
			
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(firstSave == false){
			int highScore = loadHighScore();
			int currentScore = globalModel.getScore();
			if(currentScore > highScore){
				saveHighScore(currentScore);
				
				//ROW UPDATED IN THE LEADER BOARD
				userlevel = Integer.toString(globalModel.getLevel() - 1);
				userscore = Integer.toString(globalModel.getScore());
				new UpdateAsyncTask().execute((Void) null);
				
			}
		}
		
		try {
			DataOutputStream out;
			out = new DataOutputStream(openFileOutput("firstSave.bin", Context.MODE_PRIVATE));
			out.writeBoolean(false);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }
    
    class InsertAsyncTask extends AsyncTask<Void, Void, Boolean> {

        private void postData(String name, String level, String score) {
    	
        	HttpClient httpclient = new DefaultHttpClient();
        	HttpPost httppost = new HttpPost("http://ec2-54-211-251-187.compute-1.amazonaws.com/LeaderBoardInsert.php");

        	try {
        		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
        		nameValuePairs.add(new BasicNameValuePair("user_name", name));
        		nameValuePairs.add(new BasicNameValuePair("user_level", level));
        		nameValuePairs.add(new BasicNameValuePair("user_score", score));
        		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        		HttpResponse response = httpclient.execute(httppost);
        	}
        	catch(Exception e)
        	{
        		Log.e("log_tag", "Error:  "+e.toString());
        	}
        }
        
        @Override
        protected Boolean doInBackground(Void... params) {
            postData(username, userlevel, userscore);
            return null;
        }
    }
    
    class UpdateAsyncTask extends AsyncTask<Void, Void, Boolean> {

        private void postData(String name, String level, String score) {
    	
        	HttpClient httpclient = new DefaultHttpClient();
        	HttpPost httppost = new HttpPost("http://ec2-54-211-251-187.compute-1.amazonaws.com/LeaderBoardUpdate.php");

        	try {
        		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
        		nameValuePairs.add(new BasicNameValuePair("user_name", name));
        		nameValuePairs.add(new BasicNameValuePair("user_level", level));
        		nameValuePairs.add(new BasicNameValuePair("user_score", score));
        		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        		HttpResponse response = httpclient.execute(httppost);
        	}
        	catch(Exception e)
        	{
        		Log.e("log_tag", "Error:  "+e.toString());
        	}
        }
        
        @Override
        protected Boolean doInBackground(Void... params) {
            postData(username, userlevel, userscore);
            return null;
        }
    }
}	