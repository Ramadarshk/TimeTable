package com.example.timetable

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.timetable.database.DataOfSlot
import com.example.timetable.ui.theme.TimeTableTheme

class MainActivity3 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TimeTableTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android" ,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SlotScreen(
    slotInfo: DataOfSlot = DataOfSlot(
        "" ,
        "" ,
        listOf("A1" , "V1" , "" , "") ,
        "" ,
        listOf("v%" , "V2" , "" , "") ,
        "" ,
        true
    ) , onEdit: () -> Unit = {}
) {
    // Box(modifier = modifier.fillMaxSize()){
    Card(
        Modifier
            .fillMaxWidth()

            .padding(horizontal = 15.dp , vertical = 10.dp) ,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer) ,
        shape = RoundedCornerShape(20.dp) ,
        elevation = CardDefaults.cardElevation(14.dp)
    ) {
        Box(modifier = Modifier){
            IconButton(onClick = onEdit,
                Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 10.dp)) {
                Icon(imageVector = Icons.Default.Edit , contentDescription = null,tint=  MaterialTheme.colorScheme.onPrimaryContainer)
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp) , verticalArrangement = Arrangement.Center
            ) {
                Row(Modifier.fillMaxWidth()) {
                    Text(
                        text = slotInfo.courseName ,
                        modifier = Modifier.weight(1f) ,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = slotInfo.courseId ,
                        modifier = Modifier.weight(1f) ,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(Modifier.weight(.39f))
                }
                Spacer(Modifier.height(10.dp))
                SessionDetails(
                    sessionType = "Theory" ,
                    sessionSlot = slotInfo.theory ,
                    sessionLocation = slotInfo.theoryLocation
                )
                if (slotInfo.isLab) {
                    SessionDetails(
                        sessionType = "Lab" ,
                        sessionSlot = slotInfo.lab ,
                        sessionLocation = slotInfo.labLocation!!
                    )
                }
            }
        }
    }
    //}
}


@Composable
fun SessionDetails(sessionType: String , sessionSlot: List<String?> , sessionLocation: String) {

    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = sessionType ,
            Modifier.weight(.8f) ,
            textAlign = TextAlign.Center ,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Text(
            text = plus(sessionSlot) ,
            Modifier.weight(1f) ,
            textAlign = TextAlign.Center ,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Text(
            text = sessionLocation ,
            Modifier.weight(.8f) ,
            textAlign = TextAlign.Center ,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}
fun plus(sessionSlot: List<String?>): String {
    val kel = emptyList<String?>().toMutableList()
    sessionSlot.filter {
        it != null && it != ""
    }.forEach {
        kel += it
    }
    return kel.joinToString(separator = "+")
}
@Composable
fun Greeting(name: String , modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!" ,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TimeTableTheme {
        Greeting("Android")
    }
}