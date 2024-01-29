package com.app.roomdatabase.screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.roomdatabase.presentation.MainViewModel

@Composable
fun UpdateScreen(
    id: Int,
    mainViewModel: MainViewModel,
    onBack: () -> Unit,
) {
    val task = mainViewModel.todo.task
    val isImportant = mainViewModel.todo.isImportant

    LaunchedEffect(
        key1 = true,
        block = {
            mainViewModel.getTodoById(id = id)
        }
    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Update Todo",
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onBack() }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.size(16.dp))

            Icon(
                imageVector = Icons.Filled.Edit,
                contentDescription = null,
                modifier = Modifier.size(50.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.size(16.dp))
            TextField(
                value = task,
                onValueChange = { newValue ->
                    mainViewModel.updateTask(newValue = newValue)
                },
                modifier = Modifier.fillMaxWidth(.9f),
                label = {
                    Text(
                        text = "Task",
                        fontSize = 18.sp
                    )
                },
                shape = RectangleShape,
                keyboardOptions = KeyboardOptions(
                    KeyboardCapitalization.Sentences
                ),
                textStyle = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal
                )
            )
            Spacer(modifier = Modifier.size(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Important",
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.size(8.dp))
                Checkbox(
                    checked = isImportant,
                    onCheckedChange = { newValue ->
                        mainViewModel.updateIsImportant(newValue = newValue)
                    }
                )
            }
            Spacer(modifier = Modifier.size(8.dp))

            Button(onClick = {
                mainViewModel.updateTodo(mainViewModel.todo)
                onBack()
            }
            ) {
                Text(
                    text = " Save Task",
                    fontSize = 18.sp
                )

            }
        }

    }
}