package com.example.timetable.dataclass

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Week(
    val id: String ,
    val tuesday: List<String> ,
    val wednesday: List<String> ,
    val thursday: List<String> ,
    val friday: List<String> ,
    val saturday: List<String> ,
    val tuesdayLab: List<String> ,
    val wednesdayLab: List<String> ,
    val thursdayLab: List<String> ,
    val fridayLab: List<String> ,
    val saturdayLab: List<String>
){
    constructor(): this("",emptyList(),emptyList(),emptyList(),emptyList(),emptyList(),emptyList(),emptyList(),emptyList(),emptyList(),emptyList())
}
