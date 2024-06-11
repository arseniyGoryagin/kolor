package com.kolor.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "stats_table")
data class StatEntity (
    @PrimaryKey
    val id : Int = 0,
    var clicks : Int  = 0,
    var coinsSpent : Int =0
)
