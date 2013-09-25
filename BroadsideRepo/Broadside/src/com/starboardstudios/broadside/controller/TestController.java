package com.starboardstudios.broadside.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.starboardstudios.broadside.R;
import com.starboardstudios.broadside.gameunits.Model;
import com.starboardstudios.broadside.gameunits.ships.TestShip;

public class TestController extends Activity {



    private Model model;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
	
		Intent intent = new Intent(Intent.ACTION_MAIN,null);
		intent.setPackage(getPackageName());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_controller_view);
		model = new Model(getBaseContext());
        model.setCurrentActivity(this);



    }


    public void init()
    {




    }


    public void runTask(Runnable runnable)
    {
        runnable.run();
    }



    public void addShip(View view)
    {
        System.out.println("Ship Requested");
       // EditText xvel = (EditText)view.findViewById(R.id.xvel);
        TestShip x = new TestShip(model);
       // x.setVelocity(Integer.parseInt(xvel.getText().toString()),1,0);
        x.setVelocity(1,1,0);
        model.addShip(x);

    }
	
	
}
