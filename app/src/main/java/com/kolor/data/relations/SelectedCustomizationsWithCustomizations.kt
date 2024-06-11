package com.kolor.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.kolor.data.entities.ColorEntity
import com.kolor.data.entities.SelectedCustomizations
import com.kolor.data.entities.WheelEntity


data class SelectedCustomizationsWithCustomizations (
    @Embedded()
    val selectedCustomizations : SelectedCustomizations,
    @Relation(parentColumn = "wheel", entityColumn = "id")
    var wheel: WheelEntity,
    @Relation(parentColumn = "clickingColor", entityColumn = "id")
    var clickColor : ColorEntity

)