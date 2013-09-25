package com.starboardstudios.broadside.gameunits.ships;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.starboardstudios.broadside.R.drawable;
import com.starboardstudios.broadside.gameunits.Model;

public class TestShip extends Ship {

    public ImageView imageView = new ImageView(context);
    private Model model;
	private int x,y,z,xVelo,yVelo,zVelo;

	public TestShip(Model model)
	{
        super(model.context);
        this.model=model;
		x=0;y=0;z=0;
        imageView.setImageResource(drawable.testship);
        imageView.setAdjustViewBounds(true);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(75,75));
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

    public void update()
    {


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


	
	
}
