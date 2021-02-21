package com.example.travelapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.travelapp.Activity.LoginActivity;

public class PreferenceSettings {

    private static final String TAG = "TravelApp";
    private String LOGIN = "login";
    private Context context;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private String userName = "username";
    private String userId = "userId";
    private String wishdata = "";

    public PreferenceSettings(Context context) {
        this.context = context;
        sp = context.getSharedPreferences(TAG, context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public void setIslogin(boolean flag) {
        editor.putBoolean(LOGIN, flag).commit();
    }

    public boolean getIsLogin() {
        return sp.getBoolean(LOGIN, false);
    }


    public void logoutUser() {
        // Clearing all data from Shared Preferences
        try {
//            // Yes button clicked, do something
//            editor.clear();
//            editor.commit();
            context.getSharedPreferences(TAG, Context.MODE_PRIVATE).edit().remove(LOGIN).commit();

            Intent i = new Intent(context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            context.startActivity(i);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getUserName() {
        return sp.getString(this.userName, "");
    }

    public void setUserName(String userName) {
        sp.edit().putString(this.userName, userName).commit();
    }

    public int getUserId() {
        return sp.getInt(userId, 0);

    }

    public void setUserId(int id) {
        sp.edit().putInt(userId, id).commit();

    }

    public void putWishPreference(String wishData) {

        Log.d("putData", "Data: " + wishData);
        editor.putString("data", wishData).commit();

    }

    public String getWishList(Context context) {
        this.wishdata = sp.getString("data", "");
        return wishdata;

    }

}
