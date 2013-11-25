package com.starboardstudios.broadside.gameunits.turrets;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.View;
import android.widget.ImageView;

import com.starboardstudios.broadside.gameunits.BaseUnit;
import com.starboardstudios.broadside.gameunits.projectile.CannonBall;
import com.starboardstudios.broadside.gameunits.projectile.Projectile;
import com.starboardstudios.broadside.gameunits.Model;
import com.starboardstudios.broadside.interfaces.Draggable;

public abstract class Turret extends BaseUnit implements Draggable {
	
	//Properties
	protected Model model;
	protected Context context;
	protected Projectile projectile;
	protected ImageView imageView;
	/**numbers*/
	protected float fireSpeed, size;
	double range;
	protected int cost, turretNum, cooldown, currentCooldown;
	
	//Constructors
	/*Used by Main Cannon*/
	public Turret(Model model, float x, float y) {
		this.context = model.context;
		imageView = new ImageView(context);
		this.model = model;
		this.x = x;
		this.y = y;
		turretNum = 0;
	}
	
	/*Used by turrets*/
	public Turret(Model model) {
		this.context = model.context;
		imageView = new ImageView(context);
		this.model = model;
		currentCooldown = 0;
	}

	//Methods
	public Projectile getProjectile() {
		return this.projectile;
	}

	/*Used by mainCannon and turret1/cannon*/
	public void fire(float xTarget, float yTarget) { //fire at designated target
		if (currentCooldown == 0){ 
			//determine center of turret image and firing angle
			float centerX = imageView.getX();
			float centerY = imageView.getY() + (float)(imageView.getHeight()/2.7);
			float xDifference = (xTarget - centerX);
			float yDifference = (yTarget - centerY);
			float angle = (float) Math.atan(yDifference / xDifference);
			if (xDifference > 0 && yDifference > 0) { // x+, y+
			} else if (xDifference < 0 && yDifference > 0) { // x-, y+
				angle += Math.PI;
			} else if (xDifference > 0 && yDifference < 0) { // x+, y-
			} else { // x-, y-
				angle += Math.PI;
			}
			//handle offsets
			float lengthFromCenterToBarrelTip = (float)(imageView.getWidth()/4);
			float xOffset = (float) (lengthFromCenterToBarrelTip*Math.cos(angle)); 
			float yOffset = (float) (lengthFromCenterToBarrelTip*Math.sin(angle));
			float startX = centerX+xOffset;
			float startY = centerY+yOffset;
			imageView.setRotation((float)(angle*(180/Math.PI)));
			//spawn projectile
			Projectile temp = projectile.create(model, projectile.getDamage(), startX, startY, fireSpeed, angle);
			temp.creator = this;
			model.addUnit(temp);
			//handle cooldown
			imageView.setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
			currentCooldown=cooldown;
			//printDebugInfo(xTarget, yTarget, xDifference, yDifference);
		}
	}
	
	/*Used by torpedo and missile launchers*/
	public void fire() { //simply fire straight ahead
		float startX = this.x;
		float startY = this.y;
		float angle = 0;
		Projectile temp = projectile.create(model, projectile.getDamage(), startX, startY, fireSpeed, angle);
		temp.creator = this;
		model.addUnit(temp);
		imageView.setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
		currentCooldown=cooldown;
		System.out.println("MainShip input damage: " + projectile.getDamage());
	}

	/**
	 * Checks if x and y coordinate are a valid place to put a turret
	 * 
	 * @param x         x coordinate to check
	 * @param y		    y coordinate to check
	 * @return 			boolean for if it's a correct place
	 */
	public boolean turretCheck(float x, float y) {
		if (model.checkCollision(this, model.getMainShip())) {
			return true;
		}
		return false;
	}
	
	public abstract void setImageView(ImageView image);
	
	public void spendSetCost(int cost) {
		this.cost = cost;
		model.spendBooty(cost);
	}
	
	public void printXY() {
		System.out.println(" X: " + this.x + " Y: " + this.y);
	}
	
	public void printDebugInfo(float xTarget, float yTarget, float xDifference, float yDifference) {
		float angle = (float) Math.atan(yDifference / xDifference);
		System.out.println("angle: " + angle +" X: " +this.x+" Y: "+ this.y+ " xTarget:  " + xTarget+ " yTarget: "+ yTarget+" xDiff: "+xDifference+" ydiff: "+yDifference);
	}
	
	public int getCost() {
		return cost;
	}

}
