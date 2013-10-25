package com.starboardstudios.broadside.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import com.starboardstudios.broadside.R;
import com.starboardstudios.broadside.app.BroadsideApplication;
import com.starboardstudios.broadside.gameunits.Model;
import com.starboardstudios.broadside.gameunits.projectile.CannonBall;
import com.starboardstudios.broadside.gameunits.ships.MainShip;
import com.starboardstudios.broadside.gameunits.turrets.*;

public class UpgradeController extends BaseController {

	private Model model;
	private MainShip mainShip;

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final View screen = ((LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.upgrade_view,null);
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
		screen.setVisibility(View.VISIBLE);
		screen.setOnDragListener(new View.OnDragListener() {

			@Override
			public boolean onDrag(View v, DragEvent event) {

				if (event.getAction() == DragEvent.ACTION_DRAG_STARTED) {
					System.out.println("Begin Drag ");
					v.setBackgroundColor(Color.RED);
					((MainCannon) event.getLocalState()).getImage()
							.setColorFilter(Color.RED);
					((MainCannon) event.getLocalState()).getImage()
							.setVisibility(View.INVISIBLE);
				} else if (event.getAction() == DragEvent.ACTION_DRAG_ENTERED) {
					System.out.println("Begin Drag 2 ");
					v.setBackgroundColor(Color.GREEN);
				} else if (event.getAction() == DragEvent.ACTION_DROP) {
					// TODO: Replace with correct superclass which can cast all
					// draggable types, probably create a draggable interface.
					int centerX = (((MainCannon) event.getLocalState())
							.getImage().getLeft() + ((MainCannon) event
							.getLocalState()).getImage().getRight()) / 2;
					int centerY = (((MainCannon) event.getLocalState())
							.getImage().getTop() + ((MainCannon) event
							.getLocalState()).getImage().getBottom()) / 2;

					// ((MainCannon) event.getLocalState()).setPosition(((int)
					// (event.getX()-screen.getX())), (int)
					// (event.getY()-screen.getY()));
					((MainCannon) event.getLocalState()).setPosition(
							((int) (event.getX() - centerX)),
							(int) (event.getY() - centerY));
					((MainCannon) event.getLocalState()).getImage()
							.clearColorFilter();
					((MainCannon) event.getLocalState()).getImage()
							.setVisibility(View.VISIBLE);

					System.out.println("Begin Drop ");
					v.setBackgroundColor(Color.GREEN);
					System.out.println("Location:" + event.getX() + "  "
							+ event.getY());
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

	/** For implementing turret options... Will de-derpify later. */
	public void addTurret1(View view) {
		System.out.println("addturret1 clicked");
		if (enoughBooty(1)) {
			model.addUnit(new Turret1(model, new CannonBall(model, 20)));
		}
	}

	public void addTurret2(View view) {
		System.out.println("addturret2 clicked");
		if (enoughBooty(2)) {
			model.addUnit(new Turret2(model, new CannonBall(model, 20)));
		}
	}

	public void addTurret3(View view) {
		System.out.println("addturret3 clicked");
		if (enoughBooty(3)) {
			model.addUnit(new Turret3(model, new CannonBall(model, 20)));
		}
	}

	public void addTurret4(View view) {
		System.out.println("addturret4 clicked");
		if (enoughBooty(4)) {
			model.addUnit(new Turret4(model, new CannonBall(model, 20)));
		}
	}

	public void addTurret5(View view) {
		System.out.println("addturret5 clicked");
		if (enoughBooty(5)) {
			model.addUnit(new Turret5(model, new CannonBall(model, 20)));
		}
	}

	public void addTurret6(View view) {
		System.out.println("addturret6 clicked");
		if (enoughBooty(6)) {
			model.addUnit(new Turret6(model, new CannonBall(model, 20)));
		}
	}
	
	public boolean enoughBooty(int turretNum) {
		if (model.getBooty()>=model.getTurretCostAt(turretNum)) {
			return true;
		}
		//TODO display "you don't have enough booty..."
		return false;
	}

}
