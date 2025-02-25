package com.example.timetable.notify

interface AlarmSchedular {
    fun schedule(item: AlarmItem)
    fun cancel(item: AlarmItem)
}