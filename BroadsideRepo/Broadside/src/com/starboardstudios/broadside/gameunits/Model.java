package com.starboardstudios.broadside.gameunits;

import android.content.Context;

import com.starboardstudios.broadside.controller.HomeController;
import com.starboardstudios.broadside.gameunits.ships.MainShip;
import com.starboardstudios.broadside.gameunits.ships.TestShip;

public class Model extends Thread {

    public Context context;
    private HomeController currentActivity;
    private MainShip mainShip;
    public Model(Context context)
    {
        this.context=context;
        this.start();
    }

    @Override
    public void run()
    {
        System.out.println("Model Started ");
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


    public void update()
    {
        if(currentActivity!=null){
        	
        	System.out.println("We are in the update loop!");
          mainShip.update();

        }

    }
    public void setCurrentActivity(HomeController homeController)
    {
        this.currentActivity = homeController;
    }

    public void runOnMain(Runnable x)
    {
        currentActivity.runOnUiThread(x);
    }

}
