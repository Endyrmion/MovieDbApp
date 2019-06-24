package com.example.moviedb.utils.extensions

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by Kolincodes on 10/05/2018.
 */

class SharedPreference(context: Context) {
    private val PREFS_NAME = "kotlincodes"
    val sharedPref: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)


    //save the String data inside the SharedPreference
    fun save(KEY_NAME: String, text: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(KEY_NAME, text)
        editor.apply()
    }

    //retrieve String Data

    fun getValueString(KEY_NAME: String): String? {
        return sharedPref.getString(KEY_NAME, null)
    }

    //remove all the data

    fun clearSharedPreferences(){
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.clear()
    }

    fun clearSharedPreference() {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        //sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        editor.clear()
    }

    //remove specific data
    fun removeValue(KEY_NAME: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.remove(KEY_NAME)
    }
}

