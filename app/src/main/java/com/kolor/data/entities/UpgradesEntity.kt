package com.kolor.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "upgrades_table")
data class UpgradesEntity(
    @PrimaryKey
    val id : Int,
    var price : Long,
    var qty : Int = 0,
    val name : String
)
