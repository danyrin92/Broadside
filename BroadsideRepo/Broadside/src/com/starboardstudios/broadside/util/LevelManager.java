package com.starboardstudios.broadside.util;

import com.starboardstudios.broadside.controller.PlayController;
import com.starboardstudios.broadside.R;
import com.starboardstudios.broadside.gameunits.Model;

import java.io.*;
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
	 * Order: EasyShip, MediumShip, HardShip, EasyAircraft, EasySubmarine <br>
	 *<p>
	 * Example case:<br>
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
	            System.out.println("BufferReader for Levemanager Successful");
	            
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
	
		/** Start enemy spawning timers in the model */
		int difficulty = (int) Math.floor(level/ MAXLEVEL) + 1;
		System.out.println("Spawn ammount modifier = " + difficulty);
		int row = (level - 1) % MAXLEVEL;
		System.out.println("Row in Levelmanager equals " + row);
		model.startSpawn(ID_EASYSHIP, difficulty*levelArray[row][0], levelArray[row][1]);
		model.startSpawn(ID_MEDIUMSHIP, difficulty*levelArray[row][2],levelArray[row][3]);
		model.startSpawn(ID_HARDSHIP, difficulty*levelArray[row][4], levelArray[row][5]);
		model.startSpawn(ID_EASYAIRCRAFT, difficulty*levelArray[row][6], levelArray[row][7]);
		model.startSpawn(ID_EASYSUBMARINE, difficulty*levelArray[row][8], levelArray[row][9]); 
	
		model.setNumOfEnemies(difficulty*(levelArray[level][0] + levelArray[level][2] + levelArray[level][4]+ levelArray[level][6] + levelArray[level][8]));
		
		TimerTask waitForSuccess = new TimerTask() {
			@Override
			public void run() {	
				if (model.getCurrentActivity().name.equalsIgnoreCase("PlayController")) {	
					/**Start timers for spawning enemies */
					 if (model.getNumOfEnemies() <= 0) {
						/** When all enemies have been defeated go to the next level */
						/** Increment level*/
						 int level = model.getLevel() + 1;
						 
						 /** Manages infinite level by increasing difficulty in the model */
						 model.setLevel(level);
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
