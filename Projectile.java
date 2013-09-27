
public class Projectile{

	private int x, y, velocity;
	private boolean show;
	
	
	public Projectile(int X, int Y, int V){
		/*
		 * This is the constructor, where we initialize a projectile that starts
		 * from an x-coordinate X and a y-coordinate Y. The velocity should also be
		 * set to V, as different projectiles should be able to travel at a 
		 * different speed. In addition show should be set to 1. If the projectile
		 * is active and in the air, show is equal to 1, otherwise it equals 0.
		 */
	}
	
	public void update(){
		/*
		 * This method gets called by the main class to refresh the position and
		 * action of the projectile. It should increase the x (and/or) y value by the 
		 * amount of velocity. (I.E. velocity = sqrt((increase in x)^2 + (increase in
		 * y)^2)) Then it checks to see if the projectile is still on the screen. If
		 * it isnt then the show variable is set to 0. If the projectile is on the
		 * screen, then we see if the projectile has made contact with something by 
		 * calling the collision method.
		 */
	}

	private void collision() {
		/*
		 * Here we check if the projectile has made contact and with what if it has.
		 * We should first check if it had touched the player's boat, since this
		 * could be a projectile fired by the enemy. If it has, then the appropriate
		 * amount of health should be deduced from the player. Additionally, the
		 * show variable of the projectile should be set to 0 and a break; statement
		 * should be added. If the projectile has not hit the player, we check if it
		 * has hit one of the enemies and which one if it has. If it has, then we 
		 * deduce the appropriate amount of health from the appropriate enemy and
		 * set the show variable of the projectile as 0.
		 */
	}


	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getVelocity() {
		return velocity;
	}

	public boolean active() {
		return show;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setvelocity(int velocity) {
		this.velocity = velocity;
	}

	public void setShow(boolean show) {
		this.show = show;
	}
	
}
