package com.starboardstudios.broadside.gameunits;

import java.util.Timer;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.starboardstudios.broadside.R.drawable;
import com.starboardstudios.broadside.gameunits.projectile.Projectile;
import com.starboardstudios.broadside.gameunits.ships.MainShip;

public abstract class CombatUnit extends BaseUnit {

	public ImageView imageView;
	/** Variables intrinsic to combat units */
	protected Projectile projectile;
	protected float xSpeed, ySpeed, fireSpeed;
	protected int health;
	public int lifetime = 0;
	/**
	 * Boolean values used in pathing to see if a node has been visited. It
	 * looks crazy, but I promise it will make sense
	 * 
	 * Talk to Graham if this drives you crazy
	 * */
	protected boolean onen = false, twon = false, threen = false,
			fourn = false, fiven = false, sixn = false, sevenn = false,
			eightn = false, ninen = false, tenn = false, elevenn = false,
			twelven = false, thirteenn = false, fourteenn = false,
			fifteenn = false, sixteenn = false, seventeenn = false,
			eighteenn = false, nineteenn = false, twentyn = false;
	protected Context context;
	protected Model model;
	/** Reward for giving when destroyed */
	protected int plunder;

	public CombatUnit(Context c) {
		this.context = c;

		imageView = new ImageView(context);
		imageView.setVisibility(View.GONE);
		imageView.setImageResource(drawable.error);

		// set default z
		z = 20;

	}

	public void destroy() {
		model.removeUnit(this);
		model.addBooty(plunder);
	}

	void GoTo(int x, int y) {
		this.x = x;
		this.y = y;
		xSpeed = 0;
		ySpeed = 0;
	}

