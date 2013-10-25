package com.starboardstudios.broadside.gameunits.submarine;

import android.view.View;
import android.widget.ImageView;
import com.starboardstudios.broadside.gameunits.BaseUnit;
import com.starboardstudios.broadside.gameunits.Model;

import java.util.Random;

  public abstract class BaseSubmarine extends com.starboardstudios.broadside.gameunits.CombatUnit {
	  
	/** Random value for choosing  pathing track */
	Random rand = new Random();
	int random = rand.nextInt(2);
	int moveCount = 0;

	public BaseSubmarine(Model model) {
		super(model.context);
		this.model = model;

		imageView.setVisibility(View.INVISIBLE);
		imageView.setAdjustViewBounds(true);
	}

	

	@Override
	public ImageView getImage() {
		return imageView;
	}

	public void setVelocity(int xSpeed, int ySpeed) {
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
	}

    public void collide(BaseUnit unit)
    {
       x=0;
    }

   
}
