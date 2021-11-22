package com.mudalel.prayer.data_layer.entity

import com.google.gson.annotations.SerializedName

data class PrayerData(
    val code: Int,
    val status: String,
    @SerializedName("data") val allData : List<Data>

)