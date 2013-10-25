package com.starboardstudios.broadside.util;

import android.R.integer;
import com.starboardstudios.broadside.R;
import com.starboardstudios.broadside.gameunits.Model;
import com.starboardstudios.broadside.gameunits.aircrafts.EasyAircraft;
import com.starboardstudios.broadside.gameunits.ships.EasyShip;
import com.starboardstudios.broadside.gameunits.ships.HardShip;
import com.starboardstudios.broadside.gameunits.ships.MediumShip;
import com.starboardstudios.broadside.gameunits.submarine.EasySubmarine;

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
		static int[][] levelArray = new int[100][11];
		
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

                int lineCounter = 0;
                String line = null;
				try {
					while ((line = breader.readLine()) != null)
					{
						String[] fileLine = line.split(",");
						System.out.print(fileLine.toString());
						int numberShips = 0;
						int counter = 0;
						for(String lineInfo : fileLine)
						{
							if(Integer.parseInt(lineInfo) % 2 != 1)
							{
								numberShips += Integer.parseInt(lineInfo);
							}
							levelArray[lineCounter][counter] = Integer.parseInt(lineInfo);
							counter++;
						}
						levelArray[lineCounter][counter] = numberShips;
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
				//startLevel(model.getLevel());
				newLevel = false;
			}
		}	
	}
