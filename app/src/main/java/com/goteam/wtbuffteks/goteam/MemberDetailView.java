package com.goteam.wtbuffteks.goteam;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;


/**
 * Created by christophercoffee on 9/20/16.
 */

public class MemberDetailView extends Fragment {
    ImageView pic;


    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_member_detail_view,container,false);

        Log.d("member detail view", "in member detail view");
        Bundle args = getArguments();
        final Member member = (Member) args
                .getSerializable("member");

        TextView name = (TextView)v.findViewById(R.id.memberDetailName);
        TextView email = (TextView)v.findViewById(R.id.memberDetailEmail);
        TextView phone = (TextView)v.findViewById(R.id.memberDetailPhone);
        pic = (ImageView)v.findViewById(R.id.memberDetailPic);

        name.setText(member.getFirstName() + " " + member.getLastName());
        name.setTextColor(Color.BLACK);
        email.setText(member.getEmail());
        email.setTextColor(Color.BLACK);
        phone.setText(member.getPhone());
        phone.setTextColor(Color.BLACK);

        Glide.with(getActivity())
                .load(member.getPic()).asBitmap()
                .override(100,100)
                .into(new BitmapImageViewTarget(pic){
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(getActivity().getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        pic.setImageDrawable(circularBitmapDrawable);
                    }
                });

        v.setBackgroundColor(Color.YELLOW);

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{member.getEmail()} );
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + member.getPhone()));
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });



        return v;
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater)
    {
        super.onCreateOptionsMenu(menu,menuInflater);
        menuInflater.inflate(R.menu.menu_main,menu);

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.clear();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case 1:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

