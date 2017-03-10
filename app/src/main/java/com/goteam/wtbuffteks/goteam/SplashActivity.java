package com.goteam.wtbuffteks.goteam;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by christophercoffee on 10/12/16.
 */

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
