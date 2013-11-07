package com.starboardstudios.broadside.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.LayoutInflater;

import com.starboardstudios.broadside.interfaces.Draggable;
import com.starboardstudios.broadside.util.LevelManager;
import com.starboardstudios.broadside.R;
import com.starboardstudios.broadside.R.drawable;
import com.starboardstudios.broadside.app.BroadsideApplication;
import com.starboardstudios.broadside.gameunits.Crew;
import com.starboardstudios.broadside.gameunits.Model;
import com.starboardstudios.broadside.gameunits.aircrafts.EasyAircraft;
import com.starboardstudios.broadside.gameunits.ships.EasyShip;
import com.starboardstudios.broadside.gameunits.ships.HardShip;
import com.starboardstudios.broadside.gameunits.ships.MainShip;
import com.starboardstudios.broadside.gameunits.ships.MediumShip;
import com.starboardstudios.broadside.gameunits.submarine.EasySubmarine;
import android.app.AlertDialog;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.app.Dialog;
import android.widget.ImageView;
import android.widget.TextView;

public class PlayController extends BaseController {

	@SuppressLint("NewApi")
	public Model model;

	final Context context = this;
	private Button pauseButton;

	private View activityScreen;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("it gets this far... 1");
		final View screen = ((LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.play_view, null);
        this.activityScreen = screen;
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(screen);
		
		System.out.println("it gets this far... 2");
		
		pauseButton = (Button) findViewById(R.id.buttonAlert);
		
		//Listener for pauseButton
		pauseButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				
				
				// custom dialog
				final Dialog pauseDialog = new Dialog(context);
				pauseDialog.setContentView(R.layout.pause_dialog);
				pauseDialog.setTitle("Title...");
	 
				// set the custom dialog components - text, image and button
				TextView text = (TextView) pauseDialog.findViewById(R.id.text);
				text.setText("Android custom dialog example!");
				ImageView image = (ImageView) pauseDialog.findViewById(R.id.image);
				image.setImageResource(R.drawable.turret);
	 
				Button dialogButton = (Button) pauseDialog.findViewById(R.id.dialogButtonOK);
				// if button is clicked, close the custom dialog
				dialogButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						pauseDialog.dismiss();
					}
				});
	 
				pauseDialog.show();
				
				
				
				/*
				model.setPaused(true);
				AlertDialog.Builder alertDialogBuilder = new 
						AlertDialog.Builder(context, 1);
	 
				// the Broadside title
				alertDialogBuilder.setTitle("Broadside");
	 
				// the Game Paused message
				alertDialogBuilder
					.setIcon(drawable.turret)
					.setMessage("Game Paused")
					.setCancelable(false)
					
					.setPositiveButton("Main Menu",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							// returns the user to
							// the home view
							PlayController.this.finish();
						}
					  })
					 .setNeutralButton("Restart Level",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							// restarts the level
							PlayController.this.finish();
						}
					  })
					.setNegativeButton("Resume Game",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							// closes the pause dialog
							// and resumes game
							model.setPaused(false);
							dialog.cancel();
						}
					});
	 
					// creates alert dialog
					AlertDialog alertDialog = alertDialogBuilder.create();
	 
					// actually shows the dialog
					alertDialog.show();
				*/
			}
			});
		
		name = "PlayController";
		model = ((BroadsideApplication) this.getApplication()).getModel();
		model.setCurrentActivity(this);
		LevelManager.startLevel(model);

		System.out.print("Model Rendering");

		/**
		 * Below is an example of how to add to the model without keylistener
		 * logic! Don't delete!
		 */
        if(model.getLevel()==1)
        {
		    model.addUnit(new MainShip(model));
            model.addUnit(model.getMainShip().getMainCannon());
            //TODO finish testing crew
            model.getMainShip().addCrew(new Crew(context, model));
            model.addUnit(model.getMainShip().getCrew().get(0));
            
        }
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		screen.setVisibility(View.VISIBLE);

		screen.setOnDragListener(new View.OnDragListener() {

			@Override
			public boolean onDrag(View v, DragEvent event) {

                ((Draggable) event.getLocalState()).midDrag(event.getX(),event.getY());


                if (event.getAction() == DragEvent.ACTION_DRAG_STARTED) {

					((Draggable) event.getLocalState()).dragStarted();

				} else if (event.getAction() == DragEvent.ACTION_DRAG_ENTERED) {

				} else if (event.getAction() == DragEvent.ACTION_DROP) {

                    ((Draggable) event.getLocalState()).endDrag(event.getX(), event.getY());
                    System.out.println("drop registered");



                }
				return true;

			}
		});
        /**FIRING LISTENER*/
        screen.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				model.getMainShip().getMainCannon().fire(event.getX(), event.getY());
				return true;
			}
		});  
        
        
	}
	
	/** for getting the upgrades button to work... */
	public void gotoUpgrades(View view) {
		Intent gotoUpgrades = new Intent(this, com.starboardstudios.broadside.controller.UpgradeController.class);
		startActivity(gotoUpgrades);
	}
	
	public void gotoUpgrades() {
		Intent gotoUpgrades = new Intent(this, com.starboardstudios.broadside.controller.UpgradeController.class);
		startActivity(gotoUpgrades);
	}

	public void init() {

	}

	/** For adding turret keylistener. */
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
