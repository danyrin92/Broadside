package com.starboardstudios.broadside.gameunits.ships;

import java.util.ArrayList;

import android.content.Context;

import com.starboardstudios.broadside.gameunits.projectile.Projectile;
import com.starboardstudios.broadside.gameunits.turrets.Turret;

public abstract class Ship {

	private ArrayList<Turret> turrets;
	protected int x;
	protected int y;
	protected Context context;
	
	public Ship(Context c)
	{
		this.context=c;
	}
	
	
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
