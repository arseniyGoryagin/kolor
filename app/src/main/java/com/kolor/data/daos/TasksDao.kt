package com.kolor.data.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kolor.data.entities.TaskEntity


@Dao

interface TasksDao {
    @Query("Select * from tasks_table")
    fun getTasksLiveData() : LiveData<List<TaskEntity>>
    @Query("Select * from tasks_table where id = :id")
    suspend fun getTask(id : Int) : TaskEntity


    @Insert()
    suspend fun insertTask(task: TaskEntity)
    @Insert()
    suspend fun insertTasks(tasks : List<TaskEntity>)

    @Query("Update tasks_table set progress = :newProgress where id = :id")
    suspend fun setProgress(id : Int, newProgress : Int)
    @Query("Update tasks_table set progress = progress + :newProgress where id = :id")
    suspend fun updateProgress(id : Int, newProgress : Int)

    @Query("Update tasks_table set collected = :value where id = :id")
    suspend fun setCollected(id : Int, value : Boolean)

    @Query("Select progress from tasks_table where id = :id")
    suspend fun getTaskProgress(id : Int) : Int

    @Query("Select Count(*) from tasks_table")
    suspend fun getCount() : Int

}