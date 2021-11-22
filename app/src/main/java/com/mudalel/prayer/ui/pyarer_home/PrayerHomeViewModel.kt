package com.mudalel.prayer.ui.pyarer_home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mudalel.app.data_layer.remote_sourse.RemoteDataSource
import com.mudalel.prayer.data_layer.entity.PrayerData
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PrayerHomeViewModel  : ViewModel() {
    var prayerData = MutableLiveData<PrayerData?>()
    var apiRepository: RemoteDataSource = RemoteDataSource()
    val handler = CoroutineExceptionHandler { _, exception ->
        Log.e("HANDLER", "CoroutineExceptionHandler got $exception")

    }

    fun getPrayerData() {
        CoroutineScope(Dispatchers.IO + handler).launch {
            val response = apiRepository.getPrayerTimes("33.312805","44.361488","11","2021")
            prayerData.postValue(response)
        }

    }
}