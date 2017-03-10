package com.goteam.wtbuffteks.goteam;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Parse.enableLocalDatastore(this);
        //Parse.initialize(new Parse.Configuration.Builder(this).applicationId("buffteks_goteam").clientKey("net.buffteks.parseclientkey").server("http://buffteks.net/goteam/").build());
        //Parse.initialize(this, "iSsX8gEavbWeQulkytM0hoCe84E5AB7evvQrJLdl", "cCipYIaOaR9sPIuBTsh31EZ9rsaDCyS0HAIj5LGQ"); // Chris API KEYS
        //Parse.initialize(this, "P8CCuFtVGzXiaxy4c42tnMZ13qP6VB4s8GcpUSuT", "9gEEB0huTPaSn54TMO0b5U7sIwpEDWL7d1384Ogz"); // Jerami API KEYS

        if (savedInstanceState == null) {
            // Add Your Fragments Here
           // Member_list newFragment = new Member_list();
           // FragmentTransaction ft = getFragmentManager().beginTransaction();
           // ft.add(android.R.id.content, newFragment).commit();
            Intent intent = new Intent(this, StartActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_profile:
                ParseUser currentUser = ParseUser.getCurrentUser();
                if (currentUser != null) {
                Intent viewProfile = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(viewProfile);
                } else {
                    Intent logIn = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(logIn);
                }
            default:

                return super.onOptionsItemSelected(item);

        }
    }

}


