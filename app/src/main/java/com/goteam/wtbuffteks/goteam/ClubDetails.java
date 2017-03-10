package com.goteam.wtbuffteks.goteam;



import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
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
 * Created by rbroome on 2/24/17.
 */



public class ClubDetails extends Fragment {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_club_detail_view,container,false);
        Club club = (Club) getArguments().getSerializable("club");

        final ImageView backgroundimageView = (ImageView) v.findViewById(R.id.club_detail_image);
        final TextView clubName = (TextView) v.findViewById(R.id.club_detail_name);
        final TextView clubDate = (TextView) v.findViewById(R.id.club_detail_date);
        final TextView clubDescription = (TextView) v.findViewById(R.id.club_detail_description);

        clubName.setText(club.getName());
        clubDate.setText(club.getMeeting_Time());
        clubDescription.setText(club.getDescription());

        Glide.with(getActivity().getApplicationContext()).load(club.getBackground_Image()).asBitmap().override(100,100).into(new BitmapImageViewTarget(backgroundimageView){
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(getActivity().getApplicationContext().getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                backgroundimageView.setImageDrawable(circularBitmapDrawable);
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
