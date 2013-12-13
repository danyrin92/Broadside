package com.starboardstudios.broadside.gameunits.projectile;

import android.content.Context;
import android.widget.ImageView;
import com.starboardstudios.broadside.controller.PlayController;
import com.starboardstudios.broadside.gameunits.*;
import com.starboardstudios.broadside.gameunits.ships.MainShip;
import com.starboardstudios.broadside.gameunits.turrets.Turret;

public abstract class Projectile extends BaseUnit {
	protected Context context;
	protected ImageView imageView;
	public Model model;
    public BaseUnit creator;
    protected Turret turret;
	protected int damage, defaultDamage;
	protected float speed, angle, xSpeed, ySpeed, xTarget, yTarget, startX, startY, startZ, height, width, size;
	protected float range = -1;
	protected double distanceToTarget = -1;
	protected double r2; //radius squared
	protected boolean drop = false;
	
    /*Used by subclasses*/
	public Projectile(Model model) {
		this.model = model;
		this.context = model.context;
		imageView = new ImageView(context);
	}
	
	/*Used by cannonballs, torpedoes, and missiles*/
	public Projectile(Projectile projectile, float x, float y, float speed, float angle) {
		this.model = projectile.getModel();
		this.context = model.context;
		this.x = startX = x;
		this.y = startY = y;
		this.z = startZ = model.getMainShip().getZ();
		this.damage = projectile.getDamage();
		this.speed = speed;
		this.angle = angle;
		this.ySpeed = (float) Math.sin(angle) * speed;
		this.xSpeed = (float) Math.cos(angle) * speed;
		imageView = new ImageView(context);
		xTarget = yTarget = -1;
	}

	private Model getModel() {
		return model;
	}

	public void update() {
		x = x + xSpeed;
		y = y + ySpeed;
		
		double distanceFromStart = Math.sqrt(Math.pow((startX - x), 2) + Math.pow((startY - y), 2));
		float offset = (float) ((startZ*distanceFromStart)/distanceToTarget);
		z = (float) Math.sqrt(r2 - Math.pow((distanceFromStart - .5*distanceToTarget), 2)) - offset;
		if (turret != null) {
			//System.out.println(drop);
		}
		boolean maxRange = distanceFromStart > range;
		if (range !=-1 && maxRange) {
			destroy();
		} else if (z<0 && drop) {
			destroy();
		}

		model.runOnMain(new Runnable() {
			public void run() {
                    imageView.setX(x);
				    imageView.setY(y);
				    resize(); //resize based on z
			}
		});
	}
	
	@Override
	public void collide(final BaseUnit collidedWith) {


        addExplosion(new Explosion(model), collidedWith.getX(), collidedWith.getY());




		if (creator instanceof MainShip) {
			if (!((MainShip) collidedWith instanceof MainShip) && !((Turret) collidedWith instanceof Turret)) {
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
		distanceToTarget = Math.sqrt(Math.pow((startX - xTarget), 2) + Math.pow((startY - yTarget), 2));
		r2 = distanceToTarget*distanceToTarget*.25; //radius squared
	}
	
	public void setTurret(Turret turret) {
		this.turret = turret;
		this.range = turret.getRange();
	}
	
	public Turret getTurret() {
		return turret;
	}
	
	public void drop(boolean drop) {
		this.drop = drop;
	}
	
	public void resize() {
		if (drop) {
			float scale = (float) 7;
			float scaleFactor = (z/model.getScreenX())*scale;
			imageView.getLayoutParams().height = (int) (height*scaleFactor);
			imageView.getLayoutParams().width = (int) (width*scaleFactor);
		}
	}
	
	//for avoiding instanceof checks when using fire methods
	public abstract Projectile create(Projectile projectile, float x,	float y, float fireSpeed, float angle);

	public boolean getDrop() {
		return drop;
	}
 
    public void addExplosion(Explosion fire, float x, float y) {
        try {
            //System.out.println("Add fire...");
            PlayController currentActivity = (PlayController) model
                    .getCurrentActivity();
            currentActivity
                    .runOnUiThread(new MyRunnable(fire, x, y, this.model));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

class MyRunnable implements Runnable {
    private Explosion fire;
    private float x;
    private float y;
    private PlayController currentActivity;
    private Model model;

    public MyRunnable(Explosion fire, float x, float y, Model model) {
        this.fire = fire;
        this.x = x;
        this.y = y;
        this.model = model;
        currentActivity = (PlayController) model.getCurrentActivity();
    }

    public void run() {
        currentActivity.addExplosion(fire, x, y);
    }

}