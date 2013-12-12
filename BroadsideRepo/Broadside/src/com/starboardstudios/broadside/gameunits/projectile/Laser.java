package com.starboardstudios.broadside.gameunits.projectile;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.widget.LinearLayout;
import com.starboardstudios.broadside.gameunits.BaseUnit;
import com.starboardstudios.broadside.gameunits.CombatUnit;
import com.starboardstudios.broadside.R.drawable;
import com.starboardstudios.broadside.gameunits.Model;
import com.starboardstudios.broadside.gameunits.ships.MainShip;
import com.starboardstudios.broadside.gameunits.turrets.Turret;

//MUHUHAHAHA!!!!!!! The fluff is gone!
public class Laser extends Projectile {

	/*Used to create cannonball that stores data for other constructor*/
	public Laser(Model model, int damage) {
		super(model);
		this.damage = defaultDamage = 20;
		if (damage != -1) {
			this.damage = damage;
		} 
	}

	/* Used by turrets and units */
	public Laser(Projectile projectile, float x, float y,
			float fireSpeed, float angle) {
		super(projectile, x, y, fireSpeed, angle);
		size = (float) .05;
		imageView.setImageResource(drawable.laser);
		imageView.setAdjustViewBounds(true);
		imageView.setLayoutParams(new LinearLayout.LayoutParams((int) (model
				.getScreenX() * size), (int) (model.getScreenY() * size))); // Set size
		imageView.setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY); //make red
		System.out.println("Laser created");
	}

	@Override
	public Projectile create(Projectile projectile, float x, float y,
			float fireSpeed, float angle) {
		return new Laser(projectile, x, y, fireSpeed, angle);
	}

}
