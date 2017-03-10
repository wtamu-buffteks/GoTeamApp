package com.goteam.wtbuffteks.goteam;


import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.goteam.wtbuffteks.goteam.constants.ParseConstants;
import com.koushikdutta.ion.Ion;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private ArrayList<Event> eventList;
    private Fragment eventFragment;
    private Context mContext;
    private List<ParseObject> eventObjects;
    private eventListInterface fListener;

    public EventAdapter(List<ParseObject> objects, eventListInterface eListener){
        eventObjects = objects;
        fListener = eListener;
    }
    public EventAdapter(ArrayList<Event> eventList, Fragment targetFragment) {
        this.eventList = eventList;
        eventFragment = targetFragment;
        mContext = targetFragment.getActivity().getApplicationContext();
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_event_list_items, parent, false);
        return new EventViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final EventViewHolder holder, final int position) {

//        Event event = eventList.get(position);
//        holder.eventName.setText(event.getEventName());
//
//        Glide.with(mContext).load(event.getPicture()).asBitmap().override(100,100).into(new BitmapImageViewTarget(holder.pictureView){
//            @Override
//            protected void setResource(Bitmap resource) {
//                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
//                circularBitmapDrawable.setCircular(true);
//                holder.pictureView.setImageDrawable(circularBitmapDrawable);
//            }
//        });
//
//        Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(mContext, R.anim.bounce_interpolator);
//        holder.itemView.setAnimation(animAnticipateOvershoot);


        ParseObject eventObject = eventObjects.get(position);
        holder.eventName.setText(eventObject.getString(ParseConstants.eventNameEvent));
        holder.dateTextView.setText(eventObject.getString(ParseConstants.dateTimeEvent));
        ParseFile eventImage = eventObject.getParseFile(ParseConstants.pictureEvent);
        if(eventImage.getUrl().contains("http://buffteks.net/goteam/files/")){
            Ion.with(holder.pictureView).load(eventImage.getUrl());
        }else{
            eventImage.getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                    holder.pictureView.setImageBitmap(bmp);

                }
            });
        }
        holder.eventObject = eventObject;
        holder.eventListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fListener.eventOnClick(holder.eventObject);
            }
        });




    }

    @Override
    public int getItemCount() {
        return eventObjects.size();
    }


    public class EventViewHolder extends RecyclerView.ViewHolder {

        public ParseObject eventObject;
        public View eventListView;
        public ImageView pictureView;
        public TextView eventName;
        public TextView dateTextView;
        public EventViewHolder(View view) {
            super(view);

            eventListView = view;
            pictureView = (ImageView) view.findViewById(R.id.eventImageView);
            eventName = (TextView) view.findViewById(R.id.eventNameTextView);
            dateTextView = (TextView) view.findViewById(R.id.dateTextView);
        }
    }


    public interface eventListInterface {
        void eventOnClick(ParseObject parseObject);
    }

}
