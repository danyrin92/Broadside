package com.starboardstudios.broadside.gameunits.turrets;

import android.content.Context;
import android.widget.ImageView;

import com.starboardstudios.broadside.gameunits.BaseUnit;
import com.starboardstudios.broadside.gameunits.projectile.Projectile;
import com.starboardstudios.broadside.gameunits.Model;
import com.starboardstudios.broadside.interfaces.Draggable;

public abstract class Turret extends BaseUnit implements Draggable {
	protected Model model;
	protected float x, y, z;
	protected int fireSpeed;
	protected Context context;
	protected Projectile projectile;
	boolean canAffect;
	double range;
	protected int cost;

	public Turret(Model model, Projectile projectile) {
		this.context = model.context;
		this.model = model;
		this.projectile = projectile;
	}
	
	public Turret(Model model, Projectile projectile, int turretNum) {
		this.context = model.context;
		this.model = model;
		this.projectile = projectile;
		this.cost = model.getTurretCostAt(turretNum);
		model.spendBooty(cost);
	}

	public Turret(Model model, Projectile projectile, int x, int y, int z) {
		this.context = model.context;
		this.model = model;
		this.projectile = projectile;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Turret(Model model, Projectile projectile, int x, int y, int z, int fireSpeed) {
		this.context = model.context;
		this.model = model;
		this.projectile = projectile;
		this.x = x;
		this.y = y;
		this.z = z;
		this.fireSpeed = fireSpeed;
	}

	public Turret(Model model, int x, int y) {
		this.context = model.context;
		this.model = model;
		this.x = x;
		this.y = y;
	}
	
	public Turret(Model model, float x, float y) {
		this.context = model.context;
		this.model = model;
		this.x = x;
		this.y = y;
	}

	public Projectile getProjectile() {
		return this.projectile;
	}

	void spawn() {

	}

	void destroy() {

	}

	void fire() {
		//TODO turret directional firing
		model.addUnit(projectile.create(model, projectile.getDefaultDamage(),
				x, y, fireSpeed, 0));
	}

	/**
	 * Checks if x and y coordinate are a valid place to put a turret
	 * 
	 * @param x         x coordinate to check
	 * @param y		    y coordinate to check
	 * @return 			boolean for if it's a correct place
	 */
	public boolean turretCheck(int x, int y) {

		int yMax = model.getScreenY();
		/** .25 value below is an arbitrary testing value.
		 * Going to need trial & error to find true value needed.
		 */
		int xMax = (int) (model.getScreenX() * .25);
		boolean turretCheck = false;

		if (y > 0 && y < yMax && x > 0 && x < xMax)
			turretCheck = true;

		return turretCheck;

	}
	
	public abstract void setImageView(ImageView image);

}
