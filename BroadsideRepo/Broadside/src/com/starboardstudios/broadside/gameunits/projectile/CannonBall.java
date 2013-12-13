package com.starboardstudios.broadside.gameunits.projectile;

import android.widget.LinearLayout;
import com.starboardstudios.broadside.gameunits.BaseUnit;
import com.starboardstudios.broadside.gameunits.CombatUnit;
import com.starboardstudios.broadside.R.drawable;
import com.starboardstudios.broadside.gameunits.Model;
import com.starboardstudios.broadside.gameunits.ships.EasyShip;
import com.starboardstudios.broadside.gameunits.ships.MainShip;
import com.starboardstudios.broadside.gameunits.turrets.Swivel;
import com.starboardstudios.broadside.gameunits.turrets.Turret;

//MUHUHAHAHA!!!!!!! The fluff is gone!
public class CannonBall extends Projectile {

	/*Used to create cannonball that stores data for other constructor*/
	public CannonBall(Model model, int damage) {
		super(model);
		this.damage = defaultDamage = 20;
		if (damage != -1) {
			this.damage = damage;
		} 
	}

	/* Used by turrets and units */
	public CannonBall(Projectile projectile, float x, float y,
			float fireSpeed, float angle) {
		super(projectile, x, y, fireSpeed, angle);
		size = (float) .04;
		imageView.setImageResource(drawable.cannon_ball);
		imageView.setAdjustViewBounds(true);
		imageView.setLayoutParams(new LinearLayout.LayoutParams((int) (model
				.getScreenX() * size), (int) (model.getScreenY() * size))); // Set size
		height = imageView.getLayoutParams().height;
		width = imageView.getLayoutParams().width;
		if (projectile.turret instanceof Swivel || projectile.turret == null) {
			drop = false;
		} else {
			drop = false; //false for testing
		}
	}

	@Override
	public Projectile create(Projectile projectile, float x, float y,
			float fireSpeed, float angle) {
		return new CannonBall(projectile, x, y, fireSpeed, angle);
	}

}
