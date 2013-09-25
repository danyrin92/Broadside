package com.starboardstudios.broadside.gameunits;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import com.starboardstudios.broadside.R;
import com.starboardstudios.broadside.controller.TestController;
import com.starboardstudios.broadside.gameunits.ships.TestShip;

import java.util.ArrayList;

public class Model extends Thread {

    public Context context;
    private TestController currentActivity;
    private ArrayList<TestShip> ships = new ArrayList<TestShip>();
    private FrameLayout inner;
    public Model(Context context)
    {
        this.context=context;
        this.start();

    }


    @Override
    public void run()
    {
        System.out.println("Model Started ");


        LayoutInflater inf = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View newView = inf.inflate(R.layout.test_controller_view,null);
        newView.setLayoutParams(new FrameLayout.LayoutParams(500,500));
         inner = (FrameLayout)newView.findViewById(R.id.inner_frame);
        currentActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                currentActivity.setContentView(newView, newView.getLayoutParams());

            }
        });

        while(true)
       {

           update();


           try {
               Thread.sleep(10);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }


       }

    }


    public void addShip(TestShip s)
    {
        ships.add(s);
        inner.addView(s.imageView);
        System.out.println("Ship Added");

    }

    public void update()
    {
        if(currentActivity!=null){





            for(int x=0; x<ships.size();x++)
            {
                ships.get(x).update();


           }



        }

    }
    public void setCurrentActivity(TestController a)
    {
        this.currentActivity=a;
    }

    public void runOnMain(Runnable x)
    {
        currentActivity.runOnUiThread(x);
    }
	

}
