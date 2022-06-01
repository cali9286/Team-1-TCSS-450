package edu.uw.tcss450.ckald.team1tcss450.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class SharedPreferenceManager {


    /**
     * Store user settings.
     */
    private final SharedPreferences mPreference;

    private final Context context;


    /**
     * An SharedPreferencesManager constructor.
     * @param context the context of the shared manager
     */
    public SharedPreferenceManager(Context context) {
        this.context = context;
        mPreference = PreferenceManager.getDefaultSharedPreferences(context);
    }
    /**
     * Retrieve an int in sharedPreferences
     * @param tag identifies the value
     * @param defValue default value
     * @return the stored or default value
     */
    public int retrieveInt(String tag, int defValue) {

        return mPreference.getInt(tag, defValue);
    }
}
