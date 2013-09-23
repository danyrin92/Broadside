package com.starboardstudios.broadside.structures;

import java.util.ArrayList;
import java.math.*;

public class MainShip extends Ship{
	private ArrayList<Section> sections;
	private ArrayList<Crew> crew;
	private MainCannon mainCannon;
	
	private int waterLevel = 0;
	private int health = 100;
	
	void Damage(Projectile p){
		//Dummy example
		health = health - p.getDamage();
	}
	
	void UpdateVisuals(){
		
	}
	
	void FireMain(int x, int y){
		int deltaX = x-this.x;
		//Below is swapped due to Y being inverted with our screen
		int deltaY = this.y-y;
		double degreeAngle = Math.atan((deltaY/deltaX))* 180 / Math.PI;
		
		//Make new projectile firing in this direction.
	}
	
}
