package com.mudalel.prayer.data_layer.entity


data class Month(
    val name: String,
    val days: List<Day>
)
data class Day(
        val date: String,
        val day_en: String,
        val times: Timings,
        var selected :Boolean
)