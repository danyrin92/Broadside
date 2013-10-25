package com.starboardstudios.broadside.gameunits;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.starboardstudios.broadside.R;
import com.starboardstudios.broadside.controller.BaseController;
import com.starboardstudios.broadside.gameunits.projectile.Projectile;
import com.starboardstudios.broadside.gameunits.ships.MainShip;
import com.starboardstudios.broadside.util.LevelManager;

import java.util.ArrayList;

public class Model extends Thread {

	private BaseController currentActivity;
	public Context context;

	/** Starting difficulty is 1.
	 * Modifier that indicates the number of times the player has made it past the max level subtracted by 1.
	 * Intended to help emulate infinite levels. 
	 */
	protected int difficulty; 
	private int level;
	
	private int booty; //currency
	private int numTurretTypes;
	private int[] turretCosts; //centralizes turret pricing
	
	/** Below will contain all units in the game. All units extend baseunit. */
	private ArrayList<BaseUnit> units = new ArrayList<BaseUnit>();
	
	/** Below will contain all projectiles in the game. All types of projectiles extend projectile */
    private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
    
	public Model(Context context) {
		this.context = context;
		this.booty = 200;
		/**This turret stuff should probably be done elsewhere...*/
	    //TODO: Make currency values make more sense
			this.numTurretTypes = 6;
		this.turretCosts = new int[numTurretTypes+1];
		this.turretCosts[1] = 50;
		this.turretCosts[2] = 25;
		this.turretCosts[3] = 50;
		this.turretCosts[4] = 75;
		this.turretCosts[5] = 100;
		this.turretCosts[6] = 200;
		this.difficulty = 1;
		this.level = 1;
	
		this.start();
    }

