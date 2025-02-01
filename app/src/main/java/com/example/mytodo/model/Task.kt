package com.example.mytodo.model

data class Task(
    val id: Int =1,
    val taskName: String = "",
    val taskDescription: String = "",
    val completed: Boolean = false,
    val dueDate: String = "",
    val dueTime: String = ""
)
