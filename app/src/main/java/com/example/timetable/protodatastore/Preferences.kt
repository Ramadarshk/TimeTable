package com.example.timetable.protodatastore

import kotlinx.serialization.Serializable
@Serializable
data class Preferences(
    val sem:String = "Please Select a Day By Pressing ->",
    val timeAmPm:Boolean = true
)
