package com.starboardstudios.broadside.gameunits.projectile;

import android.widget.LinearLayout;
import com.starboardstudios.broadside.R.drawable;
import com.starboardstudios.broadside.gameunits.Model;

public class CannonBall extends Projectile {
	private int defaultDamage = 20;
	
	public CannonBall(Model model) {
		super(model);
	}
	
	public CannonBall(Model model, int damage) {
		super(model, damage);
		x = 0;
		y = 0;
		z = 0;
		xSpeed = 0;
		ySpeed = 0;
		zSpeed = 0;

		imageView.setImageResource(drawable.cannon_ball);
		imageView.setAdjustViewBounds(true);
		imageView.setLayoutParams(new LinearLayout.LayoutParams((int) (model
				.getScreenX() * .7), (int) (model.getScreenY() * .7))); // Set
																		// size
		

	}
	
	public CannonBall(Model model, int damage, int x, int y, int z, int xSpeed, int ySpeed, int zSpeed) {
		super(model, damage);
		x = this.x;
		y = this.y;
		z = this.z;
		xSpeed = this.xSpeed;
		ySpeed = this.ySpeed;
		zSpeed = this.zSpeed;

		imageView.setImageResource(drawable.cannon_ball);
		imageView.setAdjustViewBounds(true);
		imageView.setLayoutParams(new LinearLayout.LayoutParams((int) (model
				.getScreenX() * .7), (int) (model.getScreenY() * .7))); // Set
																		// size
		

	}
	public int getDefaultDamage() {
		return defaultDamage;
	}
	
	@Override
	public Projectile create(Model model, int x, int y, int z, int xFireSpeed, int yFireSpeed, int zFireSpeed) {
		return new CannonBall(model, defaultDamage, x, y, z, xFireSpeed, yFireSpeed, zFireSpeed);
	}
	
	@Override
	public Projectile create(Model model, int damage, int x, int y, int z, int xFireSpeed, int yFireSpeed, int zFireSpeed) {
		return new CannonBall(model, damage, x, y, z, xFireSpeed, yFireSpeed, zFireSpeed);
	}

    @Override
    public void setPosition(int x, int y) {

    }
}
