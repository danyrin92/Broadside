package com.starboardstudios.broadside.gameunits.ships;

import android.view.View;
import android.widget.LinearLayout;

import com.starboardstudios.broadside.R.drawable;
import com.starboardstudios.broadside.gameunits.Model;
import com.starboardstudios.broadside.gameunits.projectile.CannonBall;

public class BasicShip extends BaseShip {

	public BasicShip(Model model) {
		super(model);

		/** Health for a BasicShip */
		health = 10;

		/** Art asset assigned to BasicShip */
		imageView.setImageResource(drawable.testship);

		/** Scale of the BasicShip type */
		imageView.setLayoutParams(new LinearLayout.LayoutParams((int) (model
				.getScreenX() * .15), (int) (model.getScreenY() * .15)));

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

		/** Starting position of ship */
		x = ((int) (model.getScreenX()) + 75);
		y = ((int) (model.getScreenY() * .4));

		imageView.setVisibility(View.VISIBLE); // BaseShip is set to invisible

		/** Starting speed of the ship */
		xSpeed = -(int) (model.getScreenX() * .003);
	}

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

	/** Testing firing of BasicShip */
	public static int xFireSpeed = -2;
	public static int yFireSpeed = 0;
	public static int zFireSpeed;
	public int z = 0;

	void testFire() {
		model.addUnit(new CannonBall(model, 20, x, y, z, xFireSpeed,
				yFireSpeed, zFireSpeed));
	}

}
