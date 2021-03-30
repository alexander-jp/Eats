package com.mx.mundet.eats.ui.model

import androidx.lifecycle.MutableLiveData

/**
 * Created by Alexander Juárez with Date 28/03/2021
 * @author Alexander Juárez
 */

class LoginUserModel {

    var email: String? = null
        set(value) {
            field = value?.trim() ?: ""
            emailUser.value = field
        }

    var password: String? = null
        set(value) {
            field = value?.trim() ?: ""
            passwordUser.value = field
        }

    val emailUser = MutableLiveData("")
    val passwordUser = MutableLiveData("")
}