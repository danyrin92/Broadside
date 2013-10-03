package com.starboardstudios.broadside.gameunits.turrets;

import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.starboardstudios.broadside.R.drawable;
import com.starboardstudios.broadside.gameunits.Model;

public class MainCannon extends Turret {
	public ImageView imageView = new ImageView(context);
    private Model model;
    int x,y,z;
    
	public MainCannon(Model model) {
		super(model.context);
		this.model = model;
		x=0;y=0;z=0;
		 imageView.setImageResource(drawable.turret); //Set to image
	        imageView.setAdjustViewBounds(true);
	        imageView.setLayoutParams(new LinearLayout.LayoutParams(75,75)); //Set size
	        /*imageView.setOnDragListener(new View.OnDragListener() {
	            @Override
				public boolean onDrag(View v, DragEvent event) {
					if (event.getAction() == DragEvent.ACTION_DRAG_STARTED) {
						return true;
					} else if (event.getAction() == DragEvent.ACTION_DRAG_ENTERED) {
						v.setX(event.getX());
						v.setY(event.getY());
						return true;
					} else {
						return false;
					}
				}
	        });*/
	         System.out.println("Turret Created");
	    }

	    public void setPosition(int x, int y, int z)
	    {
	            this.x = x;
	            this.y = y;
	            this.z = z;
	        imageView.setX(x);
	        imageView.setY(y);
	        //imageView.setZ(z);

	    }
	    public void update()
	    {
	        model.runOnMain(new Runnable() {
	            @Override
	            public void run() {
	                imageView.setX(x);
	                imageView.setY(y);
	                imageView.setImageResource(drawable.turret);
	            }
	        });
	    }

    @Override
    public ImageView getImage() {
        return null;
    }
}
