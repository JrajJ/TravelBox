package com.sparklinktech.stech.travelbox.sharedpref;

import android.content.Context;
import android.content.SharedPreferences;


public class SessionManager
{

    public void setPreferences(Context context, String key, String value)
    {

        SharedPreferences.Editor editor = context.getSharedPreferences("status", Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.apply();


    }



    public  String getPreferences(Context context, String key)
    {

        SharedPreferences prefs = context.getSharedPreferences("status",	Context.MODE_PRIVATE);
        return prefs.getString(key, "");



    }
}