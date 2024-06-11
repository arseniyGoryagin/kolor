package com.kolor.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey





class Wheel(){






}


@Entity(tableName = "wheel_table")
data class WheelEntity (

    @PrimaryKey(autoGenerate = true)
    val id : Int,

    val progressColorId : Int,
    val backgroundColorId : Int,
    val innerCircleBackgroundId : Int,
){

    companion object {







    }



}