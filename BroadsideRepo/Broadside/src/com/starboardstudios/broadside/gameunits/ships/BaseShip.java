package com.starboardstudios.broadside.gameunits.ships;

import android.graphics.Color;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.starboardstudios.broadside.R.drawable;
import com.starboardstudios.broadside.gameunits.BaseUnit;
import com.starboardstudios.broadside.gameunits.CombatUnit;
import com.starboardstudios.broadside.gameunits.Model;
import com.starboardstudios.broadside.gameunits.projectile.CannonBall;
import com.starboardstudios.broadside.gameunits.turrets.MainCannon;

import java.util.Random;

  public abstract class BaseShip extends CombatUnit {
	//Top level of all types of Ships
	public ImageView imageView = new ImageView(context); // Image for ship
	//protected MainCannon mainCannon;
	//private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();    Projectiles are put in the MODEL, not owned by a ship... once a ship shoots something, why would the ship have any control over it?
	Random rand = new Random();
	int random = rand.nextInt(2);

	public BaseShip(Model model) {
		super(model.context);
		this.model = model;

		// Can't make image another file because it's not auto-generating the
		// address in R.java. What gives?
		// Using test for now
		imageView.setVisibility(imageView.INVISIBLE);
		imageView.setImageResource(drawable.error);
		imageView.setAdjustViewBounds(true);

		imageView.setLayoutParams(new LinearLayout.LayoutParams((int) (model
				.getScreenX() * .15), (int) (model.getScreenY() * .15)));

		imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				System.out.print("I shot");
                testFire();

			}
		});
		
		x = ((int) (model.getScreenX()) + 75);
		y = ((int) (model.getScreenY() * .4));

		// Below will be updated
		xSpeed = -(int) (model.getScreenX() * .003);
        imageView.setOnDragListener( new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {

                System.out.println("Encountered Drag Event");
                if(event.getAction()== DragEvent.ACTION_DRAG_STARTED)
                {
                    System.out.println("Begin Drag ");
                    v.setBackgroundColor(Color.BLUE);
                }
                else if(event.getAction()== DragEvent.ACTION_DRAG_ENTERED)
                {
                    System.out.println("Begin Drag 2 ");
                    v.setBackgroundColor(Color.GREEN);
                }
                else if(event.getAction()== DragEvent.ACTION_DROP)
                {
                    System.out.println("Begin Drop ");
                    v.setBackgroundColor(Color.RED);
                    System.out.println("Location:" + v.getX() +"  "+ v.getY() );
                }
                v.invalidate();
                return true;

            }
        });
	}

	public void update() {
		x = x + xSpeed;
		y = y + ySpeed;

		if (x < ((int) (model.getScreenX()) * .5)) {
			xSpeed = 0;
			if (random == 1)
				ySpeed = (int) (model.getScreenX() * .003);
			if (random == 0)
				ySpeed = -(int) (model.getScreenX() * .003);
		}
	    /**  Projectiles are not here.... in model, see above...
		    for(int i=0;i<projectiles.size();i++){
	    		projectiles.get(i).update();
	       	}
        */
		model.runOnMain(new Runnable() {
			public void run() {
				imageView.setX(x);
				imageView.setY(y);
				//imageView.setImageResource(drawable.testship);
			}

		});
	}

	@Override
	public ImageView getImage() {
		return imageView;
	}

	public void setVelocity(int xSpeed, int ySpeed) {
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		imageView.setX(x);
		imageView.setY(y);

	}

    public void collide(BaseUnit unit)
    {
       x=0;
    }

    //TESTING
    public static int xFireSpeed = 1;
    public static int yFireSpeed = 0;
    public static int zFireSpeed;
    public int z = 0;
    void testFire() {
		model.addUnit(new CannonBall(model, 20, x, y, z, xFireSpeed, yFireSpeed, zFireSpeed));
	}
}
