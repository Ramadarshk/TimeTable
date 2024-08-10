package com.example.timetable


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.InsertEmoticon
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.timetable.dataclass.DataSlot
import com.example.timetable.ui.theme.TimeTableTheme
import kotlinx.serialization.Serializable
class MainActivity3 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TimeTableTheme {
              //  val dataStore1 = dataStore.data.collectAsState(initial = Preferences())
                val nav = rememberNavController()
                NavHost(navController = nav, startDestination =Screen1) {
                    composable<Screen1> {
                        MainActivity().OnScreen()
                    }
                    composable<Screen2> {
                        MainActivity2().MyScreen{
                            nav.navigate(Screen1)
                        }
                    }
                }
            }
        }
    }

    @Serializable
    object Screen1
    @Serializable
    object Screen2


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
                    if(sizeNil)
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

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        TimeTableTheme {
            Column(){
                DailySlotCard(Modifier.padding(vertical = 10.dp) ,data = DataSlot(
                    title = "NO CLASSES TODAY TIME TO RELAX" ,
                    timeTitle = "" ,
                    locationTitle = "" ,
                    slotId = "" ,
                    lab = false
                ))
                MainActivity().But(fnu = { /*TODO*/ } , modifier = Modifier , day = "Monday")
            }
        }
    }
}