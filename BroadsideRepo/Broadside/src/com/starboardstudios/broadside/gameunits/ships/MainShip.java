package com.starboardstudios.broadside.gameunits.ships;

import android.widget.ImageView;
import android.widget.LinearLayout;
import com.starboardstudios.broadside.R.drawable;
import com.starboardstudios.broadside.gameunits.BaseUnit;
import com.starboardstudios.broadside.gameunits.Crew;
import com.starboardstudios.broadside.gameunits.Model;
import com.starboardstudios.broadside.gameunits.aircrafts.BaseAircraft;
import com.starboardstudios.broadside.gameunits.projectile.Projectile;
import com.starboardstudios.broadside.gameunits.turrets.Cannon;
import com.starboardstudios.broadside.gameunits.turrets.LaserCannon;
import com.starboardstudios.broadside.gameunits.turrets.MainCannon;
import com.starboardstudios.broadside.gameunits.turrets.Turret;

import java.util.ArrayList;

public class MainShip extends com.starboardstudios.broadside.gameunits.CombatUnit {

	private ImageView imageView; 
	private Model model;
	private ArrayList<Crew> crews = new ArrayList<Crew>();
	private ArrayList<Turret> turrets = new ArrayList<Turret>();
	private MainCannon mainCannon;
	private boolean invulnerable = false;
	private Section bow,midship,stern;
	private int maxHealth;
	private float xKey = .1025f;

	public MainShip(Model model, boolean b) {
		super(model.context);
		this.model = model;
		imageView = new ImageView(model.context);

		/** PNG to be used as image */
		imageView.setImageResource(drawable.main_ship_final_trimmed);
		imageView.setAdjustViewBounds(true);

		/** Determines rendering size of object */
		imageView.setLayoutParams(new LinearLayout.LayoutParams((int) (model
				.getScreenX() * .75), (int) (model.getScreenY() * 1.2)));

		/** Starting position. As for now on the left. */
		x = -((int) (model.getScreenX() * .0125));
		y = (float) -(model.getScreenY() * .2);

		//xKey = .1125f;
		mainCannon = new MainCannon(model,
				(float) (this.x + ((model.getScreenX() * xKey))),
				(float) (this.y + ((model.getScreenX() * .3))));
		
		/** Sections... As of now just to localize visuals 
		 * (get shot in stern a lot, fires appear in stern, etc.*/
		float x = (float) (model.getScreenX() * xKey *.4);
		float y = (float) model.getScreenY();
		health = maxHealth = 1000;
		bow = new Section(model,x,y*(float).1, health/2);
		midship = new Section(model,x,y*(float).4, health/2);
		stern = new Section(model,x,y*(float).7, health/2);
		y = (float) -(model.getScreenY() * .2);
		float offset = 0; // for crew
		float crewX;
		float crewY;
		for (int i = 0; i < crews.size(); i++) {
			offset = ((float) i) / 50;
			crewX = (float) (this.x + ((model.getScreenX() * (xKey+.05))));
			crewY = (float) (this.y + ((model.getScreenX() * (.3 - offset))));
			crews.get(i).setPosition(crewX, crewY);
			crews.get(i).setStations(crewX, crewY);
		}
	}
	
