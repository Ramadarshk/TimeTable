package com.example.timetable.old

import com.example.timetable.database.DataOfSlot
import com.example.timetable.dataclass.Week
import com.example.timetable.viewmodels.StableView


class ConvertTheDataOfSlot(semWeek: Week) {
    private var tut = semWeek.tuesday //arrayListOf("TEE1","A1","B1","C1","D1","","E2","A2","TBB2","C2","TDD2","","","","","","","","SE1","","G2","","","")
    private var wed = semWeek.wednesday//arrayListOf("TG1","D1","F1","E1","B1","","E2","D2","F2","B2","TCC2","","","","","","","","SC1","","","","","")
    private var thu = semWeek.thursday//arrayListOf("TF1","TC1","TD1","TA1","TFF1","","B2","F2","TD2","TA2","TG2","","","","","","","","","","","","","")
    private var fri = semWeek.friday//arrayListOf("TCC1","TB1","TAA1","TE1","F1","","C2","TB2","TAA2","TE2","TF2","","","","G1","","","","","","G2","SD1","","")
    private var sat = semWeek.saturday//arrayListOf("TDD1","C1","A1","TBB1","E1","","D2","TC2","A2","SF1","TEE2","","","","","G1","","","","","","","","")
    private var tutL = semWeek.tuesdayLab//arrayListOf("L1", "L2" ,"L3", "L4" ,"L5" ,"L6" ,"L31","L32","L33","L34","L35","L36")
    private var wedL = semWeek.wednesdayLab//arrayListOf("L7","L8","L9","L10","L11","L12","L37","L38","L39","L40","L41","L42")
    private var thuL = semWeek.thursdayLab//arrayListOf("L13","L14","L15","L16","L17","L18","L43","L44","L45","L46","L47","L48")
    private var friL = semWeek.fridayLab//arrayListOf("L19","L20","L21","L22","L23","L24","L49","L50","L51","L52","L53","L54")
    private var satL = semWeek.saturdayLab//arrayListOf("L25","L26","L27","L28","L29","L30","L55","L56","L57","L58","L59","L60")
    fun listOfSlotInDay(day: String , type: Int): List<String> {
        return when(day){
            "tuesday"->when(type){
                1->tut
                else->tutL
            }
            "wednesday"->when(type){
                1->wed
                else->wedL
            }
            "thursday"->when(type){
                1->thu
                else->thuL
            }
            "friday"->when(type){
                1->fri
                else->friL
            }
            "saturday"->when(type){
                1->sat
                else->satL
            }
                else-> listOf()
            }
        }


    fun dataOfSlotConverter(listConverter: List<DataOfSlot>): HashMap<String , String> {
        val hah = HashMap<String , String>()
        for (x in listConverter) {
            if (x.isLab) {
                hah.putAll(convertToHash(labFilter(x.lab) , x.courseId))
            }

            hah.putAll(convertToHash(x.theory , x.courseId))
        }
        return hah
    }

    private fun labFilter(lab: List<String?>): List<String?> {
        val filter = lab.filter {
            it != null && it != ""
        }
        val inter: List<Int> = filter.map {
            it?.substring(1)?.toInt() ?: 0
        }.sorted()
        val inter1 = mutableListOf<String?>()
        for (x in inter.indices step 2) {
            inter1 += "L" + inter[x]
        }
        return inter1/*.sorted()*/
    }

    private fun convertToHash(slot: List<String?> , courseCode: String): HashMap<String , String> {
        val hah = HashMap<String , String>()
        val k = slot.filter {
            it != null && it != "" && it != " "
        }.map {
            it?.trim()
        }
        k.forEach {
            hah[it!!] = courseCode
        }
        return hah
    }

    fun myListMapsToTime(
        k: HashMap<String , String> ,
        k1: List<String> ,
        startOfDay: Int ,
        lastOfDay: Int
    ): HashMap<Int , String> {
        val c = HashMap<Int , String>()
        var a = startOfDay
        for (x in k1) {
            if (k.containsKey(x)) {
                c[a] = x
            }
            a++
            /*if(a==13){
                a=1
            }*/
            if (a == lastOfDay) {
                a = 8
            }
        }
        return c
    }

    fun findSlotData(s: String? , slots: List<DataOfSlot>): DataOfSlot {
        if (s != null) {
            for (x in slots) {
                if (x.courseId == s) {
                    return x
                }
            }
        }
        return DataOfSlot(
            courseId = "" ,
            courseName = "" ,
            theory = listOf("" , "" , "" , "") ,
            theoryLocation = "" ,
            lab = listOf("" , "" , "" , "") ,
            labLocation = "" ,
            isLab = false
        )
    }

    fun labCheck(s: String): Boolean {
        return s[0] == 'L'
    }

    fun time(it: Int , s: String , b: Boolean): String {
        var a = 0
        var m = "50"
        if (s[0] == 'L') {
            a = 1
            m = "40"
        }
        return "${timeFormat(it , b = b)}-${timeFormat(it + a , m , b)}"
    }

    private fun timeFormat(it: Int , m: String = "00" , b: Boolean): String {
        return if (b) {
            if (it < 12) {
                "${it%12}:$m PM"
            } else {
                "${it%12}:$m AM"
            }
        } else {
            "$it:$m"
        }
    }
}
    fun main() {
        val view= StableView()
    val obj=ConvertTheDataOfSlot(view.findTable())
    val day="wednesday"
    val slots=listOf(DataOfSlot ("CSE1002","Crypto",listOf("TD2","D2","",""),"CB101",listOf("L37","L38","L2","L3"),"CB202",true))
    val all=obj.dataOfSlotConverter(slots)
    val slotInDay = obj.myListMapsToTime(all,obj.listOfSlotInDay(day = day,1)+obj.listOfSlotInDay(day = day,0),8,20)
    val timeZone= IntRange(8,20).toList()
    /*for (x in timeZone){
        println(x)
    }*/
   // println(timeZone.joinToString(separator = "+"))
    timeZone.forEach {
        if (slotInDay.containsKey(it)){
           println(obj.findSlotData(all[slotInDay[it]],slots))

        }
    }
    println(all)
    println(slotInDay)
    println("Hello, world!!!")
}
