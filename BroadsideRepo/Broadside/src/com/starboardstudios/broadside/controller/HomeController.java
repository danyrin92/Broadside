package com.starboardstudios.broadside.controller;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import com.starboardstudios.broadside.R;
import com.starboardstudios.broadside.app.BroadsideApplication;
import com.starboardstudios.broadside.gameunits.Model;
import com.starboardstudios.broadside.interfaces.GifPlayer;

import java.io.*;

//COMMENT YO SHIT
public class HomeController extends BaseController {
	
	private Model model;
	final Context context = this;
	private EditText edittext;
	private boolean save = false;
	private String user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		Intent intent = new Intent(Intent.ACTION_MAIN,null);
		intent.setPackage(getPackageName());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_view);
		model = ((BroadsideApplication) this.getApplication()).getModel();
		model.setCurrentActivity(this);

        playMusic();
        
        /**LOAD PLAYER USER NAME**/
        FileInputStream fin;
		try {
			fin = openFileInput("username.bin");
			DataInputStream isr = new DataInputStream(fin);
			((BroadsideApplication) this.getApplication()).username = isr.readUTF();
			Toast.makeText(context, "Welcome Back " + ((BroadsideApplication) this.getApplication()).username, Toast.LENGTH_LONG).show();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			/** USER NAME DIALOG IMPLEMENTED HERE **/
			final Dialog usernameDialog = new Dialog(context,android.R.style.Theme_WallpaperSettings);
			usernameDialog.setContentView(R.layout.username_change);
			usernameDialog.setTitle("You have not chosen a User Name");
			
			Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/Pieces of Eight.ttf");
			TextView InstrView = (TextView)usernameDialog.findViewById(R.id.descView);
			InstrView.setTypeface(myTypeface);

			//Text Field where user enters username
			edittext = (EditText)usernameDialog.findViewById(R.id.editText1);

			//OK Button
			ImageView okButton = (ImageView) usernameDialog
					.findViewById(R.id.imageView1);
			okButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					usernameDialog.dismiss();
					/** SAVE PLAYER USER NAME **/
					try {
						DataOutputStream out = 
						new DataOutputStream(openFileOutput("username.bin", Context.MODE_PRIVATE));
						user = edittext.getText().toString();
						out.writeUTF(user);
						out.close();
						save = true;
						Toast.makeText(context, "Welcome " + user, Toast.LENGTH_LONG).show();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			usernameDialog.show();
			
			//Toast.makeText(context, "User Name Saved", Toast.LENGTH_LONG).show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
		}
	}
	
	public void playGame(View view)
	{
		if(save == true){
			((BroadsideApplication) this.getApplication()).username = user;
		}
		wipeMP();
		((BroadsideApplication) this.getApplication()).clearModel();
		Intent playIntent = new Intent(this, PlayController.class);
		startActivity(playIntent);
	}
	
	public void gotoLeaderboards(View view)
	{
		Intent leaderBoardIntent = new Intent(this, LeaderBoardController.class);
		startActivity(leaderBoardIntent);
	}
	
	public void loadGame(View view)
	{
		if(save == true){
			((BroadsideApplication) this.getApplication()).username = user;
		}
		((BroadsideApplication) this.getApplication()).load = true;
		((BroadsideApplication) this.getApplication()).clearModel();
		Intent playIntent = new Intent(this, PlayController.class);
		startActivity(playIntent);
	}
	
	public void gotoCredits(View view) {
		Intent playIntent = new Intent(this, CreditsController.class);
		startActivity(playIntent);
	}

	@Override
	public void playMusic() { //
		theme = R.raw.fighting_the_storm;
		playTheme();
	}
	
}
