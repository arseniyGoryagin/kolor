package com.kolor.data.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kolor.data.entities.PreferenceEntity


@Dao()
interface PreferenceDao {
    @Query("Select * from preferences_table where id = :id")
    fun getPreferencesLiveData(id : Int = 0) : LiveData<PreferenceEntity>
    @Insert()
    suspend fun insertPreferences(prefs : PreferenceEntity)
    @Query("Select COUNT(*) from preferences_table")
    suspend fun getCount() : Int
    @Query("Update preferences_table set soundVolume = :newVolume where id = :id")
    suspend fun setSoundVolume(newVolume : Int, id: Int = 0)
}