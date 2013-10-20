package com.starboardstudios.broadside.gameunits.aircrafts;

import android.view.View;
import android.widget.LinearLayout;

import com.starboardstudios.broadside.R.drawable;
import com.starboardstudios.broadside.gameunits.Model;

public class BasicAircraft extends BaseAircraft {

	public BasicAircraft(Model model) {
		super(model);
		
		health = 10;
		
		imageView.setImageResource(drawable.tutorial_cloud);
		imageView.setLayoutParams(new LinearLayout.LayoutParams((int) (model
				.getScreenX() * .15), (int) (model.getScreenY() * .15)));

		imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				System.out.print("I shot");
                testFire();

			}
		});
		
		imageView.setVisibility(View.VISIBLE);		//BaseShip is set to invisible
	}

}
