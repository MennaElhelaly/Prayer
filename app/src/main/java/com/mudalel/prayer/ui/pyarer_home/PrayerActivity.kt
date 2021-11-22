package com.mudalel.prayer.ui.pyarer_home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.mudalel.app.data_layer.shared_data.SharedPref
import com.mudalel.prayer.databinding.ActivityPrayerBinding

class PrayerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPrayerBinding
    private lateinit var prayerViewModel: PrayerHomeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val actionBar = supportActionBar
        actionBar?.hide()

        prayerViewModel = ViewModelProvider(this)[PrayerHomeViewModel::class.java]
        getData()


    }

    private fun getData() {
        if (prayerViewModel.prayerData.value ==null){
            prayerViewModel.getPrayerData()
        }
        prayerViewModel.prayerData.observe(this) {
            it?.let {
                if(it.status == "OK"){

                }
            }
        }
    }
}