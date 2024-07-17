package com.example.timetable

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class StableView:ViewModel() {
    private val _days=listOf("monday","tuesday","wednesday","thursday","friday","saturday","sunday")
    val day= mutableStateOf("monday")
    val days = _days
    val timeAmPm= mutableStateOf(true)
}