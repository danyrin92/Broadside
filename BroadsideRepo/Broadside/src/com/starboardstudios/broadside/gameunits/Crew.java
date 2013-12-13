package com.starboardstudios.broadside.gameunits;

import java.util.ArrayList;

import com.starboardstudios.broadside.R.drawable;
import com.starboardstudios.broadside.gameunits.projectile.Projectile;
import com.starboardstudios.broadside.gameunits.ships.MainShip;
import com.starboardstudios.broadside.gameunits.ships.Section;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * @author comcc_000, 11/5 This is just a first draft of crew. Classes affected
 *         are crew, mainship, model, and playcontroller. Currently a crew
 *         member is spawned on the first level by the play controller and
 *         displayed to the screen. //TODO Have crew influence health. (depends
 *         on health algorithm) //TODO Have crew move. //TODO Have crew rotate.
 */

public class Crew extends BaseUnit {
	// properties
	private ImageView imageView;
	private float x, y, xStation, yStation, xTarget, yTarget, moveSpeed,
			xSpeed, ySpeed, repairRate;
	private double angle;
	boolean repairTrip, repairing, busy, patrolling;
	// patrolling does not mean busy...
	private Context context;
	private Model model;
	private Section currentSect, firstSection, secondSection, thirdSection;
	private int defaultSectNum;

	// constructors
	public Crew(Context c, Model model) {
		this.context = c;
		this.model = model;
		// create image
		imageView = new ImageView(context);
		imageView.setAdjustViewBounds(true);
		imageView.setImageResource(drawable.crew_member);
		imageView.setLayoutParams(new LinearLayout.LayoutParams((int) (model
				.getScreenX() * .10), (int) (model.getScreenY() * .10)));
		imageView.setVisibility(View.VISIBLE);
		// initialize other stuff
		x = y = xSpeed = ySpeed = 0;
		repairTrip = repairing = busy = false;
		moveSpeed = (float) 1;
		repairRate = (float) .1; //.25 good for testing
		patrolling = true; // still debugging
		setDefaultSect();
		setSectionOrder();
	}

	// methods
	public void update() {
		//Actual movement/repair stuff (micro)
		if (!repairing) {
			// move
			x = x + xSpeed;
			y = y + ySpeed;
			// TODO play move animation
		} else if (repairing) {
			if (currentSect.isDamaged()) {
				// TODO play repair animation
				currentSect.repair(repairRate);
			} else { // no stuff to fix
				repairing = busy = false;
				// sect = null;
			}
		}
		//Behavior logic (macro)
		if (repairTrip) {
			//printMovementInfo();
			boolean closeEnough = Math.sqrt(Math.pow((xTarget - x), 2)
					+ Math.pow((yTarget - y), 2)) < 10 * moveSpeed;
			if (closeEnough) {
				if (xTarget == xStation && yTarget == yStation) { //At station
					// repair complete, so reset
					repairTrip = busy = false;
					angle = xSpeed = ySpeed = 0;
				} else {
					// Stop and commence repair
					repairing = busy = true;
					// Return to station once done
					returnToStation();
				}
			}
		} else if (patrolling) {
			//repair in one of 3 sequences (e.g. bow->stern) then returnToStation
			Fire fire = null;
			if (firstSection.isDamaged()) { 
				fire = firstSection.getAFire();
			} else if (secondSection.isDamaged()) { 
				fire = secondSection.getAFire();
			} else if (thirdSection.isDamaged()) { 
				fire = thirdSection.getAFire();
			} else { // return to station
				repairing = false;
				returnToStation();
			}
			fightFire(fire);
		}
		//standard update imageview
		model.runOnMain(new Runnable() {
			public void run() {
				imageView.setX(x);
				imageView.setY(y);
				imageView.setRotation((float) angle);
			}
		});
	}

	@Override
	public ImageView getImage() {
		return imageView;
	}

	@Override
	public void collide(BaseUnit collidedWith) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
		setStations(x, y);
	}

	public void setImageView(ImageView image) {
		imageView = image;
	}

	public void repairAt(float xTarget, float yTarget) {
		goTo(xTarget, yTarget);
		// determine section
		this.currentSect = model.getMainShip().determineSection(yTarget);
		// repair
		repairTrip = busy = true;
		// System.out.println("RepairAt X: " + xTarget + " Y: " + yTarget);
	}

	public void goTo(float xTarget, float yTarget) {
		// calculate x and y speeds
		this.xTarget = xTarget;
		this.yTarget = yTarget;
		float yDifference = yTarget - (this.y);
		float xDifference = xTarget - (this.x);
		angle = Math.atan(yDifference / xDifference);
		;
		if (xDifference > 0 && yDifference > 0) { // x+, y+
			//
		} else if (xDifference < 0 && yDifference > 0) { // x-, y+
			angle += Math.PI;
		} else if (xDifference > 0 && yDifference < 0) { // x+, y-

		} else { // x-, y-
			angle += Math.PI;
		}
		ySpeed = (float) Math.sin(angle) * moveSpeed;
		xSpeed = (float) Math.cos(angle) * moveSpeed;
		// convert angle from radians to degrees for rotations
		angle = (angle * 180) / (Math.PI);
		angle+=90; //corrects visual offset
	}

	public void setStations(float X, float Y) {
		xStation = X;
		yStation = Y;
	}

	public boolean getRepairTrip() {
		return repairTrip;
	}

	public float getXTarget() {
		return xTarget;
	}

	public float getYTarget() {
		return yTarget;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public boolean getBusy() {
		return busy;
	}

	public void setSection(Section sect) {
		this.currentSect = sect;
	}

	public void fightFire(Fire fire) {
		if (fire != null) {
			// System.out.println("fightFire at X: " + fire.getX() + " Y: " +
			// fire.getY());
			repairAt(fire.getX(), fire.getY());
		}
	}

	public void patrol() {
		patrolling = true;
	}

	public void returnToStation() {
		xTarget = xStation;
		yTarget = yStation;
		boolean closeEnough = Math.sqrt(Math.pow((xTarget - x), 2)
				+ Math.pow((yTarget - y), 2)) < 10 * moveSpeed;
		if (!closeEnough) {
			goTo(xTarget, yTarget);
			repairTrip = true;
		}
	}

	public void setDefaultSect() {
		MainShip ms = model.getMainShip(); // 0 -> Bow
		int sectNum = ms.getNumCrew(); // 1 -> Midship
		defaultSectNum = sectNum % 3; // 2 -> Stern
	}
	
	//determine starting section and path of repair patrol
	public void setSectionOrder() {
		MainShip ms = model.getMainShip();
		Section Bow = ms.getBow();
		Section Midship = ms.getMidship();
		Section Stern = ms.getStern();
		switch (defaultSectNum) {
		case 0:
			firstSection = Bow;
			secondSection = Midship;
			thirdSection = Stern;
			break;
		case 1:
			firstSection = Midship;
			secondSection = Stern;
			thirdSection = Bow;
			break;
		case 2:
			firstSection = Stern;
			secondSection = Bow;
			thirdSection = Midship;
			break;
		default:
			System.out.println("Patrol error, defaultSectNum: "
					+ defaultSectNum);
			firstSection = Bow;
			secondSection = Midship;
			thirdSection = Stern;
		}
	}

	public void printMovementInfo() {
		System.out.println("X: " + x + " Y: " + y + " xSpeed: " + xSpeed
				+ " ySpeed: " + ySpeed);
		System.out.println("xTarget: " + xTarget + " yTarget: " + yTarget
				+ " angle: " + angle);
	}

}
