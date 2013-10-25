package com.starboardstudios.broadside.gameunits.aircrafts;

import android.view.View;
import android.widget.LinearLayout;

import com.starboardstudios.broadside.gameunits.projectile.Missile;
import com.starboardstudios.broadside.R.drawable;
import com.starboardstudios.broadside.gameunits.Model;

public class EasyAircraft extends BaseAircraft {

	public EasyAircraft(Model model) {
		super(model);

		/** Unique variables for an EasyAircraft */
		health = 10;

		/** Projectile speed */
		xFireSpeed =  (int) (model.getScreenX() * .005);

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
				fire();

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

		if (random == 1)
			pathOne();

		if (random == 0)
			pathTwo();

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
		model.addUnit(new Missile(model, 20, xFireSpeed, yFireSpeed));
	}

}
