package com.example.test.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.api.ApiException
import com.example.test.data.Datasource
import com.example.test.model.PersonalProfile
import kotlinx.coroutines.launch

class MyPersonalProfileViewModel : ViewModel() {

    val profileLiveData = MutableLiveData<PersonalProfile?>(null)
    val msgLiveData = MutableLiveData<String?>(null)

    fun getProfile(account: String) {
        viewModelScope.launch {

            try {
                val data = Datasource.getProfile(account)
                profileLiveData.postValue(data.profile)
            }catch (ex: ApiException){
                msgLiveData.postValue(ex.message)
            }

        }
    }

    fun updateProfile(account: String, profile: PersonalProfile) {
        viewModelScope.launch {

            try {
                val data = Datasource.updateProfile(account, profile)

                profileLiveData.postValue(data)
                msgLiveData.postValue("update success")

            } catch (ex: ApiException) {
                msgLiveData.postValue(ex.message)
            }

        }
    }
}