package com.starboardstudios.broadside.gameunits.projectile;

import android.widget.LinearLayout;

import com.starboardstudios.broadside.R.drawable;
import com.starboardstudios.broadside.gameunits.Model;

public class Torpedo extends Projectile {
	private int defaultDamage = 20;
	private int scaleFactor = (int) (model.getScreenY() * .08);

	public Torpedo(Model model) {
		super(model);
	}

	public Torpedo(Model model, int damage) {
		super(model, damage);
		x = 0;
		y = 0;
		xSpeed = 0;
		ySpeed = 0;

		imageView.setImageResource(drawable.torpedo);
		imageView.setAdjustViewBounds(true);
		imageView.setLayoutParams(new LinearLayout.LayoutParams((int) (model
				.getScreenX() * .7), scaleFactor));

	}

	public Torpedo(Model model, int damage, float x, float y, float fireSpeed, float angle) {
		super(model, damage, x, y, fireSpeed, angle);

		imageView.setImageResource(drawable.torpedo);
		imageView.setAdjustViewBounds(true);
		imageView.setLayoutParams(new LinearLayout.LayoutParams((int) (model
				.getScreenX() * .05), scaleFactor));

	}

	public int getDefaultDamage() {
		return defaultDamage;
	}

	@Override
	public void setPosition(int x, int y) {

	}
	
	@Override
	public Projectile create(Model model, int damage, float x, float y, float fireSpeed, float angle) {
		return new Torpedo(model, damage, x, y, fireSpeed, angle);
	}
}
