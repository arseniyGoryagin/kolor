package com.kolor.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kolor.data.constants.Customizations


@Entity(tableName = "selected_customizations")
data class SelectedCustomizations (
    @PrimaryKey
    val id : Int = 0,
    val clickingColor : Int,
    val wheel : Int,
)