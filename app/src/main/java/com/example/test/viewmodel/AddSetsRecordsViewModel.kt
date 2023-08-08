package com.example.test.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.api.ApiException
import com.example.test.data.Datasource
import kotlinx.coroutines.launch

class AddSetsRecordsViewModel : ViewModel() {

    val msgLiveData = MutableLiveData<String?>(null)

    fun createSetsRecords(userId: Int, content: String, sets: Int, reps: Int, weight: Float) {
        viewModelScope.launch {
            try {
                Datasource.createSetsRecords(userId, content, sets, reps, weight)
                msgLiveData.postValue("success")

            } catch (ex: ApiException) {
                msgLiveData.postValue(ex.message)
            }

        }
    }
}