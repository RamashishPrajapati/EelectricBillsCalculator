package com.ram.electricbillcalculator.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ConsumptionDetails")
data class ConsumptionModel(
    var serviceNumber: String,
    var consumptionUnit: Int,
    var rate: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}