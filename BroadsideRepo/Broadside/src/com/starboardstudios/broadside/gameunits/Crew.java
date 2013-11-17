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
	private float x, y;
	private float xStation, yStation;
	private float xTarget, yTarget;
	private float moveSpeed, xSpeed, ySpeed;
	private float repairRate;
	private double angle;
	boolean repairTrip, repairing, busy, patrolling; // patrolling doesn't mean
														// busy...
	private Context context;
	private Model model;
	private Section sect;

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
		/** vital stats */
		moveSpeed = (float) 1;
		repairRate = (float) .5;
		/**			*/
		repairTrip = repairing = busy = false;
		patrolling = false; // still debugging
	}

	// methods
	public void update() {
		// System.out.println("Crew updating...");

		if (!repairing) {
			// move
			x = x + xSpeed;
			y = y + ySpeed;
			// TODO play move animation
		} else if (repairing) {
			if (sect.getHealth() < sect.getMaxHealth()) { // if damage left to
															// fix
				// TODO play repair animation
				// increase health
				sect.repair(repairRate); // change all to floats and ints if
											// weird stuff happens...
			} else {
				repairing = busy = false;
				// sect = null;
			}
		}

		if (repairTrip) {
			// System.out.println("X: " + x + " Y: " + y + " xSpeed: " + xSpeed
			// + " ySpeed: " + ySpeed);
			// System.out.println("xTarget: " + xTarget + " yTarget: " + yTarget
			// + " angle: " + angle);
			boolean closeEnough = Math.sqrt(Math.pow((xTarget - x), 2)
					+ Math.pow((yTarget - y), 2)) < 2 * moveSpeed;
			if (closeEnough) {
				if (xTarget == xStation && yTarget == yStation) {
					// repair complete
					repairTrip = busy = false;
					angle = xSpeed = ySpeed = 0;
				} else {
					// Stop and commence repair
					repairing = busy = true;
					// Return to station
					xTarget = xStation;
					yTarget = yStation;
					goTo(xTarget,yTarget);
				}
			}
		} else if (patrolling) {
			// repair bow->stern in sequence then revert to station in cycle
			MainShip ms = model.getMainShip();
			Section bow = ms.getBow();
			Section midShip = ms.getMidship();
			Section stern = ms.getStern();
			Fire fire = null;
			if (bow.isDamaged()) {
				fire = bow.getAFire();
			} else if (midShip.isDamaged()) {
				fire = midShip.getAFire();
			} else if (stern.isDamaged()) {
				fire = stern.getAFire();
			} else { // return to station
				xTarget = xStation;
				yTarget = yStation;
				repairTrip = true;
			}
			fightFire(fire);
		}

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
		goTo(xTarget,yTarget);
		// determine section
		this.sect = model.getMainShip().determineSection(yTarget);
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
		if (xDifference > 0 && yDifference > 0) { // x+,y+
			angle = 180 - angle;
		} else if (xDifference < 0 && yDifference > 0) { // x-,y+
			angle = 360 - angle;
		} else if (xDifference > 0 && yDifference < 0) { // x+, y-
			angle = 360 - angle;
		} else { // x-, y-
			angle = 180 - angle;
		}
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
		this.sect = sect;
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

}
