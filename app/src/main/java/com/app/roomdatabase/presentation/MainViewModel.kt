package com.app.roomdatabase.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.roomdatabase.data.repository.TodoRepository
import com.app.roomdatabase.domain.model.Todo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: TodoRepository
): ViewModel() {

    var todo by mutableStateOf(Todo(0,"", false))
        private set
    val getAllTodos = repository.getAllTodos()
    private var deletedTodo: Todo? = null

    fun insertTodo(todo: Todo){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTodo(todo = todo)
        }
    }

    fun updateTodo(todo: Todo){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTodo(todo = todo)
        }
    }

    fun deleteTodo(todo: Todo){
        viewModelScope.launch(Dispatchers.IO) {
            deletedTodo = todo
            repository.deleteTodo(todo = todo)
        }
    }

    fun undoDeletedTodo(){
        deletedTodo?.let {todo->
            viewModelScope.launch(Dispatchers.IO) {
                repository.insertTodo(todo = todo)
            }
        }
    }

    fun getTodoById(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            todo = repository.getTodoById(id = id)
        }
    }

    fun updateTask(newValue: String){
        todo = todo.copy(task = newValue)
    }

    fun updateIsImportant(newValue: Boolean){
        todo = todo.copy(isImportant = newValue)
    }

}