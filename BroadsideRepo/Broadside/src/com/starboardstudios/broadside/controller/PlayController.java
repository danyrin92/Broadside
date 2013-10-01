package com.starboardstudios.broadside.controller;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.starboardstudios.broadside.R;
import com.starboardstudios.broadside.gameunits.Model;
import com.starboardstudios.broadside.gameunits.ships.MainShip;

public class PlayController extends BaseController{

	@SuppressLint("NewApi")

	private Model model;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.play_view);
		
		model = new Model(getBaseContext());
		
	}
	
	public void init(){
		
	}
	
	
}
