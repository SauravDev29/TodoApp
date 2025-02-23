package com.example.todoapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodoData(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val name : String,
    val time : String ,
    var isEditing : Boolean = false,

)
