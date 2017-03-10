package com.goteam.wtbuffteks.goteam;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * Created by christophercoffee on 10/17/16.
 */

public class GoTeamFrag {
    //only used to replace already existing fragment
    public void launchFragWithName(Activity activity, String fragClassName, Bundle bundleArgs)
    {

        FragmentActivity mycontext = (FragmentActivity)activity;
        FragmentManager fragmentManager = mycontext.getSupportFragmentManager();

        Fragment fragToLaunch = null;

        try
        {
            fragToLaunch = (Fragment) Class.forName("com.goteam.wtbuffteks.goteam." + fragClassName).newInstance();
            if(!(bundleArgs == null))
            {
                fragToLaunch.setArguments(bundleArgs);
            }
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.main_content, fragToLaunch, fragClassName)
                    .addToBackStack(fragClassName).commit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void popFragByName(Activity activity, String fragClassName)
    {
        FragmentActivity mycontext = (FragmentActivity)activity;
        FragmentManager fragmentManager = mycontext.getSupportFragmentManager();

        fragmentManager.popBackStack (fragClassName, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}
