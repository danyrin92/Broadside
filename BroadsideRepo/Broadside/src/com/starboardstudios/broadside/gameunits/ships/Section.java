package com.starboardstudios.broadside.gameunits.ships;

import com.starboardstudios.broadside.gameunits.Model;
import com.starboardstudios.broadside.gameunits.projectile.Projectile;

public class Section {
	
	//properties
	private int x,y;
	private int maxHealthBars;
	private int maxBarHealth;
	private int numHealthBars;
	private int currentBarHealth;
	
	//constructors
	Section(Model model, float x, float y) {
		this.x = (int)x;
		this.y = (int)y;
		numHealthBars = maxHealthBars = 3;
		currentBarHealth = maxBarHealth = 10;
	}
	
	//methods
	public void damage(Projectile p) {
		int damage = p.getDamage();
		if (currentBarHealth-damage<0) {
			//surplus damage discounted, next healthbar started
			if (numHealthBars-1<=0) {
				//section is basically destroyed; either more vulnerable, bleeding damage, or both
			} else {
				numHealthBars--;
				//TODO spawn fire(s)
				currentBarHealth = maxBarHealth;
			}
		} else {
			currentBarHealth-=damage;
		}
	}
	
	public void repair(int repair) {
		//repairs carry over
		if (currentBarHealth+repair>maxBarHealth) {
			if (numHealthBars == maxHealthBars) {
				currentBarHealth = maxBarHealth;
			} else {
				numHealthBars++;
				//All fires for that level of damage should be removed by repairAt method...
				currentBarHealth = repair - (maxBarHealth-currentBarHealth); //overflow
			}
		} else {
			currentBarHealth+=repair;
		}
	}
	
}