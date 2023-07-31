package com.ram.electricbillcalculator.repository


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ram.electricbillcalculator.dao.ConsumptionDao
import com.ram.electricbillcalculator.model.ConsumptionModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ConsumptionRepository(private val consumptionDao: ConsumptionDao) {

    private var customerConsumptionList = MutableLiveData<List<ConsumptionModel>>()

    /*Adding the data into the database*/
    fun addConsumptionDetailsIntoDb(consumptionModel: ConsumptionModel) {
        CoroutineScope(Dispatchers.Default).launch {
            consumptionDao.insertConsumptionDetails(consumptionModel)
        }
    }

        /*fetching the required data from database*/
    fun getCustomerConsumptionDetails(serviceNumber: String) {
        CoroutineScope(Dispatchers.Default).launch {
            /*as process is going on background thread that's why using postValue to get the data */
            customerConsumptionList.postValue(consumptionDao.getConsumptionList(serviceNumber))
        }
    }

    /*Passing the fetch data from database to view model*/
    fun showCustomerConsumptionList(): LiveData<List<ConsumptionModel>> {
        return customerConsumptionList
    }

}