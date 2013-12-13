package com.starboardstudios.broadside.gameunits.ships;

import android.view.View;
import android.widget.LinearLayout;

import com.starboardstudios.broadside.R.drawable;
import com.starboardstudios.broadside.gameunits.Model;
import com.starboardstudios.broadside.gameunits.projectile.CannonBall;

public class MediumShip extends BaseShip {

	public MediumShip(Model model) {
		super(model);
		plunder = 20;

		/** Unique variables for an MediumShip */
		health = 50;
		projectile = new CannonBall(model, -1);
		projectile.creator = this;

		/** Projectile speed */
		fireSpeed = -(float) (model.getScreenX() * .004);

		/** Art asset assigned to MediumShip */
		imageView.setImageResource(drawable.mediumship);

		/** Scale of the MediumShip type */
		imageView.setLayoutParams(new LinearLayout.LayoutParams((int) (model
				.getScreenX() * .15), (int) (model.getScreenY() * .2)));

		imageView.setVisibility(View.VISIBLE);

		
		x = (int) (model.getScreenX() + 75);
		y = (int) (model.getScreenY() * .4);
	}

	/**
	 * Features current basic pathing TODO: Implement advanced pathing
	 */
	public void update() {
		checkShipCollisions();
		x = x + xSpeed;
		y = y + ySpeed;
		lifetime++;
		if (random == 1)
			pathOne();

		if (random == 0)
			pathTwo();

		if (random == 2) {
			random = rand.nextInt(2);
		}

		model.runOnMain(new Runnable() {
			public void run() {
				imageView.setX(x);
				imageView.setY(y);

				if (lifetime > 150) {
					fire();
					lifetime = 0;
				}

			}

		});

	}

}
