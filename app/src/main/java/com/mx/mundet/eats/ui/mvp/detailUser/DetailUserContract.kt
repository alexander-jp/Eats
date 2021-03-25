package com.mx.mundet.eats.ui.mvp.detailUser

import com.mx.mundet.eats.bd.Entity.PersonasEntity
import com.mx.mundet.eats.ui.base.BasePresenter
import com.mx.mundet.eats.ui.base.BaseView

interface DetailUserContract {
    interface View : BaseView<Presenter> {
        fun resultGetPeople(response : PersonasEntity){}
    }

    interface Presenter : BasePresenter<View> {
        fun getPeople(userId : Int){}
    }
}