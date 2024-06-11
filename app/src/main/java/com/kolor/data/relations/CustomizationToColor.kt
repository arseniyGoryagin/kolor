package com.kolor.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.kolor.data.entities.ColorEntity
import com.kolor.data.entities.CustomizationEntity

data class CustomizationToColor (
    @Embedded()
    val customiaztion : CustomizationEntity,

    @Relation(
        parentColumn = "referenceId",
        entityColumn = "id"
    )
    val color : ColorEntity

)