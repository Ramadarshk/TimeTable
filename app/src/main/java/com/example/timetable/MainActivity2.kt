package com.example.timetable

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.timetable.database.DataOfSlot
import com.example.timetable.database.RoomDB
import com.example.timetable.protodatastore.Preferences
import com.example.timetable.ui.theme.TimeTableTheme
import com.example.timetable.viewmodels.AddSlot
import com.example.timetable.viewmodels.StableView
import kotlinx.coroutines.launch

class MainActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TimeTableTheme {
                MyScreen()
            }
        }
    }


    @Composable
    fun SelectedTable( view: StableView = viewModel()) {
        var show by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()
        val dataStore1 = dataStore.data.collectAsState(initial = Preferences())
        view.getDataOf.value=dataStore1.value.sem
        Box(
            Modifier
                .padding(top = 10.dp , start = 10.dp , end = 10.dp)
                .border(1.dp , MaterialTheme.colorScheme.onBackground , RoundedCornerShape(20.dp))) {
            val showDown: () -> Unit = {
                show = !show
                scope.launch {
                   view.getData()
                }
            }
            Column(Modifier/*.padding( start = 10.dp , end = 10.dp)*/) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .border(
                            1.dp ,
                            MaterialTheme.colorScheme.onBackground ,
                            RoundedCornerShape(20.dp)
                        ),
                    horizontalArrangement = Arrangement.SpaceBetween ,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = showDown,
                        modifier = Modifier.weight(8.5f)) {
                        Text(
                            text = view.getDataOf.value,
                            textAlign = TextAlign.Center,
                            fontSize = 17.sp
                        )
                    }
                    IconButton(onClick = showDown , Modifier.weight(1.5f)) {
                        Icon(
                            imageVector = if (show) {
                                Icons.Default.KeyboardArrowUp
                            } else {
                                Icons.Default.KeyboardArrowDown
                            } , contentDescription = null
                        )
                    }
                }
                AnimatedVisibility(visible = show) {
                    Box(
                        Modifier
                            .fillMaxWidth()
                    ) {
                        Column(
                            Modifier.fillMaxWidth() ,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            view.data.forEach { sem ->
                                TextButton(onClick = {
                                    view.getDataOf.value = sem
                                    scope.launch {
                                        dataStore.updateData {
                                            it.copy(sem = sem)
                                        }
                                    }
                                    show = false
                                }) {
                                    Text(text = sem , fontWeight = FontWeight.ExtraBold,color = MaterialTheme.colorScheme.onBackground)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun SlotScreen(slotInfo: DataOfSlot=DataOfSlot("","", listOf("","","",""),"",listOf("","","",""),"",true),onEdit:()->Unit={}) {
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
    fun Dialog(
        viewModel: AddSlot = viewModel() ,
        onSave: () -> Unit = {} ,
        onDelete: () -> Unit = {} ,
        onEdit:Boolean=false ,
        onDismiss: () -> Unit = {}
    ) {

        AlertDialog(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.fillMaxWidth() ,
            onDismissRequest = onDismiss ,
            confirmButton = {
                TextButton(onClick = onSave) {
                    Text(text = "OK")
                }
            } ,
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text(text = "Cancel")
                }
            } ,
            title = {
                Box(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Add Slot")
                    if(onEdit){
                IconButton(onClick = onDelete ,
                    Modifier
                        .align(Alignment.TopEnd)
                        .padding(end = 10.dp)) {
                    Icon(imageVector = Icons.Default.Delete , contentDescription = null,tint=  MaterialTheme.colorScheme.onPrimaryContainer)
                }} }},
            text = {

                var headText by viewModel.headText
                var courseCode by viewModel.courseCode
                var theoryLocation by viewModel.theoryLocation
                var labLocation by viewModel.labLocation
                var isLab by viewModel.isLab
                val keyOption = KeyboardOptions(
                    KeyboardCapitalization.Characters ,
                    imeAction = ImeAction.Next ,
                    keyboardType = KeyboardType.Text
                )
                val keyDone=KeyboardOptions(
                    KeyboardCapitalization.Characters ,
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Text
                )
                Box(modifier = Modifier , contentAlignment = Alignment.TopCenter) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        OutlinedTextField(value = headText , onValueChange = {
                            headText = it.uppercase()
                        } , label = { Text(text = "Course Name") } , keyboardOptions = keyOption)
                        OutlinedTextField(value = courseCode , onValueChange = {
                            courseCode = it.uppercase()
                        } , label = { Text(text = "Course Code") } , keyboardOptions = keyOption)
                        Spacer(Modifier.height(10.dp))
                        SlotSet("Theory" , 1 , viewModel , keyOption)
                        Spacer(Modifier.height(10.dp))
                        OutlinedTextField(
                            value = theoryLocation ,
                            onValueChange = {
                                theoryLocation = it.uppercase()
                            } ,
                            label = { Text(text = "Theory Location") } ,
                            keyboardOptions = if(isLab){keyOption}else{keyDone}
                        )
                        Row(
                            Modifier
                                .padding(horizontal = 5.dp)
                                .fillMaxWidth() ,
                            verticalAlignment = Alignment.CenterVertically ,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Lab")
                            Switch(checked = isLab , onCheckedChange = { isLab = it } , Modifier)
                        }
                        if (isLab) {
                            SlotSet("Lab" , 2 , viewModel , keyOption )
                            Spacer(Modifier.height(10.dp))
                            OutlinedTextField(value = labLocation , onValueChange = {
                                labLocation = it.uppercase()
                            } , label = { Text(text = "Lab Location") },keyboardOptions=keyDone)
                        }
                    }
                }
            })

    }



    @Composable
    fun SlotSet(
        session: String ,
        init: Int ,
        viewModel: AddSlot = viewModel() ,
        keyOption: KeyboardOptions ,
    ) {
        val theorySlot = if (init == 1) {
            viewModel.theorySlot
        } else  {
            viewModel.labSlot
        }
        var itemNo by remember { mutableIntStateOf(init) }
        var state by remember { mutableStateOf(false) }
        Column(Modifier) {
            Row(
                Modifier
                    .padding(horizontal = 10.dp)
                    .fillMaxWidth() ,
                verticalAlignment = Alignment.CenterVertically ,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "$session Slot" , modifier = Modifier)
                IconButton(onClick = {
                    itemNo = change(itemNo , init , state)
                    if (itemNo == 4 && !state) {
                        state = true
                    }
                    if (itemNo == init && state) {
                        state = false
                    }
                } , modifier = Modifier) {
                    if (state)
                        Icon(Icons.Default.Remove , contentDescription = null)
                    else {
                        Icon(Icons.Default.Add , contentDescription = null)
                    }
                }
            }
            Row(Modifier , verticalAlignment = Alignment.CenterVertically) {
                LazyRow(modifier = Modifier.weight(4f)) {
                    items(itemNo) { index ->
                        OutlinedTextField(value = theorySlot[index] ,
                            onValueChange = {
                                theorySlot[index] = it.uppercase()
                            } ,
                            label = { Text(text = "Slot ${index + 1}" , fontSize = 10.sp) } ,
                            modifier = Modifier
                                .padding(end = 2.dp)
                                .size(64.dp) ,
                            keyboardOptions = keyOption
                        )
                    }
                }

                //OutlinedTextField(value =  , onValueChange = )
            }
        }
    }

    private fun change(itemNo: Int , init: Int , state: Boolean): Int {
        var itemNo1 = itemNo
        if (itemNo < 4 && !state) {
            itemNo1 += init
        } else {
            itemNo1 -= init
        }
        return itemNo1

    }

    @Composable
    fun Top(deleteAll: () -> Unit = {} , dialogOpen: () -> Unit = {}) {
        Row(
            verticalAlignment = Alignment.CenterVertically ,
            horizontalArrangement = Arrangement.SpaceBetween ,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        ) { IconButton(
                onClick = { Intent(this@MainActivity2 , MainActivity::class.java).also { startActivity(it) } },
            modifier = Modifier
                .weight(2f)
                .padding(end = 6.dp)
            ) {
                Icon(Icons.Default.ArrowBackIosNew , contentDescription = null)
            }

            Text(
                text = stringResource(R.string.slot_info) ,
                modifier = Modifier
                    .weight(8f)
                    .padding(start = 16.dp , top = 5.dp) ,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 25.sp
            )
            IconButton(
                onClick = deleteAll , modifier = Modifier
                    .weight(2f)
                    .padding(end = 6.dp)
            ) {
                Icon(Icons.Default.DeleteSweep , contentDescription = null)
            }
            IconButton(
                onClick = dialogOpen , modifier = Modifier
                    .weight(2f)
                    .padding(end = 16.dp)
            ) {
                Icon(Icons.Default.Add , contentDescription = null)
            }
        }
    }
    @Composable
fun ShowDialog(onDismiss: () -> Unit = {},text:String="",onAccept: () -> Unit = {}){
    AlertDialog(onDismissRequest = onDismiss , confirmButton = {
        TextButton(onClick = onAccept) {
            Text(text = "OK")
        }
    },dismissButton = {
        TextButton(onClick = onDismiss) {
            Text(text = "Cancel")
        }
    }, title = { Text(text = "Please Ensure")  }, text = { Text(text =text ) })
}
    @Composable
    fun MyScreen() {
        val viewModel: AddSlot = viewModel()
        val data = RoomDB.getDataBase(applicationContext)
        val list by data.slotDao().getAll().collectAsState(initial = emptyList())
        var dialogOpen by remember { mutableStateOf(false) }
        var updateOpen by remember { mutableStateOf(false) }
        var alert by remember { mutableStateOf(false) }
        var textOnAlert by remember { mutableStateOf("") }
        val initialState:DataOfSlot by remember {
            mutableStateOf(DataOfSlot("","", listOf("","","",""),"",listOf("","","",""),"",false))
        }
        var currentSelected:DataOfSlot by remember {
            mutableStateOf(DataOfSlot("","", listOf("","","",""),"",listOf("","","",""),"",false))
        }
        Scaffold(modifier = Modifier.fillMaxSize() , topBar = {
            Top(deleteAll = {
                if(list.isNotEmpty()){
                alert = true
                }
                textOnAlert = "Are you sure you want to delete all data?"
            }
            ){

                dialogOpen = true

            }
        }) { innerPadding ->
            Box(Modifier.padding(innerPadding)) {
                Column{
                    SelectedTable()
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(list) {
                        SlotScreen(it) {
                                updateOpen = true
                            viewModel.uploading(it)
                            }

                        }
                    }
                }
                }
            }
            if (dialogOpen) {
                Dialog(viewModel , onSave = {
                    dialogOpen = false
                    val k = viewModel.onSaved()
                    if (k==initialState) {
                        lifecycleScope.launch {
                        data.slotDao().insert(k)
                    }}
                }, onEdit = false) {
                    dialogOpen = false
                }
            }
            if (updateOpen){
                Dialog(viewModel , onSave = {
                    updateOpen = false
                    val k = viewModel.onSaved()
                    lifecycleScope.launch {
                        data.slotDao().delete(currentSelected)
                        data.slotDao().insert(k)
                    }
                }, onEdit = true, onDelete = {
                    updateOpen=false
                    textOnAlert = "Are you sure you want to delete the slot?"
                    alert=true
                    viewModel.cleanUp()
                }) {
                    currentSelected=initialState
                    viewModel.cleanUp()
                    updateOpen= false
                }
            }
        if(alert){
            ShowDialog(onDismiss = {alert=false},textOnAlert){
                if (textOnAlert == "Are you sure you want to delete all data?") {
                    lifecycleScope.launch {
                        data.slotDao().delete(currentSelected)
                        currentSelected=initialState
                    }
                }
                 lifecycleScope.launch {
                    data.slotDao().deleteAll()
                }
                alert=false
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview2() {
        TimeTableTheme {
            MyScreen()
        }
    }
}