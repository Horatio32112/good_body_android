package com.example.test.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.api.ApiException
import com.example.test.data.Datasource
import com.example.test.model.User
import kotlinx.coroutines.launch


class RegisterViewModel : ViewModel() {
    val msgLiveData = MutableLiveData<String?>(null)
    val idLiveData = MutableLiveData<Int?>(null)

    fun register(user: User) {
        viewModelScope.launch {

            try {
                val userId = Datasource.register(user)
                idLiveData.postValue(userId)
            } catch (ex: ApiException) {
                msgLiveData.postValue(ex.message)
            }
        }
    }
}