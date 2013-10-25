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

		imageView.setImageResource(drawable.missile);
		imageView.setAdjustViewBounds(true);
		imageView.setLayoutParams(new LinearLayout.LayoutParams((int) (model
				.getScreenX() * .7), scaleFactor));

	}

	public Missile(Model model, int damage, int x, int y) {
		super(model, damage, x, y);

		imageView.setImageResource(drawable.missile);
		imageView.setAdjustViewBounds(true);
		imageView.setLayoutParams(new LinearLayout.LayoutParams((int) (model
				.getScreenX() * .7), scaleFactor)); // Set
																		// size
	}

	public Missile(Model model, int damage, float x, float y, float xFireSpeed, float yFireSpeed) {
		super(model, damage, x, y, xFireSpeed, yFireSpeed);

		imageView.setImageResource(drawable.missile);
		imageView.setAdjustViewBounds(true);
		imageView.setLayoutParams(new LinearLayout.LayoutParams((int) (model
				.getScreenX() * .05), scaleFactor)); 
																		

	}

	public Missile(Model model, int defaultDamage2, int x, int y,
			float xFireSpeed, float yFireSpeed) {
		super(model,defaultDamage2,x,y, xFireSpeed,yFireSpeed);
	}

	public int getDefaultDamage() {
		return defaultDamage;
	}

	@Override
	public void setPosition(int x, int y) {

	}

	@Override
	public Projectile create(Model model, float x, float y, float xFireSpeed, float yFireSpeed) {
		return new Missile(model, defaultDamage, x, y, xFireSpeed, yFireSpeed);
	}

	@Override
	public Projectile create(Model model, int damage, float x, float y, int xFireSpeed, int yFireSpeed) {
		return new Missile(model, damage, x, y, xFireSpeed, yFireSpeed);
	}
	
}
