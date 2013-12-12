package com.starboardstudios.broadside.gameunits.submarine;

import android.view.View;
import android.widget.LinearLayout;

import com.starboardstudios.broadside.R.drawable;
import com.starboardstudios.broadside.gameunits.Model;
import com.starboardstudios.broadside.gameunits.projectile.Torpedo;

public class EasySubmarine extends BaseSubmarine {

	public EasySubmarine(Model model) {
		super(model);
		plunder = 10;

		/** Unique variables for an EasySubmarine */
		health = 10;
		projectile = new Torpedo(model, 20);
		projectile.creator = this;
		
		fireSpeed = -(float) (model.getScreenX() * .005);

		/** Art asset assigned to EasySubmarine */
		imageView.setImageResource(drawable.easysubmarine);

		/** Scale of the EasyShip type */
		imageView.setLayoutParams(new LinearLayout.LayoutParams((int) (model
				.getScreenX() * .15), (int) (model.getScreenY() * .15)));

		/**
		 * Current onClick listener for testing firing. TODO: Delete and
		 * implement periodic firing
		 */

		imageView.setVisibility(View.VISIBLE);

		/** Starting speed of the submarine */
		xSpeed = -(int) (model.getScreenX() * .003);

		x = (int) (model.getScreenX() + 75);
		y = (int) (model.getScreenY() * .4);

	}

	/**
	 * Features current basic pathing TODO: Implement advanced pathing
	 */
	public void update() {

		xSpeed /= 2;
		ySpeed /= 2;
		lifetime++;
		x = x + xSpeed;
		y = y + ySpeed;

		if (random == 0)
			pathFour();

		model.runOnMain(new Runnable() {
			public void run() {
				imageView.setX(x);
				imageView.setY(y);

				if (lifetime > 350) {
					fire();
					lifetime = 0;
				}

			}

		});

	}

}
