package com.mudalel.prayer.data_layer.entity

import com.google.gson.annotations.SerializedName

data class PrayerData(
    val status: String,
    @SerializedName("data") val allData : List<Data>
)
data class Data(
    val date: Date,
    //val meta: Meta,
    val timings: Timings
)
data class Timings(
    val Asr: String,
    val Dhuhr: String,
    val Fajr: String,
    val Imsak: String,
    val Isha: String,
    val Maghrib: String,
    val Midnight: String,
    val Sunrise: String,
    val Sunset: String
)