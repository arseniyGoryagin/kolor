package com.kolor.data.entities;

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tasks_table")
data class TaskEntity(
    @PrimaryKey val id: Int,
    val reward: Long,
    val name: String,
    val description: String,
    val progress: Int = 0,
    val collected: Boolean = false
)



