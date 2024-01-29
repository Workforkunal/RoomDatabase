package com.app.roomdatabase.screen.components

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Checkbox
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.roomdatabase.domain.model.Todo
import com.app.roomdatabase.presentation.MainViewModel
import kotlinx.coroutines.job

@Composable
fun AlertDialogHome(
    openDialog: Boolean,
    onClose: () -> Unit,
    mainViewModel: MainViewModel,
) {

    var text by remember { mutableStateOf("") }
    var isImportant by remember { mutableStateOf(false) }

    val todo = Todo(0, text, isImportant)
    val focusRequester = FocusRequester()
    val context = LocalContext.current

    if (openDialog) {
        AlertDialog(
            title = {
                Text(
                    text = "Todo",
                )
            },
            text = {
                LaunchedEffect(
                    key1 = true,
                    block = {
                        coroutineContext.job.invokeOnCompletion {
                            focusRequester.requestFocus()
                        }
                    }
                )
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextField(
                        value = text,
                        onValueChange = { text = it },
                        placeholder = {
                            Text(text = "Add Task")
                        },
                        shape = RectangleShape,
                        modifier = Modifier.focusRequester(focusRequester),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                if (text.isNotBlank()) {
                                    mainViewModel.insertTodo(todo = todo)
                                    text = ""
                                    isImportant = false
                                    onClose()
                                } else {
                                    Toast.makeText(context, "Empty Task", Toast.LENGTH_SHORT).show()
                                }
                            }
                        ),
                        trailingIcon = {
                            IconButton(onClick = { text = "" }) {
                                Icon(
                                    imageVector = Icons.Filled.Clear,
                                    contentDescription = null
                                )
                            }
                        },
                        textStyle = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Normal
                        )
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Important",
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.size(8.dp))

                        Checkbox(
                            checked = isImportant,
                            onCheckedChange = { isImportant = it })
                    }

                }
            },
            onDismissRequest = {
                onClose()
                text = ""
                isImportant = false
            },
            confirmButton = {
                Button(onClick = {
                    if (text.isNotBlank()) {
                        mainViewModel.insertTodo(todo = todo)
                        text = ""
                        isImportant = false
                        onClose()
                    } else {
                        Toast.makeText(context, "Empty Task", Toast.LENGTH_SHORT).show()
                    }
                }
                ) {
                    Text(text = "Save")

                }
            },
            dismissButton = {
                OutlinedButton(onClick = {
                    onClose()
                    text = ""
                    isImportant = false
                }
                ) {
                    Text(text = "Close")
                }
            }
        )
    }

}