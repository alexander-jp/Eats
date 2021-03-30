package com.mx.mundet.eats.ui.model

import androidx.lifecycle.MutableLiveData

/**
 * Created by Alexander Juárez with Date 28/03/2021
 * @author Alexander Juárez
 */

class RegisterModel {
    var name: String? = null
        set(value) {
            field = value?.trim() ?: ""
            userName.value = value
        }

    var Ap: String? = null
        set(value) {
            field = value?.trim() ?: ""
            userAp.value = field
        }

    var Am: String? = null
        set(value) {
            field = value?.trim() ?: ""
            userAm.value = field
        }

    var email : String?=null
    set(value) {
        field = value?.trim() ?: ""
        userEmail.value = field
    }

    var password : String?=null
    set(value) {
        field = value?.trim() ?: ""
        userPassword.value = field
    }

    val userName =  MutableLiveData("")
    val userAp = MutableLiveData("")
    var userAm = MutableLiveData("")
    var userEmail = MutableLiveData("")
    var userPassword = MutableLiveData("")
}