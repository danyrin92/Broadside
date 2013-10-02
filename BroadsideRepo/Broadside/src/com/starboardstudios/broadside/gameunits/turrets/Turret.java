package com.starboardstudios.broadside.gameunits.turrets;

import android.content.Context;
import com.starboardstudios.broadside.gameunits.BaseUnit;
import com.starboardstudios.broadside.gameunits.projectile.Projectile;

public abstract class Turret extends BaseUnit {

	protected int x,y,z;
	protected Context context;
	protected Projectile projectile;
	boolean canAffect;
	double range;
	
	public Turret(Context c) {
		this.context = c;
	}	    
	    
	void spawn() {
		
	}
	void destroy() {
		
	}
	void fire() {
		
	}
	void randomFire() {
		
	}
	
}
