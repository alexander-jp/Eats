package com.mx.mundet.eats.ui.mvp.home

import com.mx.mundet.eats.bd.Entity.PersonasEntity
import com.mx.mundet.eats.ui.base.BasePresenter
import com.mx.mundet.eats.ui.base.BaseView


interface HomeContract {
    interface View : BaseView<Presenter>{
        fun resultObtenerListaPersonas(response: List<PersonasEntity>){}
    }
    interface Presenter : BasePresenter<View>{
        fun obtenerListaPersonas(){}
    }
}