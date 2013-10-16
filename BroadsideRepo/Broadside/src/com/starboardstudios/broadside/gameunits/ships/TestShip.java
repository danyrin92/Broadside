package com.starboardstudios.broadside.gameunits.ships;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.starboardstudios.broadside.R.drawable;
import com.starboardstudios.broadside.gameunits.BaseUnit;
import com.starboardstudios.broadside.gameunits.CombatUnit;
import com.starboardstudios.broadside.gameunits.Model;



//THIS CLASS IS OUTDATED AND PROBABLY WONT WORK
//USE IT'S SYNTAX FOR REFERENCE, BUT NOT FOR ACTUAL TESTING


public class TestShip extends CombatUnit {

    public ImageView imageView = new ImageView(context); //The image representing the ship
    private Model model; //the model
	private int x,y,z,xVelo,yVelo,zVelo; //position and speed
    boolean inPosition =false;


	public TestShip(Model model)
	{
        super(model.context); //Esthablish context 
        this.model=model;
		x=0;y=0;z=0; //Position

        imageView.setImageResource(drawable.testship); //Set to image
        imageView.setAdjustViewBounds(true);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(75,75)); //Set size
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 xVelo=xVelo*2;
            }
        });
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
        yVelo = yVelo*2;

                return false;
            }
        });
         System.out.println("Ship Created");
    }

    public void setVelocity(int xVelocity, int yVelocity, int zVelocity)
    {
            xVelo=xVelocity;
            yVelo=yVelocity;
            zVelo=zVelocity;
        imageView.setX(x);
        imageView.setY(y);

    }

    public void setVelocity(View view)
    {

    }
/**
 * called from the model each 'frame'. 
 */
    public void update()
    {

        if(!inPosition)
        {
            y=y-1;
            if(y>300)
            {
                inPosition=true;
            }


        }


        x=x+xVelo;
        y=y+yVelo;
        z=z+zVelo;
        if(x>300 || x < 0)
        {
            xVelo = -xVelo;
            yVelo = -yVelo;
        }
        model.runOnMain(new Runnable() {
            @Override
            public void run() {
                imageView.setX(x);
                imageView.setY(y);
                imageView.setImageResource(drawable.testship);
            }
        });



    }

    @Override
    public ImageView getImage() {
        return null;
    }

    @Override
    public void collide(BaseUnit collidedWith) {

    }


}
