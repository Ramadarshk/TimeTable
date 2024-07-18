package com.example.timetable.old

class back(){
    var tutL= arrayListOf("L1", "L2" ,"L3", "L4" ,"L5" ,"L6" ,"L31","L32","L33","L34","L35","L36")
    var wedL= arrayListOf("L7","L8","L9","L10","L11","L12","L37","L38","L39","L40","L41","L42")
    var thuL= arrayListOf("L13","L14","L15","L16","L17","L18","L43","L44","L45","L46","L47","L48")
    var friL= arrayListOf("L19","L20","L21","L22","L23","L24","L49","L50","L51","L52","L53","L54")
    var satL= arrayListOf("L25","L26","L27","L28","L29","L30","L55","L56","L57","L58","L59","L60")
    var tut= arrayListOf("TEE1","A1","B1","C1","D1","","E2","A2","TBB2","C2","TDD2","","","","","","","","SE1","","G2","","","")
    var wed= arrayListOf("TG1","D1","F1","E1","B1","","E2","D2","F2","B2","TCC2","","","","","","","","SC1","","","","","")
    var thu= arrayListOf("TF1","TC1","TD1","TA1","TFF1","","B2","F2","TD2","TA2","TG2","","","","","","","","","","","","","")
    var fri= arrayListOf("TCC1","TB1","TAA1","TE1","F1","","C2","TB2","TAA2","TE2","TF2","","","","G1","","","","","","G2","SD1","","")
    var sat= arrayListOf("TDD1","C1","A1","TBB1","E1","","D2","TC2","A2","SF1","TEE2","","","","","G1","","","","","","","","")


    fun ttsen(day:String,type:Int):List<String>{
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
            else->when(type){
                1-> listOf()
                else-> listOf()
            }

        }
    }
    fun hashl():HashMap<String,String>{
        var hash=hashMapOf<String,String>()
        //  var hash1=hashMapOf<Any?,Any?>()
        //val day="friday"
        hash["A2"]= "DBMS"
        hash["TA2"]="DBMS"
        hash["B1"]="STATS"
        hash["TB1"]="STATS"
        hash["C1"]="CN"
        hash["TC1"]="CN"
        hash["D2"]="CRYPTO"
        hash["TD2"]="CRYPTO"
        hash["F1"]="STS"
        hash["TF1"]="STS"
        hash["F2"]="HAPPY"
        hash["TF2"]="HAPPY"
        hash["G1"]="DT"
        hash["TG1"]="DT"
        hash["L37"]="CRYPTO"
        hash["L53"]="CN"
        hash["L51"]="STATS"
        hash["L41"]="DBMS"
        return hash}
    fun myt(k:HashMap<String,String>,k1:List<String>):HashMap<Int,String>{
        var c=HashMap<Int,String>()
        var a=8
        for (x in k1){
            if(k.containsKey(x)){

                c[a]=x
            }
            a++
            if(a==13){
                a=1
            }
        }
        return c

    }
    fun Location(loc:String?,s:String?): String? {
        var hash=hashMapOf<String,String>()
        hash["DBMS"]="CB-215"
        hash["CN"]="CB-G11"
        hash["STATS"]="CB-325"
        hash["CRYPTO"]="CB-112"
        hash["STS"]="CB-215"
        hash["HAPPY"]="CB-G17"
        hash["DT"]="CB-G09"
        var hash1=hashMapOf<String,String>()
        hash1["DBMS"]="CB-101"
        hash1["CN"]="CB-102"
        hash1["CRYPTO"]="CB-301"
        hash1["STATS"]="CB-122"
        if(s?.let { labCheck(it) } == true){
            return hash1[loc]
        }
        else{
            return hash[loc]
        }
    }
    fun labCheck(s: String):Boolean{
        return s[0]=='L'
    }
/*    private fun checktime(n:Int , m:Int=0):String{
        when(m){
            0->{ if(n<8||n==12){
                return "$n:00 PM"
            }
            else{
                return "$n:00 AM"
            }}
            else->{
                if(n<8||n==12){
                    return "$n:$m PM"
                }
                else{
                    return "$n:$m AM"
                }
            }
        }
    }
    fun time(n:Int,s:String?):String{
        var a=0
        var b=50
        if(s?.let { labCheck(it) } == true){
            a=1
            b=40}
        return "${checktime(n)} - ${checktime(n + a,b)}"
    }*/
fun check(n:Int ,m:String,hour12:Boolean):String{
    var hr=n
    hr=hr%12
    if(hr==0){
        hr=12
    }
    var h12=0
    var PM="PM"
    var AM="AM"
    if(!hour12){
        if(hr!=12){
            h12=12}
        PM=""
        AM=""
    }
    println("hr= $hr m= $m ")
    return if(hr<8||hr==12){"${hr+h12}:$m $PM"}
    else{
        "$hr:$m $AM"
    }
}
    fun time(hr:Int ,slot:String, hour12:Boolean):String{
        var a=0
        var m="50"
        if(slot[0]=='L'){
            a=1
            m="40"
        }
        else if(slot[0]=='K'){
            a=1
        }
        val k="00"
        println(slot)
        return "${check(hr,k,hour12)} - ${check(hr+a,m,hour12)}"
    }

}