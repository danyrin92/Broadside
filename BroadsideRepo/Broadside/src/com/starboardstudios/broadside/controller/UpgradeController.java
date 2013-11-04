package com.starboardstudios.broadside.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import com.starboardstudios.broadside.R;
import com.starboardstudios.broadside.app.BroadsideApplication;
import com.starboardstudios.broadside.gameunits.Model;
import com.starboardstudios.broadside.gameunits.projectile.CannonBall;
import com.starboardstudios.broadside.gameunits.ships.MainShip;
import com.starboardstudios.broadside.gameunits.turrets.*;
import com.starboardstudios.broadside.interfaces.Draggable;

public class UpgradeController extends BaseController {

	private Model model;
	private MainShip mainShip;

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
		// TODO: spawn ship in correctly...

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

}
