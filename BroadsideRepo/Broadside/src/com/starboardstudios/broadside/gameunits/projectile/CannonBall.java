package com.starboardstudios.broadside.gameunits.projectile;

import android.widget.LinearLayout;
import com.starboardstudios.broadside.gameunits.BaseUnit;
import com.starboardstudios.broadside.R.drawable;
import com.starboardstudios.broadside.gameunits.Model;

public class CannonBall extends Projectile {
	private int defaultDamage = 10;
	private int scaleFactor = (int) (model.getScreenY() * .065);

	public CannonBall(Model model) {
		super(model);
	}

	public CannonBall(Model model, int damage) {
		super(model, damage);
		
		x = 0;
		y = 0;
		xSpeed = 0;
		ySpeed = 0;

		imageView.setImageResource(drawable.cannon_ball);
		imageView.setAdjustViewBounds(true);
		imageView.setLayoutParams(new LinearLayout.LayoutParams((int) (model
				.getScreenX() * .15), scaleFactor));

	}
	
	public CannonBall(Model model, int damage, float x, float y, float fireSpeed, float angle) {
		super(model, damage, x, y, fireSpeed, angle);

		imageView.setImageResource(drawable.cannon_ball);
		imageView.setAdjustViewBounds(true);
		imageView.setLayoutParams(new LinearLayout.LayoutParams((int) (model.getScreenX() * .15), scaleFactor)); 
		}  	

	public int getDefaultDamage() {
		return defaultDamage;
	}
	
	@Override
	public Projectile create(Model model, int damage, float x, float y, float fireSpeed, float angle) {
		return new CannonBall(model, damage, x, y, fireSpeed, angle);
	}
	
    @Override
    public void collide(BaseUnit collidedWith)
    {
       model.removeUnit(this);
    }

	@Override
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	
}
