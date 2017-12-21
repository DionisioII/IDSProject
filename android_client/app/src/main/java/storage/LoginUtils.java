package storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.securepreferences.SecurePreferences;

/**
 * Created by domenico on 24/05/16.
 */
public class LoginUtils {
    //public static final String PREFS_LOGIN_USERNAME_KEY = "__USERNAME__" ;
    //public static final String PREFS_LOGIN_PASSWORD_KEY = "__PASSWORD__" ;
    public static final String PREFS_LOGIN_TOKEN_KEY = "TOKEN";

    /**
     * Called to save supplied value in shared preferences against given key.
     * @param context Context of caller activity
     * @param key Key of value to save against
     * @param value Value to save
     */
    public static void saveToPrefs(Context context, String key, String value) {
        SharedPreferences sharedPrefs = new SecurePreferences(context, "61c84703fbe0cd5ee3799aad572e8d41fd6eab92", "preferenze");
        //SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(key,value);
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
        SharedPreferences sharedPrefs = new SecurePreferences(context, "61c84703fbe0cd5ee3799aad572e8d41fd6eab92", "preferenze");
        //SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        try {
            return sharedPrefs.getString(key, defaultValue);
        } catch (Exception e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    public static void deleteFromPrefs(Context context, String key)
    {
        SharedPreferences sharedPrefs = new SecurePreferences(context, "61c84703fbe0cd5ee3799aad572e8d41fd6eab92", "preferenze");
        //SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.remove(key);
        editor.commit();
    }
}