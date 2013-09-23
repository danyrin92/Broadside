package com.starboardstudios.broadside.structures;

import java.util.ArrayList;
import com.starboardstudios.broadside.structures.turrets.Turret;

public abstract class Ship {

	private ArrayList<Turret> turrets;
	protected int x;
	protected int y;
	
	
	ArrayList<Turret> getTurrets(){
		return turrets;
	}
	
	void destroy(){
		//Destroy means different things depending on if it's a main ship
		//Or enemy. Will be implemented in extended classes
	}
	
	void GoTo(int x, int y){
	}
	
	void Damage(Projectile p){
		
	}
	
	void Update(){
		
	}
	
	
}
