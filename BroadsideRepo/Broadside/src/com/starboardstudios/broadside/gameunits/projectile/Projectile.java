package com.starboardstudios.broadside.gameunits.projectile;

import android.content.Context;
import android.widget.ImageView;

import com.starboardstudios.broadside.gameunits.BaseUnit;
import com.starboardstudios.broadside.gameunits.CombatUnit;
import com.starboardstudios.broadside.gameunits.Model;
import com.starboardstudios.broadside.gameunits.ships.MainShip;
import com.starboardstudios.broadside.gameunits.turrets.Turret;

public abstract class Projectile extends BaseUnit {
	protected Context context;
	protected ImageView imageView;
	public Model model;
    public BaseUnit creator;
    protected Turret turret;
	protected int damage, defaultDamage;
	protected float speed, angle, z, xSpeed, ySpeed, xTarget, yTarget, startX, startY;
	protected float range = -1;
	
    /*Used by subclasses*/
	public Projectile(Model model) {
		this.model = model;
		this.context = model.context;
		imageView = new ImageView(context);
	}
	
	/*Used by cannonballs, torpedoes, and missiles*/
	public Projectile(Model model, int damage, float x, float y, float speed, float angle) {
		this.model = model;
		this.context = model.context;
		this.x = startX = x;
		this.y = startY = y;
		this.damage = damage;
		this.speed = speed;
		this.angle = angle;
		this.ySpeed = (float) Math.sin(angle) * speed;
		this.xSpeed = (float) Math.cos(angle) * speed;
		imageView = new ImageView(context);
	}

	public void update() {
		x = x + xSpeed;
		y = y + ySpeed;
		boolean maxRange = Math.sqrt(Math.pow((startX - x), 2) + Math.pow((startY - y), 2)) > range;
		if (range!=-1 && maxRange) {
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
	public void collide(BaseUnit collidedWith) {
		if (creator instanceof MainShip) {
			if (!((MainShip) collidedWith instanceof MainShip)&& !((Turret) collidedWith instanceof Turret)) {
				destroy();
			}
		} else if ((creator instanceof CombatUnit)&& !(creator instanceof MainShip)) {
			if ((MainShip) collidedWith instanceof MainShip) {
				destroy();
			}
		}
	}

	@Override
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
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
	public int getDefaultDamage() {
		return defaultDamage;
	}
	
	public void setTarget(float x, float y) {
		this.xTarget = x;
		this.yTarget = y;
	}
	
	public void setTurret(Turret turret) {
		this.turret = turret;
		this.range = turret.getRange();
	}
	
	//for avoiding instanceof checks when using fire methods
	public abstract Projectile create(Model model, int damage, float x,	float y, float fireSpeed, float angle);

}
