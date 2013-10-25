package com.starboardstudios.broadside.util;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import android.R.integer;

import com.starboardstudios.broadside.gameunits.CombatUnit;
import com.starboardstudios.broadside.gameunits.Model;
import com.starboardstudios.broadside.gameunits.aircrafts.EasyAircraft;
import com.starboardstudios.broadside.gameunits.ships.EasyShip;
import com.starboardstudios.broadside.gameunits.ships.HardShip;
import com.starboardstudios.broadside.gameunits.ships.MainShip;
import com.starboardstudios.broadside.gameunits.ships.MediumShip;
import com.starboardstudios.broadside.gameunits.submarine.EasySubmarine;

abstract final LevelManager extends Thread {
	final int MAX_LEVEL = 2; 
		
	//BaseShips: 000 to 099
	final int ID_EASYSHIP = 000;
	final int ID_MEDIUMSHIP = 001;
	final int ID_HARDSHIP = 002;
		
	//BaseAircraft: 100 to 199
	final int ID_EASYAIRCRAFT = 100;
		
	//BaseSubmarine: 200 to 299
	final int ID_EASYSUBMARINE = 200;
		
	/** For knowing when to go to the next level */
	boolean finalWave = false;
	boolean stillAlive;
		
	/** Amount of delay until the next wave in milliseconds*/
	int delay;
		
	/** Needed for added units to the model */
	Model model;
		
	/** Stores unit ID's for next Wave of enemies to spawn */
	ArrayList<integer> nextWaveUnits = new ArrayList<integer>();
		
	private void LevelReader(){
			
		BufferedReader reader = new BufferedReader(new FileReader("~/Broadside/BroadsideLevels.txt"));
			
			
	}
		
	/** For timing the spawning of units and reading the level */
	/*@Override
	public void run() {
			
		System.out.println("LevelManager Started ");
		while (true) {
			if (currentActivity != null) {
				if (currentActivity.name.equalsIgnoreCase("PlayController")) {
					if (finalWave == true) {
						/** Check if there are any enemies left before going to the next level
						
						delay = 2000; /** Needed for sleep while on the final wave. Arbiltarily picked amount
						stillAlive = false;
						for(int i = 0; i < units.size(); i++) {
							if ((units.get(i) instanceof CombatUnit == true) & (units.get(i) instanceof MainShip == false ) ) {
								stillAlive = true;
								break;
							}	
						}
						if (stillAlive == false) {
							nextLevel();
						}
					} else {
						nextWave(); //read next two lines from the level text. Gets units to add and next delay.
					}
				}
			}
			
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}*/
		
		
	/**
	 * TODO: Get LevelManager update method working for unit spawning and level management.
	 * 		Must implement a real time delay for wave spawning. 
	 */
	private void nextWave() {
			
	}
		
	/**
	 * Increment level. Set the reader location to zero. Change file to next level. Go to upgrade screen.
	 */
	private void nextLevel() {
		//TODO: Increment level. Set the reader location to zero. Change file to next level. Go to upgrade screen.
		/** Loop levels to minic infinite levels.
		 * Use difficulty parameter to adjust difficulty  */
		int level = model.getLevel();
		
		delay = 100; /** Reset the delay after it was changed in the final wave loop */
		
		if ((level % Max_LEVEL + 2) >= MAX_LEVEL){
			difficulty += 1;
		}
		
		level++;
		model.setDifficulty(difficulty);
		model.setLevel(level);
	}
		
	/**
	 * Spawn unit based off of integer.
	 * 
	 *  Hundreds place represents type:
	 *  	0 = BaseShip
	 *  	1 = BaseAircraft
	 *  	2 = BaseSubmarine
	 *  Tens and single digits place represent the specific type.
	 *  
	 *  ID given as a final in level manager
	 *  
	 * @param id
	 */
	private void spawn(int id) {
		if ((currentActivity.name.equalsIgnoreCase("PlayController"))) {
			if (id > 300) {
				return;
			} else {
				/** Increases the spawn amount by difficulty*/
				for(int i = 0; i < model.getDifficulty; i++) {
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
