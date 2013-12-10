package com.starboardstudios.broadside.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.starboardstudios.broadside.R;

public class LeaderBoardController extends BaseController {
	
	private String url = "http://broadsideserver.rileinc.com/LeaderBoard2.php";
	TextView leaderBoardView;
	
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.leaderboard_view);
		StrictMode.enableDefaults(); //STRICT MODE ENABLED
		leaderBoardView = (TextView) findViewById(R.id.leaderboardView);
		
		getData();
	}
	
	public void getData(){
		String result = "";
    	InputStream isr = null;
    	try{
            HttpClient httpclient = new DefaultHttpClient();
            //address to the PHP script
            HttpPost httppost = new HttpPost(url); 
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            isr = entity.getContent();
    	}
    	catch(Exception e){
    		Toast.makeText(getApplicationContext(), "Error in http connection "+e.toString(), Toast.LENGTH_LONG).show();
            Log.e("log_tag", "Error in http connection "+e.toString());
            leaderBoardView.setText("Couldnt connect to database");
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
    		   String s = "";
    		   JSONArray jArray = new JSONArray(result);
    		   
    		   for(int i=0; i<jArray.length();i++){
    			   JSONObject json = jArray.getJSONObject(i);
    			   s = s + 
    					   "User Name : "+json.getString("username")+"\n"+
    					   "User Level : "+json.getInt("userlevel")+"\n"+
    					   "User Score : "+json.getInt("userscore")+"\n\n";
    		   }
    		   
    		   leaderBoardView.setText(s);
    		
    	 } catch (Exception e) {
    		// TODO: handle exception
    		 Toast.makeText(getApplicationContext(), "Error Parsing Data "+e.toString(), Toast.LENGTH_LONG).show();
    		   Log.e("log_tag", "Error Parsing Data "+e.toString());
    	 }
    	    
    	    }
	
	public void playMusic() {
		//
	}
}
