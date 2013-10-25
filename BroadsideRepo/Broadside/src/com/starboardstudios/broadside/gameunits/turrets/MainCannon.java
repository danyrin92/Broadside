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
    MainCannon me;
    Projectile shotType;
    
	public MainCannon(Model model, int x, int y) {
		super(model, x, y);
        me=this;
		this.x=x;this.y=y; this.cooldown = 10; this.currentCooldown = 0;
		shotType= new CannonBall(model);
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
	
		
		public void fireMainCannon(float xTarget, float yTarget){
			if (currentCooldown == 0){
				model.addUnit(shotType.create(model, x, y, xTarget, yTarget));
				currentCooldown=cooldown;
			}
		}
		
	    public void setPosition(int x, int y, int z)
	    {
	            this.x = x;
	            this.y = y;
	            this.z = z;

	        //imageView.setZ(z);

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
