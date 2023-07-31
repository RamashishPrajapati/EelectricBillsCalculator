package com.ram.electricbillcalculator.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ram.elctricbillscalculator.R
import com.ram.electricbillcalculator.dao.ConsumptionDao
import com.ram.electricbillcalculator.dao.SlabDao
import com.ram.electricbillcalculator.model.ConsumptionModel
import com.ram.electricbillcalculator.model.SlabModel

@Database(
    entities = [SlabModel::class, ConsumptionModel::class],
    version = 1,
    exportSchema = false
)
abstract class ElectricBillDatabase : RoomDatabase() {

    abstract fun slabDao(): SlabDao
    abstract fun consumptionDao(): ConsumptionDao

    companion object {
        @Volatile
        private var INSTANCE: ElectricBillDatabase? = null

        fun getDatabase(
            context: Context,
        ): ElectricBillDatabase {

            synchronized(this)
            {
                var tempInstance = INSTANCE
                if (tempInstance == null) {
                    tempInstance = Room.databaseBuilder(
                        context.applicationContext,
                        ElectricBillDatabase::class.java,
                        context.getString(R.string.databas)
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = tempInstance

                }
                return tempInstance
            }
        }
    }
}