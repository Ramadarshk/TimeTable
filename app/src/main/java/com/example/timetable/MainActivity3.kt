package com.example.timetable


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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



    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        TimeTableTheme {
            Column(){
                MainActivity().DailySlotCard(Modifier.padding(vertical = 10.dp) ,data = DataSlot())
                MainActivity().But(fnu = { /*TODO*/ } , modifier = Modifier , day = "Monday")
            }
        }
    }
}