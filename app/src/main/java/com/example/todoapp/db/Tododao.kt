package com.example.todoapp.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todoapp.TodoData

@Dao
interface Tododao {
@Query("SELECT * FROM TodoData ORDER  BY id DESC")
    fun getAllTodo() : List<List<TodoData>>
    @Insert
    suspend fun insertTodo(todo: TodoData)

    @Update
    suspend fun updateTodo(todo: TodoData)

    @Delete
    suspend fun deleteTodo(todo: TodoData)
}