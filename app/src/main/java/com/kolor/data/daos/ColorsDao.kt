package com.kolor.data.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kolor.data.entities.ColorEntity


@Dao()
interface ColorsDao {
    @Insert()
    suspend fun insertColors(colors : List<ColorEntity>)

    @Insert()
    suspend fun insertColor(colors : ColorEntity) : Long

    @Query("Select COUNT(*) from colors_table")
    fun getCount() : Int
}