package com.starboardstudios.broadside.gameunits.ships;

import com.starboardstudios.broadside.controller.PlayController;
import com.starboardstudios.broadside.gameunits.Fire;
import com.starboardstudios.broadside.gameunits.Model;
import com.starboardstudios.broadside.gameunits.projectile.Projectile;

import java.util.ArrayList;

public class Section {

	// properties
	private float x, y;
	private int maxHealthBars, maxBarHealth, numHealthBars, health, maxHealth, damageLevel;
	private float currentBarHealth;
	private ArrayList<Fire> fires = new ArrayList<Fire>();
	private Model model;
	private boolean damaged;

	// constructors
	Section(Model model, float x, float y, int health) {
		this.model = model;
		this.x = x;
		this.y = y;
		damageLevel = 0;
		this.health = maxHealth = health;
		numHealthBars = maxHealthBars = 5;
		currentBarHealth = maxBarHealth = maxHealth / maxHealthBars;
		damaged = false;
	}

	// methods
	public void damage(Projectile p) {
		int damage = p.getDamage();
		if (!damaged) {
			damaged = true;
			damageLevel = 1;
			spawnFires(damageLevel);
		}
		// surplus damage discounted, next healthbar started
		if (health > 0) {
			health -= damage;
			if (currentBarHealth - damage <= 0) { //healthbar gone
				if (numHealthBars - 1 == 0) { //section all but destroyed
					//do nothing
				} else { //damageLevel increased, update visuals
					numHealthBars--;
					damageLevel = maxHealthBars - numHealthBars+1;
					//System.out.println("spawnFires damageLevel: " + damageLevel);
					spawnFires(damageLevel);
					currentBarHealth = maxBarHealth;
					health = numHealthBars*maxBarHealth;
				}
			} else { //healthbar chipped at
				currentBarHealth -= damage;
			}
		} else { //health depleted
			health = 0;
		}
	}

	public void repair(float repair) {
		// repairs carry over
		if (currentBarHealth + repair > maxBarHealth) { //overflow
			if (numHealthBars >= maxHealthBars) { //fully repaired
				currentBarHealth = maxBarHealth;
				numHealthBars = maxHealthBars;
				despawnAllFires();
				damageLevel = 0;
				damaged = false;
				health = maxHealth;
			} else { //decrease damageLevel, update visuals
				//System.out.println("despawnFires damageLevel: " + damageLevel);
				despawnFires(damageLevel);
				numHealthBars++;
				damageLevel = maxHealthBars - numHealthBars+1;
				float overflow = repair - (maxBarHealth - currentBarHealth);
				currentBarHealth = overflow; 
				addHealth(maxBarHealth);
			}
			model.getMainShip().updateHealth();
		} else { //add health to healthbar
			currentBarHealth += repair;
		}
	}

	public void spawnFires(int numFiresToSpawn) {
		// determine fire spawns
		float x, y;
		Fire fire;
		int range = 75;
		int r1, r2;
		// spawn maxHealthBars - numHealthBars Fires in section
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
	
	private void despawnFires(int numFiresToDespawn) {
		for (int i= 0; i < numFiresToDespawn && i < fires.size(); i++) {
			model.removeUnit(fires.get(i));
		}
	}
	
	private void despawnAllFires() {
		for (int i = fires.size() - 1; i >= 0; i--) {
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
		printStatus();
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
	
	public void printStatus() {
		String name = model.getMainShip().determineSectionName(y);
		System.out.println(name + " at: " + health + " health after repair cycle.");
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