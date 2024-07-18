package com.example.timetable.viewmodels


import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.example.timetable.database.DataOfSlot

class AddSlot:ViewModel() {

    fun onSaved(): DataOfSlot {
         val max=if(isLab.value){
         DataOfSlot(courseId = courseCode.value.trim(), courseName = headText.value.trim(), theory = theorySlot.toList().map { it.trim() }, theoryLocation = theoryLocation.value.trim(), lab = labSlot.toList().map { it.trim() }, labLocation = labLocation.value.trim(), isLab = isLab.value)
    }else{
        DataOfSlot(courseId = courseCode.value.trim(), courseName = headText.value.trim(), theory = theorySlot.toList().map { it.trim() }, theoryLocation = theoryLocation.value.trim(), lab = listOf(), labLocation = "", isLab = isLab.value)
        }
        cleanUp()
        return max
    }



    fun cleanUp() {
        headText.value=""
        courseCode.value=""
        theoryLocation.value=""
        labLocation.value=""
        theorySlot =  mutableStateListOf("","","","")
        labSlot =  mutableStateListOf("","","","")
        isLab.value=false
    }

    private fun mix(theory: List<String?> ): SnapshotStateList<String> {
        val mix = mutableStateListOf("","","","")
        for (i in 0..3){
            mix[i]= theory[i].toString()
        }
        return mix
    }

    fun uploading(dataOfSlot: DataOfSlot) {
        headText.value = dataOfSlot.courseName
        courseCode.value = dataOfSlot.courseId
        theoryLocation.value = dataOfSlot.theoryLocation
        labLocation.value = dataOfSlot.labLocation?:""
        theorySlot = mix(dataOfSlot.theory)
        if(dataOfSlot.isLab){
        labSlot = mix(dataOfSlot.lab)
        isLab.value = true
        }
    }

    var headText = mutableStateOf("")
    var courseCode = mutableStateOf("")
    var theoryLocation = mutableStateOf("")
    var labLocation= mutableStateOf("")
    var theorySlot =  mutableStateListOf("","","","")
    var labSlot = mutableStateListOf("","","","")
    var isLab = mutableStateOf(false)
}