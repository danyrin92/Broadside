package com.starboardstudios.broadside.controller;

import com.starboardstudios.broadside.R;

import android.app.Activity;
import android.media.MediaPlayer;

//Music done in this class
public abstract class BaseController extends Activity {

	/*Properties*/
    public String name="None";
    protected MediaPlayer mp;
    protected int theme;
    
    /*Methods*/
    public void playMainTheme() {
    	mp = MediaPlayer.create(this,R.raw.fighting_the_storm);
		//http://www.newgrounds.com/audio/listen/556463
        mp.start();
    }
    
    public void playTheme() {
    	mp = MediaPlayer.create(this,theme);
        mp.start();
    }
    
    public void playBattleMusic() {
    	int[] battleMusic = {R.raw.fighting_the_storm, 
    			R.raw.captain_blood_theme,
    			R.raw.freedom_of_the_seas,
    			R.raw.hes_a_pirate_rock}; 
    	int randomPick = rand(0, battleMusic.length-1);
    	mp = MediaPlayer.create(this,battleMusic[randomPick]);
    	mp.start();
    }
    
    public void wipeMP() {
    	mp.release();
    	mp = null;
    }
    
    public void pauseMusic() {
    	mp.pause();
    }
    
    public void resumeMusic() {
    	mp.start();
    }
    
    //randomly generate a number from min to max
 	public static int rand(int Min, int Max) {
 		return Min + (int) (Math.random() * ((Max - Min) + 1));
 	}
 	
 	public abstract void playMusic();
 	
 	/*List of songs and sources*/
 	//fighting_the_storm	http://www.newgrounds.com/audio/listen/556463
 	//captain_blood_theme	https://archive.org/details/CaptainBloodTrailer
 	//sid_meier_pirates		http://www.newgrounds.com/audio/listen/408846
 	//hes_a_pirate_rock		http://www.newgrounds.com/audio/listen/472610
 	//into_the_pirate_bay	http://www.newgrounds.com/audio/listen/412882
 	//freedom_of_the_seas	http://www.newgrounds.com/audio/listen/474398
 	
 	/*Considered*/
 	//acid_paradox_the_pi	http://www.newgrounds.com/audio/listen/515051
 	
 	
}
