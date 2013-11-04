package com.starboardstudios.broadside.gameunits.projectile;

import android.content.Context;
import android.widget.ImageView;
import com.starboardstudios.broadside.gameunits.BaseUnit;
import com.starboardstudios.broadside.gameunits.Model;

public abstract class Projectile extends BaseUnit {
	protected Context context;
	protected ImageView imageView;
	protected int damage;
	/** Added Z value for possible image scaling */
	protected float z, xSpeed, ySpeed;
	protected float xTarget, yTarget;
	public Model model;
    public BaseUnit creator;

	public Projectile(Model model) {
		this.model = model;
		this.context = model.context;
		imageView = new ImageView(context);
	}

	public Projectile(Model model, int damage) {
		this.model = model;
		this.context = model.context;
		this.damage = damage;
		imageView = new ImageView(context);

	}

	public Projectile(Model model, int damage, int x, int y) {
		this.model = model;
		this.context = model.context;
		this.damage = damage;
		this.x = x;
		this.y = y;
		imageView = new ImageView(context);

	}

	public Projectile(Model model, int damage, float x, float y, float xSpeed,
			float ySpeed) {
		this.model = model;
		this.context = model.context;
		this.damage = damage;
		this.x = x;
		this.y = y;
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		if ((int) xSpeed != 0) {
			if (xSpeed / (int) xSpeed >= 1.01) { // handle float to int
													// conversion
				this.xSpeed++;
			}
		}
		if ((int) ySpeed != 0) {
			if (ySpeed / (int) ySpeed >= 1.01) {
				this.ySpeed++;
			}
		}
		//TODO maybe cast as int...

		imageView = new ImageView(context);

	}

	public Projectile(Model model, int damage, int x, int y, int xSpeed,
			int ySpeed) {
		this.model = model;
		this.context = model.context;
		this.damage = damage;
		this.x = x;
		this.y = y;
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		imageView = new ImageView(context);

	}

	public void collide(BaseUnit collidedWith) {
		this.destroy();
	}

	public void update() {
		// TODO delete projectile once off screen

		x = x + xSpeed;
		y = y + ySpeed;

		model.runOnMain(new Runnable() {
			public void run() {


                    imageView.setX(x);
				    imageView.setY(y);





				//System.out.println(" x " + x + " y " + y);


			}

		});


	}

	public ImageView getImage() {
		return imageView;
	}

	public void destroy() {
		model.removeUnit(this);

	}

	public int getDamage() {
		return this.damage;
	}

	public float getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

	public float getxSpeed() {
		return xSpeed;
	}

	public void setxSpeed(int xVelo) {
		this.xSpeed = xVelo;
	}

	public float getySpeed() {
		return ySpeed;
	}

	public void setySpeed(int yVelo) {
		this.ySpeed = yVelo;
	}

	public abstract int getDefaultDamage();

	public abstract Projectile create(Model model, float x, float y,
			float xFireSpeed, float yFireSpeed);

	public abstract Projectile create(Model model, int damage, float x,
			float y, int xFireSpeed, int yFireSpeed);
}
