package com.starboardstudios.broadside.gameunits.ships;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.starboardstudios.broadside.R.drawable;
import com.starboardstudios.broadside.gameunits.Crew;
import com.starboardstudios.broadside.gameunits.Model;
import com.starboardstudios.broadside.gameunits.projectile.Projectile;
import com.starboardstudios.broadside.gameunits.turrets.MainCannon;

import java.util.ArrayList;

public class MainShip extends Ship {

	public ImageView imageView = new ImageView(context); // Image for ship
	private Model model; // THe model for the program
	private ArrayList<Section> sections;
	private ArrayList<Crew> crew;
	private MainCannon mainCannon;
	private int waterLevel = 0;
	private int health = 100;
    private  boolean inPosition=false;

	public MainShip(Model model) {
		super(model.context);// Makes context

		this.model = model;
		y = 100;
        //setVelocity(5,10);
		imageView.setImageResource(drawable.mainship);
		imageView.setAdjustViewBounds(true);
		imageView.setLayoutParams(new LinearLayout.LayoutParams((int)(model.getScreenX()*.75), (int)(model.getScreenY()*.75))); // Set	// 75,75?
        x = (int) (model.getScreenX()*.5);
        System.out.print("hi");


        System.out.println("Graham was here");
        System.out.println("Main Ship Created");



	}

	void Damage(Projectile p) {
		// Dummy example
		health = health - p.getDamage();
	}

	public void setVelocity(int xSpeed, int ySpeed){
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		imageView.setX(x);
		imageView.setY(y);
		
	}
	
	public void setVelocity(View view){
		
	}
	
	public void update(){



        x = x + xSpeed;
		y = y + ySpeed;
		
		model.runOnMain(new Runnable(){
			public void run(){
				imageView.setX(x);
				imageView.setY(y);
				imageView.setImageResource(drawable.mainship);
				
			}
			
		});
		
	}

    @Override
    public ImageView getImage() {
        return imageView;
    }

    void FireMain(int x, int y) {
		int deltaX = x - this.x;
		// Below is swapped due to Y being inverted with our screen
		int deltaY = this.y - y;
		double degreeAngle = Math.atan((deltaY / deltaX)) * 180 / Math.PI;

		// Make new projectile firing in this direction.
	}

}
