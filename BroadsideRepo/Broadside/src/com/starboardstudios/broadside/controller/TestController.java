package com.starboardstudios.broadside.controller;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import com.starboardstudios.broadside.R;
import com.starboardstudios.broadside.gameunits.Model;
import com.starboardstudios.broadside.gameunits.ships.TestShip;

/**
 * A basic controller to test MVC functionality
 */
public class TestController extends Activity {


    /**
     * The global model
     */
    private Model model;

    /**
     * Called when the class is instantiated
     */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
	
<<<<<<< Updated upstream
	//	Intent intent = new Intent(Intent.ACTION_MAIN,null);
	//	intent.setPackage(getPackageName());
	//	super.onCreate(savedInstanceState);
		setContentView(R.layout.test_controller_view);
		model = new Model(getBaseContext());
=======
		//Intent intent = new Intent(Intent.ACTION_MAIN,null);
		//intent.setPackage(getPackageName());
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.test_controller_view);
		//model = new Model(getBaseContext());
		//model.setCurrentActivity(this);
>>>>>>> Stashed changes



    }


    public void init()
    {




    }




    /**
     * Is run when the user clicks on add ship
     * @param view
     */
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
