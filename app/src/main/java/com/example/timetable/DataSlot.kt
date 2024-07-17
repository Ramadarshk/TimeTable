package com.example.timetable

data class DataSlot(
    val title: String ,
    val timeTitle: String ,
    val locationTitle: String ,
    val slotId: String ,
    val lab: Boolean ,
) {
    constructor() : this(
        title = "" ,
        timeTitle = "" ,
        locationTitle = "" ,
        slotId = "" ,
        lab = false
    )
}