	/** Pass in projectile that collides, take damage */
	protected void damage(int x) {
		health -= x;
		if (health <= 0)
			destroy();
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	/**
	 * Pass only x and y values that are functions of screen size into method
	 * below!
	 */
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * HERE BE THE PATHING METHODS! Each "go" method makes the object go to a
	 * node and sets it's flag to true.
	 */
	protected void goOne() {
		onen = setPath((int) (model.getScreenX() * .3), 0);
	}

	protected void goTwo() {
		twon = setPath(percentX(.33), 0);
	}

	protected void goThree() {
		threen = setPath(percentX(.66), 0);
	}

	protected void goFour() {
		fourn = setPath((int) (model.getScreenX() * .8), 0);
	}

	protected void goFive() {
		fiven = setPath(percentX(0), percentY(.25));

	}

	protected void goSix() {
		sixn = setPath(percentX(.33), percentY(.25));
	}

	protected void goSeven() {
		sevenn = setPath(percentX(.66), percentY(.25));
	}

	protected void goEight() {
		eightn = setPath(percentX(1), percentY(.25));
	}

	protected void goNine() {
		ninen = setPath(percentX(0), percentY(.5));
	}

	protected void goTen() {
		tenn = setPath(percentX(.33), percentY(.5));
	}

	protected void goEleven() {
		elevenn = setPath(percentX(.66), percentY(.5));
	}

	protected void goTwelve() {
		twelven = setPath(percentX(1), percentY(.5));
	}

	protected void goThirteen() {
		thirteenn = setPath(percentX(0), percentY(.75));
	}

	protected void goFourteen() {
		fourteenn = setPath(percentX(.33), percentY(.75));
	}

	protected void goFifteen() {
		fifteenn = setPath(percentX(.66), percentY(.75));
	}

	protected void goSixteen() {
		sixteenn = setPath(percentX(1), percentY(.75));
	}

	protected void goSeventeen() {
		seventeenn = setPath(percentX(0), percentY(1));
	}

	protected void goEighteen() {
		eighteenn = setPath(percentX(.33), percentY(1));
	}

	protected void goNineteen() {
		nineteenn = setPath(percentX(.66), percentY(1));
	}

	protected void goTwenty() {
		twentyn = setPath((int) (model.getScreenX() * .8),
				(int) (model.getScreenY() * .7));
	}

	/**
	 * Clean nodes lets you reset all nodes after a path has been completed
	 */
	protected void cleanNodes() {
		onen = false;
		twon = false;
		threen = false;
		fourn = false;
		fiven = false;
		sixn = false;
		sevenn = false;
		eightn = false;
		ninen = false;
		tenn = false;
		elevenn = false;
		twelven = false;
		thirteenn = false;
		fourteenn = false;
		fifteenn = false;
		sixteenn = false;
		seventeenn = false;
		fifteenn = false;
		sixteenn = false;
		seventeenn = false;
		eighteenn = false;
		nineteenn = false;
		twentyn = false;
	}

	/**
	 * BELOW BE THE PATHING SCRIPTS Pretty self explanatory. Feel free to go ham
	 * group and make as many as you want Just call these in an objects update
	 * method and it should path accordingly
	 */
	/** PathOne should be a triangular figure 8 */
	protected void pathOne() {
		if (!fourn)
			goFour();
		else if (!onen)
			goOne();
		else if (!twentyn)
			goTwenty();
		else if (!seventeenn)
			goSeventeen();
		else
			cleanNodes();
	}

	/**
	 * Swoop around
	 * 
	 */
	protected void pathTwo() {
		if (!fourn)
			goFour();
		else if (!sixteenn)
			goSixteen();
		else if (!nineteenn)
			goNineteen();
		else if (!threen)
			goThree();
		else if (!twon)
			goTwo();
		else if (!fourteenn)
			goFourteen();
		else if (!seventeenn)
			goSeventeen();
		else if (!onen)
			goOne();
		else if (!sixn)
			goSix();
		else if (!eighteenn)
			goEighteen();
		else if (!fifteenn)
			goFifteen();
		else if (!sevenn)
			goSeven();
		else
			cleanNodes();

	}

	/**
	 * go to center and chill
	 */

	protected void pathThree() {

		if (!tenn)
			goTen();
		else {
			xSpeed = 0;
			ySpeed = 0;
		}

	}

	/**
	 * Chill around perimeter
	 * 
	 */
	protected void pathFour() {

		if (!fourn)
			goFour();
		else if (!onen)
			goOne();
		else if (!seventeenn)
			goSeventeen();
		else if (!twentyn)
			goTwenty();
		else
			cleanNodes();

	}

	/**
	 * Fly around perimeter quickly
	 */
	protected void pathFive() {
		if (!fourn)
			goFour();
		else if (!onen)
			goOne();
		else if (!seventeenn)
			goSeventeen();
		else if (!twentyn)
			goTwenty();
		else
			cleanNodes();
	}

	/**
	 * Strafing run
	 * 
	 * /**
	 * 
	 * 
	 */

	protected void pathSix() {

	}

	/**
	 * Used to set the units xSpeed and ySpeed based upon a coordinate that it's
	 * trying to reach
	 * 
	 * @param x
	 * @param y
	 * @return
	 */

	boolean setPath(int x, int y) {
		float speed = (float) (model.getScreenX() * .0015);

		if (this.x < x)
			xSpeed = speed;
		else if (this.x > x)
			xSpeed = -speed;
		else
			xSpeed = 0;

		if (this.y < y)
			ySpeed = speed;
		else if (this.y > y)
			ySpeed = -speed;
		else
			ySpeed = 0;

		if (xSpeed != 0 && ySpeed != 0) {
			xSpeed /= 1.41;
			ySpeed /= 1.41;
		}

		if (compare(this.x, x) && compare(this.y, y))
			return true;

		else
			return false;

	}

	/**
	 * There was an issue with setPath() where the resolution of the screen was
	 * too precise and didn't allow an unforgiving comparison. This method
	 * allows for a more forgiving comparison.
	 * 
	 * @param num1
	 * @param num2
	 * @return
	 */
	boolean compare(float num1, float num2) {
		if ((num1 + 5) > num2 && (num1 - 5) < num2)
			return true;
		else
			return false;

	}

	/**
	 * Oh my god. Stuff wont work. I'm cheating and making these methods
	 * 
	 * @return
	 */
	int percentX(double percent) {
		int num = (int) (model.getScreenX() * .8)
				- (int) (model.getScreenX() * .3);
		num = (int) (num * percent);
		return (num + (int) (model.getScreenX() * .3));

	}

	int percentY(double percent) {
		int num = (int) (model.getScreenY() * .7);
		num = (int) (num * percent);
		return num;
	}

	protected void fire() {
		Projectile temp = projectile.create(projectile, x, y, fireSpeed, 0);
		temp.creator = this;
		model.addUnit(temp);
	}

	public void collide(BaseUnit unit) {
		if (unit instanceof Projectile) {
			if (((Projectile) unit).creator instanceof MainShip) {
				damage(((Projectile) unit).getDamage());
			}
		}

		if (unit instanceof CombatUnit) {

			xSpeed = 0;
			ySpeed = 0;
			System.out.println("Set xSpeed and ySpeed to 0");

		}
	}

	public void checkShipCollisions() {
		int i;
		for (i = 0; i < model.getUnits().size(); i++) {
			if (model.getUnits().get(i) == this)
				break;
		}
		for (int j = 0; j < model.getUnits().size(); j++) {
			if (model.getUnits().get(j) == this)
				j++;

			if (j >= model.getUnits().size())
				break;

			if (model.getUnits().get(j) instanceof CombatUnit) {
				float absx = Math.abs(this.x - model.getUnits().get(j).x);
				float absy = Math.abs(this.y - model.getUnits().get(j).y);

				if (absx < 100 && absy < 130) {
					if (i < j) {
						xSpeed = 0;
						ySpeed = 0;
					}
				}
			}

		}
	}

}
