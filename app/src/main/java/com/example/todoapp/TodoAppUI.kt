package com.example.todoapp

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoAppUI(modifier: Modifier = Modifier) {
    var todoItems by remember { mutableStateOf(emptyList<TodoData>()) }
    var newItemName by remember { mutableStateOf("") }
    var todotime by remember { mutableStateOf("") }
    var newDialogueBox by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Todo App", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 26.sp) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1976D2))
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { newDialogueBox = true },
                containerColor = Color(0xF01976D2)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "add", tint = Color.White)
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            LazyColumn {
                items(todoItems) { todo ->
                    if (todo.isEditing) {
                        EditableToDo(todo = todo) { editedName, editedTime ->
                            todoItems = todoItems.map {
                                if (it.id == todo.id) {
                                    it.copy(name = editedName, time = editedTime, isEditing = false)
                                } else {
                                    it
                                }
                            }
                        }
                    } else {
                        TodoListItem(
                            todo = todo,
                            onEdit = { todoItems = todoItems.map { it.copy(isEditing = it.id == todo.id) } },
                            onDelete = { todoItems = todoItems.filter { it.id != todo.id } }
                        )
                    }
                }
            }

            if (newDialogueBox) {
                AddItemDialogue(
                    todoName = newItemName,
                    todoTime = todotime,
                    onTodoChange = { newItemName = it },
                    onTimeChange = { todotime = it },
                    onDismiss = { newDialogueBox = false },
                    onConfirm = {
                        if (newItemName.isBlank()) {
                            Toast.makeText(context, "Todo name cannot be empty", Toast.LENGTH_SHORT).show()
                        } else {
                            todoItems = todoItems + TodoData(id = todoItems.size + 1, name = newItemName, time = todotime)
                            Toast.makeText(context, "Todo Added", Toast.LENGTH_SHORT).show()
                            newDialogueBox = false
                            newItemName = ""
                            todotime = ""
                        }
                    }
                )
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemDialogue(
    todoName: String,
    todoTime: String,
    onTodoChange: (String) -> Unit,
    onTimeChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Add New Todo",fontWeight = FontWeight.Bold,
                color = androidx.compose.ui.graphics.Color(0xFF1976D2) // Changed title text color to blue
            )
        },
        text = {
            Column {
                OutlinedTextField(
                    value = todoName,
                    onValueChange = onTodoChange,
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Todo Name", color = androidx.compose.ui.graphics.Color(0xFF1976D2)) }, // Changed label text color
                    placeholder = { Text(text = "Enter Todo", color = androidx.compose.ui.graphics.Color.Gray) }, // Changed placeholder color
                    colors = androidx.compose.material3.TextFieldDefaults.outlinedTextFieldColors(
                        focusedTextColor = androidx.compose.ui.graphics.Color(0xFF100E0E),
                        focusedBorderColor = androidx.compose.ui.graphics.Color(0xFF1976D2), // Changed outline color when focused
                        unfocusedBorderColor = androidx.compose.ui.graphics.Color.Gray // Outline color when not focused
                    )
                )



                Spacer(modifier = Modifier.padding(16.dp))

                OutlinedTextField(
                    value = todoTime,
                    onValueChange = onTimeChange,
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Time", color = androidx.compose.ui.graphics.Color(0xFF1976D2)) }, // Changed label text color
                    placeholder = { Text(text = "Enter Time", color = androidx.compose.ui.graphics.Color.Gray) }, // Changed placeholder color
                    colors = androidx.compose.material3.TextFieldDefaults.outlinedTextFieldColors(
                        focusedTextColor = androidx.compose.ui.graphics.Color(0xFF090808), // Set entered text color to Turkish Blue
                        focusedBorderColor = androidx.compose.ui.graphics.Color(0xFF1976D2), // Changed outline color when focused
                        unfocusedBorderColor = androidx.compose.ui.graphics.Color.Gray
                    )
                )

            }
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm,
                colors = androidx.compose.material3.ButtonDefaults.textButtonColors(
                    contentColor = androidx.compose.ui.graphics.Color(0xFF1976D2)
                ),
                modifier = Modifier.padding(8.dp),
            )
            {
                Text(
                    text = "Add",fontWeight = FontWeight.Bold,
                    color = androidx.compose.ui.graphics.Color(0xFF1976D2)// Changed button text color
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                colors = androidx.compose.material3.ButtonDefaults.textButtonColors(
                    contentColor = androidx.compose.ui.graphics.Color(0xFF1976D2)
                ),
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = "Delete", fontWeight = FontWeight.Bold,
                    color = androidx.compose.ui.graphics.Color(0xFF1976D2)
                )
            }
        },
        containerColor = androidx.compose.ui.graphics.Color(0xE9F0FAFF) // Changed dialog background color to blue
    )
}



@Composable
fun TodoListItem(
    todo : TodoData,
    onEdit : (TodoData) -> Unit,
    onDelete : (TodoData) -> Unit
) {
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
    ){
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
            , horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Column {
                Text(text = todo.name, style = MaterialTheme.typography.titleMedium, color = Color.Black)
                Text(text = todo.time , style = MaterialTheme.typography.bodyMedium, color = Color.Black)
            }

            Row {
               IconButton(onClick = { onEdit(todo) }) {
                   Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit", tint = Color(0xFF1976D2))
               }
                IconButton(onClick = { onDelete(todo) }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete",tint = Color(
                        0xFF4CAF50
                    )
                    )
               }
            }

        }
    }
}

@Composable
fun EditableToDo(
    todo : TodoData,
    onEditComplete : (String , String) -> Unit

) {
      var editedName by remember { mutableStateOf(todo.name) }
      var editedTime by remember { mutableStateOf(todo.time.toString()) }


    Card (
        modifier = Modifier.fillMaxSize().padding(8.dp)
    ){
        Column (
            modifier = Modifier.padding(16.dp)
        ){
            OutlinedTextField(
                value = editedName,
                onValueChange = { editedName = it}
                , label = { Text(text = " Todo Name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
       Spacer(modifier = Modifier.padding(16.dp))

            OutlinedTextField(
                value = editedTime,
                onValueChange = { editedTime = it}
                , label = { Text(text = " Time") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
           Button(
               onClick = {
                   onEditComplete(editedName, editedTime )
               }
           ) {
               Text(text = "Update")
           }
        }
    }
}