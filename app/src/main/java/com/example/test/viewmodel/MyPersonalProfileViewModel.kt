package com.example.test.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.data.Datasource
import com.example.test.model.PersonalProfile
import kotlinx.coroutines.launch

class MyPersonalProfileViewModel : ViewModel() {

    val profileLiveData = MutableLiveData<PersonalProfile?>(null)
    val context = this
    val showErrorDialog = MutableLiveData<String?>(null)
    val msgLiveData = MutableLiveData<String?>(null)

    fun getProfile(account: String) {
        viewModelScope.launch {
            val data = Datasource().getProfile(account)
            if(data.msg.isNullOrEmpty()){
                profileLiveData.postValue(data.profile)
            }else{
                msgLiveData.postValue(data.msg)
            }

        }
    }

    fun updateProfile(account: String, profile: PersonalProfile) {
        viewModelScope.launch {

            val data = Datasource().updateProfile(account, profile)
            if(data!=null){
                profileLiveData.postValue(data)
                msgLiveData.postValue("update success")
            }else{
                msgLiveData.postValue("update failed")
            }

        }
    }
}