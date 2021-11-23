package com.mudalel.prayer.ui.pyarer_home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mudalel.app.data_layer.remote_sourse.RemoteDataSource
import com.mudalel.prayer.data_layer.entity.Day
import com.mudalel.prayer.data_layer.entity.Month
import com.mudalel.prayer.data_layer.entity.PrayerData
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class PrayerHomeViewModel  : ViewModel() {
    var prayerData = MutableLiveData<PrayerData?>()
    var apiRepository: RemoteDataSource = RemoteDataSource()
    var monthData = MutableLiveData<Month?>()
    val handler = CoroutineExceptionHandler { _, exception ->
        Log.e("HANDLER", "CoroutineExceptionHandler got $exception")

    }

    fun getPrayerData(lat:String,lang: String,month:String,year:String) {
        CoroutineScope(Dispatchers.IO + handler).launch {
            val response = apiRepository.getPrayerTimes(lat,lang,month,year)
            prayerData.postValue(response)
        }
    }
    fun mapData(data : PrayerData){
        val days :MutableList<Day> = arrayListOf()
        val name = data.allData[0].date.readable
        for (item in data.allData){
            val num = item.date.gregorian.day
            val day =item.date.gregorian.weekday.en
            val times = item.timings
            days.add(Day(num,day,times,false))
        }
        monthData.postValue(Month(name ,days))
    }

}