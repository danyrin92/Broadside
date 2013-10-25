package com.starboardstudios.broadside.gameunits.aircrafts;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.starboardstudios.broadside.gameunits.BaseUnit;
import com.starboardstudios.broadside.gameunits.CombatUnit;
import com.starboardstudios.broadside.gameunits.Model;
import com.starboardstudios.broadside.gameunits.projectile.CannonBall;

import java.util.Random;

public abstract class BaseAircraft extends CombatUnit {

	/** Random value for choosing pathing track */
	Random rand = new Random();
	int random = rand.nextInt(2);
	int moveCount = 0;

	public BaseAircraft(Model model) {
		super(model.context);
		this.model = model;

		imageView.setVisibility(View.INVISIBLE);
		imageView.setAdjustViewBounds(true);
	}

	@Override
	public ImageView getImage() {
		return imageView;
	}

	public void setVelocity(int xSpeed, int ySpeed) {
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
	}

	public void collide(BaseUnit unit) {
		x = 0;
	}

}