package com.mudalel.prayer.ui.pyarer_home

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mudalel.app.data_layer.shared_data.SharedPref
import com.mudalel.prayer.data_layer.entity.Day
import com.mudalel.prayer.data_layer.entity.Timings
import com.mudalel.prayer.databinding.ActivityPrayerBinding
import com.mudalel.prayer.ui.location.MyLocation
import com.mudalel.prayer.ui.location.MyLocation.Companion.PERMISSION_ID
import java.util.*

class PrayerActivity : AppCompatActivity(),PrayerAdapter.OnClickDayListener {
    private lateinit var binding: ActivityPrayerBinding
    private lateinit var prayerViewModel: PrayerHomeViewModel
    private val calendar = Calendar.getInstance()
    private var currentMonth = 0
    private var currentYear = 0
    private lateinit var daysAdapter: PrayerAdapter
    private lateinit var mylocation:MyLocation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding = ActivityPrayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val actionBar = supportActionBar
        actionBar?.hide()

        prayerViewModel = ViewModelProvider(this)[PrayerHomeViewModel::class.java]
        daysAdapter = PrayerAdapter(emptyList(),this)
        SharedPref.createPrefObject(this)
        mylocation = MyLocation(this)
        calendar.time = Date()

        currentMonth = calendar[Calendar.MONTH] + 1
        currentYear = calendar[Calendar.YEAR]
        Log.i("Menna","month888    "+currentMonth +"  "+currentYear)
        SharedPref.setYear(currentYear.toString())

        initUI()
        mylocation.getLastLocation()
        getData()
        loadUI()

        binding.btnRight.setOnClickListener {
            ++currentMonth
            if (currentMonth == 13){
                currentMonth=1
                ++currentYear
            }
            prayerViewModel.getPrayerData(currentMonth.toString(),currentYear.toString())
        }
        binding.btnLeft.setOnClickListener {
            --currentMonth
            if (currentMonth == 0){
                currentMonth=12
                --currentYear
            }
            prayerViewModel.getPrayerData(currentMonth.toString(),currentYear.toString())
        }
    }
    private fun initUI() {
        binding.recyclerDays.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = daysAdapter
            (layoutManager as LinearLayoutManager).scrollToPositionWithOffset(10, 20);
            scrollToPosition(10);
        }
    }
    private fun loadUI() {
        prayerViewModel.monthData.observe(this) {
            it?.let {
                daysAdapter.setData(it.days)
                binding.month.text = it.name.substring(2)
                bindData(it.days[0].times)
            }
        }
    }

    private fun bindData(it: Timings) {
        binding.fajrTime.text = it.Fajr.substring(0,5)
        binding.dhuhrTime.text = it.Dhuhr.substring(0,5)
        binding.asrTime.text = it.Asr.substring(0,5)
        binding.maghribTime.text = it.Maghrib.substring(0,5)
        binding.ishaTime.text = it.Isha.substring(0,5)
    }

    private fun getData() {
        if (prayerViewModel.prayerData.value ==null){
            prayerViewModel.getPrayerData(currentMonth.toString(),currentYear.toString())
        }
        prayerViewModel.prayerData.observe(this) {
            it?.let {
                if(it.status == "OK"){
                   prayerViewModel.mapData(it)
                }
            }
        }
    }
    override fun onDayClick(item: Day) {
        bindData(item.times)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    if (requestCode == PERMISSION_ID) {
        if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
            mylocation.getLastLocation()
        }
    }}
    companion object{
        const val PERMISSION_ID = 42
    }
}