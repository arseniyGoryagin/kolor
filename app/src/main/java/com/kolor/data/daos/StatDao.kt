package com.kolor.data.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kolor.data.entities.StatEntity


@Dao
interface StatsDao {


    @Query("Select * from stats_table where id = :id")
    fun getStatsLiveData(id : Int = 0) :LiveData<StatEntity>

    @Query("Update stats_table set clicks = clicks + :newClicks where id = :id")
    suspend  fun updateClicks(newClicks : Long, id : Int = 0)
    @Query("Update stats_table set clicks = :newClicks where id = :id")
    suspend fun setClicks(newClicks : Long, id : Int = 0)

    @Query("Update stats_table set coinsSpent = coinsSpent + :value where id = :id")
    suspend fun updateCoinsSpent(value : Long, id : Int = 0)
    @Query("Update stats_table set coinsSpent = :value where id = :id")
    suspend fun setCoinsSpent(value : Long, id : Int = 0)

    @Query("Select COUNT(*) from stats_table")
    suspend fun getCount() : Int

    @Insert()
    suspend fun insertStat(stat : StatEntity)

}