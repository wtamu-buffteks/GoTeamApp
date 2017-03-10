package com.goteam.wtbuffteks.goteam;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Created by Bubsavvy on 10/3/2016.
 */

public class ParseController {

    /** Event Controller Methods ********************************************/

    // Adds Target Event To Parse Table "Event"
    protected void addEvent(Event target){
        ParseObject event = new ParseObject("Event");
        event.put("EventName", target.getEventName());
        event.put("DateTime", target.getDateTime());
        event.put("Description", target.getDescription());
        event.put("ClubHost", target.getClubHost());
        event.put("Picture", target.getPicture());
        event.put("HostFlag", target.getHostFlag());
        event.saveInBackground();
    }

    // Removes Target Event From Parse Table "Event" By objectId
    protected void removeEvent(String objectId){
        try {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Event");
            ParseObject event = query.get(objectId);
            event.deleteInBackground();
        } catch (ParseException e) { e.printStackTrace(); }
    }

    // Fetches Target Event From Parse Table "Event" By objectId
    protected Event getEvent(String objectId){
        try {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Event");
            ParseObject event = query.get(objectId);
            Event targetEvent = new Event();
            targetEvent.setEventName(event.getString("EventName"));
            targetEvent.setDateTime(event.getString("DateTime"));
            targetEvent.setDescription(event.getString("Description"));
            targetEvent.setClubHost(event.getString("ClubHost"));
            targetEvent.setPicture(event.getParseFile("Picture"));
            targetEvent.setHostFlag(event.getInt("HostFlag"));
            return targetEvent;
        } catch (ParseException e) { e.printStackTrace(); }
        return null;
    }

    // Fetches Events From Parse Table "Event"
    protected List<Event> getEvents(final List<Event> eventList, final EventAdapter adapter){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Event");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> events, ParseException e) {
                if (e == null) {

                    for (ParseObject event : events) {
                        Event targetEvent = new Event();
                        targetEvent.setEventName(event.getString("EventName"));
                        targetEvent.setDateTime(event.getString("DateTime"));
                        targetEvent.setDescription(event.getString("Description"));
                        targetEvent.setClubHost(event.getString("ClubHost"));
                        targetEvent.setHostFlag(event.getInt("HostFlag"));
                        targetEvent.setPicture(event.getParseFile("Picture"));
                        eventList.add(targetEvent);
                        adapter.notifyDataSetChanged();
                    }

                }
                else {
                    // Something went wrong.
                    Log.d("Parse", e.toString());
                }
            }
        });


        return eventList;


        }


    public void getMembersFromParse(final List<Member> members, final MemberAdapter adapter, final List<Member> saveAdapter, final Context context)
    {
        ParseQuery<ParseUser> query = ParseUser.getQuery();

        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    // The query was successful.
                    for(int i = 0;i < objects.size(); i++) {

                        ParseUser user = objects.get(i);
                        Member member = new Member();
                        member.setFirstName(user.getString("Firstname"));
                        member.setLastName(user.getString("Lastname"));
                        member.setId(user.getObjectId());
                        Log.d("parsegmem", user.getObjectId());
                        member.setEmail(user.getString("email"));

                        member.setPrivilege(user.getInt("Privilege"));
                        member.setUserName(user.getString("username"));
                        member.setNumber(user.getString("Phone_number"));

                        member.setPic(user.getParseFile("Picture").getUrl());
                        members.add(member);
                    }
                    saveAdapter.addAll(members);
                    adapter.notifyDataSetChanged();

                } else {
                    // Something went wrong.
                    Log.d("Parse", e.toString());

                }
            }
        });

    }


    /** Private Conversion Methods ********************************************/

    // Converts Bitmap To ParseFile
    private ParseFile convertToParseFile(Bitmap bitmap){
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteStream);
        return new ParseFile(byteStream.toByteArray());
    }

    // Converts ParseFile To Bitmap
    private Bitmap convertToBitMap(ParseFile parseFile){
        try {
            return BitmapFactory.decodeByteArray(parseFile.getData(), 0, parseFile.getData().length);
        } catch (ParseException e) { e.printStackTrace(); }
        return null;
    }


}
