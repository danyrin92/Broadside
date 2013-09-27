package com.starboardstudios.broadside.gameunits;

import android.content.Context;
<<<<<<< Updated upstream
import com.starboardstudios.broadside.controller.BaseController;
=======
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import com.starboardstudios.broadside.R;
import com.starboardstudios.broadside.controller.HomeController;
>>>>>>> Stashed changes
import com.starboardstudios.broadside.gameunits.ships.TestShip;

import java.util.ArrayList;

public class Model extends Thread {

    public Context context;
    private BaseController currentActivity;
    private ArrayList<TestShip> ships = new ArrayList<TestShip>();
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

    public void addShip(TestShip s)
    {
        ships.add(s);
        //inner.addView(s.imageView);
        System.out.println("Ship Added");

    }

    public void update()
    {
        if(currentActivity!=null)
        {
            for(int x=0; x<ships.size();x++)
            {
                ships.get(x).update();
           }

        }

    }
    public void setCurrentActivity(HomeController homeController)
    {
        this.currentActivity = homeController;
    }

    public void setCurrentActivity(BaseController a)
    {
        this.currentActivity=a;
    }

    public void runOnMain(Runnable x)
    {
        currentActivity.runOnUiThread(x);
    }
<<<<<<< Updated upstream

=======
	
>>>>>>> Stashed changes

}
