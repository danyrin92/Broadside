package com.starboardstudios.broadside.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.starboardstudios.broadside.R;

public class HomeController extends BaseController{
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		Intent intent = new Intent(Intent.ACTION_MAIN,null);
		intent.setPackage(getPackageName());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_view);
	}
	
	public void playGame(View view)
	{
		Intent playIntent = new Intent(this, PlayController.class);
		startActivity(playIntent);
	}
}