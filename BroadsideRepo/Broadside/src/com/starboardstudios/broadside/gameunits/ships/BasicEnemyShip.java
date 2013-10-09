package com.starboardstudios.broadside.gameunits.ships;

import java.util.ArrayList;

import com.starboardstudios.broadside.R.drawable;
import com.starboardstudios.broadside.gameunits.Crew;
import com.starboardstudios.broadside.gameunits.Model;
import com.starboardstudios.broadside.gameunits.turrets.MainCannon;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class BasicEnemyShip extends Ship {

	public ImageView imageView = new ImageView(context); // Image for ship
	private Model model;
	private MainCannon mainCannon;
	private int health = 10;

	public BasicEnemyShip(Model model) {
		super(model.context);
		this.model = model;

		// Can't make image another file because it's not auto-generating the
		// address in R.java. What gives?
		// Using test for now
		imageView.setImageResource(drawable.testship);
		imageView.setAdjustViewBounds(true);

		imageView.setLayoutParams(new LinearLayout.LayoutParams((int) (model
				.getScreenX() * .15), (int) (model.getScreenY() * .15)));
		x = ((int) (model.getScreenX()) +75);
		y = ((int) (model.getScreenY() * .4));

		// Below will be updated
		xSpeed = -(int) (model.getScreenX() * .003);
	}

	@Override
	public void update() {
		x = x + xSpeed;
		y = y + ySpeed;

		model.runOnMain(new Runnable() {
			public void run() {
				imageView.setX(x);
				imageView.setY(y);
				imageView.setImageResource(drawable.testship);
			}

		});

	}

	@Override
	public ImageView getImage() {
		return imageView;
	}

	public void setVelocity(int xSpeed, int ySpeed) {
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		imageView.setX(x);
		imageView.setY(y);

	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

}
