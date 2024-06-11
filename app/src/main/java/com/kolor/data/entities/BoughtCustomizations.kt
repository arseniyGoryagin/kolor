package com.kolor.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.lang.ref.Reference


@Entity(tableName = "BoughtCustomizations")
data class BoughtCustomizations(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val referenceId : Int
)