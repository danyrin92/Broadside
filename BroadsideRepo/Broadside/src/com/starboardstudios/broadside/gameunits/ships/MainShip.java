package com.starboardstudios.broadside.gameunits.ships;

import android.widget.ImageView;
import android.widget.LinearLayout;
import com.starboardstudios.broadside.gameunits.BaseUnit;
import com.starboardstudios.broadside.gameunits.aircrafts.BaseAircraft;
import com.starboardstudios.broadside.gameunits.projectile.Projectile;
import com.starboardstudios.broadside.gameunits.turrets.MainCannon;
import com.starboardstudios.broadside.R.drawable;
import com.starboardstudios.broadside.gameunits.Crew;
import com.starboardstudios.broadside.gameunits.Model;
import com.starboardstudios.broadside.gameunits.turrets.Turret;

import java.util.ArrayList;

public class MainShip extends
		com.starboardstudios.broadside.gameunits.CombatUnit {

	ImageView imageView; // Image for ship
	private Model model;
	private ArrayList<Crew> crews = new ArrayList<Crew>();
	private ArrayList<Turret> turrets = new ArrayList<Turret>();
	private MainCannon mainCannon;
	private int waterLevel;
	private boolean inPosition = false;
	private Section bow,midship,stern;

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
		mainCannon = new MainCannon(model,
				(float) (this.x + ((model.getScreenX() * .325))),
				(float) (this.y + ((model.getScreenX() * .3))));
		
		/** Sections... As of now just to localize visuals 
		 * (get shot in stern a lot, fires appear in stern, etc.*/
		float x = (float) (model.getScreenX() * .325);
		float y = (float) model.getScreenY();
		bow = new Section(model,x,y*(float).1);
		midship = new Section(model,x,y*(float).4);
		stern = new Section(model,x,y*(float).7);
	}

	protected void Damage(Projectile p) {
		health = health - p.getDamage();
	}
	
	protected void Damage(Projectile p, Section s) {
		Damage(p);
		//manage section
		s.damage(p);
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

		if (y > -(model.getScreenY() * .2)) {
			ySpeed = -(int) (model.getScreenY() * .003);
			
			// update turrets and crew
			mainCannon.setPosition(
					(float) (this.x + ((model.getScreenX() * .325))),
					(float) (this.y + ((model.getScreenX() * .3))));
			float offset = 0; // for crew
			float crewX;
			float crewY;
			for (int i = 0; i < crews.size(); i++) {
				offset = ((float) i) / 50;
				crewX = (float) (this.x + ((model.getScreenX() * .345)));
				crewY = (float) (this.y + ((model.getScreenX() * (.3 - offset))));
				crews.get(i).setPosition(crewX, crewY);
				crews.get(i).setStations(crewX, crewY);
			}
		} else {
			ySpeed = 0;
		}

		model.runOnMain(new Runnable() {
			public void run() {
				imageView.setX(x);
				imageView.setY(y);
				imageView.setImageResource(drawable.mainship);
			}

		});

	}

	/** test to put mainShip onto upgrades screen */
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

	public void collide(BaseUnit unit) {
		if (unit instanceof Projectile) {
			if ((((Projectile)unit).creator instanceof BaseShip) || (((Projectile)unit).creator instanceof BaseAircraft) 
					|| (((Projectile)unit).creator instanceof BaseShip)) {
				damage(((Projectile) unit).getDamage());
			}
		}
	}
	
	public void damage(int damage) {
		health -= damage;
		//TODO: Add animation to the damage method
		if (health < 0) {
			destroy();
		}
	}
	
	public void destroy() {
		//TODO: Add animations to the destroy method
		
		model.removeUnit(this);
	}
	
	@Override
	public void setPosition(int x, int y) {

	}

	/**
	 *
     */
	/*
	 * void FireMain(int x, int y) { int deltaX = x - this.x; int deltaY =
	 * this.y - y; double degreeAngle = Math.atan((deltaY / deltaX)) * 180 /
	 * Math.PI; }
	 */

	public ArrayList<Turret> getTurrets() {
		return turrets;
	}

	public void addTurret(Turret turret) {
		turrets.add(turret);
	}

	public MainCannon getMainCannon() {
		return mainCannon;

	}

	public void addCrew(Crew crew) {
		crews.add(crew);
	}

	public ArrayList<Crew> getCrew() {
		return crews;
	}

	public float getX() {
		return imageView.getX();
	}

	public float getY() {
		return imageView.getY();
	}

	public void setImageView(ImageView upImage) {
		this.imageView = upImage;
	}

	public Crew getLastCrew() {
		return crews.get(crews.size() - 1);
	}

}
