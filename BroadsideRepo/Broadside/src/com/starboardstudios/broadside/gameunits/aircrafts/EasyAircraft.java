package com.starboardstudios.broadside.gameunits.aircrafts;

import android.view.View;
import android.widget.LinearLayout;

import com.starboardstudios.broadside.R.drawable;
import com.starboardstudios.broadside.gameunits.Model;
import com.starboardstudios.broadside.gameunits.projectile.CannonBall;
import com.starboardstudios.broadside.gameunits.projectile.Missile;

public class EasyAircraft extends BaseAircraft {

	public EasyAircraft(Model model) {
		super(model);

		/** Unique variables for an EasyAircraft */
		health = 10;

		/** Projectile speed */
		xFireSpeed = -(int) (model.getScreenX() * .005);

		/** Art asset assigned to EasyAircraft */
		imageView.setImageResource(drawable.easyaircraft);

		/** Scale of the EasyAircraft type */
		imageView.setLayoutParams(new LinearLayout.LayoutParams((int) (model
				.getScreenX() * .15), (int) (model.getScreenY() * .15)));

		/**
		 * Current onClick listener for testing firing. TODO: Delete and
		 * implement periodic firing
		 */
		imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				testFire();

			}
		});

		imageView.setVisibility(View.VISIBLE);

		/** Starting speed of the submarine */
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
		model.addUnit(new Missile(model, 20, x, y, z, xFireSpeed,
				yFireSpeed, zFireSpeed));
	}

}
