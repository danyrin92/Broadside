package com.starboardstudios.broadside.gameunits.turrets;

import android.content.ClipData;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.starboardstudios.broadside.gameunits.projectile.Projectile;
import com.starboardstudios.broadside.R.drawable;
import com.starboardstudios.broadside.gameunits.BaseUnit;
import com.starboardstudios.broadside.gameunits.Model;

public class Turret2 extends Turret {
	public ImageView imageView = new ImageView(context);
    int x,y,z;
    Turret2 me;
    
	public Turret2(Model model, Projectile projectile) {
		super(model, projectile, 2);
		/*ARBITRARY VALUES*/
		this.fireSpeed = 20;
		this.cooldown = 120;
        me=this;
		x=35;y=35;z=0;
		    imageView.setImageResource(drawable.turret2); //Set to image
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
		public Turret2(Model model, Projectile projectile, float x, float y){
		super(model, projectile, x, y);
		/*ARBITRARY VALUES*/
		this.fireSpeed = 20;
		this.cooldown = 120;
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
		public Turret2(Model model, Projectile projectile, float x, float y, float fireSpeed) {
			super(model, projectile, x, y, fireSpeed);
			this.cooldown = 120;
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
	    	if (cooldown == 0) {
	    		cooldown = 120;
	    		fire();
	    	} else cooldown--;
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
	@Override
	public void dragStarted() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void midDrag(float x, float y) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void endDrag(float x, float y) {
		// TODO Auto-generated method stub
        this.setPosition((int)x,(int)y);
        this.update();
        this.imageView.setVisibility(View.VISIBLE);
		
	}
	@Override
	public void setImageView(ImageView image) {
		imageView = image;
	}
}
