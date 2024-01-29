package com.app.roomdatabase.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.roomdatabase.presentation.MainViewModel
import com.app.roomdatabase.presentation.common.MySnackBar
import com.app.roomdatabase.screen.components.AlertDialogHome
import com.app.roomdatabase.screen.components.EmptyTaskScreen
import com.app.roomdatabase.screen.components.TodoCard


@Composable
fun HomeScreen(
    mainViewModel: MainViewModel,
    onUpdate: (id: Int) -> Unit,
) {
    val todos by mainViewModel.getAllTodos.collectAsStateWithLifecycle(
        initialValue = emptyList()
    )
    var openDialog by rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Todo- List",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            )

        },
        floatingActionButton = {
            FloatingActionButton(onClick = { openDialog = true }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = null
                )
            }
        }
    ) { paddingValues ->
        AlertDialogHome(
            openDialog = openDialog,
            onClose = { openDialog = false },
            mainViewModel = mainViewModel
        )

        if (todos.isEmpty()) {
            EmptyTaskScreen(paddingValues = paddingValues)
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = todos,
                    key = { it.id }
                ) { todo ->
                    TodoCard(
                        todo = todo,
                        onDone = {
                            mainViewModel.deleteTodo(todo = todo)
                            MySnackBar(
                                scope = scope,
                                snackbarHostState = snackbarHostState,
                                msg = "\"${todo.task}\"",
                                actionLabel = "Undo",
                                onAction = { mainViewModel.undoDeletedTodo() }
                            )
                        },
                        onUpdate = onUpdate
                    )
                }
            }
        }
    }
}