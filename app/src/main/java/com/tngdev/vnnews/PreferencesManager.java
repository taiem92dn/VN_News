package com.tngdev.vnnews;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesManager {

    private static final String PREF_NAME = NewsApp.class.getName() + "PREF_MANAGER";
    private static final String KEY_DARK_MODE = "KEY_DARK_MODE";


    private static PreferencesManager sInstance;
    private final SharedPreferences mPref;

    private PreferencesManager(Context context) {
        mPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized PreferencesManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PreferencesManager(context);
        }
        return sInstance;
    }

    public void setValue(String key, boolean value) {
        mPref.edit()
                .putBoolean(key, value)
                .apply();
    }

    public boolean getValue(String key) {
        return mPref.getBoolean(key, false);
    }

    public boolean isDarkMode() {
        return mPref.getBoolean(KEY_DARK_MODE, false);
    }

    public void toggleDarkMode() {
        boolean curr = getValue(KEY_DARK_MODE);
        setValue(KEY_DARK_MODE, !curr);
    }

    public void remove(String key) {
        mPref.edit()
                .remove(key)
                .apply();
    }

    public void clear() {
         mPref.edit()
                .clear()
                .apply();
    }
}
