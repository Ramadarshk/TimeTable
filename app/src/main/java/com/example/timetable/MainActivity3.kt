package com.example.timetable

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.timetable.database.RoomDB
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


    @Composable
    fun Greeting(name: String , modifier: Modifier = Modifier) {
        val viewModel: AddSlot = viewModel()
        val data = RoomDB.getDataBase(applicationContext)
        val list by data.slotDao().getAll().collectAsState(initial = emptyList())
        var dialogOpen by remember { mutableStateOf(false) }
        Scaffold(modifier = Modifier.fillMaxSize() , topBar = {
/*           Row(
                verticalAlignment = Alignment.CenterVertically ,
                horizontalArrangement = Arrangement.SpaceBetween ,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            ) {
                Text(
                    text = "SLOT INFO" ,
                    modifier = Modifier
                        .padding(start = 16.dp , top = 5.dp) ,
                    textAlign = TextAlign.Start
                )
                IconButton(
                    onClick = { dialogOpen = true } ,
                    modifier = Modifier.padding(end = 16.dp)
                ) {
                    Icon(Icons.Default.Add , contentDescription = null)
                }
            }*/

        }) { innerPadding ->
            Box(Modifier.padding(innerPadding)) {
                Box(modifier = Modifier.padding(innerPadding)) {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(list) {
                            Text(text = "${it}", modifier = Modifier.padding(innerPadding).align(Alignment.Center))
                        }
                    }
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview2() {
        TimeTableTheme {
            Greeting("Android")
        }
    }
}