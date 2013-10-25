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
		static int level = 0;
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
		public static int update() {
			if (newLevel == true) {
				level = model.getLevel();
				
				EasyShip es = new EasyShip(model);
				es.setPosition(((int) (model.getScreenX()) + 75),
				((int) (model.getScreenY() * .4)));
				model.addUnit(es, levelArray[level][0], levelArray[level][1]);
				
				MediumShip ms = new MediumShip(model);
				ms.setPosition(((int) (model.getScreenX()) + 75),
						((int) (model.getScreenY() * .4)));
				model.addUnit(ms, levelArray[level][2],  levelArray[level][3]);
				
				HardShip hs = new HardShip(model);
				hs.setPosition(((int) (model.getScreenX()) + 75),
						((int) (model.getScreenY() * .4)));
				model.addUnit(hs, levelArray[level][4],  levelArray[level][5]);
				
				EasySubmarine esub = new EasySubmarine(model);
				esub.setPosition(((int) (model.getScreenX()) + 75),
						((int) (model.getScreenY() * .4)));
				model.addUnit(esub, levelArray[level][6],  levelArray[level][7]);
				
				EasyAircraft ea = new EasyAircraft(model);
				ea.setPosition(((int) (model.getScreenX()) + 75),
						((int) (model.getScreenY() * .4)));
				model.addUnit(ea, levelArray[level][8],  levelArray[level][9]);
				
				newLevel = false;
				return levelArray[level][10];
			}
		}	
	}
