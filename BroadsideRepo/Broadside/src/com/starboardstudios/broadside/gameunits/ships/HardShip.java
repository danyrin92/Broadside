package com.starboardstudios.broadside.gameunits.ships;

import android.view.View;
import android.widget.LinearLayout;

import com.starboardstudios.broadside.R.drawable;
import com.starboardstudios.broadside.gameunits.Model;
import com.starboardstudios.broadside.gameunits.projectile.CannonBall;

public class HardShip extends BaseShip {
	private float fireSpeed;

	public HardShip(Model model) {
		super(model);

		/** Unique variables for a HardShip */
		health = 100;

		/** Projectile speed */
		fireSpeed = -(float) (model.getScreenX() * .005);

		/** Art asset assigned to HardShip */
		imageView.setImageResource(drawable.hardship);

		/** Scale of the HardShip type */
		imageView.setLayoutParams(new LinearLayout.LayoutParams((int) (model
				.getScreenX() * .15), (int) (model.getScreenY() * .26)));

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

	}

	void fire() {
		model.addUnit(new CannonBall(model, 20, x, y, fireSpeed, 0));
	}

}
