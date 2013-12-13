package com.starboardstudios.broadside.gameunits.projectile;

import java.util.ArrayList;

import android.widget.LinearLayout;

import com.starboardstudios.broadside.R.drawable;
import com.starboardstudios.broadside.gameunits.*;
import com.starboardstudios.broadside.gameunits.ships.MainShip;
import com.starboardstudios.broadside.gameunits.turrets.Turret;

public class Missile extends Projectile {
	private BaseUnit target;
	private int defaultDamage = 20;

	public Missile(Model model, int damage) {
		super(model);
		this.damage = defaultDamage = 20;
		if (damage != -1) {
			this.damage = damage;
		}
	}

	public Missile(Projectile projectile, float x, float y, float speed, float angle) {
		super(projectile, x, y, speed, angle);
		size = (float) .1;
		imageView.setImageResource(drawable.missile);
		imageView.setAdjustViewBounds(true);
		imageView.setLayoutParams(new LinearLayout.LayoutParams((int) (model
				.getScreenX() * size), (int) (model.getScreenY() * size))); // Set size
		if (xSpeed>0) {
			imageView.setRotation(180); //spin to face enemy...
		}
		//Checking if target is found
		if ((target=selectTarget()) != null) {
			System.out.println("TARGET FOUND!!!!!!!!!!!!!!!!!!!!!!!!");
		} 
	}
	
	@Override
	public Projectile create(Projectile projectile, float x, float y, float fireSpeed, float angle) {
		return new Missile(projectile, x, y, fireSpeed, angle);
	}
	
	private BaseUnit selectTarget() {
			ArrayList<BaseUnit> units = model.getUnits();
			BaseUnit unit = null;
			float minDistance,xDistance,yDistance,distance;
			distance = minDistance = model.getScreenX();
			//Check if there are units. Find closest Unit.
			for(int i=0; i < units.size(); i++) {
				if (units.get(i) instanceof CombatUnit) {
					if (units.get(i).getX() > this.x) {
						xDistance = Math
								.abs(units.get(i).getX() - this.x);
						yDistance = Math
								.abs(units.get(i).getY() - this.y);
						distance = (float) Math.sqrt(xDistance
								* xDistance + yDistance * yDistance);
						if (distance < minDistance) {
							minDistance = distance;
							unit = units.get(i);
						}
					}
				}
			}
			return unit;
	}

	@Override
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
	}/*
		if (target == null) {
			selectTarget();
			return;
		}
		float targetX = (target.getImage().getWidth()/2) + target.getX();
		float targetY = (target.getImage().getHeight()/2) + target.getY();
		float missileX = (this.imageView.getWidth()/2) + this.x;
		float missileY = (this.imageView.getHeight()/2) + this.y; 
		float xDistance = targetX - missileX;
		float yDistance = targetY - missileY;
		float distance = (float) Math.sqrt(xDistance*xDistance + yDistance*yDistance);
		float targetAngle = (float) Math.acos(xDistance/distance);
		if (target.getY() < this.y) { //target above missile
			if (Math.abs(angle) > targetAngle) this.angle += 0.025;
			else this.angle -= 0.025;
		} else { //target below missile
			if (Math.abs(angle) > targetAngle) this.angle -= 0.025;
			else this.angle += 0.025;
		}
		float ySpeed = (float) Math.sin(angle) * speed;
		float xSpeed = (float) Math.cos(angle) * speed;
		
				x = x + xSpeed;
				y = y + ySpeed;

				model.runOnMain(new Runnable() {
					public void run() {
							imageView.setRotation((float)(angle*180/Math.PI)+180);
		                    imageView.setX(x);
						    imageView.setY(y);
					}
				});
	*/
}
