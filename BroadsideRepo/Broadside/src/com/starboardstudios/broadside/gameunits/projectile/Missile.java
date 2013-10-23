package com.starboardstudios.broadside.gameunits.projectile;

import android.widget.LinearLayout;
import com.starboardstudios.broadside.R.drawable;
import com.starboardstudios.broadside.gameunits.Model;

public class Missile extends Projectile {
	private int defaultDamage = 20;
	private int scaleFactor = (int) (model.getScreenY() * .08);
	public Missile(Model model) {
		super(model);
	}

	public Missile(Model model, int damage) {
		super(model, damage);
		x = 0;
		y = 0;
		z = 0;
		xSpeed = 0;
		ySpeed = 0;
		zSpeed = 0;

		imageView.setImageResource(drawable.missile);
		imageView.setAdjustViewBounds(true);
		imageView.setLayoutParams(new LinearLayout.LayoutParams((int) (model
				.getScreenX() * .7), scaleFactor));

	}

	public Missile(Model model, int damage, int x, int y, int z) {
		super(model, damage, x, y, z);

		imageView.setImageResource(drawable.missile);
		imageView.setAdjustViewBounds(true);
		imageView.setLayoutParams(new LinearLayout.LayoutParams((int) (model
				.getScreenX() * .7), scaleFactor)); // Set
																		// size
	}

	public Missile(Model model, int damage, int x, int y, int z, int xSpeed,
			int ySpeed, int zSpeed) {
		super(model, damage, x, y, z, xSpeed, ySpeed, zSpeed);

		imageView.setImageResource(drawable.missile);
		imageView.setAdjustViewBounds(true);
		imageView.setLayoutParams(new LinearLayout.LayoutParams((int) (model
				.getScreenX() * .05), scaleFactor)); 
																		

	}

	public int getDefaultDamage() {
		return defaultDamage;
	}

	@Override
	public Projectile create(Model model, int x, int y, int z, int xFireSpeed,
			int yFireSpeed, int zFireSpeed) {
		return new CannonBall(model, defaultDamage, x, y, z, xFireSpeed,
				yFireSpeed, zFireSpeed);
	}

	@Override
	public Projectile create(Model model, int damage, int x, int y, int z,
			int xFireSpeed, int yFireSpeed, int zFireSpeed) {
		return new CannonBall(model, damage, x, y, z, xFireSpeed, yFireSpeed,
				zFireSpeed);
	}

	@Override
	public void setPosition(int x, int y) {

	}
}
