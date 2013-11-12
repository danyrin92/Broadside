package com.starboardstudios.broadside.controller;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import com.starboardstudios.broadside.R;
import com.starboardstudios.broadside.R.drawable;
import com.starboardstudios.broadside.app.BroadsideApplication;
import com.starboardstudios.broadside.gameunits.Crew;
import com.starboardstudios.broadside.gameunits.Model;
import com.starboardstudios.broadside.gameunits.projectile.CannonBall;
import com.starboardstudios.broadside.gameunits.ships.MainShip;
import com.starboardstudios.broadside.gameunits.turrets.*;
import com.starboardstudios.broadside.interfaces.Draggable;

public class UpgradeController extends BaseController {

	private Model model;
	private MainShip mainShip;
	/**For renderMainShip()*/
	private ImageView msPlay;
	private ImageView msUp;
	private ImageView mcPlay;
	private ImageView mcUp;
	private ArrayList<ImageView> crewPlays = new ArrayList<ImageView>();
	private ArrayList<ImageView> crewUps = new ArrayList<ImageView>();
	/** ^ */

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final View screen = ((LayoutInflater) getBaseContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.upgrade_view, null);
		setContentView(R.layout.upgrade_view);
		name = "UpgradeController";
		model = ((BroadsideApplication) this.getApplication()).getModel();
		model.setCurrentActivity(this);

		// get and display mainShip
		mainShip = model.getMainShip();
		renderMainShip();
		

		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}

        RelativeLayout layout =  (RelativeLayout)this.findViewById(R.id.upgrade);
		/**DRAG LISTENER*/
		screen.setVisibility(View.VISIBLE);
		layout.setOnDragListener(new View.OnDragListener() {
			@Override
			public boolean onDrag(View v, DragEvent event) {
				((Draggable) event.getLocalState()).midDrag(event.getX(),
						event.getY());
				if (event.getAction() == DragEvent.ACTION_DRAG_STARTED) {
					((Draggable) event.getLocalState()).dragStarted();
				} else if (event.getAction() == DragEvent.ACTION_DRAG_ENTERED) {
					//
				} else if (event.getAction() == DragEvent.ACTION_DROP) {
                    System.out.println("Dropped");
					((Draggable) event.getLocalState()).endDrag(event.getX(),
							event.getY());
				}
				v.invalidate();
				return true;

			}
		});
	}

	public void nextLevel(View view) {
		model.setLevel(model.getLevel() + 1);
		Intent playIntent = new Intent(this, PlayController.class);
		startActivity(playIntent);
	}
	
	public void hire(View view) {
		int cost = 25;
		if (model.getBooty()>=cost) {
			model.addUnit(new Crew(this, model));
			model.spendBooty(cost);
		}
	}
	
	public void heave(View view) {
		ArrayList<Crew> crew = mainShip.getCrew();
		int numCrew = crew.size();
		if (numCrew>0) {
			model.removeUnit(crew.get(numCrew-1));
			model.addBooty(10);
		}
	}

	/** For implementing turret options*/
	public void addTurret1(View view) {
		addTurret(1); //Turret1
	}

	public void addTurret2(View view) {
		addTurret(2); //Turret2
	}

	public void addTurret3(View view) {
		addTurret(3); //Turret3
	}

	public void addTurret4(View view) {
		addTurret(4); //Turret4
	}

	public void addTurret5(View view) {
		addTurret(5); //Turret5
	}

	public void addTurret6(View view) {
		addTurret(6);  //Turret6
	}

	public void addTurret(int turretNum) {
		if (enoughBooty(turretNum)) {
			switch (turretNum) { //1-6
			case 1:
				model.addUnit(new Turret1(model, new CannonBall(model, 20) ) );
				break;
			case 2:
				model.addUnit(new Turret2(model, new CannonBall(model, 20) ) );
				break;
			case 3:
				model.addUnit(new Turret3(model, new CannonBall(model, 20) ) );
				break;
			case 4:
				model.addUnit(new Turret4(model, new CannonBall(model, 20) ) );
				break;
			case 5:
				model.addUnit(new Turret5(model, new CannonBall(model, 20) ) );
				break;
			case 6:
				model.addUnit(new Turret6(model, new CannonBall(model, 20) ) );
				break;
			default:
				System.out.println("Turret not implemented yet!");
				break;
			}
		}
	}

	public boolean enoughBooty(int turretNum) {
		if (model.getBooty() >= model.getTurretCostAt(turretNum)) {
			return true;
		}
		// TODO display in-game "you don't have enough booty..."
		return false;
	}
	
	//render mainship, its crew and turrets in the upgrades screen
	public void renderMainShip() {
		savePlays(); //saves the play screen imageViews (identical save for context...)
		createUps(); //creates upgrade screen compatible version of plays
		/** set ups to objects and add to model*/
		//mainship
		mainShip.setImageView(msUp);
		model.addToUp(mainShip);
		//maincannon
		mainShip.getMainCannon().setImageView(mcUp);
		model.addToUp(mainShip.getMainCannon());
		//crew loop
		ArrayList<Crew> crew = mainShip.getCrew();
		int numCrew = crew.size();
		for (int i = 0; i < numCrew; i++) {
			crew.get(i).setImageView(crewUps.get(i));
			model.addToUp(crew.get(i));
		}
	}
	
	//saves play screen imageViews of mainship, its crew and turrets
	public void savePlays() {
		//mainship
		msPlay = mainShip.getImage();
		msPlay.setTag(1);
		//maincannon
		mcPlay = mainShip.getMainCannon().getImage();
		mcPlay.setTag(1);
		//crew loop
		ArrayList<Crew> crew = mainShip.getCrew();
		int numCrew = crew.size();
		for (int i = 0; i < numCrew; i++) {
			crewPlays.add(crew.get(i).getImage());
			crewPlays.get(i).setTag(1);
		}
	}
	
	//creates upgrade screen compatible version of plays (context changed)
	public void createUps() {
		//mainship
		msUp = new ImageView(this);
		msUp.setImageResource((Integer) msPlay.getTag());
		msUp.setAdjustViewBounds(true);
		msUp.setLayoutParams(msPlay.getLayoutParams());
		//maincannon
		mcUp = new ImageView(this);
		mcUp.setImageResource((Integer) mcPlay.getTag());
		mcUp.setAdjustViewBounds(true);
		mcUp.setLayoutParams(mcPlay.getLayoutParams());
		//crew loop
		ArrayList<Crew> crew = mainShip.getCrew();
		int numCrew = crew.size();
		for (int i = 0; i < numCrew; i++) {
			crewUps.add(new ImageView(this));
			crewUps.get(i).setImageResource((Integer) crewPlays.get(i).getTag());
			crewUps.get(i).setAdjustViewBounds(true);
			crewUps.get(i).setLayoutParams(crewPlays.get(i).getLayoutParams());
		}
	}

}
