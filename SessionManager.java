package com.example.tugaspmob_ayu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import java.util.HashMap;

@SuppressLint("CommitPrefEdits")

public class SessionManager {
    //Shared Preferences
    SharedPreferences pref;
    //Editor for Shared preferences
    Editor editor;
    //context
    Context _context;
    //shared pref mode
    int PRIVATE_MODE = 0;
    //nama sharepreference
    private static final String PREF_NAME = "Sesi";
    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_FIRST_NAME = "first_name";
    //constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
    //Create Login Session
    public void createLoginSession(String username, String first_name){
        //Storing Login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_FIRST_NAME, first_name);
        editor.commit();
    }
    /**
     * Check Login method wil check user Login status
     * If false it will redirect user to Login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check Login status
        if(!this.isLoggedIn()){
            Intent i = new Intent(_context, Login.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);
        }
    }
    /**
     * Get stored session data
     * */
    public HashMap<String, String>getUserDetails(){
        HashMap<String,String> user = new HashMap<String,String>();
        user.put(KEY_USERNAME, pref.getString(KEY_USERNAME, null));
        user.put(KEY_FIRST_NAME, pref.getString(KEY_FIRST_NAME, null));
        return user;
    }
    /**
     * Clear session details
     * */
    public void logoutUser(){
// Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
        Intent i = new Intent(_context, Login.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(i);
    }
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}
