package com.example.timetable

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.InsertEmoticon
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.dataStore
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.timetable.database.RoomDB
import com.example.timetable.dataclass.DataSlot
import com.example.timetable.dataclass.Week
import com.example.timetable.old.ConvertTheDataOfSlot
import com.example.timetable.protodatastore.Preferences
import com.example.timetable.protodatastore.PreferencesSerialization
import com.example.timetable.ui.theme.TimeTableTheme
import com.example.timetable.viewmodels.StableView
import kotlinx.coroutines.launch

val Context.dataStore by dataStore("preferences.Json", PreferencesSerialization)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TimeTableTheme() {
                val dataStore1 = dataStore.data.collectAsState(initial = Preferences())
                val view: StableView = viewModel()
                val int =this.intent.getStringExtra("sem")
                view.getDataOf.value =int?: dataStore1.value.sem
                Log.d("data" , dataStore1.value.sem)
                view.getData()
                val week: Week = view.findTable()
                Log.d("week", week.toString())
                Log.d("week",dataStore1.value.sem)
                //Text(text = week.toString())
                OnScreen(viewModel = view,week = week)
            }
        }
    }

    @Composable
    fun getDataDay(day: String , view: StableView = viewModel(),week1: Week=Week()): List<DataSlot> {
        val data = RoomDB.getDataBase(applicationContext)
        val dataStore1 = dataStore.data.collectAsState(initial = Preferences())
        val slots by data.slotDao().getAll().collectAsState(initial = emptyList())
        view.getDataOf.value = dataStore1.value.sem
        Log.d("data" , dataStore1.value.sem)
        if (view.check(week1)){
             view.getData()
        }
        Text(text = view.getDataOf.value, color = MaterialTheme.colorScheme.background, modifier = Modifier.height(2.dp))
        val week = view.findTable()
        Log.d("week", week.toString())
        //Text(text = week.toString())
        val listOfSlot: MutableList<DataSlot> = mutableListOf()
        val obj= ConvertTheDataOfSlot(week)
        val all=obj.dataOfSlotConverter(slots)
        //Text(text = all.toString())
        val slotInDay = obj.myListMapsToTime(all,obj.listOfSlotInDay(day = day,1)+obj.listOfSlotInDay(day = day,0),8,20)
       //Text(text = slotInDay.toString())
        val timeZone= IntRange(8,20).toList()
        /*for (x in timeZone){
            println(x)
        }*/
        // println(timeZone.joinToString(separator = "+"))
        timeZone.forEach {
            if (slotInDay.containsKey(it)){
                val slot=obj.findSlotData(all[slotInDay[it]],slots)
                val isLabSlot=obj.labCheck(slotInDay[it]!!)
                val slotLocation=if(isLabSlot){
                    slot.labLocation
                }else{
                    slot.theoryLocation

                }!!
                val time=if(view.timeAmPm.value){
                    obj.time(it,slotInDay[it]!!,true)
                }else{
                    obj.time(it,slotInDay[it]!!,false)
                }
                listOfSlot += DataSlot(slot.courseName,
                    time ,slotLocation,slot.courseId,isLabSlot)
            }
        }
        return listOfSlot.toList()
    }

@Preview(showBackground = true)
@Composable
fun OnScreen(
    viewModel: StableView = viewModel() ,
    week: Week = Week()
) {
    Scaffold(modifier = Modifier
        .background(MaterialTheme.colorScheme.background)
        .padding(top = 40.dp , bottom = 40.dp)
        .fillMaxSize() , topBar = {
            val scope = rememberCoroutineScope()
        val dataStore1 = dataStore.data.collectAsState(initial = Preferences())
        viewModel.getDataOf.value=dataStore1.value.sem
        viewModel.timeAmPm.value=dataStore1.value.timeAmPm
        DateRow(viewModel = viewModel) {
            viewModel.timeAmPm.value = !viewModel.timeAmPm.value
            scope.launch {
                dataStore.updateData {
                    it.copy(timeAmPm = viewModel.timeAmPm.value)
                }
            }
        }
        
    }) { innerPadding ->
        val day=viewModel.day.value
        Box(
            Modifier
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)) {
            Column(
                Modifier
                    .fillMaxSize()
                    .animateContentSize(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioLowBouncy ,
                            stiffness = Spring.StiffnessLow
                        )
                    )){
           val dataList = getDataDay(day,viewModel,week)
            if (dataList.isEmpty()) {
                val data0 = DataSlot(
                    title = "NO CLASSES ON THIS ${viewModel.day.value.uppercase()}" ,
                    timeTitle = "" ,
                    locationTitle = "" ,
                    slotId = viewModel.day.value + 0 ,
                    lab = false
                )
                DailySlotCard(data = data0 , sizeNil = true)
            } else {
                LazyColumn(
                    Modifier
                        .fillMaxSize()
                        .animateContentSize(
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioLowBouncy ,
                                stiffness = Spring.StiffnessLow
                            )
                        )) {
                    items(dataList) {
                        DailySlotCard(data = it)
                        Spacer(Modifier.height(10.dp))
                    }
                }
            }
        }
        }
    }
}


