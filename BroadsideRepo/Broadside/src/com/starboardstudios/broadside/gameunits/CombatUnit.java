package com.starboardstudios.broadside.gameunits;

import android.content.Context;
import android.widget.ImageView;

import com.starboardstudios.broadside.gameunits.projectile.Projectile;
import com.starboardstudios.broadside.gameunits.turrets.Turret;

import java.util.ArrayList;

public abstract class CombatUnit extends BaseUnit {
	//Renamed from "Ship" to more appropriately reflect hierarchy and purpose
	
	protected int x;
	protected int y;
	protected int xSpeed;
	protected int ySpeed;
	protected Context context;
	protected int health;
	protected Model model;
	
	public CombatUnit(Context c)
	{
		this.context=c;
	}
	
	void destroy(){
		//Destroy means different things depending on if it's a main ship
		//Or enemy. Will be implemented in extended classes
	}
	
	void GoTo(int x, int y){
		this.x = x;
		this.y = y;
		xSpeed = 0;
		ySpeed = 0;
	}
	
	protected void Damage(Projectile p){
		health = health - p.getDamage();
	}
	
	void Update(){
		
	}
	
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
    public void setPosition(int x, int y) {
		this.x = ((int) (model.getScreenX()) + x);
		this.y = ((int) (model.getScreenY() + y));
    }
}
