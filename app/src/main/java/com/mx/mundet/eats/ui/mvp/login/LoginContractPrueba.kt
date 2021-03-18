package com.mx.mundet.eats.ui.mvp.login

import com.mx.mundet.eats.domain.model.PersonResponseBean
import com.mx.mundet.eats.ui.base.BasePresenter
import com.mx.mundet.eats.ui.base.BaseView

interface LoginContractPrueba {
    interface View {
        fun resultShowToast(msg : String){}
    }

    interface Presenter  {
        fun setView(view: LoginActivity){}
        fun obtenerShowToast(){}
    }

}