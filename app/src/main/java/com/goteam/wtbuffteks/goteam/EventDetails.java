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
 * Created by Bubsavvy on 11/6/2016.
 */

public class EventDetails extends Fragment {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_event_detail_view,container,false);
        Event event = (Event) getArguments().getSerializable("event");

        final ImageView pictureView = (ImageView) v.findViewById(R.id.event_detail_image);
        final TextView eventName = (TextView) v.findViewById(R.id.event_detail_name);
        final TextView eventDate = (TextView) v.findViewById(R.id.event_detail_date);
        final TextView eventDescription = (TextView) v.findViewById(R.id.event_detail_description);

        eventName.setText(event.getEventName());
        eventName.setTextColor(Color.BLACK);
        eventDate.setText(event.getDateTime());
        eventDate.setTextColor(Color.BLACK);
        eventDescription.setText(event.getDescription());
        eventDescription.setTextColor(Color.BLACK);

        Glide.with(getActivity().getApplicationContext()).load(event.getPicture()).asBitmap().override(100,100).into(new BitmapImageViewTarget(pictureView){
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(getActivity().getApplicationContext().getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                pictureView.setImageDrawable(circularBitmapDrawable);
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
