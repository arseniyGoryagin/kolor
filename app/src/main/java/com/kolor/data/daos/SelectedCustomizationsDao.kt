package com.kolor.data.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.kolor.data.entities.SelectedCustomizations
import com.kolor.data.relations.SelectedCustomizationsWithCustomizations


@Dao()
interface SelectedCustomizationsDao {

    @Query("Select * from selected_customizations where id  = :id")
    fun getGameCustomizationsLiveData(id: Int = 0): LiveData<SelectedCustomizations>

    @Query("Update selected_customizations set clickingColor = :color ")
    suspend fun setClickingColor(color : Int)

    @Query("Update selected_customizations set wheel = :wheelId")
    suspend fun setWheel(wheelId : Int)

    @Insert()
    suspend fun insertGameCustomizations(gameCust : SelectedCustomizations)

    @Transaction()
    @Query("Select * from selected_customizations where id = :id")
    fun getSelectedCustomizationsWithCustomizations(id : Int = 1) : LiveData<SelectedCustomizationsWithCustomizations>

    @Query("Select COUNT(*) from selected_customizations")
    fun getCount() : Int
}