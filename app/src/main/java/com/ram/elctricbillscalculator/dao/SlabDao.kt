package com.ram.electricbillcalculator.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ram.electricbillcalculator.model.SlabModel

@Dao
interface SlabDao {

    @Insert
    suspend fun insertSlabDetails(slabModel: SlabModel): Long

    @Query("SELECT * FROM SlabTable")
    fun getSlabDetails(): LiveData<List<SlabModel>>

    @Query("SELECT * FROM SlabTable")
    fun getSlabDetailsList(): List<SlabModel>
}