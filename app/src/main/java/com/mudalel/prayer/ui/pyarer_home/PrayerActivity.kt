package com.mudalel.prayer.ui.pyarer_home

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mudalel.prayer.data_layer.entity.Day
import com.mudalel.prayer.data_layer.entity.Timings
import com.mudalel.prayer.databinding.ActivityPrayerBinding
import com.mudalel.prayer.ui.pyarer_home.location.MyLocation
import com.mudalel.prayer.ui.pyarer_home.adapter.PrayerAdapter
import java.util.*

class PrayerActivity : AppCompatActivity(), PrayerAdapter.OnClickDayListener {
    private lateinit var binding: ActivityPrayerBinding
    private lateinit var prayerViewModel: PrayerHomeViewModel
    private val calendar = Calendar.getInstance()
    private var currentDay = 0
    private var currentMonth = 0
    private var currentYear = 0
    private var day = 0
    private var month = 0
    private var year = 0
    private var mylat=""
    private var myLong=""
    private lateinit var daysAdapter: PrayerAdapter
    private lateinit var myLocation:MyLocation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val actionBar = supportActionBar
        actionBar?.hide()

        prayerViewModel = ViewModelProvider(this)[PrayerHomeViewModel::class.java]
        daysAdapter = PrayerAdapter(emptyList(),this)
        myLocation = MyLocation(this)

        initUI()
        getDateToday()
        getDataFromMyLocation()
        sendDataToViewModelToEdit()
        loadUI()
        binding.btnRight.setOnClickListener {
            getNextMonth()
        }
        binding.btnLeft.setOnClickListener {
            getPrevMonth()
        }
        binding.buttonLocation.setOnClickListener {
            myLocation.getLastLocation()
        }
    }
    private fun initUI() {
        binding.recyclerDays.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = daysAdapter
            scrollToPosition(10);
        }
    }
    private fun getDateToday() {
        calendar.time = Date()
        currentDay =calendar[Calendar.DAY_OF_MONTH]
        currentMonth = calendar[Calendar.MONTH] + 1
        currentYear = calendar[Calendar.YEAR]
        day = currentDay
        month =currentMonth
        year = currentYear
    }
    private fun getDataFromMyLocation() {
        myLocation.getLastLocation()
        myLocation.callback = { lat, long->
            visibleTheView()
            mylat =lat
            myLong=long
            prayerViewModel.getPrayerData(lat,long,month.toString(),year.toString())
        }
    }
    private fun loadUI() {
        prayerViewModel.monthData.observe(this) {
            it?.let {
                daysAdapter.setData(it.days)
                binding.progressBar.visibility=View.GONE
                binding.prayersView.visibility=View.VISIBLE
                binding.month.text = it.name
                binding.location.text = it.location

                if (month == currentMonth && day == currentDay && year == currentYear){
                    bindData(it.days[currentDay-1].times)
                    it.days[currentDay-1].selected = true
                    it.days[currentDay-1].today = true
                    binding.recyclerDays.scrollToPosition(currentDay-1)
                }
                else{
                    binding.recyclerDays.scrollToPosition(0)
                }
            }
        }
    }
    private fun bindData(it: Timings) {
        binding.fajrTime.text = it.Fajr.substring(0,5)
        binding.dherTime.text = it.Dhuhr.substring(0,5)
        binding.asrTime.text = it.Asr.substring(0,5)
        binding.maghribTime.text = it.Maghrib.substring(0,5)
        binding.ishaTime.text = it.Isha.substring(0,5)
    }
    private fun sendDataToViewModelToEdit() {
        prayerViewModel.prayerData.observe(this) {
            it?.let {
                if(it.status == "OK"){
                   prayerViewModel.mapData(it)
                }
            }
        }
    }
    private fun getPrevMonth() {
        --month
        if (month == 0){
            month=12
            --year
        }
        prayerViewModel.getPrayerData(mylat,myLong,month.toString(),year.toString())
    }

    private fun getNextMonth() {
        ++month
        if (month == 13){
            month=1
            ++year
        }
        prayerViewModel.getPrayerData(mylat,myLong,month.toString(),year.toString())
    }
    private fun visibleTheView() {
        binding.noLocation.visibility = View.GONE
        binding.btnLeft.visibility = View.VISIBLE
        binding.btnRight.visibility = View.VISIBLE
    }
    override fun onDayClick(item: Day) {
        bindData(item.times)
        item.selected =true
        daysAdapter.notifyDataSetChanged()
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                myLocation.getLastLocation()
            }
            else
            {
                binding.noLocation.visibility =View.VISIBLE
                binding.btnLeft.visibility = View.GONE
                binding.btnRight.visibility = View.GONE
            }
        }
    }
    companion object{
        const val PERMISSION_ID = 42
    }
}