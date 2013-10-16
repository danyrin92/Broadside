package com.starboardstudios.broadside.gameunits.submarine;

import java.util.Random;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.starboardstudios.broadside.R.drawable;
import com.starboardstudios.broadside.gameunits.BaseUnit;
import com.starboardstudios.broadside.gameunits.CombatUnit;
import com.starboardstudios.broadside.gameunits.Model;
import com.starboardstudios.broadside.gameunits.projectile.Projectile;
import com.starboardstudios.broadside.gameunits.turrets.MainCannon;

public class Submarine extends CombatUnit{
	//Top level of all types of Ships

	public ImageView imageView = new ImageView(context); // Image for ship
	protected MainCannon mainCannon;
	//private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();    Projectiles are put in the MODEL, not owned by a ship... once a ship shoots something, why would the ship have any control over it?
	Random rand = new Random();
	int random = rand.nextInt(2);
	
	public Submarine(Model model) {
		super(model.context);
		this.model = model;
			// Can't make image another file because it's not auto-generating the
		// address in R.java. What gives?
			// Using test for now
		imageView.setImageResource(drawable.enemyship); //new image needed
		imageView.setAdjustViewBounds(true);

		imageView.setLayoutParams(new LinearLayout.LayoutParams((int) (model
				.getScreenX() * .15), (int) (model.getScreenY() * .15)));

		imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				System.out.print("I shot");
	               shoot();

			}
		});
			
		health = 10;
			
		x = ((int) (model.getScreenX()) + 75);
		y = ((int) (model.getScreenY() * .4));

		// Below will be updated
		xSpeed = -(int) (model.getScreenX() * .003);
	}

	public void update() {
		x = x + xSpeed;
		y = y + ySpeed;

		if (x < ((int) (model.getScreenX()) * .5)) {
			xSpeed = 0;
			if (random == 1)
				ySpeed = (int) (model.getScreenX() * .003);
			if (random == 0)
				ySpeed = -(int) (model.getScreenX() * .003);
		}
	    /**  Projectiles are not here.... in model, see above...
		    for(int i=0;i<projectiles.size();i++){
	    		projectiles.get(i).update();
	       	}
	       */
		model.runOnMain(new Runnable() {
			public void run() {
				imageView.setX(x);
				imageView.setY(y);
				imageView.setImageResource(drawable.enemyship);
			}
			
		});
	}

	@Override
	public ImageView getImage() {
		return imageView;
	}
	
	public void setVelocity(int xSpeed, int ySpeed) {
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		imageView.setX(x);
		imageView.setY(y);

	}

	public void collide(BaseUnit unit) {

	       x=0;


	}

	public void shoot() {

		Projectile p = new Projectile(this.model);
		p.setX(x);
		p.setY(y);
		p.setxSpeed(-(int) (model.getScreenX() * .009));
	    model.addUnit(p);
	}
}
