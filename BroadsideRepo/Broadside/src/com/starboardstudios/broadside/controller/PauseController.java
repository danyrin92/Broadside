package com.starboardstudios.broadside.controller;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.starboardstudios.broadside.R;

public class PauseController extends BaseController{
	
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pause_view);
		
	}
}
