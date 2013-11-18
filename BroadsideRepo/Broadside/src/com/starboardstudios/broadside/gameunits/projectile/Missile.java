package com.starboardstudios.broadside.gameunits.projectile;

import java.util.ArrayList;

import android.widget.LinearLayout;

import com.starboardstudios.broadside.R.drawable;
import com.starboardstudios.broadside.gameunits.*;

public class Missile extends Projectile {
	private BaseUnit target;
	private int defaultDamage = 20;
	private int scaleFactor = (int) (model.getScreenY() * .08);
	
	public Missile(Model model) {
		super(model);
		imageView.setImageResource(drawable.missile);
	}

	public Missile(Model model, int damage) {
		super(model, damage);
		
		x = 0;
		y = 0;
		xSpeed = 0;
		ySpeed = 0;

		imageView.setImageResource(drawable.missile);
		imageView.setAdjustViewBounds(true);
		imageView.setLayoutParams(new LinearLayout.LayoutParams((int) (model.getScreenX() * .7), scaleFactor));

	}

	public Missile(Model model, int damage, float x, float y, float speed) {
		super(model, damage, x, y);
		
		this.speed = speed;

		imageView.setImageResource(drawable.missile);
		imageView.setAdjustViewBounds(true);
		imageView.setLayoutParams(new LinearLayout.LayoutParams((int) (model.getScreenX() * .05), scaleFactor)); 
		
		//Checking if target is found
		if ((target=selectTarget()) != null) {
			System.out.println("TARGET FOUND!!!!!!!!!!!!!!!!!!!!!!!!");
		} else {
			
		}

	}
	
	private BaseUnit selectTarget() {
			ArrayList<BaseUnit> units = model.getUnits();
			BaseUnit unit = null;
			float minDistance,xDistance,yDistance,distance;
			minDistance = 0;
			//Check if there are units. Calculate distance from first target
			if (units.size() > 0) {
				if (units.get(0) instanceof CombatUnit) {
					if (units.get(0).getX() > this.x) {
						unit = units.get(0);
						xDistance = Math
								.abs(units.get(0).getX() - this.x);
						yDistance = Math
								.abs(units.get(0).getY() - this.y);
						minDistance = (float) Math.sqrt(xDistance * xDistance
								+ yDistance * yDistance);
					} else {
						minDistance = -1;
					}
				}
			} else {
				return null;
			}
			//Determine which unit is closest
			for(int i = 1; i < units.size(); i++) {
				if (units.get(i) instanceof CombatUnit) {
					if (units.get(i).getX() > this.x) {
						xDistance = Math
								.abs(units.get(i).getX() - this.x);
						yDistance = Math
								.abs(units.get(i).getY() - this.y);
						distance = (float) Math.sqrt(xDistance
								* xDistance + yDistance * yDistance);
						if (distance < minDistance || minDistance == -1) {
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
		if (target == null) {
			selectTarget();
			return;
		}
		// TODO delete projectile once off screen
		float xDistance = Math.abs(target.getX() - this.x);
		float yDistance = Math.abs(target.getY() - this.y);
		float distance = (float) Math.sqrt(xDistance*xDistance + yDistance*yDistance);
		float targetAngle = (float) Math.acos((target.getX() - this.x)/distance);
		float angleDifference = this.angle - targetAngle;
		System.out.println("this x: " + this.x + ". this y: " + this.y);
		System.out.println("target x: " + target.getX() + ". target y: " + target.getY());
		System.out.println("angle: " + this.angle);
		System.out.println("target angle: " + targetAngle);
		if (angleDifference > 0) this.angle--;
		else this.angle++;
		float ySpeed = (float) Math.sin(angle) * speed;
		float xSpeed = (float) Math.cos(angle) * speed;
		
				x = x + xSpeed;
				y = y + ySpeed;

				model.runOnMain(new Runnable() {
					public void run() {
		                    imageView.setX(x);
						    imageView.setY(y);
					}
				});
	}
	
	public int getDefaultDamage() {
		return defaultDamage;
	}

	@Override
	public void setPosition(int x, int y) {

	}

	@Override
	public Projectile create(Model model, int damage, float x, float y, float fireSpeed, float angle) {
		return new Missile(model, damage, x, y, fireSpeed);
	}
	
}
