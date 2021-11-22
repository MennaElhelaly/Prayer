package com.mudalel.prayer.data_layer.entity

data class Hijri(
    val date: String,
    val day: String,
    val designation: Designation,
    val format: String,
    val holidays: List<Any>,
    val month: Month,
    val weekday: Weekday,
    val year: String
)