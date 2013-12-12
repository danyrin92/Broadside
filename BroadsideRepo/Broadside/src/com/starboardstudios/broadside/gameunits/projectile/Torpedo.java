package com.starboardstudios.broadside.gameunits.projectile;

import android.widget.LinearLayout;
import android.widget.ImageView.ScaleType;

import com.starboardstudios.broadside.R.drawable;
import com.starboardstudios.broadside.gameunits.BaseUnit;
import com.starboardstudios.broadside.gameunits.CombatUnit;
import com.starboardstudios.broadside.gameunits.Model;
import com.starboardstudios.broadside.gameunits.ships.MainShip;
import com.starboardstudios.broadside.gameunits.turrets.Turret;

public class Torpedo extends Projectile {
	private int defaultDamage;

	public Torpedo(Model model, int damage) {
		super(model);
		this.damage = defaultDamage = 20;
		if (damage != -1) {
			this.damage = damage;
		}
	}

	public Torpedo(Projectile projectile, float x, float y, float fireSpeed, float angle) {
		super(projectile, x, y, fireSpeed, 0);
		size = (float) .05;
		imageView.setImageResource(drawable.torpedo);
		imageView.setAdjustViewBounds(true);
		imageView.setLayoutParams(new LinearLayout.LayoutParams((int) (model
				.getScreenX() * size), (int) (model.getScreenY() * size))); // Set size
		imageView.setScaleType(ScaleType.CENTER_CROP);
		if (xSpeed>0) {
			imageView.setRotation(180); //spin to face enemy...
		}
		System.out.println("Torpedo damage: " + damage);
	}
	
	public void update() {
		x = x + xSpeed;
		y = y + ySpeed;
		
		double distanceFromStart = Math.sqrt(Math.pow((startX - x), 2) + Math.pow((startY - y), 2));
		
		boolean maxRange = distanceFromStart > range;
		if (range !=-1 && maxRange) {
			destroy();
		} 

		model.runOnMain(new Runnable() {
			public void run() {
                    imageView.setX(x);
				    imageView.setY(y);
			}
		});
	}
	
	@Override
	public Projectile create(Projectile projectile, float x, float y, float fireSpeed, float angle) {
		return new Torpedo(projectile, x, y, fireSpeed, angle);
	}

}
