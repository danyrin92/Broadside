package com.starboardstudios.broadside.controller;

import com.starboardstudios.broadside.R;
import com.starboardstudios.broadside.gameunits.Model;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeController extends Activity{
	
	//private Model model;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		Intent intent = new Intent(Intent.ACTION_MAIN,null);
		intent.setPackage(getPackageName());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_view);
		//model = new Model(getBaseContext());
		//model.setCurrentActivity(this);
	}
	
	public void playGame(View view)
	{
		Intent playIntent = new Intent(this, PlayController.class);
		startActivity(playIntent);
	}
}