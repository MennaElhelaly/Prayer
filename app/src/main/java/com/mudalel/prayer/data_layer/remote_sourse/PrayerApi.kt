package com.mudalel.prayer.data_layer.remote_sourse

import com.mudalel.prayer.data_layer.entity.PrayerData
import retrofit2.Response
import retrofit2.http.*

interface PrayerApi {

    @GET("/v1/calendar?")
    suspend fun getPrayerTimes(@Query("latitude") latitude: String,
                               @Query("longitude") longitude: String ,
                               @Query("month") month: String,
                               @Query("year") year: String): Response<PrayerData?>

}