package Broadside;

public class Enemy {

	private int maxHealth, currentHealth, power, speedX, centerX, centerY;
	private Background bg = StartingClass.getBg1();
	
	public void update() {
		
		centerX += speedX;
		speedX = bg.getSpeedX();
	}

	public void sink() {
	}
		
	public void attack() {
	}
	
}
