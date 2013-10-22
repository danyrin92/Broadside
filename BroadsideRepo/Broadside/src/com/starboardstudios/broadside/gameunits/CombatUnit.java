package com.starboardstudios.broadside.gameunits;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import com.starboardstudios.broadside.R.drawable;
import com.starboardstudios.broadside.gameunits.projectile.Projectile;

public abstract class CombatUnit extends BaseUnit {
	
	public ImageView imageView;
	
	/** Variables intrinsic to combat units */
	protected int xSpeed;
	protected int ySpeed;
	protected int health;
	
	protected Context context;
	protected Model model;
	
	public CombatUnit(Context c)
	{
		this.context=c;
		
		imageView = new ImageView(context);
		imageView.setVisibility(View.GONE);
		imageView.setImageResource(drawable.error);
		
	}
	
	void destroy(){
		/** TODO: Destroy should actually be the same in
		 * all units, minus an animation. How do you remove 
		 * objects from the model properly?
		 * 
		 * The logic is that if they're deleted from the model, they are 
		 * functionally destroyed.
		 */
    }
	
	
	void GoTo(int x, int y){
		this.x = x;
		this.y = y;
		xSpeed = 0;
		ySpeed = 0;
	}
	
	/** Pass in projectile that collides, take damage */
	protected void Damage(Projectile p){
		health = health - p.getDamage();
	}
	
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
    public void setPosition(int x, int y) {
		this.x = ((int) (model.getScreenX()) + x);
		this.y = ((int) (model.getScreenY() + y));
    }
}
