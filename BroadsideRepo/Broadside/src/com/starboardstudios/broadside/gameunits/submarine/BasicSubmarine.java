package com.starboardstudios.broadside.gameunits.submarine;

import android.view.View;
import android.widget.LinearLayout;

import com.starboardstudios.broadside.R.drawable;
import com.starboardstudios.broadside.gameunits.Model;

public class BasicSubmarine extends BaseSubmarine {

	public BasicSubmarine(Model model) {
		super(model);
		
		health = 10;
		
		imageView.setImageResource(drawable.enemyship);
		imageView.setLayoutParams(new LinearLayout.LayoutParams((int) (model
				.getScreenX() * .15), (int) (model.getScreenY() * .15)));
		imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				System.out.print("I shot");
                testFire();

			}
		});
		imageView.setVisibility(imageView.VISIBLE);		//BaseShip is set to invisible
	}

}
