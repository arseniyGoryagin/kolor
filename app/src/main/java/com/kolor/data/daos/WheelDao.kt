package com.kolor.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kolor.data.entities.ColorEntity
import com.kolor.data.entities.WheelEntity

@Dao()
interface WheelDao {
    @Insert()
    suspend fun insertWheels(colors : List<WheelEntity>)

    @Insert()
    suspend fun insertWheel(colors : WheelEntity) : Long

    @Query("Select COUNT(*) from colors_table")
    fun getCount() : Int
}