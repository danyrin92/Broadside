package com.starboardstudios.broadside.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.util.TypedValue;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.starboardstudios.broadside.R;
import com.starboardstudios.broadside.app.BroadsideApplication;
import com.starboardstudios.broadside.gameunits.Model;

public class LeaderBoardController extends BaseController {
	
	public Model model;
	TextView user0, user1, user2, user3, user4, 
			 user5, user6, user7, user8, user9;
	TextView level0, level1, level2, level3, level4, 
	 		 level5, level6, level7, level8, level9;
	TextView score0, score1, score2, score3, score4, 
	 		 score5, score6, score7, score8, score9;
	String[] users = new String[10];
	String[] levels = new String[10];
	String[] scores = new String[10];
	
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.leaderboard_view);
		StrictMode.enableDefaults(); //STRICT MODE ENABLED
		
		model = ((BroadsideApplication) this.getApplication()).getModel();
		model.setCurrentActivity(this);
		
		setTxtAttributes();
		getData();
		displayData();
	}
	
	public void getData(){
		String result = "";
    	InputStream isr = null;
    	try{
            HttpClient httpclient = new DefaultHttpClient();
            //address to the PHP script
            HttpPost httppost = new HttpPost("http://ec2-54-211-251-187.compute-1.amazonaws.com/LeaderBoard2.php"); 
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            isr = entity.getContent();
    	}
    	catch(Exception e){
    		Toast.makeText(getApplicationContext(), "Error in http connection "+e.toString(), Toast.LENGTH_LONG).show();
            Log.e("log_tag", "Error in http connection "+e.toString());
    	}
    	
    	//convert table from JSON to string
    	try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(isr,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
            }
            isr.close();
     
            result=sb.toString();
    	}
    	catch(Exception e){
    		Toast.makeText(getApplicationContext(), "Error  converting result "+e.toString(), Toast.LENGTH_LONG).show();
            Log.e("log_tag", "Error  converting result "+e.toString());
    	}
    	
    	//parse the table
    	try {
    		   JSONArray jArray = new JSONArray(result);
    		   
    		   for(int i=0; i<jArray.length() && i<10 ;i++){
    			   JSONObject json = jArray.getJSONObject(i);
    			   
    			   users[i] = json.getString("username");
    			   levels[i] = Integer.toString(json.getInt("userlevel"));
    			   scores[i] = Integer.toString(json.getInt("userscore"));
    		   }
    	 } 
    	 catch (Exception e) {
    		// TODO: handle exception
    		 Toast.makeText(getApplicationContext(), "Error Parsing Data "+e.toString(), Toast.LENGTH_LONG).show();
    		   Log.e("log_tag", "Error Parsing Data "+e.toString());
    	 }
    	    
    	    }
	
	public void displayData(){
		user0.setText(users[0]);
		user1.setText(users[1]);
		user2.setText(users[2]);
		user3.setText(users[3]);
		user4.setText(users[4]);
		user5.setText(users[5]);
		user6.setText(users[6]);
		user7.setText(users[7]);
		user8.setText(users[8]);
		user9.setText(users[9]);
		
		level0.setText(levels[0]);
		level1.setText(levels[1]);
		level2.setText(levels[2]);
		level3.setText(levels[3]);
		level4.setText(levels[4]);
		level5.setText(levels[5]);
		level6.setText(levels[6]);
		level7.setText(levels[7]);
		level8.setText(levels[8]);
		level9.setText(levels[9]);
		
		score0.setText(scores[0]);
		score1.setText(scores[1]);
		score2.setText(scores[2]);
		score3.setText(scores[3]);
		score4.setText(scores[4]);
		score5.setText(scores[5]);
		score6.setText(scores[6]);
		score7.setText(scores[7]);
		score8.setText(scores[8]);
		score9.setText(scores[9]);
	}
	
	public void setTxtAttributes(){
		Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/Pieces of Eight.ttf");
		int txtSize = (int) (model.getScreenY() * .02721);
		
		Toast.makeText(getApplicationContext(), Integer.toString(txtSize), Toast.LENGTH_LONG).show();
		user0 = (TextView) findViewById(R.id.user0);
		user0.setTypeface(myTypeface);
		user1 = (TextView) findViewById(R.id.user1);
		user1.setTypeface(myTypeface);
		user2 = (TextView) findViewById(R.id.user2);
		user2.setTypeface(myTypeface);
		user3 = (TextView) findViewById(R.id.user3);
		user3.setTypeface(myTypeface);
		user4 = (TextView) findViewById(R.id.user4);
		user4.setTypeface(myTypeface);
		user5 = (TextView) findViewById(R.id.user5);
		user5.setTypeface(myTypeface);
		user6 = (TextView) findViewById(R.id.user6);
		user6.setTypeface(myTypeface);
		user7 = (TextView) findViewById(R.id.user7);
		user7.setTypeface(myTypeface);
		user8 = (TextView) findViewById(R.id.user8);
		user8.setTypeface(myTypeface);
		user9 = (TextView) findViewById(R.id.user9);
		user9.setTypeface(myTypeface);
		user0.setTextSize(TypedValue.COMPLEX_UNIT_SP, txtSize);
		user1.setTextSize(TypedValue.COMPLEX_UNIT_SP, txtSize);
		user2.setTextSize(TypedValue.COMPLEX_UNIT_SP, txtSize);
		user3.setTextSize(TypedValue.COMPLEX_UNIT_SP, txtSize);
		user4.setTextSize(TypedValue.COMPLEX_UNIT_SP, txtSize);
		user5.setTextSize(TypedValue.COMPLEX_UNIT_SP, txtSize);
		user6.setTextSize(TypedValue.COMPLEX_UNIT_SP, txtSize);
		user7.setTextSize(TypedValue.COMPLEX_UNIT_SP, txtSize);
		user8.setTextSize(TypedValue.COMPLEX_UNIT_SP, txtSize);
		user9.setTextSize(TypedValue.COMPLEX_UNIT_SP, txtSize);
		
		level0 = (TextView) findViewById(R.id.level0);
		level0.setTypeface(myTypeface);
		level1 = (TextView) findViewById(R.id.level1);
		level1.setTypeface(myTypeface);
		level2 = (TextView) findViewById(R.id.level2);
		level2.setTypeface(myTypeface);
		level3 = (TextView) findViewById(R.id.level3);
		level3.setTypeface(myTypeface);
		level4 = (TextView) findViewById(R.id.level4);
		level4.setTypeface(myTypeface);
		level5 = (TextView) findViewById(R.id.level5);
		level5.setTypeface(myTypeface);
		level6 = (TextView) findViewById(R.id.level6);
		level6.setTypeface(myTypeface);
		level7 = (TextView) findViewById(R.id.level7);
		level7.setTypeface(myTypeface);
		level8 = (TextView) findViewById(R.id.level8);
		level8.setTypeface(myTypeface);
		level9 = (TextView) findViewById(R.id.level9);
		level9.setTypeface(myTypeface);
		level0.setTextSize(TypedValue.COMPLEX_UNIT_SP, txtSize);
		level1.setTextSize(TypedValue.COMPLEX_UNIT_SP, txtSize);
		level2.setTextSize(TypedValue.COMPLEX_UNIT_SP, txtSize);
		level3.setTextSize(TypedValue.COMPLEX_UNIT_SP, txtSize);
		level4.setTextSize(TypedValue.COMPLEX_UNIT_SP, txtSize);
		level5.setTextSize(TypedValue.COMPLEX_UNIT_SP, txtSize);
		level6.setTextSize(TypedValue.COMPLEX_UNIT_SP, txtSize);
		level7.setTextSize(TypedValue.COMPLEX_UNIT_SP, txtSize);
		level8.setTextSize(TypedValue.COMPLEX_UNIT_SP, txtSize);
		level9.setTextSize(TypedValue.COMPLEX_UNIT_SP, txtSize);
		
		score0 = (TextView) findViewById(R.id.score0);
		score0.setTypeface(myTypeface);
		score1 = (TextView) findViewById(R.id.score1);
		score1.setTypeface(myTypeface);
		score2 = (TextView) findViewById(R.id.score2);
		score2.setTypeface(myTypeface);
		score3 = (TextView) findViewById(R.id.score3);
		score3.setTypeface(myTypeface);
		score4 = (TextView) findViewById(R.id.score4);
		score4.setTypeface(myTypeface);
		score5 = (TextView) findViewById(R.id.score5);
		score5.setTypeface(myTypeface);
		score6 = (TextView) findViewById(R.id.score6);
		score6.setTypeface(myTypeface);
		score7 = (TextView) findViewById(R.id.score7);
		score7.setTypeface(myTypeface);
		score8 = (TextView) findViewById(R.id.score8);
		score8.setTypeface(myTypeface);
		score9 = (TextView) findViewById(R.id.score9);
		score9.setTypeface(myTypeface);
		score0.setTextSize(TypedValue.COMPLEX_UNIT_SP, txtSize);
		score1.setTextSize(TypedValue.COMPLEX_UNIT_SP, txtSize);
		score2.setTextSize(TypedValue.COMPLEX_UNIT_SP, txtSize);
		score3.setTextSize(TypedValue.COMPLEX_UNIT_SP, txtSize);
		score4.setTextSize(TypedValue.COMPLEX_UNIT_SP, txtSize);
		score5.setTextSize(TypedValue.COMPLEX_UNIT_SP, txtSize);
		score6.setTextSize(TypedValue.COMPLEX_UNIT_SP, txtSize);
		score7.setTextSize(TypedValue.COMPLEX_UNIT_SP, txtSize);
		score8.setTextSize(TypedValue.COMPLEX_UNIT_SP, txtSize);
		score9.setTextSize(TypedValue.COMPLEX_UNIT_SP, txtSize);
	}
	
	public void playMusic() {
		//
	}
}
