package com.starboardstudios.broadside.gameunits.turrets;


import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.starboardstudios.broadside.R.drawable;
import com.starboardstudios.broadside.gameunits.BaseUnit;
import com.starboardstudios.broadside.gameunits.Model;
import com.starboardstudios.broadside.gameunits.projectile.CannonBall;
import com.starboardstudios.broadside.gameunits.projectile.Projectile;

public class MainCannon extends Turret {
	public ImageView imageView = new ImageView(context);
	int cooldown, currentCooldown;
	float angle; //90 to -90
    MainCannon me;
    Projectile cannonBall;
    
	public MainCannon(Model model, float x, float y) {
		super(model, x, y);
        me=this;
		this.x=x;this.y=y; this.cooldown = 10; this.currentCooldown = 0;
		cannonBall = new CannonBall(model);
		fireSpeed = 1;
		    imageView.setImageResource(drawable.main_cannon); //Set to image
	        imageView.setAdjustViewBounds(true);
	        imageView.setLayoutParams(new LinearLayout.LayoutParams(150,150)); //Set size
          
	        //TODO: move over to draggable
	        /** imageView.setOnTouchListener(new View.OnTouchListener() {
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
            });  */
	         System.out.println("Turret Created");
	    }
	
		
		public void fire(float xTarget, float yTarget){
			//if (currentCooldown == 0){ why am I broken
				float yDifference = yTarget - this.y;
				float xDifference = xTarget - this.x;
				double angle = Math.atan(yDifference/xDifference);
				float ySpeed = (float) Math.sin(angle)*fireSpeed;
				float xSpeed = (float) Math.cos(angle)*fireSpeed;
				model.addUnit(cannonBall.create(model, this.x, this.y, xSpeed, ySpeed));
				//System.out.println("angle " + angle + " xspeed  " + (float)xSpeed +","+ (int)xSpeed+" yspeed "+(float)ySpeed+","+(int)ySpeed);
				//currentCooldown=cooldown;
			//}
		}
		
	    public void setPosition(float x, float y)
	    {
	            this.x = x;
	            this.y = y;
	    }
	    
	    @Override
    public void setPosition(int x, int y) {
        this.x=x;
        this.y=y;
    }
	    
	    public void update()
	    {
            // System.out.println("Updating Main Cannon");
	        model.runOnMain(new Runnable() {
	            @Override
	            public void run() {
	                imageView.setX(x);
	                imageView.setY(y);
	    			imageView.setImageResource(drawable.main_cannon);
	            }
	        });
	        currentCooldown--;
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
    public void dragStarted() {

    }

    @Override
    public void midDrag(float x, float y) {

    }

    @Override
    public void endDrag(float x, float y) {


        int centerX = (getImage().getLeft() + getImage().getRight()) / 2;
        int centerY = (getImage().getTop() + getImage().getBottom()) / 2;
        setPosition((int)(x-centerX),(int)(y-centerY));
        getImage().setVisibility(View.VISIBLE);

    }


}
