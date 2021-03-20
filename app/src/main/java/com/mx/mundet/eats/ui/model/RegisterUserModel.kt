package com.mx.mundet.eats.ui.model

import androidx.lifecycle.MutableLiveData

/**
 * Created by Alexander Juárez with Date 18/03/2021
 * @author Alexander Juárez
 */

class RegisterUserModel {

    var name: String? = null
        set(value) {
            field = value?.trim() ?: ""
            userName.value = field
        }

    var description: String? = null
        set(value) {
            field = value?.trim() ?: ""
            userDescription.value = field
        }
    var dateBirth : String?=null
        set(value) {
            field = value?.trim() ?: ""
            userDateBirth.value = field
        }

    var timerBirth : String?=null
        set(value) {
            field = value?.trim() ?: ""
            userTimer.value = field
        }

    val userName = MutableLiveData<String>()
    val userDescription = MutableLiveData<String>()
    val userDateBirth = MutableLiveData<String>()
    val userTimer = MutableLiveData<String>()
}