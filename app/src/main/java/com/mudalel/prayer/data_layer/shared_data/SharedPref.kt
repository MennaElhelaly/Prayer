package com.mudalel.app.data_layer.shared_data

import android.content.Context
import android.content.SharedPreferences


object SharedPref{

    private var pref: SharedPreferences? = null

    fun createPrefObject(context: Context): SharedPreferences? {
        if (pref == null) {
            pref = context.getSharedPreferences("mudalel", Context.MODE_PRIVATE)
        }
        return pref
    }
    fun setUserName(answered: String) {
        val editor = pref!!.edit()
        editor.putString("name", answered)
        editor.apply()
    }
    fun getUserName(): String? {
        return pref!!.getString("name", "اوس")
    }
    fun setUserPhone(answered: String) {
        val editor = pref!!.edit()
        editor.putString("phone", answered)
        editor.apply()
    }
    fun getUserPhone(): String? {
        return pref!!.getString("phone", "")
    }
    fun setUserCityID(answered: String) {
        val editor = pref!!.edit()
        editor.putString("cityID", answered)
        editor.apply()
    }
    fun getUserCityID(): String? {
        return pref!!.getString("cityID", "")
    }

    fun setUserToken(answered: String) {
        val editor = pref!!.edit()
        editor.putString("token", answered)
        editor.apply()
    }
    fun getUserToken(): String? {
        return pref!!.getString("token", "")
    }

    fun setLang(lang: String) {
        val editor = pref!!.edit()
        editor.putString("lang", lang)
        editor.apply()
    }
    fun getLang(): String? {
        return pref!!.getString("lang", "ar")
    }
    fun setFirstTime() {
        val editor = pref!!.edit()
        editor.putBoolean("first" , true)
        editor.apply()
    }
    fun getFirstTime(): Boolean{
        return pref!!.getBoolean("first", false)
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

    fun setFCMToken(fcm:String) {
        val editor = pref!!.edit()
        editor.putString("FCM" , fcm)
        editor.apply()
    }
    fun getFCMToken(): String?{
        return pref!!.getString("FCM", "")
    }

}