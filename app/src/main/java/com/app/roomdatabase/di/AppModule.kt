package com.app.roomdatabase.di

import android.content.Context
import androidx.room.Room
import com.app.roomdatabase.data.local.TodoDao
import com.app.roomdatabase.data.local.TodoDatabase
import com.app.roomdatabase.data.repository.TodoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocalDatabase(@ApplicationContext context: Context): TodoDatabase {
        return Room.databaseBuilder(
            context,
            TodoDatabase::class.java,
            "local_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTodoDao(db: TodoDatabase) : TodoDao =db.todoDao()

    @Provides
    @Singleton
    fun provideTodoRepository(dao: TodoDao): TodoRepository = TodoRepository(dao = dao)

}