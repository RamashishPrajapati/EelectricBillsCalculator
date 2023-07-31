package com.ram.electricbillcalculator.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.ram.electricbillcalculator.dao.ConsumptionDao
import com.ram.electricbillcalculator.database.ElectricBillDatabase
import com.ram.electricbillcalculator.model.ConsumptionModel
import com.ram.electricbillcalculator.repository.ConsumptionRepository

class ConsumptionViewModel(application: Application) : AndroidViewModel(application) {

    private val consumptionRepository: ConsumptionRepository

    private val consumptionDao: ConsumptionDao =
        ElectricBillDatabase.getDatabase(application).consumptionDao()

    /*initializing the repository*/
    init {
        consumptionRepository = ConsumptionRepository(consumptionDao)
    }

    /*passing the data into repository for inserting*/
    fun addSlabDetails(consumptionModel: ConsumptionModel) {
        consumptionRepository.addConsumptionDetailsIntoDb(consumptionModel)
    }

    /* fetching required data and passing to observer for update */
    fun getCustomerConsumptionDetails(serviceNumber: String) {
        consumptionRepository.getCustomerConsumptionDetails(serviceNumber)
    }

    /*getting the requesting data and passing to observer for update */
    fun showCustomerConsumptionDetails(): LiveData<List<ConsumptionModel>> {
        return consumptionRepository.showCustomerConsumptionList()
    }
}