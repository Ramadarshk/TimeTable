package com.example.timetable.protodatastore

import com.example.timetable.dataclass.Week
import kotlinx.serialization.Serializable
@Serializable
data class Preferences(
    val sem:String = "Please Select a Day By Pressing ->",
    val timeAmPm:Boolean = true,
    val week: Week = Week()
)
