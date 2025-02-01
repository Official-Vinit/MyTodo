package com.example.mytodo.ui.task



import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import com.example.mytodo.R
import com.example.mytodo.model.Task
import kotlin.text.isNotEmpty

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun TaskListScreen(navController: NavController,viewModel: CarListViewModel = viewModel(),modifier: Modifier) {
//
//    val tasks by viewModel.taskList.collectAsStateWithLifecycle()
//    var showDialog by rememberSaveable { mutableStateOf(false) }
//    var task by rememberSaveable { mutableStateOf("") }
//    var taskDesc by rememberSaveable { mutableStateOf("") }
//    var datePickerController by rememberSaveable { mutableStateOf(false) }
//    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
//    val dateState = rememberDatePickerState(
//        yearRange = currentYear..2100,
//    )
//    var selectedDate by remember { mutableStateOf(Date().time) }
//    var timePickerState = rememberTimePickerState(12,12)
//    var timePickerController by rememberSaveable { mutableStateOf(false) }
//    var showAddAndCancelButton by rememberSaveable { mutableStateOf(false) }
//    var expanded by remember { mutableStateOf(false) }
//
//    Column(
//        modifier = Modifier.fillMaxSize()
//    )
//    {
//        Text(
//            text = "ToDos",
//            fontSize = 32.sp,
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier.padding(start = 16.dp, top = 40.dp)
//        )
//        Spacer(modifier = Modifier.padding(16.dp))
//        LazyColumn(
//            verticalArrangement = Arrangement.spacedBy(4.dp),
//        )
//        {
//            items(tasks)
//            {
//                task ->
//                val context = LocalContext.current
//                var checkedItem by remember {
//                    mutableStateOf(false)
//                }
//                Card(modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(start = 16.dp, end = 16.dp, bottom = 4.dp)) {
//                    Column(
//                        modifier = Modifier
//                            .animateContentSize(
//                                animationSpec = spring(
//                                    dampingRatio = Spring.DampingRatioMediumBouncy,
//                                    stiffness = Spring.StiffnessLow
//                                )
//                            )
//                    ) {
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(16.dp),
//                            verticalAlignment = Alignment.CenterVertically,
//                            horizontalArrangement = Arrangement.SpaceBetween
//                        ) {
//                            Checkbox(
//                                checked = checkedItem,
//                                onCheckedChange = { checkedStatus ->
//                                    checkedItem = checkedStatus
//                                    Toast.makeText(
//                                        context,
//                                        "${task.taskName} marked completed",
//                                        Toast.LENGTH_SHORT
//                                    ).show()
//                                },
//                                colors = CheckboxDefaults.colors()
//                            )
//                            Text(
//                                text = task.taskName,
//                                fontSize = 28.sp,
//                                fontWeight = FontWeight.Bold,
//                            )
//                            Spacer(Modifier.weight(1f))
//                            Column {
//                                IconButton(
//                                    onClick = {
//                                    }
//                                ) {
//                                    Icon(
//                                        imageVector = Icons.Default.Delete,
//                                        contentDescription = "Delete",
//                                        tint = Color.Black
//                                    )
//                                }
//                                ExpandedButton(
//                                    expanded = expanded,
//                                    onClick = {
//                                        expanded = !expanded
//                                    }
//                                )
//                                if (expanded){
//                                    Text(text = task.taskDescription)
//                                }
////                                IconButton(
////                                    onClick = {
////                                        expanded = !expanded
////                                    }
////                                ) {
////                                    Icon(
////                                        imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
////                                        contentDescription = "Show more",
////                                        tint = Color.Black
////
////                                    )
////                                }
//                            }
//                        }
//                    }
//                }
//
//            }
//        }
//        Box(
//            modifier = Modifier
//                .fillMaxSize())
//        {
//            FloatingActionButton(
//                onClick = {
//                    showDialog = true
//                },
//                modifier = Modifier
//                    .padding(20.dp)
//            ) {
//                Icon(
//                    imageVector = Icons.Default.Add,
//                    contentDescription = "Add"
//                )
//            }
//        }
//    }
//
//    if (showDialog)
//        {
//            AlertDialog(
//                onDismissRequest = {
//                    showDialog = false
//                },
//                confirmButton = {
//                    Button(onClick = {
//                        if (task.isNotEmpty() && taskDesc.isNotEmpty() && showAddAndCancelButton) {
//                            viewModel.createTask(taskname = task, taskdesc = taskDesc)
//                            showDialog = false
//                            showAddAndCancelButton = true
//                        }
//                    })
//                    {
//                        if (showAddAndCancelButton && task.isNotEmpty() && taskDesc.isNotEmpty()){
//                            Text(text = "Add")
//                        }else{
//                            Text(text = "Add",color = Color.Gray)
//
//                        }
//                    }
//                },
//                dismissButton = {
//                    Button(onClick = {
//                            showDialog = false
//                    }) {
//                        Text(text = "Cancel")
//                    }
//                },
//                title = {
//                    Text(text = "Add new task")
//                },
//
//                text = {
//                    Column() {
//                        OutlinedTextField(
//                            value = task,
//                            onValueChange = {
//                                task = it
//                            },
//                            modifier = Modifier
//                                .padding(start = 8.dp)
//                                .fillMaxWidth(),
//                            placeholder = {
//                                Text(text = "Enter task")
//                            }
//                        )
//                        Spacer(
//                            modifier = Modifier.padding(10.dp)
//                        )
//                        OutlinedTextField(
//                            value = taskDesc,
//                            onValueChange = {
//                                taskDesc = it
//                            },
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(start = 8.dp),
//                            placeholder = {
//                                Text(text = "Enter description")
//                            }
//                        )
//                        Spacer(
//                            modifier = Modifier.padding(10.dp)
//                        )
//                        Row(
//                            horizontalArrangement = Arrangement.SpaceBetween,
//                            verticalAlignment = Alignment.CenterVertically,
//                            modifier = Modifier
//                                    .padding(start = 8.dp)
//                        ) {
//                            Column(
//                                horizontalAlignment = Alignment.CenterHorizontally,
//                                verticalArrangement = Arrangement.Center
//                            ) {
//                                Button(
//                                    onClick = { datePickerController = true },
//                                ) {
//                                    Text(
//                                        text = "Due Date"
//                                    )
//                                }
//                                Text(
//                                    text = convertMillisToDate(selectedDate),
//                                )
//                            }
//                            Spacer(
//                                modifier = Modifier.padding(10.dp)
//                            )
//                            Column(
//                                horizontalAlignment = Alignment.CenterHorizontally,
//                                verticalArrangement = Arrangement.Center
//                            ) {
//                                Button(
//                                    onClick = {
//                                        timePickerController = true
//                                        showAddAndCancelButton = false
//                                    },
//                                ){
//                                    Text(
//                                        text = "Due Time"
//                                    )
//                                }
//                                Text(
//                                    text = "${timePickerState.hour}:${timePickerState.minute}"
//                                )
//                            }
//                        }
//                    }
//                    if (datePickerController){
//                        DatePickerDialog(
//                            onDismissRequest = { datePickerController = false },
//                            confirmButton = {
//                                TextButton(
//                                    onClick = {
//                                        if (dateState.selectedDateMillis != null) {
//                                            selectedDate = dateState.selectedDateMillis!!
//                                        }
//                                        datePickerController = false
//                                    }
//                                ) {
//                                    Text(
//                                        text = "Select"
//                                    )
//                                }
//                            },
//                            dismissButton = {
//                                TextButton(
//                                    onClick = { datePickerController = false }
//                                ) {
//                                    Text(
//                                        text = "Cancel"
//                                    )
//                                }
//                            }
//                        ) {
//                            DatePicker(state = dateState)
//                        }
//                    }
//                    if (timePickerController) {
//                        Box(
//                            modifier = Modifier
//                                .safeContentPadding()
//                                .background(color = MaterialTheme.colorScheme.background)
//                        ) {
//                            Column(){
//                                TimePicker(state = timePickerState)
//                                Row(
//                                    horizontalArrangement = Arrangement.SpaceBetween
//                                ) {
//                                    TextButton(
//                                        onClick = {
//                                            timePickerController = false
//                                            showAddAndCancelButton = true
//                                        }
//                                    ) {
//                                        Text(
//                                            text = "Cancel"
//                                        )
//                                    }
//                                    TextButton(
//                                        onClick = {
//                                            timePickerController = false
//                                            showAddAndCancelButton = true
//                                        }
//                                    ) {
//                                        Text(
//                                            text = "Select"
//                                        )
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            )
//        }
//}




fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat.getDateInstance()
    return formatter.format(Date(millis))
}

//@Composable
//@OptIn(ExperimentalMaterial3Api::class)
//fun addTaskDialog(navController: NavController,viewModel: CarListViewModel = viewModel(), modifier: Modifier = Modifier) {
//    var task by rememberSaveable { mutableStateOf("") }
//    var taskDesc by rememberSaveable { mutableStateOf("") }
//    AlertDialog(
//        onDismissRequest = {
//            showDialog = false
//        },
//        confirmButton = {
//            Button(onClick = { viewModel.createTask() }) {
//                Text(text = "Add")
//            }
//        },
//        dismissButton = {
//            Button(onClick = { }) {
//                Text(text = "Cancel")
//            }
//        },
//        title = {
//            Text(text = "Add new task")
//        },
//
//        text = {
//            Column() {
//                OutlinedTextField(
//                    value = task,
//                    onValueChange = {
//                        task = it
//                    },
//                    modifier = Modifier
//                        .padding(start = 8.dp)
//                        .fillMaxWidth(),
//                    placeholder = {
//                        Text(text = "Enter task")
//                    }
//                )
//                Spacer(
//                    modifier = Modifier.padding(10.dp)
//                )
//                OutlinedTextField(
//                    value = taskDesc,
//                    onValueChange = {
//                        taskDesc = it
//                    },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(start = 8.dp),
//                    placeholder = {
//                        Text(text = "Enter description")
//                    }
//                )
//            }
//        }
//    )
//}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FloatingActionButtonDialog(
    viewModel: CarListViewModel = viewModel(),
    task: List<Task>
){
    var showDialog by remember { mutableStateOf(false) }
    var task by rememberSaveable { mutableStateOf("") }
    var taskDesc by rememberSaveable { mutableStateOf("") }
    var datePickerController by rememberSaveable { mutableStateOf(false) }
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    val dateState = rememberDatePickerState(
        yearRange = currentYear..2100,
    )
    var selectedDate by remember { mutableStateOf(Date().time) }
    var timePickerState = rememberTimePickerState(12,12)
    var timePickerController by rememberSaveable { mutableStateOf(false) }
    var showAddAndCancelButton by rememberSaveable { mutableStateOf(false) }

    FloatingActionButton(
        onClick = {
            showDialog = true
        },
        modifier = Modifier
            .padding(20.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add"
        )
    }

    if (showDialog)
    {
        AlertDialog(
            onDismissRequest = {
                showDialog = false
            },
            confirmButton = {
                Button(onClick = {
                    if (task.isNotEmpty() && taskDesc.isNotEmpty() && showAddAndCancelButton) {
                        viewModel.createTask(taskname = task, taskdesc = taskDesc, dueDate = convertMillisToDate(selectedDate), dueTime = "${timePickerState.hour}:${timePickerState.minute}")
                        showDialog = false
                        showAddAndCancelButton = true
                    }
                })
                {
                    if (showAddAndCancelButton && task.isNotEmpty() && taskDesc.isNotEmpty()){
                        Text(text = "Add")
                    }else{
                        Text(text = "Add",color = Color.Gray)

                    }
                }
            },
            dismissButton = {
                Button(onClick = {
                    showDialog = false
                }) {
                    Text(text = "Cancel")
                }
            },
            title = {
                Text(text = "Add new task")
            },

            text = {
                Column() {
                    OutlinedTextField(
                        value = task,
                        onValueChange = {
                            task = it
                        },
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .fillMaxWidth(),
                        placeholder = {
                            Text(text = "Enter task")
                        }
                    )
                    Spacer(
                        modifier = Modifier.padding(10.dp)
                    )
                    OutlinedTextField(
                        value = taskDesc,
                        onValueChange = {
                            taskDesc = it
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp),
                        placeholder = {
                            Text(text = "Enter description")
                        }
                    )
                    Spacer(
                        modifier = Modifier.padding(10.dp)
                    )
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(start = 8.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Button(
                                onClick = { datePickerController = true },
                            ) {
                                Text(
                                    text = "Due Date"
                                )
                            }
                            Text(
                                text = convertMillisToDate(selectedDate),
                            )
                        }
                        Spacer(
                            modifier = Modifier.padding(10.dp)
                        )
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Button(
                                onClick = {
                                    timePickerController = true
                                    showAddAndCancelButton = false
                                },
                            ){
                                Text(
                                    text = "Due Time"
                                )
                            }
                            Text(
                                text = "${timePickerState.hour}:${timePickerState.minute}"
                            )
                        }
                    }
                }
                if (datePickerController){
                    DatePickerDialog(
                        onDismissRequest = { datePickerController = false },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    if (dateState.selectedDateMillis != null) {
                                        selectedDate = dateState.selectedDateMillis!!
                                    }
                                    datePickerController = false
                                }
                            ) {
                                Text(
                                    text = "Select"
                                )
                            }
                        },
                        dismissButton = {
                            TextButton(
                                onClick = { datePickerController = false }
                            ) {
                                Text(
                                    text = "Cancel"
                                )
                            }
                        }
                    ) {
                        DatePicker(state = dateState)
                    }
                }
                if (timePickerController) {
                    Box(
                        modifier = Modifier
                            .safeContentPadding()
                            .background(color = MaterialTheme.colorScheme.background)
                    ) {
                        Column(){
                            TimePicker(state = timePickerState)
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                TextButton(
                                    onClick = {
                                        timePickerController = false
                                        showAddAndCancelButton = true
                                    }
                                ) {
                                    Text(
                                        text = "Cancel"
                                    )
                                }
                                TextButton(
                                    onClick = {
                                        timePickerController = false
                                        showAddAndCancelButton = true
                                    }
                                ) {
                                    Text(
                                        text = "Select"
                                    )
                                }
                            }
                        }
                    }
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen(viewModel: CarListViewModel= viewModel()) {

    val tasks by viewModel.taskList.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TodoTopAppBar()
        },
        floatingActionButton ={
            FloatingActionButtonDialog(
                task = tasks,
                viewModel = viewModel
            )

        }
    ) { it->
        LazyColumn(contentPadding = it) {
            items(tasks){
                if (tasks.isEmpty()){
                    Text(
                        text = "No task added",
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
                TaskItem(
                    task = it,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

    }
}

@Composable
fun TaskItem(
    task: Task,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                CheckboxIcon(task.taskName)
                TaskInfo(taskname = task.taskName,duedate = task.dueDate,duetime = task.dueTime)
                Spacer(modifier = Modifier.weight(1f))
                ExpandButton(
                    expanded = expanded,
                    onClick = { expanded = !expanded }
                )
                DeleteButton()
            }
            if (expanded) {
                taskDesc(
                    taskDesc = task.taskDescription,modifier = Modifier.padding(
                        start = 16.dp,
                        top = 8.dp,
                        bottom = 16.dp,
                        end = 16.dp
                    )
                )
            }
        }
    }

}

@Composable
fun DeleteButton(){
    IconButton(
        onClick = { /*TODO*/ }
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Delete",
            tint = Color.Black
        )
    }
}

@Composable
fun taskDesc(
    taskDesc: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Description",
            style = MaterialTheme.typography.labelSmall
        )
        Text(
            text = taskDesc,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun ExpandButton(
    expanded: Boolean,
    onClick: ()-> Unit
){
    IconButton(
        onClick = onClick
    ) {
        Icon(
            imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
            contentDescription = "Show more",
            tint = Color.Black
        )
    }
}

@Composable
fun TaskInfo(
    taskname: String,
    duedate: String,
    duetime: String,
    modifier: Modifier = Modifier
) {
    Column {
        Text(
            text = taskname,
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.padding(top = 8.dp)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = duedate,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Text(
                text = duetime,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun CheckboxIcon(
    taskname: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var checkedItem by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    Checkbox(
        checked = checkedItem,
        onCheckedChange = { checkedStatus ->
            checkedItem = checkedStatus
            Toast.makeText(
                context,
                "${taskname} marked completed",
                Toast.LENGTH_SHORT
            ).show()
        },
        colors = CheckboxDefaults.colors()
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoTopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(64.dp)
                        .padding(8.dp),
                    painter = painterResource(R.drawable.group_151),
                    contentDescription = null
                )
                Text(
                    text = "Todo",
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        },
        modifier = modifier
    )
}