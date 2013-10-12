package com.starboardstudios.broadside.gameunits.projectile;

import android.content.Context;
import com.starboardstudios.broadside.gameunits.BaseUnit;
import com.starboardstudios.broadside.gameunits.Model;

public abstract class ProjectileBase extends BaseUnit {
	protected int damage;
	protected Context context;
    public Model model;


    public ProjectileBase(Context c)
	{
		this.context=c;
	}

    public void collide(BaseUnit collidedWith)
    {

          this.destroy();


    }

    public void destroy()
    {
       model.removeUnit(this);

    }



}
