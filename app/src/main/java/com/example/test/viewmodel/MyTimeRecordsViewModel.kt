package com.example.test.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.api.ApiException
import com.example.test.data.Datasource
import com.example.test.model.TimesRecord
import kotlinx.coroutines.launch

class MyTimeRecordsViewModel : ViewModel(), RecordViewModel {

    val timeRecordLiveData = MutableLiveData<List<TimesRecord>>(null)
    val msgLiveData = MutableLiveData<String>(null)
    fun loadTimesRecords(account: String) {
        viewModelScope.launch {
            try {
                val records = Datasource.loadMyTimesRecords(account)
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
                Datasource.updateTimeRecords(record)
                msgLiveData.postValue("update success")

            } catch (ex: ApiException) {
                msgLiveData.postValue(ex.message)
            }
        }
    }
}