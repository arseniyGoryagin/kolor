package com.kolor.data.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kolor.data.entities.GameEntity


@Dao
interface GameDao {
    @Query("Select Count(*) from game_table")
    fun getCount() : Int

    @Query("Select value from game_table where id = :id")
    fun getColumnLiveData(id : Int) : LiveData<Long>

    @Query("Select value from game_table where id = :id")
    suspend fun getColumnValue(id : Int) : Long

    @Query("Update game_table set value = :value where id = :id")
    suspend fun setColumn(id : Int, value : Long)

    @Query("Update game_table set value =  value + :value where id = :id")
    suspend fun updateColumn(id : Int, value : Long)
}
