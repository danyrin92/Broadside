package com.starboardstudios.broadside.gameunits;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.starboardstudios.broadside.R;
import com.starboardstudios.broadside.controller.BaseController;
import com.starboardstudios.broadside.gameunits.ships.MainShip;

import java.util.ArrayList;

public class Model extends Thread {

	public Context context;
	private BaseController currentActivity;
	// Below will contain all units in the game. All units extend baseunit.
	private ArrayList<BaseUnit> units = new ArrayList<BaseUnit>();

	public Model(Context context) {
		this.context = context;
		this.start();
	}

	@Override
	public void run() {
		System.out.println("Model Started ");
		while (true) {
			update();
			try {
				// FPS modifier below
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void update() {
		if (currentActivity != null) {
			for (int x = 0; x < units.size(); x++) {
				units.get(x).update();
			}
			
			if(currentActivity.name.equalsIgnoreCase("PlayController")){
				final TextView health = (TextView) currentActivity.findViewById(R.id.HealthView);
			    Runnable updateHealthTask = new Runnable() {
                    @Override
                    public void run() {
                        health.setText("Health: "+ getShipHealth());
                    }
                };
                runOnMain(updateHealthTask);

			}
		}

	}

	public void setCurrentActivity(BaseController homeController) {
		this.currentActivity = homeController;
	}

	public void runOnMain(Runnable x) {
		currentActivity.runOnUiThread(x);
	}

	public void addUnit(BaseUnit unit) {
		if (currentActivity.name.equalsIgnoreCase("PlayController")) {
			units.add(unit);
			((FrameLayout) currentActivity.findViewById(R.id.play_frame))
					.addView(unit.getImage());

		}

	}

	// Two methods below return a dynamic value for screen resolution. Use this
	// and mult/division to move through screen.
	public int getScreenX() {
		return currentActivity.getBaseContext().getResources()
				.getDisplayMetrics().widthPixels;
	}

	public int getScreenY() {
		return currentActivity.getBaseContext().getResources()
				.getDisplayMetrics().heightPixels;
	}

	// Enter an x and y variable, and see if it is a valid placement for a
	// turret
	public boolean turretCheck(int x, int y) {
		int yMax = getScreenY();
		int xMax = (int) (getScreenX() * .25);
		boolean turretCheck = false;

		if (y > 0 && y < yMax && x > 0 && x < xMax)
			turretCheck = true;

		return turretCheck;

	}

	public int getShipHealth() {
		int health = 0;
		for (int i = 0; i < units.size(); i++){
			
			if (units.get(i) instanceof MainShip)
				health = ((MainShip) units.get(i)).getHealth();
			
		}
		return health;
	}

	
	
	
}
