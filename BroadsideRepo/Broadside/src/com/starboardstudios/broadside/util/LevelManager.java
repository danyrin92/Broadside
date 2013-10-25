package com.starboardstudios.broadside.util;

import android.R.integer;
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
		final static int MAXLEVEL = 2; 
		
		//BaseShips: 000 to 099
		final static int ID_EASYSHIP = 000;
		final static int ID_MEDIUMSHIP = 001;
		final static int ID_HARDSHIP = 002;
		
		//BaseAircraft: 100 to 199
		final static int ID_EASYAIRCRAFT = 100;
		
		//BaseSubmarine: 200 to 299
		final static int ID_EASYSUBMARINE = 200;
		
		//Ships, then Airplane, then Submarine. Each difficulty with the delay after it.
		static int[][] levelArray = new int[100][10];
		
		/** For knowing when to go to the next level */
		static boolean finalWave = false;
		static boolean stillAlive;
		
		/** Amount of delay until the next wave in milliseconds*/
		static int delay;
		
		/** Needed for added units to the model */
		static Model model;
		
		/** Stores unit ID's for next Wave of enemies to spawn */
		static ArrayList<integer> nextWaveUnits = new ArrayList<integer>();
		
		static boolean newLevel = false;
		/** Timer for spawing enemy units */
		static Timer timer;
		
		public static void LevelReader(Model model){
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
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}

		/** Interface for model*/
		public static void update() {
			if (newLevel == true) {
				startLevel(model.getLevel());
				newLevel = false;
			}
		}
		
		/** 
		 * Starts the timers for enemy unit spawning
		 * @param level
		 */
		private static void startTimers(final int level) {
			//Reset timers
			timer = new Timer();
			
			/** EasyShip spawn timer */
			TimerTask taskEasyShip = new TimerTask() {
				@Override
				public void run() {
					spawn(levelArray[level][0]);
				};
			};
			
			/** MediumShip spawn timer */
			TimerTask taskMediumShip = new TimerTask() {
				@Override
				public void run() {
					spawn(levelArray[level][2]);
				};
			};
			
			/** HardShip spawn timer */
			TimerTask taskHardShip = new TimerTask() {
				@Override
				public void run() {
					spawn(levelArray[level][4]);
				};
			};
			
			/** EasyAircraft spawn timer */
			TimerTask taskEasyAircraft = new TimerTask() {
				@Override
				public void run() {
					spawn(levelArray[level][6]);
				};
			};
			
			/** EasySubmarine spawn timer */
			TimerTask taskEasySubmarine = new TimerTask() {
				@Override
				public void run() {
					spawn(levelArray[level][8]);
				};
			};
			
			/** Add TimerTask to timer. 
			 * An initial 10 sec delay to wait for the MainShip to stop moving.
			 */
			timer.schedule(taskEasyShip, 10000, levelArray[level][1]);
			timer.schedule(taskMediumShip, 10000, levelArray[level][3]);
			timer.schedule(taskHardShip, 10000, levelArray[level][5]);
			timer.schedule(taskEasyAircraft, 10000, levelArray[level][7]);
			timer.schedule(taskEasySubmarine, 10000 , levelArray[level][9]);
		}
		
		private static void startLevel() {
			startLevel(model.getLevel());
		}
		
		private static void startLevel(int level) {
			startTimers(level);
			
			TimerTask waitForSuccess = new TimerTask() {
				@Override
				public void run() {
					int level;
					/** When all enemies have been defeated go to the next level */
					if (model.getNumOfEnemies() <= 0) {
						/** Increment level*/
						level = model.getLevel();
						level++;
						model.setLevel(level);
						
						/** Go to Upgrade then end program*/
						//TODO: Switch to the upgrade screen from the LevelManager
						
						newLevel = true;
						timer.cancel();
						timer.purge();
						return;
					}
				}
			};
			/** Check that the player has beaten the game every two second */
			timer.schedule(waitForSuccess,0,2000);
		}
			
		/**
		 * Spawn unit based off of integer that ID's what type of enemy.
		 * 
		 *  ID given as a final in level manager
		 * @param id
		 */
		private static void spawn(int id) {
			if ((model.getCurrentActivity().name.equalsIgnoreCase("PlayController"))) {
				if (id > 300) {
					return;
				} else {
					/** Increases the spawn amount by difficulty */
					for(int i = 0; i < model.getDifficulty(); i++) {
						switch (id) {
							//BaseShips
							case ID_EASYSHIP:
								model.addUnit(new EasyShip(model));
								break;
							
							case ID_MEDIUMSHIP:
								model.addUnit(new MediumShip(model));
								break;
							
							case ID_HARDSHIP:
								model.addUnit(new HardShip(model));
								break;
							
							//BaseAircraft
							case ID_EASYAIRCRAFT:
								model.addUnit(new EasyAircraft(model));
								break;
							
							//BaseSubmarine	
							case ID_EASYSUBMARINE:
								model.addUnit(new EasySubmarine(model));
								break;
						
						}
					}
				}
			}
		}
			
	}
