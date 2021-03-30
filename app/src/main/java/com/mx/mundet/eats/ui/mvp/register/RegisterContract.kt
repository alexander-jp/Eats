package com.mx.mundet.eats.ui.mvp.register

import com.mx.mundet.eats.bd.Entity.PersonasEntity
import com.mx.mundet.eats.ui.base.BasePresenter
import com.mx.mundet.eats.ui.base.BaseView
import com.mx.mundet.eats.ui.model.RegisterModel

interface RegisterContract {
    interface View : BaseView<Presenter> {
        fun resultRegisterUser (response : PersonasEntity){
        }
    }

    interface Presenter : BasePresenter<View> {
        fun registerUser (req : PersonasEntity){}
    }
}