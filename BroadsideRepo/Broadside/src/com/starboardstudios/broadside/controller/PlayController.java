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
import com.starboardstudios.broadside.gameunits.aircrafts.BaseAircraft;
import com.starboardstudios.broadside.gameunits.projectile.CannonBall;
import com.starboardstudios.broadside.gameunits.ships.BaseShip;
import com.starboardstudios.broadside.gameunits.ships.MainShip;
import com.starboardstudios.broadside.gameunits.submarine.BaseSubmarine;
import com.starboardstudios.broadside.gameunits.turrets.MainCannon;

import java.util.Random;

//for getting the upgrades button to work...

public class PlayController extends BaseController{

	@SuppressLint("NewApi")

	public Model model;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
       final View screen = ((LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.play_view,null);

		setContentView(screen);
		name="PlayController";
		model = ((BroadsideApplication)this.getApplication()).getModel();
        model.setCurrentActivity(this);
        //pass level property between activities
        int level= getIntent().getIntExtra("level", 1);
        model.setLevel(level);
        System.out.print("Model Rendering");
        //Below is an example of how to add to the model without keylistener logic! Don't delete!
        model.addUnit(new MainShip(model));
       System.out.println("adding ship2");
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {

            e.printStackTrace();
        }
        screen.setVisibility(View.VISIBLE);

        screen.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {

                System.out.println("Encountered Drag Event " + v.toString());
                if (event.getAction() == DragEvent.ACTION_DRAG_STARTED) {
                    System.out.println("Begin Drag ");
                    v.setBackgroundColor(Color.RED);
                    ((MainCannon) event.getLocalState()).getImage().setColorFilter(Color.RED);
                    ((MainCannon) event.getLocalState()).getImage().setVisibility(View.INVISIBLE);
                } else if (event.getAction() == DragEvent.ACTION_DRAG_ENTERED) {
                    System.out.println("Begin Drag 2 ");
                    v.setBackgroundColor(Color.GREEN);
                } else if (event.getAction() == DragEvent.ACTION_DROP) {
                   //TODO: Replace with correct superclass which can cast all draggable types, probably create a draggable interface.
                    ((MainCannon) event.getLocalState()).setPosition(((int) (event.getX()-screen.getX())), (int) (event.getY()-screen.getY()));
                    ((MainCannon) event.getLocalState()).getImage().clearColorFilter();
                    ((MainCannon) event.getLocalState()).getImage().setVisibility(View.VISIBLE);

                    System.out.println("Begin Drop ");
                    v.setBackgroundColor(Color.GREEN);
                    System.out.println("Location:" + event.getX() + "  " + event.getY());
                }
                v.invalidate();
                return true;

            }
        });
	}
	
	//for getting the upgrades button to work...
	public void gotoUpgrades(View view)
	{
		Intent gotoUpgrades = new Intent(this, UpgradeController.class);
		gotoUpgrades.putExtra("level", model.getLevel());
		startActivity(gotoUpgrades);
	}
	
	public void init(){
		
	}
	
    public void addShip(View view)
    {
        model.addUnit(new MainShip(model));
    }
    public void addTurret(View view)
    {
        model.addUnit(new MainCannon(model, new CannonBall(model, 20)));
    }
    public void loseHealth(View view) {
    	MainShip mainShip = model.getMainShip();
    	mainShip.setHealth(mainShip.getHealth() - 1);
    }
	
    public void spawnBasicEnemy(View view) { 
    	//for testing
    	
    	Random rand = new Random();
    	int random = rand.nextInt(3);
    	
    	switch (random) {
    		case 0: 
    			model.addUnit(new BaseShip(model));
    			break;
    		case 1:
    			model.addUnit(new BaseAircraft(model));
    			break;
    		case 2:
    			model.addUnit(new BaseSubmarine(model));
    			break;
    			
    	}
    	
    }
	
}
