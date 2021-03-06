package com.mx.mundet.eats.ui.mvp.login

import com.mx.mundet.eats.bd.Entity.PersonasEntity
import com.mx.mundet.eats.domain.model.PersonResponseBean
import com.mx.mundet.eats.ui.base.BasePresenter
import com.mx.mundet.eats.ui.base.BaseView

interface LoginContract {

    interface View : BaseView<Presenter> {
        fun resultLoginUser(response: Boolean){}
    }

    interface Presenter : BasePresenter<View> {
        fun loginUser(userName : String, password : String){}
    }


}