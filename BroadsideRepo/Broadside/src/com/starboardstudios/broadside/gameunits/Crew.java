package com.starboardstudios.broadside.gameunits;

import com.starboardstudios.broadside.R.drawable;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * @author comcc_000, 11/5
This is just a first draft of crew. Classes affected are crew, mainship, model, and playcontroller.
Currently a crew member is spawned on the first level by the play controller and displayed to the screen.
//TODO Have crew influence health. (depends on health algorithm)
//TODO Have crew move. 
//TODO Have crew rotate.
 */

public class Crew extends BaseUnit {
	//properties
	private ImageView imageView;
	private float x, y;
	private int xSpeed;
	private int ySpeed;
	private Context context;
	private Model model;
	
	//constructors
	public Crew(Context c, Model model) {
		this.context = c;
		this.model = model;
		//create image
		imageView = new ImageView(context);
		imageView.setAdjustViewBounds(true);
		imageView.setImageResource(drawable.crew_member);
		imageView.setLayoutParams(new LinearLayout.LayoutParams((int) (model
				.getScreenX() * .10), (int) (model.getScreenY() * .10)));
		imageView.setVisibility(View.VISIBLE);
		//place image
		x = 0;
		y = 0;
		xSpeed = 0;
		ySpeed = 0;
		
	}
	
	//methods
	public void update() {
		// TODO work on moving crew members

		x = x + xSpeed;
		y = y + ySpeed;

		model.runOnMain(new Runnable() {
			public void run() {
				imageView.setX(x);
				imageView.setY(y);
				imageView.setImageResource(drawable.crew_member);
				//System.out.println(" x " + x + " y " + y);
			}
		});
	}

	@Override
	public ImageView getImage() {
		return imageView;
	}

	@Override
	public void collide(BaseUnit collidedWith) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void setImageView(ImageView image) {
		imageView = image;
	}
	
}
