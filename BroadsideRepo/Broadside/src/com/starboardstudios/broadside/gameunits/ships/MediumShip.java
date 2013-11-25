package com.starboardstudios.broadside.gameunits.ships;

import android.view.View;
import android.widget.LinearLayout;

import com.starboardstudios.broadside.R.drawable;
import com.starboardstudios.broadside.gameunits.Model;
import com.starboardstudios.broadside.gameunits.projectile.CannonBall;

public class MediumShip extends BaseShip {

	public MediumShip(Model model) {
		super(model);

		/** Unique variables for an MediumShip */
		health = 50;
		projectile = new CannonBall(model, -1);

		/** Projectile speed */
		fireSpeed = -(float) (model.getScreenX() * .005);

		/** Art asset assigned to MediumShip */
		imageView.setImageResource(drawable.mediumship);

		/** Scale of the MediumShip type */
		imageView.setLayoutParams(new LinearLayout.LayoutParams((int) (model
				.getScreenX() * .15), (int) (model.getScreenY() * .2)));

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

}
