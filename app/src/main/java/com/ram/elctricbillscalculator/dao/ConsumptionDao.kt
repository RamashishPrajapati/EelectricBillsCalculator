package com.ram.electricbillcalculator.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ram.electricbillcalculator.model.ConsumptionModel

@Dao
interface ConsumptionDao {

    @Insert
    suspend fun insertConsumptionDetails(consumptionModel: ConsumptionModel): Long

    @Query("SELECT * FROM ConsumptionDetails WHERE serviceNumber = :serviceNumber ORDER BY consumptionUnit DESC LIMIT 3 ")
    fun getConsumptionList(serviceNumber: String): List<ConsumptionModel>
}