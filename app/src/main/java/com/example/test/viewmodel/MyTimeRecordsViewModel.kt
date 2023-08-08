package com.example.test.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.api.ApiException
import com.example.test.data.Datasource
import com.example.test.model.TimesRecord
import kotlinx.coroutines.launch

class MyTimeRecordsViewModel : ViewModel() {

    val timeRecordLiveData = MutableLiveData<List<TimesRecord>>(null)
    val msgLiveData = MutableLiveData<String>(null)
    fun loadTimesRecords(account: String) {
        viewModelScope.launch {
            try {
                val records = Datasource.loadTimesRecords(account)
                timeRecordLiveData.postValue(records)

            } catch (ex: ApiException) {
                msgLiveData.postValue(ex.message)
            }
        }
    }

    fun updateTimeRecords(recordId: Int, content: String, duration: Int, distance: Float) {
        viewModelScope.launch {
            try {
                Datasource.updateTimeRecords(recordId, content, duration, distance)
                msgLiveData.postValue("update success")

            } catch (ex: ApiException) {
                msgLiveData.postValue(ex.message)
            }
        }
    }
}