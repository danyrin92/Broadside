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
	private int scaleFactor = (int) (model.getScreenY() * .08);

	public Torpedo(Model model, int damage) {
		super(model);
		this.damage = defaultDamage = 20;
		if (damage != -1) {
			this.damage = damage;
		}
	}

	public Torpedo(Model model, int damage, float x, float y, float fireSpeed, float angle) {
		super(model, damage, x, y, fireSpeed, 0);
		imageView.setImageResource(drawable.torpedo);
		imageView.setAdjustViewBounds(true);
		imageView.setLayoutParams(new LinearLayout.LayoutParams((int) (model
				.getScreenX() * .05), scaleFactor));
		imageView.setScaleType(ScaleType.CENTER_CROP);
		if (xSpeed>0) {
			imageView.setRotation(180); //spin to face enemy...
		}
		System.out.println("Torpedo damage: " + damage);
	}
	
	@Override
	public Projectile create(Model model, int damage, float x, float y, float fireSpeed, float angle) {
		return new Torpedo(model, damage, x, y, fireSpeed, angle);
	}

}
