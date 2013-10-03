package com.starboardstudios.broadside.gameunits.projectile;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.starboardstudios.broadside.gameunits.Model;

public class Projectile extends ProjectileBase {
	public ImageView imageView = new ImageView(context);
    private Model model;
	private int x,y,z,xVelo,yVelo,zVelo;
	
	public Projectile(Model model) {
		super(model.context);
        this.model=model;
		x=0;y=0;z=0;

        //imageView.setImageResource(drawable.testprojectile); //Set to image
        imageView.setAdjustViewBounds(true);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(75,75)); //Set size
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 xVelo=xVelo*2;
            }
        });
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
        yVelo = yVelo*2;

                return false;
            }
        });
	}
	public int getDamage() {
		// TODO Auto-generated method stub
		return damage;
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

    @Override
    public void update() {

    }

    @Override
    public ImageView getImage() {
        return null;
    }
}
