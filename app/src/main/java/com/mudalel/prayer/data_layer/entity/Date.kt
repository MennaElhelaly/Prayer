package com.mudalel.prayer.data_layer.entity

data class Date(
    val gregorian: Gregorian,
    val readable: String,
    val timestamp: String
)
data class Gregorian(
    val date: String,
    val day: String,
    //val designation: Designation,
    val format: String,
    //val month: Month,
    val weekday: Weekday,
    val year: String
)
//data class Month(
//    val en: String,
//    val number: Int
//)
data class Weekday(
    val en: String
)