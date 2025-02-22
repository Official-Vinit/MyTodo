package com.example.mytodo.ui.task

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.mytodo.model.Task
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CarListViewModel : ViewModel() {

    private var _taskList = MutableStateFlow<List<Task>>(emptyList())
    var taskList = _taskList.asStateFlow()
    
    private val db = Firebase.firestore

    init {
        getTaskList()
    }

    private fun getTaskList() {

        db.collection("tasks")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    return@addSnapshotListener
                }

                if (value != null) {
                    _taskList.value = value.toObjects()
                }
            }
    }

    fun createTask(taskname: String, taskdesc: String, dueDate: String, dueTime: String) {
        val task = hashMapOf(
            "id" to 4,
            "taskName" to taskname,
            "taskDescription" to taskdesc,
            "completed" to false,
            "dueDate" to dueDate,
            "dueTime" to dueTime
        )

        db.collection("tasks")
            .add(task)
            .addOnSuccessListener {
                Log.d("document", "ADDED")
            }
    }


    //    fun updateCar() {
//        val car = hashMapOf(
//            "id" to 4,
//            "brand" to "Mazda"
//        )
//        db.collection("cars")
//            .document("uQ9C9PzGXxw1ivXm0zZ4")
//            .set(car)
//            .addOnSuccessListener {
//                Log.d("document", "UPDATED")
//            }
//    }
//
//    fun deleteTask() {
//        db.collection("tasks")
//            .document("abrar")
//            .delete()
//            .addOnSuccessListener {
//                Log.d("document", "DELETED")
//            }
//    }
}

