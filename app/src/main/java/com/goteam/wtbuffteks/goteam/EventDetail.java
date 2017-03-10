package com.goteam.wtbuffteks.goteam;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.goteam.wtbuffteks.goteam.constants.ParseConstants;
import com.koushikdutta.ion.Ion;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.logging.SimpleFormatter;

public class EventDetail extends AppCompatActivity {

    public ImageView eventDetailImage;
    public TextView eventDetailName;
    public TextView eventDetailDate;
    public TextView eventDetailDescription;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);


        eventDetailImage = (ImageView) findViewById(R.id.event_detail_image);
        eventDetailName = (TextView) findViewById(R.id.event_detail_name);
        eventDetailDate = (TextView) findViewById(R.id.event_detail_date);
        eventDetailDescription = (TextView) findViewById(R.id.event_detail_description);

        final Intent eventIntent = getIntent();
        ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseConstants.classNameEvent);
        query.getInBackground(eventIntent.getStringExtra("eventObjectID"), new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if(e == null){
                    eventDetailName.setText(object.getString(ParseConstants.eventNameEvent));
                    eventDetailDescription.setText(object.getString(ParseConstants.descriptionEvent));
                    SimpleDateFormat dateSetter = new SimpleDateFormat("MMMM d, yyyy h:mm a", Locale.getDefault());
                    eventDetailDate.setText(dateSetter.format(object.getDate(ParseConstants.dateEvent)));
                    ParseFile eventPic = object.getParseFile(ParseConstants.pictureEvent);
                    if(eventPic.getUrl().contains("http://buffteks.net/goteam/files/")){
                        Ion.with(eventDetailImage).load(eventPic.getUrl());
                    }else{
                        eventPic.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                                eventDetailImage.setImageBitmap(bmp);

                            }
                        });
                    }
                }else{
                    Log.d("Parse error", e.getLocalizedMessage());
                }
            }
        });

    }
}
