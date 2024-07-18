package com.example.timetable.protodatastore

import kotlinx.serialization.Serializable
@Serializable
data class Preferences(
    val sem:String = "",
    val timeAmPm:Boolean = true
)
