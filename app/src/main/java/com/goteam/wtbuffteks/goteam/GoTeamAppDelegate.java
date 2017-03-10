package com.goteam.wtbuffteks.goteam;


import android.app.Application;

import com.parse.Parse;

/**
 * Created by christophercoffee on 12/7/16.
 */

public class GoTeamAppDelegate extends Application {



    public GoTeamAppDelegate() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);
        Parse.initialize(new Parse.Configuration.Builder(this).applicationId("buffteks_goteam").clientKey("net.buffteks.parseclientkey").server("https://buffteks.net/goteam/").build());
        //Parse.initialize(this, "iSsX8gEavbWeQulkytM0hoCe84E5AB7evvQrJLdl", "cCipYIaOaR9sPIuBTsh31EZ9rsaDCyS0HAIj5LGQ"); // Chris API KEYS
        //Parse.initialize(this, "P8CCuFtVGzXiaxy4c42tnMZ13qP6VB4s8GcpUSuT", "9gEEB0huTPaSn54TMO0b5U7sIwpEDWL7d1384Ogz"); // Jerami API KEYS
    }
}
