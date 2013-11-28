package com.starboardstudios.broadside.gameunits.turrets;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.starboardstudios.broadside.gameunits.projectile.Missile;
import com.starboardstudios.broadside.gameunits.projectile.Projectile;
import com.starboardstudios.broadside.R.drawable;
import com.starboardstudios.broadside.gameunits.BaseUnit;
import com.starboardstudios.broadside.gameunits.Model;
import com.starboardstudios.broadside.gameunits.projectile.CannonBall;

//Turret 0
public class MainCannon extends Turret {
	MainCannon me;

	public MainCannon(Model model, float x, float y) {
		super(model, x, y);
		me = this;
		//manage numbers
		this.x = x;
		this.y = y;
		this.cooldown = 60;
		this.currentCooldown = 0;
		size = (float) .15;
		this.projectile = new CannonBall(model, 40);
		fireSpeed = 5;
		//handle image
		imageView.setImageResource(drawable.main_cannon); // Set to image
		imageView.setAdjustViewBounds(true);
		imageView.setScaleType(ScaleType.FIT_CENTER);
		imageView.setLayoutParams(new LinearLayout.LayoutParams((int)(model.getScreenX()*size), (int)(model.getScreenY()*size))); // Set																		// size
		System.out.println("Turret Created");
	}

	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void update() {
		// System.out.println("Updating MainCannon");
		model.runOnMain(new Runnable() {
			@Override
			public void run() {
				imageView.setX(x);
				imageView.setY(y);
				if (currentCooldown > 0) {
					currentCooldown--;
				} else {
					imageView.setColorFilter(null);
				}
			}
		});	
	}

	@Override
	public ImageView getImage() {
		return imageView;
	}

	@Override
	public void collide(BaseUnit collidedWith) {
		// TODO: Turrent available drop.
	}

	@Override
	public void dragStarted() {

	}

	@Override
	public void midDrag(float x, float y) {

	}

	@Override
	public boolean endDrag(float x, float y) {
		int centerX = (getImage().getLeft() + getImage().getRight()) / 2;
		int centerY = (getImage().getTop() + getImage().getBottom()) / 2;
		setPosition((int) (x - centerX), (int) (y - centerY));
		getImage().setVisibility(View.VISIBLE);
		return true;
	}

	public void setImageView(ImageView image) {
		imageView = image;
	}

}
