package com.dicoding.todoapp.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery

//TODO 2 : Define data access object (DAO)

@Dao
interface TaskDao {
    @RawQuery
    fun getTasks(query: SupportSQLiteQuery): DataSource.Factory<Int, Task>

    @Query("SELECT * FROM Task WHERE id =:taskId")
    fun getTaskById(taskId: Int): LiveData<Task>

    @Query("SELECT * FROM Task WHERE isCompleted=1 ORDER BY dueDateMillis ASC")
    fun getNearestActiveTask(): Task

    @Insert
    suspend fun insertTask(task: Task): Long

    @Insert
    fun insertAll(vararg tasks: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("UPDATE Task SET isCompleted=:completed WHERE id=:taskId")
    suspend fun updateCompleted(taskId: Int, completed: Boolean)
    
}
