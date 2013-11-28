package com.starboardstudios.broadside.controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.graphics.Typeface;
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
	final Context context = this;
	View screen;
	private int numTurretsAdded = 0;

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		screen = ((LayoutInflater) getBaseContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.upgrade_view, null);
		setContentView(R.layout.upgrade_view);
		name = "UpgradeController";
		model = ((BroadsideApplication) this.getApplication()).getModel();
		model.setCurrentActivity(this);
		mainShip = model.getMainShip();
		
		Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/Pieces of Eight.ttf");
		TextView BootyTextView = (TextView)findViewById(R.id.BootyView);
		TextView LevelTextView = (TextView)findViewById(R.id.LevelView); 
		TextView manageTurretsTextView = (TextView)findViewById(R.id.textView2); //textView3
		TextView manageCrewTextView = (TextView)findViewById(R.id.textView3);
	    BootyTextView.setTypeface(myTypeface);
	    LevelTextView.setTypeface(myTypeface);
	    manageTurretsTextView.setTypeface(myTypeface);
	    manageCrewTextView.setTypeface(myTypeface);

		// ((BroadsideApplication) this.getApplication()).saveModel(context);
		((BroadsideApplication) this.getApplication()).load = false;

		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}

		RelativeLayout layout = (RelativeLayout) this
				.findViewById(R.id.upgrade);
		/** DRAG LISTENER */
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
					if (!((Draggable) event.getLocalState()).endDrag(event.getX(),
							event.getY())) { //invalid placement off mainship
						removeRefundLastTurret();
					}
				}
				v.invalidate();
				return true;

			}
		});
	}

	public void nextLevel(View view) {
		// ((BroadsideApplication) this.getApplication()).saveModel(context);
		Intent plaIntent = new Intent(this, PlayController.class);
		startActivity(plaIntent);
	}
	
	public void removeTurret(View view) {
		removeRefundLastTurret();
	}
	
	public void removeRefundLastTurret() {
		Turret turret = mainShip.getLastTurret();
		if (turret!=null && !(turret instanceof MainCannon)) {
			if (numTurretsAdded>0) {
				model.addBooty(turret.getCost());
			} else { //return half price rounded down if not bought this level
				model.addBooty(turret.getCost()/2);
			}
			model.removeUnit(turret);
		}
	}

	public void hire(View view) {
		int cost = 25;
		if (model.getBooty() >= cost) {
			Crew crew = new Crew(this, model);
			model.addUnit(crew);
			float offset = 0;
			if (mainShip.getCrew().size() > 0) {
				offset = (float) (.02 * (mainShip.getCrew().size() - 1));
			}
			float x = (float) (mainShip.getX() + ((model.getScreenX() * .345)));
			float y = (float) (mainShip.getY() + ((model.getScreenX() * (.3 - offset))));
			crew.setPosition(x, y);
			crew.update();
			model.spendBooty(cost);
		}
	}

	public void heave(View view) {
		ArrayList<Crew> crew = mainShip.getCrew();
		int numCrew = crew.size();
		if (numCrew > 0) {
			model.removeUnit(crew.get(numCrew - 1));
			model.addBooty(10);
			model.numCrew--;
		}
	}

	/** For implementing turret options */
	public void addTurret1(View view) {
		addTurret(1); // Turret1
	}

	public void addTurret2(View view) {
		addTurret(2); // Turret2
	}

	public void addTurret3(View view) {
		addTurret(3); // Turret3
	}

	public void addTurret4(View view) {
		addTurret(4); // Turret4
	}

	public void addTurret5(View view) {
		addTurret(5); // Turret5
	}

	public void addTurret6(View view) {
		addTurret(6); // Turret6
	}

	public void addTurret(int turretNum) {
		Turret turret = null;
		switch (turretNum) { // 1-6
		case 1:
			turret = new Cannon(model);
			break;
		case 2:
			turret = new Swivel(model);
			break;
		case 3:
			turret = new TorpedoLauncher(model);
			break;
		case 4:
			turret = new MineLauncher(model);
			break;
		case 5:
			turret = new MissileLauncher(model);
			break;
		case 6:
			turret = new LaserCannon(model);
			break;
		default:
			System.out.println("Turret not implemented yet!");
			break;
		}
		if (model.enoughBooty(turret)) {
			model.addUnit(turret);
			numTurretsAdded++;
		} else {
			model.addBooty(turret.getCost()); //refund if needed
		}
	}

}
