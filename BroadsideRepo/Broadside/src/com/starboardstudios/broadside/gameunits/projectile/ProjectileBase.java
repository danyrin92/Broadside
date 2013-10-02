package com.starboardstudios.broadside.gameunits.projectile;

import android.content.Context;
import com.starboardstudios.broadside.gameunits.BaseUnit;

public abstract class ProjectileBase extends BaseUnit {
	protected int damage;
	protected Context context;
	
	public ProjectileBase(Context c)
	{
		this.context=c;
	}

}
