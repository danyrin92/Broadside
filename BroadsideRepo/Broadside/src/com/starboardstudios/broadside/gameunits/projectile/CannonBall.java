package com.starboardstudios.broadside.gameunits.projectile;

import android.widget.LinearLayout;
import com.starboardstudios.broadside.R.drawable;
import com.starboardstudios.broadside.gameunits.BaseUnit;
import com.starboardstudios.broadside.gameunits.Model;

public class CannonBall extends Projectile {
	private int defaultDamage = 20;
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
				.getScreenX() * .7), scaleFactor));

	}

	public CannonBall(Model model, int damage, int x, int y) {
		super(model, damage, x, y);

		imageView.setImageResource(drawable.cannon_ball);
		imageView.setAdjustViewBounds(true);
		imageView.setLayoutParams(new LinearLayout.LayoutParams((int) (model
				.getScreenX() * .7), scaleFactor));
																	
	}

	public CannonBall(Model model, int x, int y, float xTarget, float yTarget) {
		super(model);
		this.x =x; this.y=y; this.xTarget = xTarget; this.yTarget = yTarget;
		
		imageView.setImageResource(drawable.cannon_ball);
		imageView.setAdjustViewBounds(true);
		imageView.setLayoutParams(new LinearLayout.LayoutParams((int) (model
				.getScreenX() * .7), (int) (model.getScreenY() * .7))); // Set
																		// size
	}
	
	public CannonBall(Model model, int damage, int x, int y, int z, int xSpeed,
			int ySpeed, int zSpeed) {
		super(model, damage, x, y, xSpeed, ySpeed);

		imageView.setImageResource(drawable.cannon_ball);
		imageView.setAdjustViewBounds(true);
		imageView.setLayoutParams(new LinearLayout.LayoutParams((int) (model
				.getScreenX() * .05), scaleFactor)); 
																		

	}
	
	public CannonBall(Model model, int damage, int x, int y, float xTarget, float yTarget){
		super(model,damage,x,y,xTarget,yTarget);
		
		imageView.setImageResource(drawable.cannon_ball);
		imageView.setAdjustViewBounds(true);
		imageView.setLayoutParams(new LinearLayout.LayoutParams((int) (model
				.getScreenX() * .7), (int) (model.getScreenY() * .7))); // Set
																		// size
  	}
  	

	public int getDefaultDamage() {
		return defaultDamage;
	}


	@Override
	public void setPosition(int x, int y) {

	}
	
	@Override
	public Projectile create(Model model, int x, int y, int xFireSpeed, int yFireSpeed) {
		return new CannonBall(model, defaultDamage, x, y, xFireSpeed, yFireSpeed);
	}

	@Override
	public Projectile create(Model model, int damage, int x, int y, int xFireSpeed, int yFireSpeed) {
		return new CannonBall(model, damage, x, y, xFireSpeed, yFireSpeed);
	}
    @Override
    public void collide(BaseUnit collidedWith)
    {
       model.removeUnit(this);
    }
	
	
}
