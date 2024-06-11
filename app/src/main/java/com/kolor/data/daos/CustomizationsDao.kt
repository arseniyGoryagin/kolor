package com.kolor.data.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.kolor.data.entities.CustomizationEntity
import com.kolor.data.relations.CustomizationToColor
import com.kolor.data.relations.CustomizationToWheel


@Dao
interface CustomizationsDao {


    @Query("Select Count(*) from customizations_table")
    fun getCount() : Int

    @Query("Select * from customizations_table")
    fun getCustomizationLiveData() : LiveData<List<CustomizationEntity>>

    @Query("UPDATE customizations_table SET bought = :value where id = :id")
    fun updateCustomizationIsBought(id : Int, value : Boolean)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCustomizations(customizations : List<CustomizationEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCustomization(customizations : CustomizationEntity)


    @Transaction
    @Query("Select * from customizations_table where id = :id")
    suspend fun getColorCustomization(id : Int) : CustomizationToColor

    @Transaction
    @Query("Select * from customizations_table where id = :id")
    suspend fun getWheelCustomization(id : Int): CustomizationToWheel



    // @Query("UPDATE customizations_table set applied = case when id = :id then 1 else 0 end where type = (Select type from customizations_table where id = :id)")
    //suspend fun applyCustomization(id: Int)

}