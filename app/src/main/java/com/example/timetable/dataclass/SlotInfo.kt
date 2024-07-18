package com.example.timetable.dataclass

data class SlotInfo(
    val courseId: String ,
    val courseName: String ,
    val theory: List<String> ,
    val theoryLocation: String ,
    val lab: List<String?> ,
    val labLocation: String? ,
    val isLab: Boolean ,
) {
    constructor() : this(
        courseId = "" ,
        courseName="" ,
        theory=listOf("" , "" , "" , "") ,
        theoryLocation = "" ,
        lab=listOf("" , "" , "" , "") ,
        labLocation = "" ,
        isLab = false
    )
}
