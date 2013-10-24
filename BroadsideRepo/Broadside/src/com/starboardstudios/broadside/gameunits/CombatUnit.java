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
	protected int xFireSpeed, yFireSpeed, zFireSpeed;

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

	public CombatUnit(Context c) {
		this.context = c;

		imageView = new ImageView(context);
		imageView.setVisibility(View.GONE);
		imageView.setImageResource(drawable.error);

	}

	void destroy() {
		/**
		 * TODO: Destroy should actually be the same in all units, minus an
		 * animation. How do you remove objects from the model properly?
		 * 
		 * The logic is that if they're deleted from the model, they are
		 * functionally destroyed.
		 */
	}

	void GoTo(int x, int y) {
		this.x = x;
		this.y = y;
		xSpeed = 0;
		ySpeed = 0;
	}

	/** Pass in projectile that collides, take damage */
	protected void Damage(Projectile p) {
		health = health - p.getDamage();
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
	 * PathOne should be a box around the screen
	 * 
	 * NOTES: Max X: (int) (model.getScreenx() * .8) MinX: (int)
	 * (model.getScreenx() * .3) MaxY: 0 MinY: (int) (model.getScreenx() * .7)
	 * 
	 * Normal speed: xSpeed = (int) (model.getScreenX() * .003);
	 * 
	 * Spawn location: x = (int) (model.getScreenX()) + 75) y = (int)
	 * (model.getScreenY() * .4);
	 * 
	 * 
	 * NODE VISUAL: 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20
	 * 
	 * */

	protected void goOne() {
		int speed = (int) (model.getScreenX() * .003);

		if (x > ((int) (model.getScreenX() * .3)) && y > 0) {
			xSpeed = -(speed / 2);
			ySpeed = -(speed / 2);
			return;
		}
		if (x > ((int) (model.getScreenX() * .3)))
			xSpeed = -speed;
		else
			xSpeed = 0;
		if (y > 0)
			ySpeed = -speed;
		else
			ySpeed = 0;
		if (x <= ((int) (model.getScreenX() * .3)) && y >= 0)
			onen = true;

	}

	protected void goTwo() {

	}

	protected void goThree() {

	}

	protected void goFour() {
		int speed = (int) (model.getScreenX() * .003);

		if (x < ((int) (model.getScreenX() * .8)) && y > 0) {
			xSpeed = (speed / 2);
			ySpeed = -(speed / 2);
			return;
		}
		if (x < ((int) (model.getScreenX() * .8)))
			xSpeed = speed;
		else
			xSpeed = 0;
		if (y > 0)
			ySpeed = -speed;
		else
			ySpeed = 0;
		if (x >= ((int) (model.getScreenX() * .8)) && y <= 0)
			fourn = true;
	}

	protected void goFive() {

	}

	protected void goSix() {

	}

	protected void goSeven() {

	}

	protected void goEight() {

	}

	protected void goNine() {

	}

	protected void goTen() {

	}

	protected void goEleven() {

	}

	protected void goTwelve() {

	}

	protected void goThirteen() {

	}

	protected void goFourteen() {

	}

	protected void goFifteen() {

	}

	protected void goSixteen() {

	}

	protected void goSeventeen() {
		int speed = (int) (model.getScreenX() * .003);

		if (x > ((int) (model.getScreenX() * .3))
				&& y < (int) (model.getScreenY() * .7)) {
			xSpeed = -(speed / 2);
			ySpeed = (speed / 2);
			return;
		}

		if (x > ((int) (model.getScreenX() * .3)))
			xSpeed = -speed;
		else
			xSpeed = 0;
		if (y < (int) (model.getScreenY() * .7))
			ySpeed = speed;
		else
			ySpeed = 0;
		if (x <= ((int) (model.getScreenX() * .3))
				&& y >= (model.getScreenY() * .7))
			seventeenn = true;

	}

	protected void goEightteen() {

	}

	protected void goNineteen() {

	}

	protected void goTwenty() {
		int speed = (int) (model.getScreenX() * .003);

		if (x < ((int) (model.getScreenX() * .8)) && y < (model.getScreenY() * .7)) {
			xSpeed = (speed/2);
			ySpeed = (speed/2);
		}

		if (x < ((int) (model.getScreenX() * .8)))
			xSpeed = speed;
		else
			xSpeed = 0;
		if (y < (model.getScreenY() * .7))
			ySpeed = speed;
		else
			ySpeed = 0;
		if (x >= ((int) (model.getScreenX() * .8))
				&& y >= (model.getScreenY() * .7))
			twentyn = true;

	}

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

	protected void pathOne() {
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

	/** PathTwo should be a triangular figure 8 */
	protected void pathTwo() {
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

}
