package com.starboardstudios.broadside.gameunits.ships;

import android.view.View;
import android.widget.ImageView;
import com.starboardstudios.broadside.gameunits.BaseUnit;
import com.starboardstudios.broadside.gameunits.CombatUnit;
import com.starboardstudios.broadside.gameunits.Model;
import com.starboardstudios.broadside.gameunits.projectile.Projectile;

import java.util.Random;

  public abstract class BaseShip extends CombatUnit {
	  
	/** Random value for choosing pathing track */
	Random rand = new Random();
	int random = rand.nextInt(3);
	int moveCount = 0;

	public BaseShip(Model model) {
		super(model.context);
		this.model = model;

		imageView.setVisibility(View.INVISIBLE);
		imageView.setAdjustViewBounds(true);

	}

	@Override
	public ImageView getImage() {
		return imageView;
	}

	public void setVelocity(int xSpeed, int ySpeed) {
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
	}

	public void collide(BaseUnit unit) {
		if (unit instanceof Projectile) {
			if (((Projectile)unit).creator instanceof MainShip) {
				damage(((Projectile) unit).getDamage());
			}
		}
	}
	
	public void damage(int damage) {
		health -= damage;
		//TODO: Add animation to the damage method
		if (health <= 0) {
			destroy();
		}
	}
	
	public void destroy() {
		//TODO: Add animations to the destroy method
		model.removeUnit(this);
		model.addBooty(plunder);
	}

}
