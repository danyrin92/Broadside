package com.starboardstudios.broadside.controller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.starboardstudios.broadside.R;
import com.starboardstudios.broadside.app.BroadsideApplication;
import com.starboardstudios.broadside.gameunits.Model;

public class OptionsController extends BaseController {
	
	public Model model;
	private ToggleButton speedToggle;
	private Button backButton;
	
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.options_view);
		
		model = ((BroadsideApplication) this.getApplication()).getModel();
		
		speedToggle = (ToggleButton) findViewById(R.id.speedToggle);
		backButton = (Button) findViewById(R.id.back);
		
		speedToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
				if(isChecked)
					model.setFPS(5);
				else
					model.setFPS(10);
				
			}
		});
		
		backButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		
	}

	@Override
	public void playMusic() {
		//
	}
	
	
}
