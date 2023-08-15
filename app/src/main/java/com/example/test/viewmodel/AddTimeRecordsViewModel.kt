package com.example.test.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.api.ApiException
import com.example.test.repository.RecordRepository
import kotlinx.coroutines.launch

class AddTimeRecordsViewModel : ViewModel() {

    val msgLiveData = MutableLiveData<String?>(null)

    fun createTimeRecords(userId: Int, content: String, duration: Int, distance: Float) {
        viewModelScope.launch {
            try {
                RecordRepository.createTimeRecords(userId, content, duration, distance)
                msgLiveData.postValue("success")

            } catch (ex: ApiException) {
                msgLiveData.postValue(ex.message)
            }

        }
    }
}