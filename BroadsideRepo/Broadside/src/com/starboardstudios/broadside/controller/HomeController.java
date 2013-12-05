package com.starboardstudios.broadside.controller;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.starboardstudios.broadside.R;
import com.starboardstudios.broadside.app.BroadsideApplication;
import com.starboardstudios.broadside.gameunits.Model;

//COMMENT YO SHIT
public class HomeController extends BaseController {
	
	private Model model;
	final Context context = this;
	
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
        
        /**load player's username**/
        FileInputStream fin;
		try {
			fin = openFileInput("username.bin");
			DataInputStream isr = new DataInputStream(fin);
			((BroadsideApplication) this.getApplication()).username = isr.readUTF();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(context, "Save Name", Toast.LENGTH_LONG).show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
		}
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
