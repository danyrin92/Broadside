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
	private ImageView nameChangeButton;
	
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
	    nameChangeButton = (ImageView)findViewById(R.id.changeNameView);
	    /*
	    if(soundOn){
			soundButton.setImageResource(R.drawable.sound_on_cloud);
		}
		if(!soundOn)
		{
			soundButton.setImageResource(R.drawable.sound_off_cloud);
		}
		*/
	    
	    soundButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(soundOn == true){
					soundOn = false;
					soundButton.setImageResource(R.drawable.sound_off_cloud);
				}
				if(soundOn == false)
				{
					soundOn = true;
					soundButton.setImageResource(R.drawable.sound_on_cloud);
				}
				saveSoundOn();
			}
		});
	    
	    nameChangeButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
	}

	public void saveSoundOn(){
		((BroadsideApplication) this.getApplication()).soundOn = soundOn;
    }
	
	@Override
	public void playMusic() {
		//
	}
	
	
}
