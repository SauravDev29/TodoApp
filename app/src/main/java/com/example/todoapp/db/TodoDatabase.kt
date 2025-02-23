package com.example.todoapp.db

import android.content.Context

import androidx.room.Database
import androidx.room.Room
import com.example.todoapp.TodoData

@Database( entities = [ TodoData ::class] , version = 1 )
 abstract class TodoDatabase {

    abstract  fun getTododao() : Tododao


}