	public MainShip(Model model) {
		super(model.context);
		this.model = model;
		//Image
		imageView = new ImageView(model.context);
		imageView.setImageResource(drawable.main_ship_final_trimmed);
		imageView.setAdjustViewBounds(true);
		imageView.setLayoutParams(new LinearLayout.LayoutParams((int) (model
				.getScreenX() * .75), (int) (model.getScreenY() * 1.2)));
		x = -((int) (model.getScreenX() * .225));
		y = ((int) (model.getScreenY() * .7));
		//MainCannon added here
		mainCannon = new MainCannon(model,
				(float) (this.x + ((model.getScreenX() * (xKey+.2)))),
				(float) (this.y + ((model.getScreenX() * .3))));
		//Sections (health and centers set here)
		health = maxHealth = 1000;
		setupSections();
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
					(float) (this.x + ((model.getScreenX() * (xKey+.1)))),
					(float) (this.y + ((model.getScreenX() * .3))));
			float offset = 0; // for crew
			float crewX;
			float crewY;
			for (int i = 0; i < crews.size(); i++) {
				offset = ((float) i) / 50;
				crewX = (float) (this.x + ((model.getScreenX() * (xKey+.1))));
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
				damage((Projectile) unit);
			}
		}
	}
	
	protected void damage(Projectile p) {
		if (!invulnerable) {
			health -= p.getDamage();
			if (health <= 0) {
				//TODO implement destroy();
				destroy();
			} else {
				//Manage section
				determineSection(p.getY()).damage(p);
			}
		}
	}
	
	public void destroy() {
		//TODO: Add animations to the destroy method
		model.goToFailState();		
	}
	
	@Override
	public void setPosition(int x, int y) {

	}

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
	
	public Crew getNextAvailableCrew() {
		boolean busy;
		for (int i=0; i<crews.size(); i++) {
			busy = crews.get(i).getBusy();
			if (!busy) {
				return crews.get(i);
			}
		}
		return null; //all busy
	}

	public Section getBow() {
		return bow;
	}

	public void setBow(Section bow) {
		this.bow = bow;
	}

	public Section getMidship() {
		return midship;
	}

	public void setMidship(Section midship) {
		this.midship = midship;
	}

	public Section getStern() {
		return stern;
	}

	public void setStern(Section stern) {
		this.stern = stern;
	}
	
	public Section determineSection(float y) {
		float yCoeff = y / model.getScreenY ();
		if (yCoeff<(float).25) { //bow
			return bow;
		} else if (yCoeff<(float).5) { //midship
			return midship;
		} else { //stern
			return stern;
		}
	}
	
	public String determineSectionName(float y) {
		float yCoeff = y / model.getScreenY ();
		if (yCoeff<(float).25) { //bow
			return "Bow";
		} else if (yCoeff<(float).5) { //midship
			return "Midship";
		} else { //stern
			return "Stern";
		}
	}

	public void addHealth(int repairHealthBar) {
		if (health+repairHealthBar>maxHealth) {
			health = maxHealth;
		} else {
			health+=repairHealthBar;
		}
	}
	
	public void fireBroadside(float x, float y) {
		Turret turret;
		for (int i = 0; i < turrets.size(); i++) {
			turret = turrets.get(i);
			if (turret instanceof Cannon || turret instanceof LaserCannon) {
				//TODO clean up
				if (turret instanceof LaserCannon) {
					//System.out.println("Laser cannon fired...");
					turret.fireBurst(x,y);
				} else {
					turret.fire(x,y);
				}
			}
		}
	}
	
	public Turret getLastTurret() {
		if (turrets.size()>0) {
			return turrets.get(turrets.size()-1);
		}
		return null;
	}
	
	public void toggleInvulnerablity() {
		invulnerable = !invulnerable;
	}
	
	public int getNumCrew() {
		return crews.size();
	}
	
	public void setupSections() {
		float x = (float) (model.getScreenX() * xKey *.4);
		float y = (float) model.getScreenY();
		bow = new Section(model,x,y*(float).1, health/3);
		midship = new Section(model,x,y*(float).4, health/3+1);
		stern = new Section(model,x,y*(float).7, health/3);
	}  
	
	public void updateHealth() {
		health = bow.getHealth() + midship.getHealth() + stern.getHealth();
	}
	
	public void setHealth(int shipHealth) {
		bow.setHealth(shipHealth/3);
		midship.setHealth(shipHealth/3 + 1);
		stern.setHealth(shipHealth/3);
	}

	/** For getting images to appear above the MainShip 
	public void reset() {
		ArrayList<Crew> tempCrews = crews;
		MainCannon tempCannon = mainCannon;
		ArrayList<Turret> tempTurrets = turrets;
		
		crews = null;
		mainCannon = null;
		turrets = null;
		
		
		mainCannon = new MainCannon(model,
				(float) (this.x + ((model.getScreenX() * .325))),
				(float) (this.y + ((model.getScreenX() * .3))));
		
		
	}
	*/
}
