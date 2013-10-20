package com.starboardstudios.broadside.gameunits.ships;

import com.starboardstudios.broadside.R.drawable;
import com.starboardstudios.broadside.gameunits.Model;

public class BasicShip extends BaseShip {

	public BasicShip(Model model) {
		super(model);
		
		health = 10;
		
		imageView.setImageResource(drawable.testship);
		imageView.setVisibility(imageView.VISIBLE);		//BaseShip is set to invisible
	}

}
