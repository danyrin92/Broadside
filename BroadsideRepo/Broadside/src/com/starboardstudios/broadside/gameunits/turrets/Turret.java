package com.starboardstudios.broadside.gameunits.turrets;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.View;
import android.widget.ImageView;
import com.starboardstudios.broadside.gameunits.BaseUnit;
import com.starboardstudios.broadside.gameunits.CombatUnit;
import com.starboardstudios.broadside.gameunits.Model;
import com.starboardstudios.broadside.gameunits.projectile.Projectile;
import com.starboardstudios.broadside.interfaces.Draggable;

import java.util.ArrayList;

public abstract class Turret extends BaseUnit implements Draggable {
	
	//Properties
	protected Model model;
	protected Context context;
	protected Projectile projectile;
	protected ImageView imageView;
	/**numbers*/
	protected float fireSpeed, size;
	protected float range = -1; //infinite unless specified
	protected int cost, turretNum, cooldown, currentCooldown;
	protected int shotsPerBurst = 1;
	protected int numShotsLeftInBurst = shotsPerBurst;
	
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

	/*Used by mainCannon, turret1/cannon, minelauncher, and laser cannon*/
	public Projectile fire(float xTarget, float yTarget) { //fire at designated target
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
			Projectile tempProj = projectile.create(projectile, startX, startY, fireSpeed, angle);
			tempProj.creator = model.getMainShip();
			tempProj.setTarget(xTarget, yTarget);
			tempProj.setTurret(this);
			model.addUnit(tempProj);
			tempProj.getImage().setRotation((float)(angle*(180/Math.PI)));
			//handle cooldown
			imageView.setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
			currentCooldown=cooldown;
			//printDebugInfo(xTarget, yTarget, xDifference, yDifference);
			return tempProj;
		}
		return null;
	}
	
	/*Used by torpedo and missile launchers(turrets 3 and 5)*/
	public void fire() { //simply fire straight ahead
		float startX = this.x;
		float startY = this.y;
		float angle = 0;
		Projectile temp = projectile.create(projectile, startX, startY, fireSpeed, angle);
		temp.creator = model.getMainShip();
		temp.setTurret(this);
		model.addUnit(temp);
		//imageView.setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
		currentCooldown=cooldown;
		//System.out.println("MainShip input damage: " + projectile.getDamage());
	}
	
	/*Used by laser cannon*/
	public void fireBurst(float xTarget, float yTarget) {
		int burstCooldown, burstCurrentCooldown;
		burstCurrentCooldown = burstCooldown = 100;
		while (numShotsLeftInBurst > 0) {
			if(fire(xTarget,yTarget)!=null) { //actually fired
				numShotsLeftInBurst--;
				if (numShotsLeftInBurst > 0) { //burst not over
					burstCurrentCooldown = burstCooldown;
				}
			} else { //update burst cooldown and check to see if ready to fire
				burstCurrentCooldown--;
				if (burstCurrentCooldown == 0) {
					currentCooldown = 0;
				}
			}
		}
		numShotsLeftInBurst = shotsPerBurst; //reset
	}
	
	/*Used by swivel*/
	public void fireAtClosestTarget() { //fires at closest target within range
		BaseUnit target = selectTarget();
		if (target!=null) { //there is a target to shoot at
			if (withinRange(target)) {
				float targetX = target.getX();
				float targetY = target.getY();
				fire(targetX, targetY).drop(false);
			}
		}
	}
	
	protected BaseUnit selectTarget() {
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
	
	public void setCost (int cost) {
		this.cost = cost;
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

	public float getRange() {
		return range;
	}
	
	public boolean withinRange(BaseUnit target) {
		if (range == -1) {
			return true;
		} else {
			float targetX = target.getX();
			float targetY = target.getY();
			return Math.sqrt(Math.pow((targetX - x), 2) + Math.pow((targetY - y), 2)) <= range;
		}
	}

    @Override
    public void dragStarted() {


        //
    }

    @Override
    public void midDrag(float x, float y) {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean endDrag(float x, float y) {
        boolean test = turretCheck(x,y);
        System.out.println("Turet check is "+ test);
        //if (turretCheck(x,y))
        {
           // this.setPosition((int)x,(int)y);

            System.out.println("Right: " + imageView.getDrawable().getBounds().right + "Left: " + imageView.getDrawable().getBounds().left + " Top:" +imageView.getDrawable().getBounds().top + " Bottom: "+imageView.getDrawable().getBounds().bottom);
            this.setPosition((int) (x-((imageView.getDrawable().getBounds().right - imageView.getDrawable().getBounds().left)/2)+90), (int)y-50);
            this.update();
            this.imageView.setVisibility(View.VISIBLE);
            return true;
        }
        //return false;
    }


}
