package com.starboardstudios.broadside.controller;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.starboardstudios.broadside.R;
import com.starboardstudios.broadside.gameunits.Model;

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
		
	}
	
}
