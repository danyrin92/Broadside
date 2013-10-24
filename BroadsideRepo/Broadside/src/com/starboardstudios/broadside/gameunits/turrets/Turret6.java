package com.starboardstudios.broadside.gameunits.turrets;

import android.content.ClipData;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.starboardstudios.broadside.R.drawable;
import com.starboardstudios.broadside.gameunits.BaseUnit;
import com.starboardstudios.broadside.gameunits.Model;
import com.starboardstudios.broadside.gameunits.projectile.Projectile;

public class Turret6 extends Turret {
	public ImageView imageView = new ImageView(context);
    int x,y,z;
    Turret6 me;
    
	public Turret6(Model model, Projectile projectile) {
		super(model, projectile, 6);
		/*ARBITRARY VALUES*/
		this.xFireSpeed = 20;
		this.yFireSpeed = 0;
		this.zFireSpeed = 0;
        me=this;
		x=35;y=35;z=0;
		    imageView.setImageResource(drawable.turret6); //Set to image
	        imageView.setAdjustViewBounds(true);
	        imageView.setLayoutParams(new LinearLayout.LayoutParams(150,150)); //Set size
           imageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                	imageView.getParent().requestDisallowInterceptTouchEvent(true);

                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                       System.out.println(view.toString());

                       ClipData data = ClipData.newPlainText("", "");
                       View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(imageView);
                       view.startDrag(data, shadowBuilder, me , 0);
                       view.setVisibility(View.INVISIBLE);


                       return true;
                   }
                    return false;
                }
            });




	         System.out.println("Turret1 is Created");
	    }
		public Turret6(Model model, Projectile projectile, int x, int y, int z) {
		super(model, projectile, x, y, z);
		/*ARBITRARY VALUES*/
		this.xFireSpeed = 20;
		this.yFireSpeed = 0;
		this.zFireSpeed = 0;
        me=this;
		    imageView.setImageResource(drawable.main_cannon); //Set to image
	        imageView.setAdjustViewBounds(true);
	        imageView.setLayoutParams(new LinearLayout.LayoutParams(150,150)); //Set size
           imageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                	imageView.getParent().requestDisallowInterceptTouchEvent(true);

                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                       System.out.println(view.toString());

                       ClipData data = ClipData.newPlainText("", "");
                       View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(imageView);
                       view.startDrag(data, shadowBuilder, me , 0);
                       view.setVisibility(View.INVISIBLE);


                       return true;
                   }
                    return false;
                }
            });




	         System.out.println("Turret1 Created");
	    }
		public Turret6(Model model, Projectile projectile, int x, int y, int z, int xFireSpeed, int yFireSpeed, int zFireSpeed) {
			super(model, projectile, x, y, z, xFireSpeed, yFireSpeed, zFireSpeed);
	        me=this;
			    imageView.setImageResource(drawable.main_cannon); //Set to image
		        imageView.setAdjustViewBounds(true);
		        imageView.setLayoutParams(new LinearLayout.LayoutParams(150,150)); //Set size
	           imageView.setOnTouchListener(new View.OnTouchListener() {
	                @Override
	                public boolean onTouch(View view, MotionEvent motionEvent) {
	                	imageView.getParent().requestDisallowInterceptTouchEvent(true);

	                     if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
	                        System.out.println(view.toString());

	                        ClipData data = ClipData.newPlainText("", "");
	                        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(imageView);
	                        view.startDrag(data, shadowBuilder, me , 0);
	                        view.setVisibility(View.INVISIBLE);


	                        return true;
	                    }
	                    return false;
	                }
	            });




		         System.out.println("Turret1 Created");
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

    @Override
    public void collide(BaseUnit collidedWith) {
         //TODO: Turrent available drop.
    }

    @Override
    public void setPosition(int x, int y) {
        this.x=x;
        this.y=y;
    }
}
