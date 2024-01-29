package com.app.roomdatabase.data.repository

import com.app.roomdatabase.data.local.TodoDao
import com.app.roomdatabase.domain.model.Todo
import kotlinx.coroutines.flow.Flow

class TodoRepository(
    private val dao: TodoDao
) {
    suspend fun insertTodo(todo: Todo) = dao.insertTodo(todo = todo)

    suspend fun updateTodo(todo: Todo) = dao.updateTodo(todo = todo)

    suspend fun deleteTodo(todo: Todo) = dao.deleteTodo(todo = todo)

    suspend fun getTodoById(id:Int): Todo = dao.getTodoById(id = id)

    fun getAllTodos(): Flow<List<Todo>> = dao.getAllTodos()

}