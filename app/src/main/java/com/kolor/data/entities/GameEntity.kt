package com.kolor.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kolor.data.constants.Customizations


@Entity(tableName = "game_table")
data class GameEntity(

    @PrimaryKey
    val id  : Int = 0,
    var value : Long  = 0
)