package com.kolor.data.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kolor.data.entities.UpgradesEntity


@Dao
interface UpgradesDao {

    //utility
    @Query("Select Count(*) from upgrades_table")
    fun getCount() : Int

    @Query("Select * from upgrades_table")
    fun getUpgradesLiveData() : LiveData<List<UpgradesEntity>>

    @Query("UPDATE upgrades_table SET price = price * :amount where id = :id")
    suspend fun updateUpgradePrice(id : Int, amount : Long)

    @Query("UPDATE upgrades_table SET qty = qty + :amount where id = :id")
    suspend fun updateUpgradeQty(id : Int, amount : Long)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUpgrades(upgrades : List<UpgradesEntity>)

}