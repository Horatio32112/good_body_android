package com.example.test.viewmodel

import android.widget.Toast
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
    val showToast = MutableLiveData<String?>(null)

    fun getProfile(account: String) {
        viewModelScope.launch {
            val data = Datasource().getProfile(account)
            profileLiveData.postValue(data)
        }
    }

    fun updateProfile(account: String, profile: PersonalProfile) {
        viewModelScope.launch {

            val data = Datasource().updateProfile(account, profile)

            profileLiveData.postValue(data)
        }




    }
}