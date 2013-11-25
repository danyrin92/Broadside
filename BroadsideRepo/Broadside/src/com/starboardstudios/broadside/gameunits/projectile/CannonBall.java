package com.starboardstudios.broadside.gameunits.projectile;

import android.widget.LinearLayout;
import com.starboardstudios.broadside.gameunits.BaseUnit;
import com.starboardstudios.broadside.gameunits.CombatUnit;
import com.starboardstudios.broadside.R.drawable;
import com.starboardstudios.broadside.gameunits.Model;
import com.starboardstudios.broadside.gameunits.ships.MainShip;
import com.starboardstudios.broadside.gameunits.turrets.Turret;

//MUHUHAHAHA!!!!!!! The fluff is gone!
public class CannonBall extends Projectile {
	private int scaleFactor = (int) (model.getScreenY() * .03);

	/*Used to create cannonball that stores data for other constructor*/
	public CannonBall(Model model, int damage) {
		super(model);
		this.damage = defaultDamage = 20;
		if (damage != -1) {
			this.damage = damage;
		} 
	}

	/* Used by turrets and units */
	public CannonBall(Model model, int damage, float x, float y,
			float fireSpeed, float angle) {
		super(model, damage, x, y, fireSpeed, angle);
		imageView.setImageResource(drawable.cannon_ball);
		imageView.setAdjustViewBounds(true);
		imageView.setLayoutParams(new LinearLayout.LayoutParams((int) (model
				.getScreenX() * .15), scaleFactor));
	}

	@Override
	public Projectile create(Model model, int damage, float x, float y,
			float fireSpeed, float angle) {
		return new CannonBall(model, damage, x, y, fireSpeed, angle);
	}

}
