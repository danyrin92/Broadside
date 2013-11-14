package com.starboardstudios.broadside.gameunits;

import com.starboardstudios.broadside.R.drawable;
import com.starboardstudios.broadside.gameunits.projectile.Projectile;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * @author comcc_000, 11/5
This is just a first draft of crew. Classes affected are crew, mainship, model, and playcontroller.
Currently a crew member is spawned on the first level by the play controller and displayed to the screen.
//TODO Have crew influence health. (depends on health algorithm)
//TODO Have crew move. 
//TODO Have crew rotate.
 */

public class Crew extends BaseUnit {
	//properties
	private ImageView imageView;
	private float x, y;
	private float xStation, yStation;
	private float xTarget, yTarget;
	private float moveSpeed, xSpeed, ySpeed;
	boolean repairing;
	private Context context;
	private Model model;
	
	//constructors
	public Crew(Context c, Model model) {
		this.context = c;
		this.model = model;
		//create image
		imageView = new ImageView(context);
		imageView.setAdjustViewBounds(true);
		imageView.setImageResource(drawable.crew_member);
		imageView.setLayoutParams(new LinearLayout.LayoutParams((int) (model
				.getScreenX() * .10), (int) (model.getScreenY() * .10)));
		imageView.setVisibility(View.VISIBLE);
		//initialize other stuff
		x = y = xSpeed = ySpeed = 0;
		moveSpeed = 1;
		repairing = false;
	}
	
	//methods
	public void update() {
		// TODO work on moving crew members
		System.out.println("Crew updating...");
		
		//move
		x = x + xSpeed;
		y = y + ySpeed;
		
		if (repairing) {
			System.out.println("X: " + x + " Y: " + y + " xSpeed: " + xSpeed + " ySpeed: " + ySpeed);
			System.out.println("xTarget: " + xTarget + " yTarget: " + yTarget);
			boolean closeEnough = (Math.sqrt(Math.pow((xTarget-x),2) + Math.pow((yTarget-y),2))<1);
			if (closeEnough || Math.abs(x)>1000) {
				if (xTarget==xStation&&yTarget==yStation) {
					//repair complete
					repairing = false;
					xSpeed = ySpeed = 0;
				} else {
					//TODO repair
					//Return to station
					xSpeed = -xSpeed;
					ySpeed = -ySpeed;
					xTarget = xStation;
					yTarget = yStation;
					closeEnough = false;
				}
			}
		}

		model.runOnMain(new Runnable() {
			public void run() {
				imageView.setX(x);
				imageView.setY(y);
				imageView.setImageResource(drawable.crew_member);
				//System.out.println(" x " + x + " y " + y);
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
		setStations(x,y);
	}

	public void setImageView(ImageView image) {
		imageView = image;
	}
	
	public void repairAt(float xTarget, float yTarget) {
		//calculate x and y speeds
		this.xTarget = xTarget;
		this.yTarget = yTarget;
		float yDifference = yTarget - (this.y );
		float xDifference = xTarget - (this.x );
		double angle = Math.atan(yDifference / xDifference);;
		if (xDifference>0 && yDifference<0) { //x+, y-
			angle*=-1;
		} else if (xDifference<0 && yDifference>0) { //x-, y+
			angle = 180 - angle;
		} else if (xDifference<0 && yDifference<0) { //x-, y-
			angle+=Math.PI;
		}
		ySpeed = (float) Math.sin(angle) * moveSpeed;
		xSpeed = (float) Math.cos(angle) * moveSpeed;
		//repair
		update();
	}
	
	public void setStations(float X, float Y) {
		xStation = X;
		yStation = Y;
	}

	public boolean getRepairing() {
		return repairing;
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
	
}
