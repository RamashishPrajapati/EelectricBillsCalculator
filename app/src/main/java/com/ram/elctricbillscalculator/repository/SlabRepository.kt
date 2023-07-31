package com.ram.electricbillcalculator.repository


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ram.electricbillcalculator.dao.SlabDao
import com.ram.electricbillcalculator.model.SlabModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SlabRepository(private val slabDao: SlabDao) {

    private var slabDetailsList = MutableLiveData<List<SlabModel>>()

    /*Adding the data into the database*/
    fun addSlabDetailsIntoDb(slabModel: SlabModel) {
        CoroutineScope(Dispatchers.Default).launch {
            slabDao.insertSlabDetails(slabModel)
        }
    }

    /*fetching the required data from database*/
    fun getSlabDetailsList() {
        CoroutineScope(Dispatchers.Default).launch {
            /*as process is going on background thread that's why using postValue to get the data */
            slabDetailsList.postValue(slabDao.getSlabDetailsList())
        }
    }

    /*Passing the fetch data from database to view model*/
    fun showSlabDetailList(): LiveData<List<SlabModel>> {
        return slabDetailsList
    }


}