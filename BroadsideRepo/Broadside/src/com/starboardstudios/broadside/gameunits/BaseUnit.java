package com.starboardstudios.broadside.gameunits;

import android.widget.ImageView;

/**
 * Created by Alex on 9/26/13.
 */
public abstract class BaseUnit {
	
	
	protected float x, y, z;


    /**
     * Updates the unit per the unit's own logic. Called by the model each "frame". Time should be adjusted for these frames.
     */
    public abstract void update();

    /**
     * Returns the image to be rendered by the model.
     * @return the image to be rendered by the model.
     */
    public abstract ImageView getImage();

    /**
     * This method is called whenever the model detects a collision between this object and another.
     * @param collidedWith The object this object collided with.
     */
    public abstract void collide(BaseUnit collidedWith);

    public abstract void setPosition(int x, int y);

}