@Composable
fun DailySlotCard(modifier: Modifier=Modifier,data: DataSlot , sizeNil: Boolean = false) {
    //val data=DataSlot("hello","10:00am-11:00pm","CB-09","0",false)
    Card(
        modifier
            .height(100.dp)
            .padding(horizontal = 15.dp) ,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer) ,
        shape = RoundedCornerShape(20.dp) ,
        elevation = CardDefaults.cardElevation(14.dp)
    ) {
        Row(
            Modifier
                .fillMaxHeight()
                .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(20.dp))
                .padding(start = 15.dp , end = 15.dp)
                .weight(1f)
        ) {

            Icon(
                imageVector = if (data.lab) {
                    Icons.Default.Computer
                } else if (sizeNil) {
                    Icons.Default.InsertEmoticon
                } else {
                    Icons.Default.School
                } , contentDescription = null , modifier = Modifier
                    .size(100.dp)
                    .padding(5.dp) , tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Column(
                Modifier
                    .weight(4f)
                    .fillMaxSize() ,
                verticalArrangement = Arrangement.SpaceEvenly ,
            ) {
                val col = MaterialTheme.colorScheme.onPrimaryContainer
                Text(text = data.title , color = col)
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween ,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = data.timeTitle , color = col)
                    Text(text = data.locationTitle , color = col)
                }
            }
        }
    }
}


@Composable
fun DateRow(viewModel: StableView = viewModel() , onChangeTime: () -> Unit = {}) {
   // var timer by remember { mutableStateOf(true) }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 25.dp , end = 15.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = viewModel.day.value.uppercase() ,
                color = MaterialTheme.colorScheme.primary ,
                fontWeight = FontWeight.ExtraBold ,
                fontSize = 20.sp,
                modifier = Modifier.weight(1.5f)
            )
            TextButton(onClick = { Intent(this@MainActivity , MainActivity2::class.java).also { startActivity(it)} }, modifier = Modifier ){
                Text(text = when(viewModel.getDataOf.value){
                    stringResource(id = R.string.please_select_a_day_by_pressing)->{
                        "SLOT VIEW"
                    }
                    else ->{
                        viewModel.getDataOf.value}}, color = MaterialTheme.colorScheme.primary, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
            IconButton(onClick = onChangeTime,Modifier) {
                if(viewModel.timeAmPm.value){
                    Icon(imageVector = Icons.Filled.Timer , contentDescription = "AM/PM")}
                else{
                    Icon(imageVector = Icons.Outlined.Timer , contentDescription = "24")}
            }
        }
        Row(
            Modifier
                .horizontalScroll(rememberScrollState())
        ) {
            val days =
                viewModel.days/*listOf("Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday")*/
            days.forEach {
                But(fnu = { viewModel.day.value = it } , modifier = Modifier , day = it)
                //Text(text = it, modifier = Modifier.padding(16.dp))
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
    }
}

@Composable
fun But(fnu: () -> Unit , modifier: Modifier , day: String , elev: Dp = 12.dp) {
    TextButton(
        onClick = fnu ,
        modifier = modifier ,
        colors = ButtonDefaults.textButtonColors(MaterialTheme.colorScheme.onPrimary) ,
        border = BorderStroke(
            2.dp ,
            MaterialTheme.colorScheme.secondary
        ) ,
        elevation = ButtonDefaults.elevatedButtonElevation(elev)
    ) {
        Text(text = day.uppercase() , color = MaterialTheme.colorScheme.onPrimaryContainer)
    }
    Spacer(Modifier.width(10.dp))
}


}