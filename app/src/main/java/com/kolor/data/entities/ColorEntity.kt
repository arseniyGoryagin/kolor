package com.kolor.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "colors_table")
data class ColorEntity(

    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val colorId : Int,
)