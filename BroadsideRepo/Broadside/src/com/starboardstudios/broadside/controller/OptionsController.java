package com.starboardstudios.broadside.controller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.starboardstudios.broadside.R;
import com.starboardstudios.broadside.app.BroadsideApplication;
import com.starboardstudios.broadside.gameunits.Model;

public class OptionsController extends BaseController {
	
	public Model model;
	private EditText editText;
	private String cheat;
	private boolean soundOn;
	private ImageView soundButton;
	private ImageView TwoXSpeedButton;
	
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.options_view);
		model = ((BroadsideApplication) this.getApplication()).getModel();
		
		Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/Pieces of Eight.ttf");
		TextView textView = (TextView)findViewById(R.id.descView);
	    textView.setTypeface(myTypeface);
	    
	    //soundOn = ((BroadsideApplication) this.getApplication()).soundOn;
	    editText = (EditText)findViewById(R.id.editText1);
	    //cheat = editText.getText().toString();
	    
	    soundButton = (ImageView)findViewById(R.id.soundView);
	    TwoXSpeedButton = (ImageView)findViewById(R.id.changeNameView);
	    
	    soundButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				wipeMP();
			}
		});
	    
	    TwoXSpeedButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
	}

	@Override
	public void playMusic() {
		//
	}
	
	
}
