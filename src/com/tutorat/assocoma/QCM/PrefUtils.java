package com.tutorat.assocoma.QCM;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Sidd8 on 07/10/2014.
 */
public class PrefUtils
{
    public static final String PREFS_LOGIN_USERNAME_KEY = "__USERNAME__" ;
    public static final String PREFS_LOGIN_PASSWORD_KEY = "__PASSWORD__" ;

    /**
     * Called to save supplied value in shared preferences against given key.
     * @param context Context of caller activity
     * @param value Value to save
     */
    public static void saveToPrefs(Context context, String credentials, String value)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor = prefs.edit();
        editor.putString(credentials,value);
        editor.commit();
    }

    /**
     * Called to save supplied value in shared preferences against given key.
     * @param context Context of caller activity
     */
    public static void saveEvolvingToPrefs(Context context, int score, int nbQuestions)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor = prefs.edit();

        String savedScore = prefs.getString("evolution", "");
        long scoreToRatio = Math.round(((float)score / nbQuestions) * 100);

        if(savedScore != null)
            savedScore += scoreToRatio + "|";
        else
            savedScore = scoreToRatio + "|";
        editor.putString("evolution", savedScore);
        editor.commit();
    }

    /**
     * Called to retrieve required value from shared preferences, identified by given key.
     * Default value will be returned of no value found or error occurred.
     * @param context Context of caller activity
     * @param key Key to find value against
     * @param defaultValue Value to return if no data found against given key
     * @return Return the value found against given key, default if not found or any error occurs
     */
    public static String getFromPrefs(Context context, String key, String defaultValue) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        try
        {
            return sharedPrefs.getString(key, defaultValue);
        } catch (Exception e)
        {
            e.printStackTrace();
            return defaultValue;
        }
    }
}