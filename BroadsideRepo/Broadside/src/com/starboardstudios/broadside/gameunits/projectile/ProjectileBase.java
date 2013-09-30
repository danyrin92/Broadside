package com.starboardstudios.broadside.gameunits.projectile;

import android.content.Context;

public abstract class ProjectileBase {
	protected int damage;
	protected Context context;
	
	public ProjectileBase(Context c)
	{
		this.context=c;
	}

}
