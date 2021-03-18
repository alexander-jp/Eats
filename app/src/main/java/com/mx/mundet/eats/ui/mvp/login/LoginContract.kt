package com.mx.mundet.eats.ui.mvp.login

import com.mx.mundet.eats.domain.model.PersonResponseBean
import com.mx.mundet.eats.ui.base.BasePresenter
import com.mx.mundet.eats.ui.base.BaseView

interface LoginContract {

    interface View : BaseView<Presenter> {
        fun resultShowToast(msg : String){}
        fun resultObtenerListaPersonas(response: List<PersonResponseBean>){}
    }

    interface Presenter : BasePresenter<View> {
        fun setView(view: LoginActivity){}
        fun obtenerListaPersonas(){}
        fun obtenerShowToast(){}
    }


}