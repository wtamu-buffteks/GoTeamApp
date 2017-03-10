package com.goteam.wtbuffteks.goteam;

import com.parse.ParseFile;
import java.io.Serializable;

/**
 * Created by rbroome on 2/24/17.
 */

public class Club implements Serializable{

    private String Name;
    private String Meeting_Time;
    private String Description;
    private ParseFile Logo_Image;
    private ParseFile Background_Image;

    public void setName(String name){
        Name = name;
    }
    public void setMeeting_Time(String meetingtime){Meeting_Time = meetingtime; }
    public void setDescription(String description){
        Description = description;
    }
    public void setLogo_Image(ParseFile logoimage){
        Logo_Image = logoimage;
    }
    public void setBackground_Image(ParseFile backgroundimage){
        Background_Image = backgroundimage;
    }

    public String getName(){
        return Name;
    }
    public String getMeeting_Time(){ return Meeting_Time; }
    public String getDescription(){return Description; }
    public ParseFile getLogo_Image(){
        return Logo_Image;
    }
    public ParseFile getBackground_Image(){
        return Background_Image;
    }

}

