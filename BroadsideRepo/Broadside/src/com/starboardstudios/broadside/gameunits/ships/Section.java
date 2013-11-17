package com.starboardstudios.broadside.gameunits.ships;

import android.app.Activity;

import com.starboardstudios.broadside.app.BroadsideApplication;
import com.starboardstudios.broadside.controller.PlayController;
import com.starboardstudios.broadside.gameunits.Model;
import com.starboardstudios.broadside.gameunits.Fire;
import com.starboardstudios.broadside.gameunits.projectile.Projectile;

import java.util.*;

public class Section {

	// properties
	private float x, y;
	int health, maxHealth;
	private int maxHealthBars, maxBarHealth;
	private int numHealthBars;
	private float currentBarHealth;
	private ArrayList<Fire> fires = new ArrayList<Fire>();
	private Model model;
	private boolean damaged;

	// constructors
	Section(Model model, float x, float y, int health) {
		this.model = model;
		this.x = x;
		this.y = y;
		this.health = maxHealth = health;
		numHealthBars = maxHealthBars = 3;
		currentBarHealth = maxBarHealth = health / 3;
		damaged = false;
	}

	// methods
	public void damage(Projectile p) {
		int damage = p.getDamage();
		damaged = true;
		health -= damage;
		//System.out.println("Section Health: " + health);

		// surplus damage discounted, next healthbar started
		if (currentBarHealth - damage < 0) { 
			if (numHealthBars - 1 == 0) {
				// section is basically destroyed; either more vulnerable, bleeding damage, or both
			} else {
				numHealthBars--;
				spawnFires();
				currentBarHealth = maxBarHealth;
				health = numHealthBars*maxBarHealth;
			}
		} else {
			currentBarHealth -= damage;
		}

	}

	public void repair(float repair) {
		// repairs carry over
		if (currentBarHealth + repair > maxBarHealth) { //overflow
			if (numHealthBars >= maxHealthBars) { //fully repaired
				currentBarHealth = maxBarHealth;
				numHealthBars = maxHealthBars;
				despawnAllFires();
				damaged = false;
			} else {
				numHealthBars++;
				// All fires for that level of damage should be removed
				float overflow = repair - (maxBarHealth - currentBarHealth);
				currentBarHealth = overflow; 
			}
			despawnFires();
			addHealth(maxBarHealth);
			model.getMainShip().addHealth(maxBarHealth); //give the mainship an installment of health
		} else {
			currentBarHealth += repair;
		}
	}

	public void spawnFires() {
		// determine fire spawns
		float x, y;
		Fire fire;
		int range = 100;
		int r1, r2;
		// spawn maxHealthBars - numHealthBars Fires in section
		int numFiresToSpawn = maxHealthBars - numHealthBars;
		for (int i = 0; i < numFiresToSpawn; i++) {
			// TODO if needed make values relative
			r1 = rand(range);
			r2 = rand(range);
			x = this.x + r1;
			y = this.y + r2;
			fire = new Fire(model);
			/*System.out.println("Spawn fires...");
			System.out.println("X: " + x + " r1: " + r1 + " Y: " + y + " r2: "
					+ r2);*/
			addFire(fire, x, y);
		}
	}

	//add fire to play screen
	public void addFire(Fire fire, float x, float y) {
		try {
			//System.out.println("Add fire...");
			PlayController currentActivity = (PlayController) model
					.getCurrentActivity();
			currentActivity
					.runOnUiThread(new MyRunnable(fire, x, y, this.model));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//called in setposition of fire (when it is placed on play screen)
	public void addFire(Fire fire) {
		fires.add(fire);
	}
	
	private void despawnFires() {
		if (fires.size()>0) {
			int numFiresToDespawn = maxHealthBars - numHealthBars + 1;
			for (int i=0; i<numFiresToDespawn; i++) {
				model.removeUnit(fires.get(i));
			}
		}
	}
	
	private void despawnAllFires() {
		for (int i=0; i<fires.size(); i++) {
			model.removeUnit(fires.get(i));
		}
	}

	// randomly generate a number from min to max
	public static int rand(int Min, int Max) {
		return Min + (int) (Math.random() * ((Max - Min) + 1));
	}

	// randomly generates a number from -x to x
	public static int rand(int x) {
		return rand(-x, x);
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getMaxHealth() {
		return maxHealth;
	}
	
	public void addHealth(int repairAmount) {
		if (health+repairAmount>maxHealth) {
			health = maxHealth;
		} else {
			health+=repairAmount;
		}
	}
	
	public boolean isDamaged() {
		return damaged;
	}
	
	public Fire getAFire() {
		if (fires.size()>0) {
			return fires.get(0);
		} 
		return null;
	}
	
	public void removeFire(Fire fire) {
		fires.remove(fire);
	}

}

class MyRunnable implements Runnable {
	private Fire fire;
	private float x;
	private float y;
	private PlayController currentActivity;
	private Model model;

	public MyRunnable(Fire fire, float x, float y, Model model) {
		this.fire = fire;
		this.x = x;
		this.y = y;
		this.model = model;
		currentActivity = (PlayController) model.getCurrentActivity();
	}

	public void run() {
		currentActivity.addFire(fire, x, y);
	}
	
}