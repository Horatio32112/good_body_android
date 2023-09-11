package com.example.test.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.api.ApiException
import com.example.test.model.SetsRecord
import com.example.test.repository.RecordRepository
import kotlinx.coroutines.launch

class MySetsRecordsViewModel : ViewModel() , RecordViewModel{

    val setsRecordLiveData = MutableLiveData<List<SetsRecord>>(null)
    val msgLiveData = MutableLiveData<String>(null)
    fun loadSetsRecords(account:String) {
        viewModelScope.launch {
            try {
                val records = RecordRepository.loadSetsRecords(account,true)
                setsRecordLiveData.postValue(records)

            } catch (ex: ApiException) {
                msgLiveData.postValue(ex.message)
            }
        }
    }

    override fun updateRecord(record:SetsRecord) {
        super.updateRecord(record)
        viewModelScope.launch {
            try {
                RecordRepository.updateSetsRecords(record)
                msgLiveData.postValue("update success")

            } catch (ex: ApiException) {
                msgLiveData.postValue(ex.message)
            }
        }
    }

}