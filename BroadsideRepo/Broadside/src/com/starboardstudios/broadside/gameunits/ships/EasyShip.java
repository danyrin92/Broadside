package com.starboardstudios.broadside.gameunits.ships;

import android.view.View;
import android.widget.LinearLayout;

import com.starboardstudios.broadside.R.drawable;
import com.starboardstudios.broadside.gameunits.Model;
import com.starboardstudios.broadside.gameunits.projectile.CannonBall;

public class EasyShip extends BaseShip {
	private float fireSpeed;

	public EasyShip(Model model) {
		super(model);

		/** Unique variables for an EasyShip */
		health = 10;

		/** Projectile speed */
		fireSpeed = -(float) (model.getScreenX() * .005);

		/** Art asset assigned to EasyShip */
		imageView.setImageResource(drawable.easyship);

		/** Scale of the EasyShip type */
		imageView.setLayoutParams(new LinearLayout.LayoutParams((int) (model
				.getScreenX() * .15), (int) (model.getScreenY() * .15)));

		/**
		 * Current onClick listener for testing firing. TODO: Delete and
		 * implement periodic firing
		 */

		imageView.setVisibility(View.VISIBLE);

		/** Starting speed of the ship */
		xSpeed = -(int) (model.getScreenX() * .003);

		x = (int) (model.getScreenX() + 75);
		y = (int) (model.getScreenY() * .4);

	}

	/**
	 * Features current basic pathing TODO: Implement advanced pathing
	 */
	public void update() {
		int speed = (int) (Math.abs(xSpeed) + Math.abs(ySpeed));
		//sSystem.out.println("Speed: " + speed);
		
		x = x + xSpeed;
		y = y + ySpeed;

		if (random == 1)
			pathOne();

		if (random == 0)
			pathTwo();

		if (random == 2)
			pathThree();

		moveCount += Math.abs(xSpeed);
		moveCount += Math.abs(ySpeed);

		model.runOnMain(new Runnable() {
			public void run() {
				imageView.setX(x);
				imageView.setY(y);

				if (moveCount >= 250) {
					fire();
					moveCount = 0;
				}
			}

		});
		
		
		double num1 = Math.pow(xSpeed, 2);
		double num2 = Math.pow(ySpeed, 2);
		double toSqrt = num2+num1;
		double currspeed = Math.sqrt(toSqrt);
		//System.out.println("XSpeed is: " + xSpeed);
		//System.out.println("Yspeed is: " + ySpeed);
		//System.out.println("Current speed is..." + currspeed);

	}

	void fire() {
        CannonBall temp = new CannonBall(model, 20, x, y, fireSpeed, 0);
		temp.creator = this;
		model.addUnit(temp);
	}

}
