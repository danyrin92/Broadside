package com.starboardstudios.broadside.util;

import android.R.integer;
import android.content.Intent;

import com.starboardstudios.broadside.controller.BaseController;
import com.starboardstudios.broadside.controller.PlayController;
import com.starboardstudios.broadside.gameunits.aircrafts.EasyAircraft;
import com.starboardstudios.broadside.gameunits.ships.EasyShip;
import com.starboardstudios.broadside.gameunits.ships.HardShip;
import com.starboardstudios.broadside.gameunits.ships.MediumShip;
import com.starboardstudios.broadside.gameunits.submarine.EasySubmarine;
import com.starboardstudios.broadside.R;
import com.starboardstudios.broadside.gameunits.Model;

import java.io.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public abstract class LevelManager {
	private static int MAXLEVEL; 
	
	//BaseShips: 000 to 099
	public final static int ID_EASYSHIP = 000;
	public final static int ID_MEDIUMSHIP = 001;
	public final static int ID_HARDSHIP = 002;
	
	//BaseAircraft: 100 to 199
	public final static int ID_EASYAIRCRAFT = 100;
	
	//BaseSubmarine: 200 to 299
	public final static int ID_EASYSUBMARINE = 200;
	
	/**
	 * Each row is a level. <br> 
	 * Even columns are the total amount of that type of unit that column represents.<br> 
	 * Odd columns are delays between each spawn till the total amount define in the previous even column.<br> 
	 * Delay is in miliseconds <br>
	 *<p>
	 * Example:<br>
	 * 	Column 0 is EasyShip total amount then Column 1 is the delay between each one spawned till total amount reached. 
	 */
	static int[][] levelArray = new int[100][10];
	
	/** Load levelArray only once */
	static boolean hasLoadedLevelArray = false;
	
	public static void LevelReader(Model model){
		if (hasLoadedLevelArray == false) {
			try {
	            System.out.println(model.toString());
	            InputStream stream =  model.getCurrentActivity().getBaseContext().getResources().openRawResource(R.raw.broadside_levels);
	            InputStreamReader reader = new InputStreamReader(stream);
	            BufferedReader breader = new BufferedReader(reader);
	            System.out.println("BAHAHAHAHAHAH I WORKED");
	            
	            int lineCounter = 0;
	            String line = null;
				try {
					while ((line = breader.readLine()) != null)
					{
						String[] fileLine = line.split(",");
						System.out.print(fileLine.toString());
						int counter = 0;
						for(String lineInfo : fileLine)
						{
							levelArray[lineCounter][counter] = Integer.parseInt(lineInfo);
							counter++;
						}
						lineCounter++;
					}
					
					hasLoadedLevelArray = true;
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				MAXLEVEL = lineCounter;
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}
	
	/**
	 * Start level and start spawning timers in the model
	 * @param level int representing the level
	 */
	public static void startLevel(final Model model) {
		//startTimers(level);
		int level = model.getLevel();
		model.clearTimer();
	
		model.setNumOfEnemies(levelArray[level][0] + levelArray[level][2] + levelArray[level][4]+ levelArray[level][6] + levelArray[level][8]);
		
		/** Start enemy spawning timers in the model */
		model.startSpawn(ID_EASYSHIP,levelArray[level][0], levelArray[level][1]);
		model.startSpawn(ID_MEDIUMSHIP, levelArray[level][2],levelArray[level][3]);
		model.startSpawn(ID_HARDSHIP, levelArray[level][4], levelArray[level][5]);
		model.startSpawn(ID_EASYAIRCRAFT, levelArray[level][6], levelArray[level][7]);
		model.startSpawn(ID_EASYSUBMARINE, levelArray[level][8], levelArray[level][9]); 
	
		TimerTask waitForSuccess = new TimerTask() {
			@Override
			public void run() {	
				if (model.getCurrentActivity().name.equalsIgnoreCase("PlayController")) {	
					/**Start timers for spawning enemies */
					 if (model.getNumOfEnemies() <= 0) {
						/** When all enemies have been defeated go to the next level */
						/** Increment level*/
						 model.setLevel(model.getLevel() + 1);
						
						/** Go to Upgrade then end program*/
						try {
							PlayController currentActivity = (PlayController)model.getCurrentActivity();
							currentActivity.gotoUpgrades();
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						this.cancel();
						return;
					}
				}
			}
		};
		/** Check that the player has beaten the game every two second */
		model.getTimer().schedule(waitForSuccess,0,2000);
	}
	
	/**
	 * Clears all spawning timers and enemy units from the model. 
	 * Then restart the level.
	 */
		public static void restartLevel(Model model) {
			model.getTimer().cancel();
			model.removeAllEnemiesAndProjectile();
			startLevel(model);
		}
	}
