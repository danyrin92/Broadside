package com.starboardstudios.broadside.gameunits.ships;

import android.widget.ImageView;
import android.widget.LinearLayout;
import com.starboardstudios.broadside.R.drawable;
import com.starboardstudios.broadside.gameunits.BaseUnit;
import com.starboardstudios.broadside.gameunits.CombatUnit;
import com.starboardstudios.broadside.gameunits.Crew;
import com.starboardstudios.broadside.gameunits.Model;
import com.starboardstudios.broadside.gameunits.projectile.Projectile;
import com.starboardstudios.broadside.gameunits.turrets.MainCannon;
import com.starboardstudios.broadside.gameunits.turrets.Turret;

import java.util.ArrayList;

public class MainShip extends CombatUnit {

	ImageView imageView; // Image for ship
	private Model model;
	private ArrayList<Section> sections;
	private ArrayList<Crew> crew;
	private MainCannon mainCannon;
	private int waterLevel = 0;
	private boolean inPosition = false;
	private ArrayList<Turret> turrets;

	public MainShip(Model model) {
		super(model.context);
		this.model = model;
		imageView = new ImageView(model.context);

		/** PNG to be used as image */
		imageView.setImageResource(drawable.mainship);
		imageView.setAdjustViewBounds(true);

		/** Determines rendering size of object */
		imageView.setLayoutParams(new LinearLayout.LayoutParams((int) (model
				.getScreenX() * .75), (int) (model.getScreenY() * 1.2)));

		/** Starting position. As for now on the left. */
		x = -((int) (model.getScreenX() * .225));
		y = ((int) (model.getScreenY() * .7));

		health = 100;
	}

	protected void Damage(Projectile p) {
		health = health - p.getDamage();
	}

	public void setVelocity(int xSpeed, int ySpeed) {
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		imageView.setX(x);
		imageView.setY(y);

	}

	@Override
	public void update() {

		x = x + xSpeed;
		y = y + ySpeed;

		if (y > -(model.getScreenY() * .2))
			ySpeed = -(int) (model.getScreenY() * .003);
		else
			ySpeed = 0;

		model.runOnMain(new Runnable() {
			public void run() {
				imageView.setX(x);
				imageView.setY(y);
				imageView.setImageResource(drawable.mainship);
			}

		});

	}

	@Override
	public ImageView getImage() {
		return imageView;
	}

	@Override
	public void collide(BaseUnit collidedWith) {

	}

	@Override
	public void setPosition(int x, int y) {

	}

	/**
	 * 
	 * @param x
	 *            x position of where to fire
	 * @param y
	 *            y position of where to fire
	 * 
	 *            Can then call fire with returned degreeAngle
	 */
	void FireMain(int x, int y) {
		int deltaX = x - this.x;
		int deltaY = this.y - y;
		double degreeAngle = Math.atan((deltaY / deltaX)) * 180 / Math.PI;
	}

	ArrayList<Turret> getTurrets() {
		return turrets;
	}

}
