package com.starboardstudios.broadside.controller;

import com.starboardstudios.broadside.R;
import com.starboardstudios.broadside.gameunits.Model;
import android.content.Intent;
import android.os.Bundle;

public class HomeController extends BaseController{
	
	private Model model;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		setContentView(R.layout.homecontroller);
		model = new Model(getBaseContext());
		model.setCurrentActivity(this);
	}
	
    public void runTask(Runnable runnable)
    {
        runnable.run();
    }
}
