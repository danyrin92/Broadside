package Structures;
/* TODO: Figure out which methods will be Abstract
 * 		Spawn/SetPosition/CheckPlacement
 */
public abstract class Turret {
	Image image;
	Position position;
	Projectile projectile;
	boolean canAffect; //wtf is this?
	boolean repositioned;
	int range;
	int size;
	
	public Turret() {
		this.repositioned = false;
	}
	
	public Turret(Image image, Projectile projectile, boolean canAffect, int range, int size) {
		this.image = image;
		this.projectile = projectile;
		this.canAffect = canAffect;
		this.range = range;
		this.size = size;
		this.repositioned = false;
	}
	
	public void setPosition(Position position) {
		this.position = position;
	}
	
	public void spawn() {
		
	}
	
	public void destroy() {
		
	}
	
	abstract void fire() {
		
	}
	
	public void randomFire() {
		
	}
	
	/*TODO wtf is android */
	private boolean checkPlacement() {
		Position setPosition;
		while (repositioned) {
			setPosition = //wherever player is currently touching WITH turret in hand
			if(/*not overlapping*/) {
				this.image = //something visual
			}
		}
		if (setPosition.isValid()) {
			this.setPosition(setPosition);
			return true;
		} else {
			return false;
		}
	}
}
