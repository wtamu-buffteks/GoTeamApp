package com.goteam.wtbuffteks.goteam;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

/**
 * Created by cc916647 on 12/2/16.
 */

public class MemberDetail extends AppCompatActivity {
    ImageView pic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_member_detail_view);


        Log.d("member detail view", "in member detail view activity");
        Bundle args = null;
        args = this.getIntent().getExtras();

        final Member member = (Member) args
                .getSerializable("member");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarstart);
        toolbar.setTitle("Member Profiles");
        toolbar.setTitleTextColor(Color.WHITE);


        TextView name = (TextView)findViewById(R.id.memberDetailName);
        TextView email = (TextView)findViewById(R.id.memberDetailEmail);
        TextView phone = (TextView)findViewById(R.id.memberDetailPhone);
        pic = (ImageView)findViewById(R.id.memberDetailPic);




        name.setText(member.getFirstName() + " " + member.getLastName());

        email.setText(member.getEmail());

        phone.setText(member.getPhone());


        Glide.with(this)
                .load(member.getPic()).asBitmap()
                .override(100,100)
                .into(new BitmapImageViewTarget(pic){
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        pic.setImageDrawable(circularBitmapDrawable);
                    }
                });




        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{member.getEmail()} );
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + member.getPhone()));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
