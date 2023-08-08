package com.example.test.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.api.ApiException
import com.example.test.data.Datasource
import com.example.test.model.SetsRecord
import kotlinx.coroutines.launch

class MySetsRecordsViewModel : ViewModel() {

    val setsRecordLiveData = MutableLiveData<List<SetsRecord>>(null)
    val msgLiveData = MutableLiveData<String>(null)
    fun loadSetsRecords(account:String) {
        viewModelScope.launch {
            try {
                val records = Datasource.loadSetsRecords(account)
                setsRecordLiveData.postValue(records)

            } catch (ex: ApiException) {
                msgLiveData.postValue(ex.message)
            }
        }
    }

    fun updateSetsRecords(recordId:Int, content:String, sets:Int, reps:Int, weight:Float){
        viewModelScope.launch {
            try {
                Datasource.updateSetsRecords(recordId, content, sets, reps, weight)
                msgLiveData.postValue("update success")

            } catch (ex: ApiException) {
                msgLiveData.postValue(ex.message)
            }
        }
    }
}