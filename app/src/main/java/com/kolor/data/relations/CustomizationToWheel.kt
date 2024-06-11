package com.kolor.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.kolor.data.entities.ColorEntity
import com.kolor.data.entities.CustomizationEntity
import com.kolor.data.entities.WheelEntity

data class CustomizationToWheel (
    @Embedded()
    val customiaztion : CustomizationEntity,

    @Relation(
        parentColumn = "referenceId",
        entityColumn = "id"
    )
    val wheel : WheelEntity
)


