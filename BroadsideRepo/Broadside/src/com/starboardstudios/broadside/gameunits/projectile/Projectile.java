package com.starboardstudios.broadside.gameunits.projectile;

import android.widget.ImageView;
import android.widget.LinearLayout;
import com.starboardstudios.broadside.R.drawable;
import com.starboardstudios.broadside.gameunits.Model;

public class Projectile extends ProjectileBase {
	public ImageView imageView = new ImageView(context);
	private int x, y, z, xSpeed, ySpeed, zSpeed;

	public Projectile(Model model) {
		super(model.context);
		this.model = model;
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

	public int getDamage() {
		// TODO Auto-generated method stub
		return damage;
	}

	@Override
	public void update() {
		x = x + xSpeed;
		y = y + ySpeed;

		model.runOnMain(new Runnable() {
			public void run() {
				imageView.setX(x);
				imageView.setY(y);
				imageView.setImageResource(drawable.cannon_ball);
			}

		});

	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

	public int getxSpeed() {
		return xSpeed;
	}

	public void setxSpeed(int xVelo) {
		this.xSpeed = xVelo;
	}

	public int getySpeed() {
		return ySpeed;
	}

	public void setySpeed(int yVelo) {
		this.ySpeed = yVelo;
	}

	public int getzSpeed() {
		return zSpeed;
	}

	public void setzSpeed(int zVelo) {
		this.zSpeed = zVelo;
	}

	public ImageView getImage() {
		return imageView;
	}
	
	
}
