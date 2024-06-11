package com.kolor.data.daos

import androidx.room.Dao
import androidx.room.Insert
import com.kolor.data.entities.BoughtCustomizations


@Dao()
interface BoughtCustomizationsDao {

    @Insert()
    suspend fun insertBoughtCustomizations(bought : BoughtCustomizations)




}