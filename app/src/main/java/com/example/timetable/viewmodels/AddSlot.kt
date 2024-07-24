package com.example.timetable.viewmodels


import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.example.timetable.database.DataOfSlot
import com.example.timetable.plus

class AddSlot:ViewModel() {

    fun onSaved(): DataOfSlot {
        var error =false
         val max=if(isLab.value){
         DataOfSlot(courseId = courseCode.value.trim(), courseName = headText.value.trim(), theory = theorySlot.toList().map { it.trim() }, theoryLocation = theoryLocation.value.trim(), lab = labSlot.toList().map { it.trim() }, labLocation = labLocation.value.trim(), isLab = isLab.value)
    }else{
        DataOfSlot(courseId = courseCode.value.trim(), courseName = headText.value.trim(), theory = theorySlot.toList().map { it.trim() }, theoryLocation = theoryLocation.value.trim(), lab = listOf(), labLocation = "", isLab = isLab.value)
        }
        if(headText.value.isEmpty()){
            headTextError.value=true
            error=true
        }
        if(courseCode.value.isEmpty()){
            courseCodeError.value=true
            error=true
        }
        if(theoryLocation.value.isEmpty()){
            theoryLocationError.value=true
            error=true
        }
        if(plus(theorySlot.toList()).isEmpty()){
            theorySlotError.value=true
            error=true
        }
        if(isLab.value){
            if(labLocation.value.isEmpty()){
                labLocationError.value=true
                error=true
            }

        if(plus(labSlot).isEmpty()){
            labSlotError.value=true
            error=true
        }
        }
        if (error){
            return initialState.value
        }
        cleanUp()
        return max
    }



    var _namesID= mutableStateListOf<DataOfSlot>()
    var namesID:MutableList<DataOfSlot> = _namesID
    var _onEdit= mutableStateOf(false)

    fun cleanUp() {
        headText.value=""
        courseCode.value=""
        theoryLocation.value=""
        labLocation.value=""
        theorySlot =  mutableStateListOf("","","","")
        labSlot =  mutableStateListOf("","","","")
        isLab.value=false
        headTextError.value=false
        courseCodeError.value=false
        theoryLocationError.value=false
        theorySlotError.value=false
        labLocationError.value=false
        labSlotError.value=false
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
    val initialState = mutableStateOf(
            DataOfSlot(
                "" ,
                "" ,
                listOf("" , "" , "" , "") ,
                "" ,
                listOf("" , "" , "" , "") ,
                "" ,
                false
            )
        )

    var headText = mutableStateOf("")
    var courseCode = mutableStateOf("")
    var theoryLocation = mutableStateOf("")
    var labLocation= mutableStateOf("")
    var theorySlot =  mutableStateListOf("","","","")
    var labSlot = mutableStateListOf("","","","")
    var isLab = mutableStateOf(false)

    var headTextError = mutableStateOf(false)
    var courseCodeError = mutableStateOf(false)
    var theoryLocationError = mutableStateOf(false)
    var labLocationError = mutableStateOf(false)
    var theorySlotError =  mutableStateOf(false)
    var labSlotError = mutableStateOf(false)
}