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
		health = 10;

		/** Projectile speed */
		xFireSpeed = (int) (model.getScreenX() * .03);

		/** Art asset assigned to MediumShip */
		imageView.setImageResource(drawable.mediumship);

		/** Scale of the MediumShip type */
		imageView.setLayoutParams(new LinearLayout.LayoutParams((int) (model
				.getScreenX() * .15), (int) (model.getScreenY() * .2)));

		/**
		 * Current onClick listener for testing firing. TODO: Delete and
		 * implement periodic firing
		 */
		imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				System.out.print("I shot");
				testFire();

			}
		});

		imageView.setVisibility(View.VISIBLE);

		/** Starting speed of the ship */
		xSpeed = -(int) (model.getScreenX() * .003);
	}

	/**
	 * Features current basic pathing TODO: Implement advanced pathing
	 */
	public void update() {
		x = x + xSpeed;
		y = y + ySpeed;
		
		if (x < ((int) (model.getScreenX()) * .5)) {
			xSpeed = 0;
			if (random == 1)
				ySpeed = (int) (model.getScreenX() * .003);
			if (random == 0)
				ySpeed = -(int) (model.getScreenX() * .003);
		}
		model.runOnMain(new Runnable() {
			public void run() {
				imageView.setX(x);
				imageView.setY(y);
			}

		});

	}

	void testFire() {
		model.addUnit(new CannonBall(model, 20, x, y, z, xFireSpeed,
				yFireSpeed, zFireSpeed));
	}

}
