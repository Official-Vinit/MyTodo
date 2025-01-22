package com.example.mytodo.ui.task


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
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
import androidx.compose.ui.tooling.preview.Preview
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(navController: NavController,viewModel: CarListViewModel = viewModel(),modifier: Modifier) {

    val tasks by viewModel.taskList.collectAsStateWithLifecycle()
    var showDialog by rememberSaveable { mutableStateOf(false) }
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
    var checkboxState by rememberSaveable { mutableStateMapOf<String,Boolean>().withDefault { false } }


    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "ToDos",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 8.dp, top = 40.dp)
        )
        LazyColumn(
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(tasks) { task ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
//                    Text(
//                        text = task.id.toString(),
//                        fontSize = 28.sp,
//                        fontWeight = FontWeight.Bold
//                    )
//
//                    Text(
//                        text = task.taskName,
//                        fontSize = 28.sp,
//                        fontWeight = FontWeight.Bold
//                    )
                    Card() {
                        Row(){
                            Checkbox(
                                checked = task.completed == true,
                                onCheckedChange = {
                                    checkboxState = !checkboxState
                                }
                            )
                            Text(
                                text = task.taskName,
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete"
                            )
                        }
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.End)
        ){
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
        }
    }
        if (showDialog) {

            AlertDialog(
                onDismissRequest = {
                    showDialog = false
                },
                confirmButton = {
                    Button(onClick = {
                        if (task.isNotEmpty() && taskDesc.isNotEmpty() && showAddAndCancelButton) {
                            viewModel.createTask(taskname = task, taskdesc = taskDesc)
                            showDialog = false
                            showAddAndCancelButton = true
                        }
                    }) {
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