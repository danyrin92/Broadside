package com.starboardstudios.broadside.controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.starboardstudios.broadside.R;
import com.starboardstudios.broadside.gameunits.Model;
import com.starboardstudios.broadside.gameunits.ships.BasicEnemyShip;
import com.starboardstudios.broadside.gameunits.ships.MainShip;

public class UpgradeController extends BaseController{

	//I need to pass in the model in order to add turrets to the ship... right?
	//Uh, how DO I pass in the model???...
	private Model model;
	
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.upgrade_view);
		
		name="UpgradeController";
		model = new Model(getBaseContext());
        model.setCurrentActivity(this);
       
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
	}
	
	public void nextLevel(View view){
		model.setLevel(model.getLevel()+1);
    	Intent playIntent = new Intent(this, PlayController.class);
		startActivity(playIntent);
    }
	
}
