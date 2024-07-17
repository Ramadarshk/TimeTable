package com.example.timetable.old

import java.util.Locale

data class data(
    val name:String? ,
    val code:String? ,
    val slot: ArrayList<String> ,
    val lab: ArrayList<String> ,
    val labenabla:Boolean ,
    val labloc:String? ,
    val theory:String? ,
    val short:Boolean
){
    constructor():this("","", arrayListOf("","","",""),arrayListOf("","","","") ,false,"","",false)
}

class back(){
    val mon= listOf("","","TA","TB","","TC","TD","TE","","KTBB","","KTAA","","","KTCC","","KTD","")
    val tut=listOf("C","D","E","","","A","B","", "","KTC","","KTB","","","KTA","","KD","")
    val wt=listOf("","TE","TB","TA","","TC","TD","","","KB","","KA","","","KC","","KD","")
    val tht=listOf("E","","D","C","","B","A","","","KB","","KC","","","KA","","KD","")
    val ft=listOf("A","B","","","","D","C","E","","KTA","","KTB","","","KTC","","KD","")
    val st=listOf("","","C","D","","A","B","E","","KTBB","","KTCC","","","KTAA","","KTD","")
    val mol=listOf("L1","L2","L3","L4","","L25","L26","L27","L28")
    val tul=listOf("L5","L6","L7","L8","","L29","L30","L31","L32")
    val wl=listOf("L9","L10","L11","L12","","L33","L34","L35","L36")
    val thl=listOf("L13","L14","L15","L16","","L37","L38","L39","L40")
    val fl=listOf("L17","L18","L19","L20","","L41","L42","L43","L44")
    val sl=listOf("L21","L22","L23","L24","","L45","L46","L47","L48")


    fun ttsen(day:String,type:Int):List<String>{
        return when(day){
            "monday"-> when(type){
                1->mon
                else->mol
            }
            "tuesday"->when(type){
                1->tut
                else->tul
            }
            "wednesday"->when(type){
                1->wt
                else->wl
            }
            "thursday"->when(type){
                1->tht
                else->thl
            }
            "friday"->when(type){
                1->ft
                else->fl
            }
            "saturday"->when(type){
                1->st
                else->sl
            }
            else-> listOf()
        }
    }}
// fun slotalin(datalister:ArrayList<data>):HashMap<String,String>{
//     for(x in datalister){
//     }
// }
//data class data(
//    val name:String?,
//    val code:String?,
//    val slot:ArrayList<String>,
//    val lab:ArrayList<String>,
//    val labenabla:Boolean,
//    val labloc:String?,
//    val theory:String?,
//    val short:Boolean
//){
//    constructor():this("","",arrayListOf("","","",""),arrayListOf("","","",""),false,"","",false)
//}
fun filter(k:ArrayList<String>):ArrayList<String>{
    var kel:ArrayList<String> =arrayListOf()
    for(x in k){
        if(x.isNotEmpty())
            kel.add(rem(x))
    }
    return kel
}
fun rem(k:String):String{
    var re=""
    for(x in k){
        if((x.toString())!=" "){
            re=re+x
        }
    }
    return re.toUpperCase()
}
fun addk(th:ArrayList<String>):ArrayList<String>{
    var kth:ArrayList<String> =arrayListOf()
    for(x in th){
        kth.add("K$x")
    }
    return kth
}
fun puf(dt:data):HashMap<String,String>{
    var slotar:ArrayList<String>
    var rethash =HashMap<String,String>()
    var th:ArrayList<String> =filter(dt.slot)
    var la:ArrayList<String> =arrayListOf()

    if(dt.labenabla){
        la=filter(dt.lab)
    }
    if(dt.short){
        th=addk(th)
    }
    // print(th)
    //print(la)
    slotar=(th+la) as ArrayList<String>
    //println(slotar)
    for(x in slotar){
        rethash[x]=dt.code!!
    }
    // println(rethash)
    return rethash
}
fun hashl(datalist:ArrayList<data>,short:Boolean,long:Boolean):HashMap<String,String>{
    var hsh=HashMap<String,String>()
    val datae:ArrayList<data> = arrayListOf()
    if(short==long){
        if(!short){
            return hsh}
        else{
            for(x in datalist){

                hsh=(hsh+puf(x)) as HashMap<String,String>
            }
        }
    }
    else{
        for(y in datalist){
            if(short==y.short){
                datae.add(y)}
        }

        for(x in datae){

            hsh=(hsh+puf(x)) as HashMap<String,String>
        }}
    return hsh
}
fun myt(k:HashMap<String,String>,k1:List<String>):HashMap<Int,String>{
    var c=HashMap<Int,String>()
    var a=9
    for (x in k1){
        if(k.containsKey(x)){

            c[a]=x
        }
        a++
        if(a==13){
            a=1
        }
        if(a==6){
            a=9
        }
    }
    return c
}
fun labfilter(kel: List <String>):ArrayList<String>{
    var fi:ArrayList<String> =arrayListOf()
//var fli:ArrayList<Int>=arrayListOf()
    var kk=""
    var k=0
    for(x in kel){
        if(x.length==3){
            kk=x.substring(1..2)
            k=kk.toInt()}
        if(k%2!=0||x==""){
            fi.add(x)
        }
        else{
            fi.add("")
        }
    }
    return fi as ArrayList<String>
}
fun labcheck(s: String):Boolean{
    return s[0]=='L'
}
fun ix(dataList1: ArrayList<data> , s: String?):data {
    for(x in dataList1){
        if((x.code)?.toUpperCase(Locale.ROOT)  == s?.uppercase(Locale.ROOT)){
            return x
        }
    }
    return data("","", arrayListOf("","","","") , arrayListOf("","","","") ,false,"","",false)

}
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

fun main() {
    var  datalist:ArrayList<data> =arrayListOf(
        data("software Engineering","CSE1005",arrayListOf("D","TD","",""),arrayListOf("L35","L36","",""),true,"318-AB-2","301-AB-2",false)
        ,data("Applications differential","MAT1002",arrayListOf("A","TA","TAA",""),arrayListOf("","","",""),false,"318-cB-2","301-cB-2",true)
    )
    val obj:back= back()
    var days= arrayListOf("sunday","monday","tuesday","wednesday","thursday","friday","saturday")
    val hash=hashl(datalist,true,true)
    var im=""
    for(day1 in days ){
        val lab=labfilter(obj.ttsen(day1,0))
        val mt= myt(hash,obj.ttsen(day1,1)+lab)

        println(mt)}
}


