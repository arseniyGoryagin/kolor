package com.kolor.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.lang.ref.PhantomReference


@Entity(tableName = "customizations_table")
data class CustomizationEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    var price : Int =1,
    var bought : Boolean = false,
    var name : String,
    val type : Int,
    val isDefault : Boolean = false,

    var referenceId : Int
)