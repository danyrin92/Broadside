package com.starboardstudios.broadside.gameunits;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.starboardstudios.broadside.R;
import com.starboardstudios.broadside.gameunits.ships.Section;
import com.starboardstudios.broadside.interfaces.GifPlayer;

/**
 * Created by Alex on 12/13/13.
 */
public class Explosion extends  BaseUnit {

    // properties
    public GifPlayer fireImage;
    private Context context;
    private Model model;
    private Section sect;

    // constructors
    public Explosion(Model model) {
        this.context = model.context;
        this.model = model;
        // create image
        fireImage = new GifPlayer(context, R.drawable.explosion, false);
        float size = (float) .1;
        fireImage.setLayoutParams(new LinearLayout.LayoutParams((int) (model
                .getScreenX() * size), (int) (model.getScreenY() * size)));
        fireImage.setVisibility(View.VISIBLE);
        // initialize other stuff
        x = y = 0;
    }




    // methods
    public void update() {
        // System.out.println("Fire updating...");

        model.runOnMain(new Runnable() {
            public void run() {
                fireImage.setX(x);
                fireImage.setY(y);
            }
        });
    }


    public ImageView getImage() {
        return null;
    }

    //required...
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //USED
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;

    }

    public void setImageView(ImageView image) {

    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }



    @Override
    public void collide(BaseUnit collidedWith) {
        // TODO Auto-generated method stub

    }
}
