package com.starboardstudios.broadside.gameunits.turrets;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.starboardstudios.broadside.gameunits.projectile.Missile;
import com.starboardstudios.broadside.gameunits.projectile.Projectile;
import com.starboardstudios.broadside.R.drawable;
import com.starboardstudios.broadside.gameunits.BaseUnit;
import com.starboardstudios.broadside.gameunits.Model;
import com.starboardstudios.broadside.gameunits.projectile.CannonBall;

public class MainCannon extends Turret {
	public ImageView imageView = new ImageView(context);
	int cooldown, currentCooldown;
	float angle; // 90 to -90
	MainCannon me;

	public MainCannon(Model model, float x, float y) {
		super(model, x, y);
		me = this;
		this.x = x;
		this.y = y;
		this.cooldown = 10;
		this.currentCooldown = 0;
		this.projectile = new Missile(model);
		fireSpeed = 5;
		imageView.setImageResource(drawable.main_cannon); // Set to image
		imageView.setAdjustViewBounds(true);
		imageView.setLayoutParams(new LinearLayout.LayoutParams((int)(model.getScreenX()*.15), (int)(model.getScreenY()*.15))); // Set
																			// size

		// TODO: move over to draggable
		/**
		 * imageView.setOnTouchListener(new View.OnTouchListener() {
		 * 
		 * @Override public boolean onTouch(View view, MotionEvent motionEvent)
		 *           {
		 *           imageView.getParent().requestDisallowInterceptTouchEvent(true
		 *           );
		 * 
		 *           if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
		 *           System.out.println(view.toString());
		 * 
		 *           ClipData data = ClipData.newPlainText("", "");
		 *           View.DragShadowBuilder shadowBuilder = new
		 *           View.DragShadowBuilder(imageView); view.startDrag(data,
		 *           shadowBuilder, me , 0); view.setVisibility(View.INVISIBLE);
		 * 
		 * 
		 *           return true; } return false; } });
		 */
		System.out.println("Turret Created");
	}

	public void fire(float xTarget, float yTarget) {
		if (currentCooldown == 0){ 
		float yDifference = (yTarget - this.y);//rotation of the cannon
		float xDifference = (xTarget - this.x);
		double cannonAngle = Math.atan(yDifference / xDifference);
		imageView.setRotation((float)(cannonAngle*180/Math.PI));
		//offset cannonball to tip of barrel where (model.getScreenX()*.15) is the length of the cannon
		float xOffset = (float)(this.x + (model.getScreenX()*.15)*Math.cos(cannonAngle));
		float yOffset = (float)(this.y + (model.getScreenX()*.15)*Math.sin(cannonAngle));
		float angle = (float) Math.atan((yTarget - yOffset)/ (xTarget - xOffset));
		float ySpeed = (float) Math.sin(angle) * fireSpeed;
		float xSpeed = (float) Math.cos(angle) * fireSpeed;
        Projectile temp = projectile.create(model, projectile.getDefaultDamage(), this.x, this.y, fireSpeed, angle);
        temp.creator = this;
		model.addUnit(temp);
		imageView.setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
		System.out.println("angle " + angle +" thisx" +this.x+" thisy "+ this.y+ " xTarget  " + xTarget+ " yTarget "+ yTarget+"xDiff"+xDifference+"ydiff"+yDifference);
		currentCooldown=cooldown;
		}
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
		// System.out.println("Updating Main Cannon");
		model.runOnMain(new Runnable() {
			@Override
			public void run() {
				imageView.setX(x);
				imageView.setY(y);
				imageView.setImageResource(drawable.main_cannon);
				if (currentCooldown > 0)
					currentCooldown--;
				if (currentCooldown ==0)
					imageView.setColorFilter(null);
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
	public void endDrag(float x, float y) {

		int centerX = (getImage().getLeft() + getImage().getRight()) / 2;
		int centerY = (getImage().getTop() + getImage().getBottom()) / 2;
		setPosition((int) (x - centerX), (int) (y - centerY));
		getImage().setVisibility(View.VISIBLE);

	}

	public void setImageView(ImageView image) {
		imageView = image;
	}

}