	@Override
	public void run() {
		System.out.println("Model Started ");
		while (true) {
           	update();
			try {
				/** FPS modifier below */
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}


    /**
     * The update method provides a frame by frame update loop for objects within the model to
     * process movement, collisisons, etc.
     */
	public void update() {

        try{
            checkCollisions();
        } catch(Exception e)
        {
        }

        if (currentActivity != null) {
			for (int x = 0; x < units.size(); x++) {
				units.get(x).update();
			}
			for (int x = 0; x < projectiles.size(); x++) {
				projectiles.get(x).update();
			}

			/** Below is how to show text to screen */
			if (currentActivity.name.equalsIgnoreCase("PlayController")) {
				/** Below grabs appropriate TextView object */
				final TextView health = (TextView) currentActivity
						.findViewById(R.id.HealthView);
				/** Below is used because you cannot access screen from model
				* Only the application thread can access the screen
				* Runnable makes it so that you can do that */
				Runnable updateHealthTask = new Runnable() {
					@Override
					public void run() {
						health.setText("Health: " + getShipHealth());
					}
				};
				runOnMain(updateHealthTask);

			}
			
			/** Displays level text and booty to screen */
			if ((currentActivity.name.equalsIgnoreCase("PlayController"))||
					(currentActivity.name.equalsIgnoreCase("UpgradeController"))) {
				final TextView level = (TextView) currentActivity.findViewById(R.id.LevelView);
				Runnable updateLevelTask = new Runnable() {
					@Override
					public void run() {
						level.setText("Level: " + getLevel());
					}
				};
				runOnMain(updateLevelTask);
				
				/**handles booty updates */
				final TextView booty = (TextView) currentActivity.findViewById(R.id.BootyView);
				Runnable updateBootyTask = new Runnable() {
					@Override
					public void run() {
						booty.setText("Booty: " + getBooty());
					}
				};
				runOnMain(updateBootyTask);

			}       
		}

	}

    /**
     * Sets the current activity running on the application.
     * @param current The current activity
     */
	public void setCurrentActivity(BaseController current) {
		this.currentActivity = current;
        context = current.getBaseContext();

       if (currentActivity.name.equalsIgnoreCase("PlayController")) {
           LevelManager.LevelReader(this);

           for(int x=0;x< units.size();x++)
           {
               ((ViewGroup)units.get(x).getImage().getParent()).removeView(units.get(x).getImage());
               ((FrameLayout) currentActivity.findViewById(R.id.play_frame))
                       .addView(units.get(x).getImage());
           }

        }

    }

	//not sure if this is necessary; attempting to pass model between activities
	public void setContext(Context context) {
		this.context = context;
	}
	
    /**
     * Runs the passed Runnable on the Application Thread (1).
     * @param x The runnable to run on the App Thread.
     */
	public void runOnMain(Runnable x) {
		currentActivity.runOnUiThread(x);
	}

    /**
     * Adds the passed unit to the model.
     * @param unit
     */
	public void addUnit(BaseUnit unit) {
		if ((currentActivity.name.equalsIgnoreCase("PlayController"))||
				(currentActivity.name.equalsIgnoreCase("UpgradeController"))) {

            System.out.println("Adding unit to class "+ unit.toString() );
            try{
                Projectile p = (Projectile)unit;
                System.out.println("Adding Projectile To Model");
                projectiles.add((Projectile)unit);
            }catch(Exception e){
                units.add(unit);
              //maintain mainship turret list
            }
            
            if (currentActivity.name.equalsIgnoreCase("PlayController")) {
           	((FrameLayout) currentActivity.findViewById(R.id.play_frame))
					.addView(unit.getImage());
            } 
            //this makes addTurret# in upgrade screen work
            else if (currentActivity.name.equalsIgnoreCase("UpgradeController")) {
               	((FrameLayout) currentActivity.findViewById(R.id.upgrade_frame)) 
    					.addView(unit.getImage());
            }

		}

	}


    /**
     * Checks all projectiles for collisions by checking rect bounds, then redefining if necessary.
     * Calls objects' collide methods if found.
     */
     public void checkCollisions() throws Exception
     {
         for(int x =0;x<projectiles.size();x++)
         {
             Projectile tempProjectile = projectiles.get(x);

             final Rect bounds =  tempProjectile.getImage().getDrawable().getBounds();
                 for(int y=0; y<units.size();y++)
                 {
                     BaseUnit tempUnit = units.get(y);
                     if(tempUnit.getImage().getDrawable().getBounds().contains(bounds))
                     {


                         if(checkCollision(tempProjectile, tempUnit))
                         {

                             tempProjectile.collide(tempUnit);
                             tempUnit.collide(tempProjectile);
                         }


                     }
                 }
         }


     }

    /**
     * Checks two objects for pixel by pixel collisions.
     *
     * @param unit1     First unit to test
     * @param unit2     Second Unit to test
     * @return
     */
     private boolean checkCollision(BaseUnit unit1, BaseUnit unit2)
     {
         Rect bounds1 = unit1.getImage().getDrawable().getBounds();
         Rect bounds2 = unit2.getImage().getDrawable().getBounds();

         if( Rect.intersects(bounds1, bounds2) ){
             Rect collisionBounds = getCollisionBounds(bounds1, bounds2);
             for (int i = collisionBounds.left; i < collisionBounds.right; i++) {
                 for (int j = collisionBounds.top; j < collisionBounds.bottom; j++) {
                     int sprite1Pixel = getBitmapPixel(unit1, i, j);
                     int sprite2Pixel = getBitmapPixel(unit2, i, j);
                     if( isFilled(sprite1Pixel) && isFilled(sprite2Pixel)) {
                         return true;
                     }
                 }
             }
         }
         return false;


     }

    /**
     * Returns the pixel content requested
     * @param unit The unit you are testing
     * @param i    Internal x coordinate
     * @param j    Internal j coordinate
     * @return     Pixel value.
     */
    private static int getBitmapPixel(BaseUnit unit, int i, int j) {

                BitmapDrawable image = ((BitmapDrawable)(unit.getImage().getDrawable()));
        return image.getBitmap().getPixel(i-(int)unit.getImage().getX(), j-(int)unit.getImage().getY());
    }

    /**
     * Checks the overlap of the corresponding rectangles
     * @param rect1
     * @param rect2
     * @return   The overlap region
     */
    private static Rect getCollisionBounds(Rect rect1, Rect rect2) {
        int left = Math.max(rect1.left, rect2.left);
        int top = Math.max(rect1.top, rect2.top);
        int right = Math.min(rect1.right, rect2.right);
        int bottom = Math.min(rect1.bottom, rect2.bottom);
        return new Rect(left, top, right, bottom);
    }

    /**
     * Checks if the passed pixel is transparent.
     * @param pixel
     * @return true if the pixel is not transparent.
     */
    private static boolean isFilled(int pixel) {
        return pixel != Color.TRANSPARENT;
    }


    /**
     *
     * @return The pixel value of the current device's screen width.
     */
	public int getScreenX() {
		return currentActivity.getBaseContext().getResources()
				.getDisplayMetrics().widthPixels;
	}
    /**
     *
     * @return The pixel value of the current device's screen height.
     */
	public int getScreenY() {
		return currentActivity.getBaseContext().getResources()
				.getDisplayMetrics().heightPixels;
	}



	public int getShipHealth() {
		int health = getMainShip().getHealth();

		return health;
	}
	

	public MainShip getMainShip() {
		for (int i = 0; i < units.size(); i++) {
			if (units.get(i) instanceof MainShip)
				return((MainShip) units.get(i));
		}
			return null;

	}

    /**
     * Removes the requested unit from the model.
     * @param unit
     */
    public void removeUnit(BaseUnit unit)
    {
    	units.remove(unit);
        ViewGroup vg = ((ViewGroup)currentActivity.activityScreen.getParent());
        vg.removeView(unit.getImage());

    }
  
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int lvl) {
		level = lvl;
	}
	
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
	
	public int getDifficulty() {
		return difficulty;
	}
		
	/**for currency system*/
	public int getBooty() {
		return booty;
	}
	
	public void setBooty(int booty) {
		this.booty = booty;
	}
	
	public void spendBooty(int spentBooty) {
		this.booty -= spentBooty;
	}
	
	public int getTurretCostAt(int index) {
		return turretCosts[index];
	}

    public Activity getCurrentActivity()
    {
        return currentActivity;
    }
}