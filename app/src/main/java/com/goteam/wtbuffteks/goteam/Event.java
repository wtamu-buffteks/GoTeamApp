package com.goteam.wtbuffteks.goteam;

import com.parse.ParseFile;

import java.io.Serializable;

/**
 * Created by Bubsavvy on 10/3/2016.
 */

public class Event implements Serializable {

    private String EventName;
    private String DateTime;
    private String Description;
    private String ClubHost;
    private ParseFile Picture;
    private int HostFlag;

    public void setEventName(String eventName){
        EventName = eventName;
    }
    public void setDateTime(String dateTime){
        DateTime = dateTime;
    }
    public void setDescription(String description){
        Description = description;
    }
    public void setClubHost(String clubHost){
        ClubHost = clubHost;
    }
    public void setPicture(ParseFile picture){
        Picture = picture;
    }
    public void setHostFlag(int hostFlag){
        HostFlag = hostFlag;
    }

    public String getEventName(){
        return EventName;
    }
    public String getDateTime(){
        return DateTime;
    }
    public String getDescription(){
        return Description;
    }
    public String getClubHost(){
        return ClubHost;
    }
    public ParseFile getPicture(){
        return Picture;
    }
    public int getHostFlag(){
        return HostFlag;
    }

}
