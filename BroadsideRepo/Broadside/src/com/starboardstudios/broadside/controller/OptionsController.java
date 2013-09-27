package com.starboardstudios.broadside.controller;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.starboardstudios.broadside.R;

public class OptionsController extends BaseController{
	
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.options_view);
		
	}
}
