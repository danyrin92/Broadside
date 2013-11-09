package com.starboardstudios.broadside.gameunits.ships;

import android.widget.ImageView;
import android.widget.LinearLayout;
import com.starboardstudios.broadside.gameunits.BaseUnit;
import com.starboardstudios.broadside.gameunits.projectile.Projectile;
import com.starboardstudios.broadside.gameunits.turrets.MainCannon;
import com.starboardstudios.broadside.R.drawable;
import com.starboardstudios.broadside.gameunits.Crew;
import com.starboardstudios.broadside.gameunits.Model;
import com.starboardstudios.broadside.gameunits.turrets.Turret;

import java.util.ArrayList;

public class MainShip extends com.starboardstudios.broadside.gameunits.CombatUnit {

	ImageView imageView; // Image for ship
	private Model model;
	private ArrayList<Section> sections;
	private ArrayList<Crew> crews = new ArrayList<Crew>();
	private ArrayList<Turret> turrets = new ArrayList<Turret>();
	private MainCannon mainCannon;
	private int waterLevel;
	private boolean inPosition = false;

	public MainShip(Model model) {
		super(model.context);
		this.model = model;
		this.waterLevel = 0;
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
		mainCannon = new MainCannon(model,(float)(this.x+((model.getScreenX()*.325))),(float)(this.y+((model.getScreenX()*.3)))) ;
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

		//update turrets and crew
		mainCannon.setPosition((float)(this.x+((model.getScreenX()*.325))),(float)(this.y+((model.getScreenX()*.3))));
		for (int i=0; i<crews.size(); i++) {
			crews.get(i).setPosition((float)(this.x+((model.getScreenX()*.345))),(float)(this.y+((model.getScreenX()*.3))));
		}
		
		model.runOnMain(new Runnable() {
			public void run() {
				imageView.setX(x);
				imageView.setY(y);
				imageView.setImageResource(drawable.mainship);
			}

		});

	}
	
	/** test to put mainShip onto upgrades screen*/
	public void spawn() {
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
     */
	/*void FireMain(int x, int y) {
		int deltaX = x - this.x;
		int deltaY = this.y - y;
		double degreeAngle = Math.atan((deltaY / deltaX)) * 180 / Math.PI;
	}*/

	public ArrayList<Turret> getTurrets() {
		return turrets;
	}
	
	public void addTurret(Turret turret) {
		turrets.add(turret);
	}
	
	public MainCannon getMainCannon(){
		return mainCannon;
		
	}
	
	public void addCrew(Crew crew) {
		crews.add(crew);
	}
	
	public ArrayList<Crew> getCrew() {
		return crews;
	}

	public void setImageView(ImageView upImage) {
		this.imageView = upImage;
	}

}
