package com.mx.mundet.eats.ui.mvp.registerUser

import com.mx.mundet.eats.bd.Entity.PersonasEntity
import com.mx.mundet.eats.ui.base.BasePresenter
import com.mx.mundet.eats.ui.base.BaseView

interface RegisterUserContract {
    interface View : BaseView<Presenter> {
        fun resultInsertPerson(response: PersonasEntity) {}
    }

    interface Presenter : BasePresenter<View> {
        fun insertPerson(request: PersonasEntity) {}
    }
}