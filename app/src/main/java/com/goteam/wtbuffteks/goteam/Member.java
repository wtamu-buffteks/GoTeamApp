package com.goteam.wtbuffteks.goteam;


import java.io.Serializable;

/**
 * Created by christophercoffee on 9/21/16.
 */

public class Member implements Serializable {
    private String m_fname;
    private String m_lname;
    private String m_email;
    private int m_privilege;
    private String m_pic_loc;
    private String m_id;
    private String mUserName;
    private String mPhone;

    public Member()
    {

    }

    public void setFirstName(String name)
    {
        this.m_fname = name;
    }

    public String getFirstName()
    {
        return m_fname;
    }

    public void setLastName(String name)
    {
        this.m_lname = name;
    }

    public String getLastName()
    {
        return m_lname;
    }

    public void setEmail(String email)
    {
        this.m_email = email;
    }

    public String getEmail()
    {
        return m_email;
    }

    public void setPic(String pic_location)
    {
        this.m_pic_loc = pic_location;
    }

    public String getPic()
    {
        return m_pic_loc;
    }

    public String getPicName()
    {
        return "IMG" + m_id;
    }

    public void setPrivilege(int privilege)
    {
        this.m_privilege = privilege;
    }

    public int getPrivilege()
    {
        return m_privilege;
    }

    public String getId()
    {
        return m_id;
    }

    public void setId(String id)
    {
        m_id = id;
    }

    public void setUserName(String userName)
    {
        mUserName = userName;
    }

    public String getUserName()
    {
        return mUserName;
    }

    public void setNumber(String number)
    {
        mPhone = number;
    }

    public String getPhone()
    {
        return mPhone;
    }
}
