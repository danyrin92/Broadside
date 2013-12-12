package com.starboardstudios.broadside.gameunits.projectile;

import android.widget.LinearLayout;
import com.starboardstudios.broadside.gameunits.BaseUnit;
import com.starboardstudios.broadside.gameunits.CombatUnit;
import com.starboardstudios.broadside.R.drawable;
import com.starboardstudios.broadside.gameunits.Model;
import com.starboardstudios.broadside.gameunits.ships.MainShip;
import com.starboardstudios.broadside.gameunits.turrets.MineLauncher;
import com.starboardstudios.broadside.gameunits.turrets.Turret;

public class Mine extends Projectile {
	private int scaleFactor = (int) (model.getScreenY() * .03);
	private int mineNumber;
	private boolean deployed;
	private MineLauncher mineLauncher;

	/*Used to create cannonball that stores data for other constructor*/
	public Mine(Model model, int damage, int mineNumber) {
		super(model);
		this.damage = defaultDamage = 20;
		if (damage != -1) {
			this.damage = damage;
		} 
		this.mineNumber = mineNumber;
	}

	public int getMineNumber() {
		return mineNumber;
	}

	public void setMineNumber(int mineNumber) {
		this.mineNumber = mineNumber;
	}

	/* Used by turrets and units */
	public Mine(Projectile projectile, float x, float y,
			float fireSpeed, float angle) {
		super(projectile, x, y, fireSpeed, angle);
		imageView.setImageResource(drawable.viking_ship);
		imageView.setAdjustViewBounds(true);
		imageView.setLayoutParams(new LinearLayout.LayoutParams((int) (model
				.getScreenX() * .15), scaleFactor));
		System.out.println("mine created");
	}

	@Override
	public Projectile create(Projectile projectile, float x, float y,
			float fireSpeed, float angle) {
		return new Mine(projectile, x, y, fireSpeed, angle);
	}
	
	public void update() {
		if (deployed) {
			//sit there
		} else {
			//move
			x = x + xSpeed;
			y = y + ySpeed;
			//System.out.println(" xTarget: " + xTarget + " yTarget: " + yTarget);
			deployed = Math.sqrt(Math.pow((xTarget - x), 2) + Math.pow((yTarget - y), 2))< 2*speed;
		}

		model.runOnMain(new Runnable() {
			public void run() {
                    imageView.setX(x);
				    imageView.setY(y);
			}
		});
	}
	
	public void setMineLauncher(MineLauncher mineLauncher) {
		this.mineLauncher = mineLauncher;
	}

	public MineLauncher getMineLauncher() {
		return mineLauncher;
	}
	
	public int getScaleFactor() {
		return scaleFactor;
	}

}
