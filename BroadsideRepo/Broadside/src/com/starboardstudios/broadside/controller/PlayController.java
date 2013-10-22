package com.starboardstudios.broadside.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import com.starboardstudios.broadside.R;
import com.starboardstudios.broadside.app.BroadsideApplication;
import com.starboardstudios.broadside.gameunits.Model;
import com.starboardstudios.broadside.gameunits.aircrafts.EasyAircraft;
import com.starboardstudios.broadside.gameunits.projectile.CannonBall;
import com.starboardstudios.broadside.gameunits.ships.EasyShip;
import com.starboardstudios.broadside.gameunits.ships.MediumShip;
import com.starboardstudios.broadside.gameunits.ships.HardShip;
import com.starboardstudios.broadside.gameunits.ships.MainShip;
import com.starboardstudios.broadside.gameunits.submarine.EasySubmarine;
import com.starboardstudios.broadside.gameunits.turrets.MainCannon;
import java.util.Random;

public class PlayController extends BaseController {

	@SuppressLint("NewApi")
	public Model model;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final View screen = ((LayoutInflater) getBaseContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.play_view, null);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(screen);
		name = "PlayController";
		model = ((BroadsideApplication) this.getApplication()).getModel();
		model.setCurrentActivity(this);

		/** pass level property between activities */

		int level = getIntent().getIntExtra("level", 1);
		model.setLevel(level);
		System.out.print("Model Rendering");

		/**
		 * Below is an example of how to add to the model without keylistener
		 * logic! Don't delete!
		 */
		model.addUnit(new MainShip(model));

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

	/** for getting the upgrades button to work... */
	public void gotoUpgrades(View view) {
		Intent gotoUpgrades = new Intent(this, UpgradeController.class);
		gotoUpgrades.putExtra("level", model.getLevel());
		startActivity(gotoUpgrades);
	}

	public void init() {

	}

	/** For adding turret keylistener. */
	public void addTurret(View view) {
		model.addUnit(new MainCannon(model, new CannonBall(model, 20)));
	}

	/** Test method for spawning enemies */
	static int spawnnum = 0;

	public void spawnEnemies(View view) {

		switch (spawnnum) {
		case 0:
			EasyShip es = new EasyShip(model);
			es.setPosition(((int) (model.getScreenX()) + 75),
					((int) (model.getScreenY() * .4)));
			model.addUnit(es);
			break;
		case 1:
			MediumShip ms = new MediumShip(model);
			ms.setPosition(((int) (model.getScreenX()) + 75),
					((int) (model.getScreenY() * .4)));
			model.addUnit(ms);
			break;
		case 2:
			HardShip hs = new HardShip(model);
			hs.setPosition(((int) (model.getScreenX()) + 75),
					((int) (model.getScreenY() * .4)));
			model.addUnit(hs);
			break;
		case 3:
			EasySubmarine esub = new EasySubmarine(model);
			esub.setPosition(((int) (model.getScreenX()) + 75),
					((int) (model.getScreenY() * .4)));
			model.addUnit(esub);
			break;
		case 4:
			EasyAircraft ea = new EasyAircraft(model);
			ea.setPosition(((int) (model.getScreenX()) + 75),
					((int) (model.getScreenY() * .4)));
			model.addUnit(ea);
			break;

		}
		spawnnum++;
		if (spawnnum > 4)
			spawnnum = 0;

	}

}
