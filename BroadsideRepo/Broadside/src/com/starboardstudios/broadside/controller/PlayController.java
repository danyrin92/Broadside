package com.starboardstudios.broadside.controller;

import com.starboardstudios.broadside.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class PlayController extends BaseController{

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.play_view);
		
	}
}
