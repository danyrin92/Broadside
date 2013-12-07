package com.starboardstudios.broadside.gameunits.turrets;

import java.util.ArrayList;

import android.content.ClipData;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.starboardstudios.broadside.R.drawable;
import com.starboardstudios.broadside.gameunits.BaseUnit;
import com.starboardstudios.broadside.gameunits.CombatUnit;
import com.starboardstudios.broadside.gameunits.Model;
import com.starboardstudios.broadside.gameunits.projectile.CannonBall;
import com.starboardstudios.broadside.gameunits.projectile.Missile;
import com.starboardstudios.broadside.gameunits.projectile.Projectile;
import com.starboardstudios.broadside.gameunits.projectile.Torpedo;

//Turret 5
public class MissileLauncher extends Turret {
	MissileLauncher me;

	public MissileLauncher(Model model) {
		super(model);
		turretNum = 5;
		me = this;
		this.projectile = new Missile(model, 20);
		/* ARBITRARY VALUES */
		this.fireSpeed = 5;
		this.cooldown = currentCooldown = 180;
		spendSetCost(100);	
		size = (float) .125;
		
		/* Image */
		imageView.setImageResource(drawable.turret5); // Set to image
		imageView.setAdjustViewBounds(true);
		imageView.setLayoutParams(new LinearLayout.LayoutParams((int) (model
				.getScreenX() * size), (int) (model.getScreenY() * size))); // Set size
			
		/* Dragging */
		imageView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {
				imageView.getParent().requestDisallowInterceptTouchEvent(true);

				if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
					System.out.println(view.toString());

					ClipData data = ClipData.newPlainText("", "");
					View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
							imageView);
					view.startDrag(data, shadowBuilder, me, 0);
					view.setVisibility(View.INVISIBLE);

					return true;
				}
				return false;
			}
		});
		System.out.println("MissileLauncher is Created");
	}
	
	public MissileLauncher(Model model, int x, int y) {
		super(model);
		turretNum = 5;
		me = this;
		this.projectile = new Missile(model, 20);
		/* ARBITRARY VALUES */
		this.fireSpeed = 5;
		this.cooldown = currentCooldown = 180;
		setCost(100);	
		size = (float) .125;
		
		this.x = x;
		this.y = y;
		
		/* Image */
		imageView.setImageResource(drawable.turret5); // Set to image
		imageView.setAdjustViewBounds(true);
		imageView.setLayoutParams(new LinearLayout.LayoutParams((int) (model
				.getScreenX() * size), (int) (model.getScreenY() * size))); // Set size
	}

	public void update() {
		model.runOnMain(new Runnable() {
			@Override
			public void run() {
				imageView.setX(x);
				imageView.setY(y);
				if (currentCooldown > 0) {
					currentCooldown--;
				} else {
					imageView.setColorFilter(null);
					fire();
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
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public void dragStarted() {
		//
	}

	@Override
	public void midDrag(float x, float y) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean endDrag(float x, float y) {
		if (turretCheck(x,y)) {
			this.setPosition((int) x, (int) y);
			this.update();
			this.imageView.setVisibility(View.VISIBLE);
			return true;
		}
		return false;
	}

	@Override
	public void setImageView(ImageView image) {
		imageView = image;
	}

	public void setPosition(float x, float y) {
		this.x = (int) x;
		this.y = (int) y;
	}

}
