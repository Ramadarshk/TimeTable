package com.example.timetable.old

import com.example.timetable.database.DataOfSlot


class ConvertTheDataOfSlot {
    val tut = listOf(
        "TF1" ,
        "TA1" ,
        "E1" ,
        "D1" ,
        "B1" ,
        "" ,
        "TA2" ,
        "E2" ,
        "D2" ,
        "B2" ,
        "TF2" ,
        "" ,
        "" ,
        "" ,
        "STC2" ,
        "" ,
        "" ,
        "" ,
        "" ,
        "STC1" ,
        "" ,
        "" ,
        "" ,
        ""
    )
    val wt = listOf(
        "TCC1" ,
        "E1" ,
        "G1" ,
        "TBB1" ,
        "TDD1" ,
        "" ,
        "E2" ,
        "G2" ,
        "TBB2" ,
        "TDD2" ,
        "TCC2" ,
        "" ,
        "" ,
        "STA2" ,
        "TFF1" ,
        "" ,
        "" ,
        "STA1" ,
        "TFF2" ,
        "" ,
        "" ,
        "" ,
        "" ,
        ""
    )
    val tht = listOf(
        "TE1" ,
        "C1" ,
        "A1" ,
        "F1" ,
        "D1" ,
        "" ,
        "C2" ,
        "A2" ,
        "F2" ,
        "D2" ,
        "TE2" ,
        "" ,
        "" ,
        "" ,
        "" ,
        "" ,
        "" ,
        "" ,
        "" ,
        "" ,
        "" ,
        "" ,
        "" ,
        ""
    )
    val ft = listOf(
        "TAA1" ,
        "TD1" ,
        "B1" ,
        "G1" ,
        "C1" ,
        "" ,
        "TD2" ,
        "B2" ,
        "G2" ,
        "C2" ,
        "TAA2" ,
        "" ,
        "" ,
        "" ,
        "" ,
        "TEE1" ,
        "" ,
        "" ,
        "" ,
        "" ,
        "" ,
        "TEE2" ,
        "" ,
        ""
    )
    val st =
        listOf("TG1" , "TB1" , "TC1" , "A1" , "F1" , "" , "TB2" , "TC2" , "A2" , "F2" , "TG2" , "")
    val tul = listOf(
        "L1" ,
        "L2" ,
        "L3" ,
        "L4" ,
        "L5" ,
        "L6" ,
        "L31" ,
        "L32" ,
        "L33" ,
        "L34" ,
        "L35" ,
        "L36"
    )
    val wl = listOf(
        "L7" ,
        "L8" ,
        "L9" ,
        "L10" ,
        "L11" ,
        "L12" ,
        "L37" ,
        "L38" ,
        "L39" ,
        "L40" ,
        "L41" ,
        "L42"
    )
    val thl = listOf(
        "L13" ,
        "L14" ,
        "L15" ,
        "L16" ,
        "L17" ,
        "L18" ,
        "L43" ,
        "L44" ,
        "L45" ,
        "L46" ,
        "L47" ,
        "L48"
    )
    val fl = listOf(
        "L19" ,
        "L20" ,
        "L21" ,
        "L22" ,
        "L23" ,
        "L24" ,
        "L49" ,
        "L50" ,
        "L51" ,
        "L52" ,
        "L53" ,
        "L54"
    )
    val sl = listOf(
        "L25" ,
        "L26" ,
        "L27" ,
        "L28" ,
        "L29" ,
        "L30" ,
        "L55" ,
        "L56" ,
        "L57" ,
        "L58" ,
        "L59" ,
        "L60"
    )

    fun listOfSlotInDay(day: String , type: Int): List<String> {
        return when (day) {
            "tuesday" -> when (type) {
                1 -> tut
                else -> tul
            }

            "wednesday" -> when (type) {
                1 -> wt
                else -> wl
            }

            "thursday" -> when (type) {
                1 -> tht
                else -> thl
            }

            "friday" -> when (type) {
                1 -> ft
                else -> fl
            }

            "saturday" -> when (type) {
                1 -> st
                else -> sl
            }

            else -> when (type) {
                1 -> listOf()
                else -> listOf()
            }

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

    fun labFilter(lab: List<String?>): List<String?> {
        val filter = lab.filter {
            it != null && it != ""
        }
        val inter: List<Int> = filter.map {
            it?.substring(1)?.toInt() ?: 0
        }.sorted()
        var inter1 = listOf<String?>()
        for (x in 0..(inter.size - 1) step 2) {
            inter1 += "L" + inter[x]
        }
        return inter1/*.sorted()*/
    }

    fun convertToHash(slot: List<String?> , courseCode: String): HashMap<String , String> {
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

    /*    fun mytt(k1:List<String>): HashMap<Int , String> {
            val c=HashMap<Int,String>()
            var a=8
            for (x in k1){
                c[a]=x
                a++
                if(a==13){
                    a=1
                }
            }
            return c
        }*/
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
        return "${timeForamt(it , b = b)}-${timeForamt(it + a , m , b)}"
    }

    private fun timeForamt(it: Int , m: String = "00" , b: Boolean): String {
        if (b) {
            return if (it < 12) {
                "${it%12}:$m PM"
            } else {
                "${it%12}:$m AM"
            }
        } else {
            return "$it:$m"
        }
    }
}
    fun main() {
    val obj=ConvertTheDataOfSlot()
    val day="wednesday"
    val slots=listOf(DataOfSlot ("CSE1002","Crypto",listOf("TD2","D2","",""),"CB101",listOf("L37","L38","L2","L3"),"CB202",true))
    val all=obj.dataOfSlotConverter(slots)
    val slotInDay = obj.myListMapsToTime(all,obj.listOfSlotInDay(day = day,1)+obj.listOfSlotInDay(day = day,0),8,20)
   // println(obj.mytt(obj.listOfSlotInDay(day = day,0)))
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
