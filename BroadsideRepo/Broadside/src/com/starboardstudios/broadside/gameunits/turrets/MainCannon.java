package com.starboardstudios.broadside.gameunits.turrets;

import android.content.ClipData;
import android.view.MotionEvent;
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
		x=35;y=35;z=0;
		    imageView.setImageResource(drawable.main_cannon); //Set to image
	        imageView.setAdjustViewBounds(true);
	        imageView.setLayoutParams(new LinearLayout.LayoutParams(150,150)); //Set size
            imageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {

                    imageView.getParent().requestDisallowInterceptTouchEvent(true);
                       imageView.setX(motionEvent.getRawX());
                     if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                        if(view.getParent() !=null)
                        {
                            view.getParent().requestDisallowInterceptTouchEvent(true);
                        }

                        ClipData data = ClipData.newPlainText("", "");
                        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                        view.startDrag(data, shadowBuilder, view, 0);
                        view.setVisibility(View.INVISIBLE);

                        return false;
                    }
                    if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                    {
                        view.setVisibility(View.VISIBLE);
                        System.out.println("Lifted the finger");
                        return false;
                    }
                    else {
                        System.out.println("Size" + motionEvent.getX());
                        System.out.println(motionEvent.toString());
                        view.invalidate();
                        return false;
                    }
                }




            });


	         System.out.println("Turret Created");
	    }

	    public void setPosition(int x, int y, int z)
	    {
	            this.x = x;
	            this.y = y;
	            this.z = z;

	        //imageView.setZ(z);

	    }
	    public void update()
	    {
            // System.out.println("Updating Main Cannon");
	        model.runOnMain(new Runnable() {
	            @Override
	            public void run() {
	                imageView.setX(x);
	                imageView.setY(y);
	            }
	        });
	    }

    @Override
    public ImageView getImage() {
        return imageView;
    }
}
