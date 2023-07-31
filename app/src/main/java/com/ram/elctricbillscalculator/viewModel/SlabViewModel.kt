package com.ram.elctricbillscalculator.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.ram.electricbillcalculator.dao.SlabDao
import com.ram.electricbillcalculator.database.ElectricBillDatabase
import com.ram.electricbillcalculator.model.SlabModel
import com.ram.electricbillcalculator.repository.SlabRepository

class SlabViewModel(application: Application) : AndroidViewModel(application) {

    private val slabRepository: SlabRepository
    private val slabDao: SlabDao = ElectricBillDatabase.getDatabase(application).slabDao()
    var currentUnit: Int = 0
    var currentConsumptionRate: Int = 0

    /*initializing the repository*/
    init {
        slabRepository = SlabRepository(slabDao)
    }

    /*getting the requesting data and passing to observer for update */
    val getSlabList = slabDao.getSlabDetails()

    /*passing the data into repository for inserting*/
    fun addSlabDetails(slabModel: SlabModel) {
        slabRepository.addSlabDetailsIntoDb(slabModel)
    }

    /* fetching required data and passing to observer for update */
    fun getSlabDetailsList() {
        slabRepository.getSlabDetailsList()
    }

    /*getting the requesting data and passing to observer for update */
    fun showSlabDetailsList(): LiveData<List<SlabModel>> {
        return slabRepository.showSlabDetailList()
    }

    fun updateCurrentConsumptionRate(
        it: List<SlabModel>,
    ) {
        currentConsumptionRate = 0
        for (slabModel in it) {
            if (currentUnit <= slabModel.endUnit) {
                currentConsumptionRate += slabModel.rate * currentUnit
                break
            } else {
                currentConsumptionRate += slabModel.rate * slabModel.endUnit
                currentUnit -= slabModel.endUnit
            }
        }
    }

}