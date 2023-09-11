package com.example.test.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.api.ApiException
import com.example.test.model.TimesRecord
import com.example.test.repository.RecordRepository
import kotlinx.coroutines.launch

class MyTimeRecordsViewModel : ViewModel(), RecordViewModel {

    val timeRecordLiveData = MutableLiveData<List<TimesRecord>>(null)
    val msgLiveData = MutableLiveData<String>(null)
    fun loadTimesRecords(account: String) {
        viewModelScope.launch {
            try {
                val records = RecordRepository.loadTimesRecords(account,true)
                timeRecordLiveData.postValue(records)

            } catch (ex: ApiException) {
                msgLiveData.postValue(ex.message)
            }
        }
    }

    override fun updateRecord(record: TimesRecord) {
        super.updateRecord(record)
        viewModelScope.launch {
            try {
                RecordRepository.updateTimeRecords(record)
                msgLiveData.postValue("update success")

            } catch (ex: ApiException) {
                msgLiveData.postValue(ex.message)
            }
        }
    }
}