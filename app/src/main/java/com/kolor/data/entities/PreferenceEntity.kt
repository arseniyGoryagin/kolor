package com.kolor.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "preferences_table")
data class PreferenceEntity (
    @PrimaryKey()
    val id : Int = 0,
    var soundVolume : Int = 0,
    var theme : Int = 0
)