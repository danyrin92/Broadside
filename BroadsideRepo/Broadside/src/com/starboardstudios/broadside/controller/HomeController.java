package com.starboardstudios.broadside.controller;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import com.starboardstudios.broadside.R;
import com.starboardstudios.broadside.app.BroadsideApplication;
import com.starboardstudios.broadside.gameunits.Model;

//COMMENT YO SHIT
public class HomeController extends BaseController {
	
	private Model model;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		Intent intent = new Intent(Intent.ACTION_MAIN,null);
		intent.setPackage(getPackageName());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_view);
		model = ((BroadsideApplication) this.getApplication()).getModel();
		model.setCurrentActivity(this);
		
		/**play music*/
		MediaPlayer mp = MediaPlayer.create(this,R.raw.fighting_the_storm);
		//http://www.newgrounds.com/audio/listen/556463
        mp.start();
	}
	
	public void playGame(View view)
	{
		((BroadsideApplication) this.getApplication()).clearModel();
		Intent playIntent = new Intent(this, PlayController.class);
		startActivity(playIntent);
	}
	
	public void loadGame(View view)
	{
		((BroadsideApplication) this.getApplication()).load = true;
		((BroadsideApplication) this.getApplication()).clearModel();
		Intent playIntent = new Intent(this, PlayController.class);
		startActivity(playIntent);
	}
}
