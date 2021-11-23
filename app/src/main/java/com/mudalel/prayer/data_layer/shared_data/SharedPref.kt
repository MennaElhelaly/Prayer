package com.mudalel.app.data_layer.shared_data

import android.content.Context
import android.content.SharedPreferences


object SharedPref{

    private var pref: SharedPreferences? = null

    fun createPrefObject(context: Context): SharedPreferences? {
        if (pref == null) {
            pref = context.getSharedPreferences("Prayer", Context.MODE_PRIVATE)
        }
        return pref
    }
    fun setYear(answered: String) {
        val editor = pref!!.edit()
        editor.putString("year", answered)
        editor.apply()
    }
    fun getYear(): String? {
        return pref!!.getString("name", "اوس")
    }

    fun setMyLat(lat:String) {
        val editor = pref!!.edit()
        editor.putString("myLat" , lat)
        editor.apply()
    }
    fun getMyLat(): String?{
        return pref!!.getString("myLat", "33.312805")
    }
    fun setMyLong(long:String) {
        val editor = pref!!.edit()
        editor.putString("myLong" , long)
        editor.apply()
    }
    fun getMyLong(): String?{
        return pref!!.getString("myLong", "44.361488")
    }


}