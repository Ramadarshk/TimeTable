package com.example.timetable.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.timetable.dataclass.Week
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar

class StableView:ViewModel() {
    private val splashShowFlow = MutableStateFlow(true)
    val isSplashShow = splashShowFlow.asStateFlow()

    init {
        viewModelScope.launch {
            delay(3000)
            splashShowFlow.value = false
        }
    }
    private val _days=listOf("monday","tuesday","wednesday","thursday","friday","saturday","sunday")
    private val calendar: Calendar = Calendar.getInstance()
    private var dayno = calendar.get(Calendar.DAY_OF_WEEK) - 2
    val day= mutableStateOf(_days[dayno])
    val days = _days
    val timeAmPm= mutableStateOf(true)
    var getDataOf= mutableStateOf("Please Select a Day By Pressing ->")
    val firebase= Firebase.database.getReference("TimeTable")
    var data:List<String> = mutableListOf()
    var data1:List<Week> = mutableListOf()
    fun getData(){
        firebase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                data = listOf()
                data1 = listOf()
                if (snapshot.exists()){
                    for (i in snapshot.children){
                        data += i.key.toString()
                        for (j in i.children){
                            data1 += j.getValue(Week::class.java)!!
                        }
                    }
                }
                firebase.keepSynced(true)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TAG", "Failed to read value.", error.toException())        }
        })
    }
    fun findTable():Week{
        firebase.keepSynced(true)
        data1.forEach {
            if(it.id==getDataOf.value){
                return it
            }
        }
        return Week()
    }
}