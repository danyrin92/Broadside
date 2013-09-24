package com.starboardstudios.broadside.gameunits.ships;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import com.starboardstudios.broadside.R.drawable;
public class TestShip extends Ship {

	private Drawable img ; 
	
	public TestShip(Context context)
	{
		super(context);
		img = context.getResources().getDrawable(drawable.testship);
			
	}
	
	
}
