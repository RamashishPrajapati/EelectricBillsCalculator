package com.ram.electricbillcalculator.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SlabTable")
data class SlabModel(
    val startUnit: Int,
    val endUnit: Int,
    val rate: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}