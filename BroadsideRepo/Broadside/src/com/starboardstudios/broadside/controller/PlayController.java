package com.starboardstudios.broadside.controller;

import java.util.Random;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
//for getting the upgrades button to work...
import android.content.Intent;

import com.starboardstudios.broadside.R;
import com.starboardstudios.broadside.gameunits.Model;
import com.starboardstudios.broadside.gameunits.aircrafts.BaseAircraft;
import com.starboardstudios.broadside.gameunits.projectile.CannonBall;
import com.starboardstudios.broadside.gameunits.ships.BaseShip;
import com.starboardstudios.broadside.gameunits.ships.MainShip;
import com.starboardstudios.broadside.gameunits.submarine.BaseSubmarine;
import com.starboardstudios.broadside.gameunits.turrets.MainCannon;

public class PlayController extends BaseController{

	@SuppressLint("NewApi")

	private Model model;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.play_view);
		name="PlayController";
		model = new Model(getBaseContext());
        model.setCurrentActivity(this);
        //pass level property between activities
        int level= getIntent().getIntExtra("level", 1);
        model.setLevel(level);
        
        //Below is an example of how to add to the model without keylistener logic! Don't delete!
        model.addUnit(new MainShip(model));
       
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
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
