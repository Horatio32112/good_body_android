package com.example.test.feature.profile.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.model.PersonalProfileData
import com.example.test.repositories.AccountRepository
import kotlinx.coroutines.launch

class MyPersonalProfileViewModel: ViewModel() {

    val profile = MutableLiveData<PersonalProfileData?>(null)

    val showErrorDialog = MutableLiveData<String?>(null)
    val showToast = MutableLiveData<String?>(null)

    private val repository = AccountRepository

    fun getProfile(account: String) {
        viewModelScope.launch {
            val data = repository.getProfile()

            profile.postValue(data)
        }
    }

    fun updateProfile(account: String, , , ,) {
        viewModelScope.launch {
            if (age < 0 ) {
                showErrorDialog.postValue("Age can't be negative")
                return@launch
            }

            val data = repository.updateProfile()

            profile.postValue(data)
        }

//                    val toast = Toast.makeText(context, "Profile Updated", Toast.LENGTH_SHORT)
//                    toast.show()


    }
}