package com.example.akacademy.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {
    private SharedPreferences preferences;

    public Session(Context cntx) {
        // TODO Auto-generated constructor stub
        preferences = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public String getEmail() {
        return this.preferences.getString("email","");
    }

    public void setEmail(String email) {
        this.preferences.edit().putString("email",email).commit();
    }

    public String getName() {
        return this.preferences.getString("name","");
    }

    public void setName(String name) {
        this.preferences.edit().putString("name",name).commit();
    }





}
