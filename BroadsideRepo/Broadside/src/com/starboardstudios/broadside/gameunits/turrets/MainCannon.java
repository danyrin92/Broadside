package com.starboardstudios.broadside.gameunits.turrets;

import android.content.ClipData;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                        ClipData data = ClipData.newPlainText("", "");
                        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                        view.startDrag(data, shadowBuilder, view, 0);
                        view.setVisibility(View.INVISIBLE);
                        return true;
                    } else {
                        return false;
                    }
                }
            });
	        imageView.setOnDragListener(new View.OnDragListener() {
	            @Override
				public boolean onDrag(View v, DragEvent event) {
                    int action = event.getAction();
                    switch (event.getAction()) {
                        case DragEvent.ACTION_DRAG_STARTED:
                            System.out.println("Drag Started");
                            break;
                        case DragEvent.ACTION_DRAG_ENTERED:
                            System.out.println("Drag Entered");

                            break;
                        case DragEvent.ACTION_DRAG_EXITED:
                            System.out.println("Drag Exited");

                            break;
                        case DragEvent.ACTION_DROP:
                            // Dropped, reassign View to ViewGroup
                            View view = (View) event.getLocalState();
                            ViewGroup owner = (ViewGroup) view.getParent();
                            owner.removeView(view);
                            LinearLayout container = (LinearLayout) v;
                            container.addView(view);
                            view.setVisibility(View.VISIBLE);
                            System.out.println("Drag Going:"+ event.getX() + "  "+ event.getY());
                            imageView.setX(event.getX());
                            imageView.setY(event.getY());
                            break;
                        case DragEvent.ACTION_DRAG_ENDED:
                            System.out.println("Drag ended:"+ event.getX() + "  "+ event.getY());
                            imageView.setX(event.getX());
                            imageView.setY(event.getY());
                        default:
                            break;
                    }
                    return true;

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
